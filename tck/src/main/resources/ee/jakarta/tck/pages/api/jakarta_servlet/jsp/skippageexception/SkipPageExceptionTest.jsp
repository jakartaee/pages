<%--

    Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.

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
                 java.io.IOException" %>
<%@ page contentType="text/plain" %>

<%-- Begin test definitions --%>

<%!
    public void skipPageExceptionDefaultCtorTest(HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 JspWriter out)
    throws ServletException, IOException {
        SkipPageException spe = new SkipPageException();
        if (spe != null) {
            out.println("Test PASSED");
        } else {
            out.println("Test FAILED.  No Exception created.");
        }
    }
%>

<%!
    public void skipPageExceptionMessageCtorTest(HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 JspWriter out)
    throws ServletException, IOException {
        SkipPageException spe = new SkipPageException("Exception Message");
        if (spe != null) {
            String message = spe.getMessage();
            if (message != null && message.equals("Exception Message")) {
                out.println("Test PASSED");
            } else {
                out.println("Test FAILED.  Expected a message of 'Exception Message'");
                out.println("Received: " + message);
            }
        } else {
            out.println("Test FAILED.  No Exception created.");
        }
    }
%>

<%!
    public void skipPageExceptionCauseCtorTest(HttpServletRequest req,
                                               HttpServletResponse res,
                                               JspWriter out)
    throws ServletException, IOException {
        SkipPageException spe = new SkipPageException(new NullPointerException());
        if (spe != null) {
            Throwable t = spe.getCause();
            if (t != null && t instanceof NullPointerException) {
                out.println("Test PASSED");
            } else {
                out.println("Test FAILED.  Expected a Throwable of type NullPointerException");
                out.println("Received: " + t);
            }
        } else {
            out.println("Test FAILED.  No Exception created.");
        }
    }
%>

<%!
    public void skipPageExceptionCauseMessageCtorTest(HttpServletRequest req,
                                                      HttpServletResponse res,
                                                      JspWriter out)
    throws ServletException, IOException {
        SkipPageException spe = new SkipPageException("Exception Message",
                                                      new ServletException());
        if (spe != null) {
            String message = spe.getMessage();
            if (message != null && message.equals("Exception Message")) {
                Throwable t = spe.getCause();
                if (t != null && t instanceof ServletException) {
                    out.println("Test PASSED");
                } else {
                    out.println("Test FAILED.  Expected a Throwable of type ServletException");
                    out.println("Received: " + t);
                }
            } else {
                out.println("Test FAILED.  Expected a message of 'Exception Message'");
                out.println("Received: " + message);
            }
        } else {
            out.println("Test FAILED.  No Exception created.");
        }
    }
%>

<%-- Test invocation --%>

<% JspTestUtil.invokeTest(this, request, response, out); %>
