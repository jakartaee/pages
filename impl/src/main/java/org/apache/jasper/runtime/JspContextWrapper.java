/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import jakarta.el.ELContext;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.BodyContent;
import jakarta.servlet.jsp.tagext.VariableInfo;
import jakarta.servlet.jsp.el.VariableResolver;
import jakarta.servlet.jsp.el.ExpressionEvaluator;

import org.apache.jasper.compiler.Localizer;

/**
 * Implementation of a JSP Context Wrapper.
 *
 * The JSP Context Wrapper is a JspContext created and maintained by a tag handler implementation. It wraps the Invoking
 * JSP Context, that is, the JspContext instance passed to the tag handler by the invoking page via setJspContext().
 *
 * @author Kin-man Chung
 * @author Jan Luehe
 */
public class JspContextWrapper extends PageContext {

    // Invoking JSP context
    private PageContext invokingJspCtxt;

    private Hashtable<String, Object> pageAttributes;

    // ArrayList of NESTED scripting variables
    private ArrayList<String> nestedVars;

    // ArrayList of AT_BEGIN scripting variables
    private ArrayList<String> atBeginVars;

    // ArrayList of AT_END scripting variables
    private ArrayList<String> atEndVars;

    private Map<String, String> aliases;
    private HashMap<String, Object> originalNestedVars;
    private ELContext elContext;

    public JspContextWrapper(JspContext jspContext, ArrayList<String> nestedVars, ArrayList<String> atBeginVars, ArrayList<String> atEndVars,
            Map<String, String> aliases) {
        this.invokingJspCtxt = (PageContext) jspContext;
        this.nestedVars = nestedVars;
        this.atBeginVars = atBeginVars;
        this.atEndVars = atEndVars;
        this.pageAttributes = new Hashtable<String, Object>(16);
        this.aliases = aliases;

        if (nestedVars != null) {
            this.originalNestedVars = new HashMap<String, Object>(nestedVars.size());
        }
        syncBeginTagFile();
    }

