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

<%
    if (application.getAttribute("flush.exception") != null ||
        out.getBufferSize() == 0) {
        if (application.getAttribute("write.exception") != null) {
            if (application.getAttribute("close.exception") == null) {
                out.println("Test PASSED");
            } else {
                out.println("Test FAILED.  Subsequent call to JspWriter.close() " +
                    "illegally threw another IOException.");
            }
        } else {
            out.println("Test FAILED.  Stream was closed and a call to JspWriter." +
                "print() didn't cause an IOException.");
        }
    } else {
        out.println("Test FAILED.  Stream was closed and a call to JspWriter." +
            "flush() didn't cause an IOException.");
    }

    application.removeAttribute("flush.exception");
    application.removeAttribute("write.exception");
    application.removeAttribute("close.exception");
%>
