/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.tagext.TagLibraryInfo;
import org.apache.jasper.Constants;
import org.apache.jasper.EmbeddedServletOptions;
import org.apache.jasper.Options;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.apache.jasper.compiler.JspUtil;
import org.apache.jasper.compiler.Localizer;
import org.apache.jasper.runtime.JspApplicationContextImpl;
import org.glassfish.jsp.api.JspProbeEmitter;

/**
 * The JSP engine (a.k.a Jasper).
 *
 * The servlet container is responsible for providing a
 * URLClassLoader for the web application context Jasper
 * is being used in. Jasper will try get the Tomcat
 * ServletContext attribute for its ServletContext class
 * loader, if that fails, it uses the parent class loader.
 * In either case, it must be a URLClassLoader.
 *
 * @author Anil K. Vijendran
 * @author Harish Prabandham
 * @author Remy Maucherat
 * @author Kin-man Chung
 * @author Glenn Nielsen
 */
public class JspServlet extends HttpServlet {

    // Logger
    private static Logger log = Logger.getLogger(JspServlet.class.getName());

    private static final int CHAR_LIMIT = 256;

    private ServletContext context;
    private ServletConfig config;
    private Options options;
    private JspRuntimeContext rctxt;

    // START S1AS
    // jsp error count
    private AtomicInteger countErrors = new AtomicInteger(0);
    // END S1AS

    // START SJSWS 6232180
    private String httpMethodsString = null;
    private HashSet<String> httpMethodsSet = null;
    // END SJSWS 6232180

    // START GlassFish 750
    private ConcurrentHashMap<String, TagLibraryInfo> taglibs;
    private ConcurrentHashMap<String, URL> tagFileJarUrls;
    // END GlassFish 750

    private JspProbeEmitter jspProbeEmitter;

    /*
     * Initializes this JspServlet.
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        this.config = config;
        this.context = config.getServletContext();

        // Initialize the JSP Runtime Context
        options = new EmbeddedServletOptions(config, context);
        rctxt = new JspRuntimeContext(context,options);

        // START SJSWS 6232180
        // Determine which HTTP methods to service ("*" means all)
        httpMethodsString = config.getInitParameter("httpMethods");
        if (httpMethodsString != null
                && !httpMethodsString.equals("*")) {
            httpMethodsSet = new HashSet<String>();
            StringTokenizer tokenizer = new StringTokenizer(
                    httpMethodsString, ", \t\n\r\f");
            while (tokenizer.hasMoreTokens()) {
                httpMethodsSet.add(tokenizer.nextToken());
            }
        }
        // END SJSWS 6232180

        // START GlassFish 750
        taglibs = new ConcurrentHashMap<String, TagLibraryInfo>();
        context.setAttribute(Constants.JSP_TAGLIBRARY_CACHE, taglibs);

        tagFileJarUrls = new ConcurrentHashMap<String, URL>();
        context.setAttribute(Constants.JSP_TAGFILE_JAR_URLS_CACHE, 
                             tagFileJarUrls);
        // END GlassFish 750

        if (log.isLoggable(Level.FINEST)) {
            log.finest(Localizer.getMessage("jsp.message.scratch.dir.is",
                                           options.getScratchDir().toString()));
            log.finest(Localizer.getMessage("jsp.message.dont.modify.servlets"));
        }

        this.jspProbeEmitter = (JspProbeEmitter)
            config.getServletContext().getAttribute(
                "org.glassfish.jsp.monitor.probeEmitter");
    }


    /**
     * Returns the number of JSPs for which JspServletWrappers exist, i.e.,
     * the number of JSPs that have been loaded into the webapp with which
     * this JspServlet is associated.
     *
     * <p>This info may be used for monitoring purposes.
     *
     * @return The number of JSPs that have been loaded into the webapp with
     * which this JspServlet is associated
     */
    public int getJspCount() {
        return this.rctxt.getJspCount();
    }


    /**
     * Resets the JSP reload counter.
     *
     * @param count Value to which to reset the JSP reload counter
     */
    public void setJspReloadCount(int count) {
        this.rctxt.setJspReloadCount(count);
    }