    public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, String errorPageURL, boolean needsSession, int bufferSize,
            boolean autoFlush) throws IOException, IllegalStateException, IllegalArgumentException {
    }

    public Object getAttribute(String name) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        return pageAttributes.get(name);
    }

    public Object getAttribute(String name, int scope) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        if (scope == PAGE_SCOPE) {
            return pageAttributes.get(name);
        }

        return invokingJspCtxt.getAttribute(name, scope);
    }

    public void setAttribute(String name, Object value) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        if (value != null) {
            pageAttributes.put(name, value);
        } else {
            removeAttribute(name, PAGE_SCOPE);
        }
    }

    public void setAttribute(String name, Object value, int scope) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        if (scope == PAGE_SCOPE) {
            if (value != null) {
                pageAttributes.put(name, value);
            } else {
                removeAttribute(name, PAGE_SCOPE);
            }
        } else {
            invokingJspCtxt.setAttribute(name, value, scope);
        }
    }

    public Object findAttribute(String name) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        Object o = pageAttributes.get(name);
        if (o == null) {
            o = invokingJspCtxt.getAttribute(name, REQUEST_SCOPE);
            if (o == null) {
                if (getSession() != null) {
                    o = invokingJspCtxt.getAttribute(name, SESSION_SCOPE);
                }
                if (o == null) {
                    o = invokingJspCtxt.getAttribute(name, APPLICATION_SCOPE);
                }
            }
        }

        return o;
    }

    public void removeAttribute(String name) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        pageAttributes.remove(name);
        invokingJspCtxt.removeAttribute(name, REQUEST_SCOPE);
        if (getSession() != null) {
            invokingJspCtxt.removeAttribute(name, SESSION_SCOPE);
        }
        invokingJspCtxt.removeAttribute(name, APPLICATION_SCOPE);
    }

    public void removeAttribute(String name, int scope) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        if (scope == PAGE_SCOPE) {
            pageAttributes.remove(name);
        } else {
            invokingJspCtxt.removeAttribute(name, scope);
        }
    }

    public int getAttributesScope(String name) {

        if (name == null) {
            throw new NullPointerException(Localizer.getMessage("jsp.error.attribute.null_name"));
        }

        if (pageAttributes.get(name) != null) {
            return PAGE_SCOPE;
        } else {
            return invokingJspCtxt.getAttributesScope(name);
        }
    }

    public Enumeration<String> getAttributeNamesInScope(int scope) {
        if (scope == PAGE_SCOPE) {
            return pageAttributes.keys();
        }

        return invokingJspCtxt.getAttributeNamesInScope(scope);
    }

    public void release() {
        invokingJspCtxt.release();
    }

    public JspWriter getOut() {
        return invokingJspCtxt.getOut();
    }

    public HttpSession getSession() {
        return invokingJspCtxt.getSession();
    }

    public Object getPage() {
        return invokingJspCtxt.getPage();
    }

    public ServletRequest getRequest() {
        return invokingJspCtxt.getRequest();
    }

    public ServletResponse getResponse() {
        return invokingJspCtxt.getResponse();
    }

    public Exception getException() {
        return invokingJspCtxt.getException();
    }

    public ServletConfig getServletConfig() {
        return invokingJspCtxt.getServletConfig();
    }

    public ServletContext getServletContext() {
        return invokingJspCtxt.getServletContext();
    }

    static public PageContext getRootPageContext(PageContext pc) {
        while (pc instanceof JspContextWrapper) {
            pc = ((JspContextWrapper) pc).invokingJspCtxt;
        }
        return pc;
    }

    public ELContext getELContext() {
        if (elContext == null) {
            PageContext pc = invokingJspCtxt;
            while (pc instanceof JspContextWrapper) {
                pc = ((JspContextWrapper) pc).invokingJspCtxt;
            }
            PageContextImpl pci = (PageContextImpl) pc;
            elContext = pci.getJspApplicationContext().createELContext(invokingJspCtxt.getELContext().getELResolver());
            elContext.putContext(jakarta.servlet.jsp.JspContext.class, this);
            ((ELContextImpl) elContext).setVariableMapper(new VariableMapperImpl());
        }
        return elContext;
    }

    public void forward(String relativeUrlPath) throws ServletException, IOException {
        invokingJspCtxt.forward(relativeUrlPath);
    }

    public void include(String relativeUrlPath) throws ServletException, IOException {
        invokingJspCtxt.include(relativeUrlPath);
    }

    public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
        invokingJspCtxt.include(relativeUrlPath, flush);
    }

    public VariableResolver getVariableResolver() {
        return null;
    }

    public BodyContent pushBody() {
        return invokingJspCtxt.pushBody();
    }

    public JspWriter pushBody(Writer writer) {
        return invokingJspCtxt.pushBody(writer);
    }

    public JspWriter popBody() {
        return invokingJspCtxt.popBody();
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return invokingJspCtxt.getExpressionEvaluator();
    }

    public void handlePageException(Exception ex) throws IOException, ServletException {
        // Should never be called since handleException() called with a
        // Throwable in the generated servlet.
        handlePageException((Throwable) ex);
    }

    public void handlePageException(Throwable t) throws IOException, ServletException {
        invokingJspCtxt.handlePageException(t);
    }

    /**
     * Synchronize variables at begin of tag file
     */
    public void syncBeginTagFile() {
        saveNestedVariables();
    }

    /**
     * Synchronize variables before fragment invokation
     */
    public void syncBeforeInvoke() {
        copyTagToPageScope(VariableInfo.NESTED);
        copyTagToPageScope(VariableInfo.AT_BEGIN);
    }

    /**
     * Synchronize variables at end of tag file
     */
    public void syncEndTagFile() {
        copyTagToPageScope(VariableInfo.AT_BEGIN);
        copyTagToPageScope(VariableInfo.AT_END);
        restoreNestedVariables();
    }

    /**
     * Copies the variables of the given scope from the virtual page scope of this JSP context wrapper to the page scope of
     * the invoking JSP context.
     *
     * @param scope variable scope (one of NESTED, AT_BEGIN, or AT_END)
     */
    private void copyTagToPageScope(int scope) {
        Iterator<String> iter = null;

        switch (scope) {
        case VariableInfo.NESTED:
            if (nestedVars != null) {
                iter = nestedVars.iterator();
            }
            break;
        case VariableInfo.AT_BEGIN:
            if (atBeginVars != null) {
                iter = atBeginVars.iterator();
            }
            break;
        case VariableInfo.AT_END:
            if (atEndVars != null) {
                iter = atEndVars.iterator();
            }
            break;
        }

        while ((iter != null) && iter.hasNext()) {
            String varName = iter.next();
            Object obj = getAttribute(varName);
            varName = findAlias(varName);
            if (obj != null) {
                invokingJspCtxt.setAttribute(varName, obj);
            } else {
                invokingJspCtxt.removeAttribute(varName, PAGE_SCOPE);
            }
        }
    }

    /**
     * Saves the values of any NESTED variables that are present in the invoking JSP context, so they can later be restored.
     */
    private void saveNestedVariables() {
        if (nestedVars != null) {
            Iterator<String> iter = nestedVars.iterator();
            while (iter.hasNext()) {
                String varName = iter.next();
                varName = findAlias(varName);
                Object obj = invokingJspCtxt.getAttribute(varName);
                if (obj != null) {
                    originalNestedVars.put(varName, obj);
                }
            }
        }
    }

    /**
     * Restores the values of any NESTED variables in the invoking JSP context.
     */
    private void restoreNestedVariables() {
        if (nestedVars != null) {
            Iterator<String> iter = nestedVars.iterator();
            while (iter.hasNext()) {
                String varName = iter.next();
                varName = findAlias(varName);
                Object obj = originalNestedVars.get(varName);
                if (obj != null) {
                    invokingJspCtxt.setAttribute(varName, obj);
                } else {
                    invokingJspCtxt.removeAttribute(varName, PAGE_SCOPE);
                }
            }
        }
    }

    /**
     * Checks to see if the given variable name is used as an alias, and if so, returns the variable name for which it is
     * used as an alias.
     *
     * @param varName The variable name to check
     * @return The variable name for which varName is used as an alias, or varName if it is not being used as an alias
     */
    private String findAlias(String varName) {

        if (aliases == null)
            return varName;

        String alias = aliases.get(varName);
        if (alias == null) {
            return varName;
        }
        return alias;
    }
}
