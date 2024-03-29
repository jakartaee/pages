<%--

    Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@ page import="ee.jakarta.tck.pages.common.util.JspTestUtil,
             jakarta.el.ValueExpression,
             jakarta.el.ELContext"%>
<%@ page contentType="text/plain" %>

<%!
private static final String VARIABLE_EXPR = "${foo}";
private static final String EXPECTED_VALUE = "bar";
%>

<% 
    if (pageContext != null) {
        ELContext elContext = pageContext.getELContext();

        if (elContext != null) {
            JspApplicationContext jaContext = 
                    JspFactory.getDefaultFactory().getJspApplicationContext(
                    pageContext.getServletContext());
            if (jaContext != null) {
                
                try {
                    ValueExpression vexp = 
                            jaContext.getExpressionFactory().createValueExpression(
                            elContext, VARIABLE_EXPR, VARIABLE_EXPR.getClass());

                    if (vexp != null) {
                
                        vexp.setValue(elContext, EXPECTED_VALUE);
                        String name = (String) vexp.getValue(elContext);

                        if (name.equals(EXPECTED_VALUE)) 
                            out.println("Test PASSED");
                        else {
                            out.println("Test FAILED. Expected value = " + EXPECTED_VALUE);
                            out.println("Computed value = " + name); 
                        }
                    } 
                    else 
                        out.println("Test FAILED. Null value returned for expression.");
                 } catch (Throwable t) {
                    JspTestUtil.handleThrowable(t, out, "CreateValueExpressionTest");
                 }
            }
            else
                out.println("Test FAILED. Unable to obtain JspApplicationContext");
        }
        else
            out.println("Test FAILED. Unable to obtain ELContext");
    } 
    else 
        out.println("Test FAILED.  Unable to obtain PageContext.");
%>
