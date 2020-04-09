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

package org.apache.jasper.runtime;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspFactory;
import jakarta.servlet.jsp.JspEngineInfo;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.JspApplicationContext;

import org.apache.jasper.Constants;

/**
 * Implementation of JspFactory.
 *
 * @author Anil K. Vijendran
 * @author Kin-man Chung
 */
public class JspFactoryImpl extends JspFactory {

    // Logger
    private static Logger log =
            Logger.getLogger(JspFactoryImpl.class.getName());

    private static final String SPEC_VERSION = "2.1";

    // Pooling PageContextImpl intances are known to leak memories, see
    // https://glassfish.dev.java.net/issues/show_bug.cgi?id=8601
    // So pooling is off by default.  If for any reason, backwards
    // compatibility is required, set the system property to true.
    private static final boolean USE_POOL = 
        Boolean.getBoolean(
            "org.apache.jasper.runtime.JspFactoryImpl.USE_POOL");

    // Per-thread pool of PageContext objects
    private ThreadLocal<LinkedList<PageContext>> pool =
        new ThreadLocal<LinkedList<PageContext>>() {
        protected synchronized LinkedList<PageContext> initialValue() {
            return new LinkedList<PageContext>();
        }
    };
    
    public PageContext getPageContext(Servlet servlet,
				      ServletRequest request,
                                      ServletResponse response,
                                      String errorPageURL,                    
                                      boolean needsSession,
				      int bufferSize,
                                      boolean autoflush) {

	if (Constants.IS_SECURITY_ENABLED) {
	    PrivilegedGetPageContext dp =
                    new PrivilegedGetPageContext(
		        this, servlet, request, response, errorPageURL,
                        needsSession, bufferSize, autoflush);
	    return AccessController.doPrivileged(dp);
	} else {
	    return internalGetPageContext(servlet, request, response,
					  errorPageURL, needsSession,
					  bufferSize, autoflush);
	}
    }

    public void releasePageContext(PageContext pc) {
	if( pc == null )
	    return;
        if (Constants.IS_SECURITY_ENABLED) {
            PrivilegedReleasePageContext dp =
                    new PrivilegedReleasePageContext(this, pc);
            AccessController.doPrivileged(dp);
        } else {
            internalReleasePageContext(pc);
	}
    }

    public JspEngineInfo getEngineInfo() {
        return new JspEngineInfo() {
		public String getSpecificationVersion() {
		    return SPEC_VERSION;
		}
	    };
    }

    public JspApplicationContext getJspApplicationContext
            (ServletContext context) {
        return JspApplicationContextImpl.findJspApplicationContext(context);
    }

    private PageContext internalGetPageContext(Servlet servlet,
					       ServletRequest request,
					       ServletResponse response, 
					       String errorPageURL, 
					       boolean needsSession,
					       int bufferSize, 
					       boolean autoflush) {
        try {
	    PageContext pc = null;
	    if( USE_POOL ) {
                LinkedList<PageContext> pcPool = pool.get();
                if (!pcPool.isEmpty()) {
                    pc = pcPool.removeFirst();
                }
                if (pc == null) {
                    pc = new PageContextImpl(this);
                }
	    } else {
		pc = new PageContextImpl(this);
	    }
	    pc.initialize(servlet, request, response, errorPageURL, 
                          needsSession, bufferSize, autoflush);
            return pc;
        } catch (Throwable ex) {
            /* FIXME: need to do something reasonable here!! */
            log.log(Level.SEVERE, "Exception initializing page context", ex);
            return null;
        }
    }

    private void internalReleasePageContext(PageContext pc) {
        pc.release();
	if (USE_POOL && (pc instanceof PageContextImpl)) {
            LinkedList<PageContext> pcPool = pool.get();
            pcPool.addFirst(pc);
	}
    }

    private class PrivilegedGetPageContext
            implements PrivilegedAction<PageContext> {

	private JspFactoryImpl factory;
	private Servlet servlet;
	private ServletRequest request;
	private ServletResponse response;
	private String errorPageURL;
	private boolean needsSession;
	private int bufferSize;
	private boolean autoflush;

	PrivilegedGetPageContext(JspFactoryImpl factory,
				 Servlet servlet,
				 ServletRequest request,
				 ServletResponse response,
				 String errorPageURL,
				 boolean needsSession,
				 int bufferSize,
				 boolean autoflush) {
	    this.factory = factory;
	    this.servlet = servlet;
	    this.request = request;
	    this.response = response;
	    this.errorPageURL = errorPageURL;
	    this.needsSession = needsSession;
	    this.bufferSize = bufferSize;
	    this.autoflush = autoflush;
	}
 
	public PageContext run() {
	    return factory.internalGetPageContext(servlet,
						  request,
						  response,
						  errorPageURL,
						  needsSession,
						  bufferSize,
						  autoflush);
	}
    }

    private class PrivilegedReleasePageContext
            implements PrivilegedAction<Object> {

        private JspFactoryImpl factory;
	private PageContext pageContext;

        PrivilegedReleasePageContext(JspFactoryImpl factory,
				     PageContext pageContext) {
            this.factory = factory;
            this.pageContext = pageContext;
        }

        public Object run() {
            factory.internalReleasePageContext(pageContext);
	    return null;
        }
    }
}