    /**
     * Gets the number of JSPs that have been reloaded.
     *
     * <p>This info may be used for monitoring purposes.
     *
     * @return The number of JSPs (in the webapp with which this JspServlet is
     * associated) that have been reloaded
     */
    public int getJspReloadCount() {
        return this.rctxt.getJspReloadCount();
    }


    // START S1AS
    /**
     * Gets the number of errors triggered by JSP invocations.
     *
     * @return The number of errors triggered by JSP invocations
     */
    public int getJspErrorCount() {
        return countErrors.get();
    }
    // END S1AS


    /**
     * <p>Look for a <em>precompilation request</em> as described in
     * Section 8.4.2 of the JSP 1.2 Specification.  <strong>WARNING</strong> -
     * we cannot use <code>request.getParameter()</code> for this, because
     * that will trigger parsing all of the request parameters, and not give
     * a servlet the opportunity to call
     * <code>request.setCharacterEncoding()</code> first.</p>
     *
     * @param request The servlet requset we are processing
     *
     * @exception ServletException if an invalid parameter value for the
     *  <code>jsp_precompile</code> parameter name is specified
     */
    boolean preCompile(HttpServletRequest request) throws ServletException {

        String queryString = request.getQueryString();
        if (queryString == null) {
            return (false);
        }
        int start = queryString.indexOf(Constants.PRECOMPILE);
        if (start < 0) {
            return (false);
        }
        queryString =
            queryString.substring(start + Constants.PRECOMPILE.length());
        if (queryString.length() == 0) {
            return (true);             // ?jsp_precompile
        }
        if (queryString.startsWith("&")) {
            return (true);             // ?jsp_precompile&foo=bar...
        }
        if (!queryString.startsWith("=")) {
            return (false);            // part of some other name or value
        }
        int limit = queryString.length();
        int ampersand = queryString.indexOf("&");
        if (ampersand > 0) {
            limit = ampersand;
        }
        String value = queryString.substring(1, limit);
        if (value.equals("true")) {
            return (true);             // ?jsp_precompile=true
        } else if (value.equals("false")) {
	    // Spec says if jsp_precompile=false, the request should not
	    // be delivered to the JSP page; the easiest way to implement
	    // this is to set the flag to true, and precompile the page anyway.
	    // This still conforms to the spec, since it says the
	    // precompilation request can be ignored.
            return (true);             // ?jsp_precompile=false
        } else {
            throw new ServletException("Cannot have request parameter " +
                                       Constants.PRECOMPILE + " set to " +
                                       value);
        }

    }
    

    public void service(HttpServletRequest request, 
    			HttpServletResponse response)
                throws ServletException, IOException {

        // START SJSWS 6232180
        if (httpMethodsSet != null) {
            String method = request.getMethod();
            if (method == null) {
                return;
            }
            if (!httpMethodsSet.contains(method)) {
                super.service(request, response);
                return;
            }
            if (method.equals("OPTIONS")) {
                response.addHeader("Allow", httpMethodsString);
                return;
            }
        }
        // END SJSWS 6232180

        String jspUri = null;

        String jspFile = (String) request.getAttribute(Constants.JSP_FILE);
        if (jspFile != null) {
            // JSP is specified via <jsp-file> in <servlet> declaration
            jspUri = jspFile;
            request.removeAttribute(Constants.JSP_FILE);
        } else {
            /*
             * Check to see if the requested JSP has been the target of a
             * RequestDispatcher.include()
             */
            jspUri = (String) request.getAttribute(Constants.INC_SERVLET_PATH);
            if (jspUri != null) {
                /*
		 * Requested JSP has been target of
                 * RequestDispatcher.include(). Its path is assembled from the
                 * relevant jakarta.servlet.include.* request attributes
                 */
                String pathInfo = (String) request.getAttribute(
                                    "jakarta.servlet.include.path_info");
                if (pathInfo != null) {
                    jspUri += pathInfo;
                }
            } else {
                /*
                 * Requested JSP has not been the target of a 
                 * RequestDispatcher.include(). Reconstruct its path from the
                 * request's getServletPath() and getPathInfo()
                 */
                jspUri = request.getServletPath();
                String pathInfo = request.getPathInfo();
                if (pathInfo != null) {
                    jspUri += pathInfo;
                }
            }
        }

        if (log.isLoggable(Level.FINE)) {	    
            StringBuilder msg = new StringBuilder();
            msg.append("JspEngine --> [" + jspUri);
            msg.append("] ServletPath: [" + request.getServletPath());
            msg.append("] PathInfo: [" + request.getPathInfo());
            msg.append("] RealPath: [" + context.getRealPath(jspUri));
            msg.append("] RequestURI: [" + request.getRequestURI());
            msg.append("] QueryString: [" + request.getQueryString());
            msg.append("]");
            log.fine(msg.toString());
        }

        try {
            boolean precompile = preCompile(request);
            serviceJspFile(request, response, jspUri, null, precompile);
        } catch (RuntimeException e) {
            // STARTS S1AS
            incrementErrorCount(jspUri);
            // END S1AS
            throw e;
        } catch (Error e) {
            incrementErrorCount(jspUri);
            throw e;
        } catch (ServletException e) {
            // STARTS S1AS
            incrementErrorCount(jspUri);
            // END S1AS
            throw e;
        } catch (IOException e) {
            // STARTS S1AS
            incrementErrorCount(jspUri);
            // END S1AS
            throw e;
        } catch (Throwable e) {
            // STARTS S1AS
            incrementErrorCount(jspUri);
            // END S1AS
            throw new ServletException(e);
        }

    }

