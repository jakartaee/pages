<%--

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

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

<html>
<title>negativeClassCastExceptionFwd</title>
<body>
<% /** 	Name : negativeClassCastFwd
        Description: Declare a bean with session scope
                     and then forward the request to another
                     page.
	    Result : None.  The interesting stuff will occur in the 
                 negativeClassCastException.jsp page.
**/ %>
<jsp:useBean id="ncounter"  class="ee.jakarta.tck.pages.spec.core_syntax.actions.usebean.Counter" scope="session" />
<jsp:forward page="negativeClassCastException.jsp" />
</body>
</html>
