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

<%@ page contentType="text/plain" %>
<%@ taglib uri="http://java.sun.com/tck/jsp/el" prefix="el" %>

<jsp:useBean id="ecma" scope="page"
             class="ee.jakarta.tck.pages.spec.core_syntax.scripting.el.EcmaBean" />

<jsp:setProperty name="ecma" property="simple" value="property set"/>

<% try { %>
    ${ecma.exception}
    Test FAILED.  Accessed a bean whose getter method throws an exception,
    but the exception wasn't propagated by the EL machinery.
<% } catch (Throwable t) {
       out.clearBuffer();
} %>

<el:checkEcma control="property set" object="${ecma.simple}" display="true" />
