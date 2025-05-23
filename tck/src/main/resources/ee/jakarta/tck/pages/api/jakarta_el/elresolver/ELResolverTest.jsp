<%--

    Copyright (c) 2005, 2025 Oracle and/or its affiliates and others.
    All rights reserved.

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
             ee.jakarta.tck.pages.common.el.resolver.ResolverTest,
             jakarta.el.ELResolver,
             jakarta.el.ELContext"%>
<%@ page contentType="text/plain" %>

<% 
    if (pageContext != null) {
        ELContext elContext = pageContext.getELContext();
        StringBuffer buf = new StringBuffer();

        if (elContext != null) {
            try {
                ELResolver resolver = elContext.getELResolver();
                boolean pass = ResolverTest.testELResolver(
                        elContext, resolver, null, "Color", "blue", buf, false);
                out.println(buf.toString());
                if (pass) out.println("Test PASSED.");
            } catch (Throwable t) {
                out.println(buf.toString());
                JspTestUtil.handleThrowable(t, out, "ElResolverTest");
            }
        }
        else
            out.println("Test FAILED. Unable to obtain ELContext");
    } 
    else 
        out.println("Test FAILED.  Unable to obtain PageContext.");
%>