    public void destroy() {
        if (log.isLoggable(Level.FINE)) {
            log.fine("JspServlet.destroy()");
        }

        rctxt.destroy();
        JspApplicationContextImpl.removeJspApplicationContext(context);

        // START GlassFish 750
        taglibs.clear();
        tagFileJarUrls.clear();
        // END GlassFish 750

        // START GlassFish 747
        HashMap tldUriToLocationMap = (HashMap) context.getAttribute(
            Constants.JSP_TLD_URI_TO_LOCATION_MAP);
        if (tldUriToLocationMap != null) {
            tldUriToLocationMap.clear();
        }
        // END GlassFish 747
    }


    // -------------------------------------------------------- Private Methods

    private void serviceJspFile(HttpServletRequest request,
                                HttpServletResponse response, String jspUri,
                                Throwable exception, boolean precompile)
        throws ServletException, IOException {

        JspServletWrapper wrapper =
            (JspServletWrapper) rctxt.getWrapper(jspUri);
        if (wrapper == null) {
            synchronized(this) {
                wrapper = (JspServletWrapper) rctxt.getWrapper(jspUri);
                if (wrapper == null) {
                    // Check if the requested JSP page exists, to avoid
                    // creating unnecessary directories and files.
                    if (null == context.getResource(jspUri)
                            && !options.getUsePrecompiled()) {
                        String includeRequestUri = (String) 
                            request.getAttribute("jakarta.servlet.include.request_uri");
                        if (includeRequestUri != null) {
                            // Missing JSP resource has been the target of a
                            // RequestDispatcher.include().
                            // Throw an exception (rather than returning a 
                            // 404 response error code), because any call to
                            // response.sendError() must be ignored by the
                            // servlet engine when issued from within an
                            // included resource (as per the Servlet spec).
                            throw new FileNotFoundException(
                                JspUtil.escapeXml(jspUri));
                        }

                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        log.severe(Localizer.getMessage(
                            "jsp.error.file.not.found",
                            context.getRealPath(jspUri)));
                        return;
                    }
                    boolean isErrorPage = exception != null;
                    wrapper = new JspServletWrapper(config, options, jspUri,
                                                    isErrorPage, rctxt);
                    rctxt.addWrapper(jspUri,wrapper);
                }
            }
        }

        wrapper.service(request, response, precompile);

    }


    // STARTS S1AS
    private void incrementErrorCount(String jspUri) {
        countErrors.incrementAndGet();
        // Fire the jspErrorEvent probe event
        if (jspProbeEmitter != null) {
            jspProbeEmitter.jspErrorEvent(jspUri);
        }
    }
    // END S1AS
}
