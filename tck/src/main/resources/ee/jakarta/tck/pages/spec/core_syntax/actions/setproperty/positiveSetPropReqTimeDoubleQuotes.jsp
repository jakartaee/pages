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
<title>positiveSetPropReqTimeDoubleQuotes</title>
<body>
<% /** 	Name : positiveSetPropReqTimeDoubleQuotes
	Description : use a scriptlet expression in double quotes
                      as 'value' attribute in setProperty
	Result :we should get the expected page without error
**/ %>
<!-- testing if are able to set a  property using double quoted expression -->
<jsp:useBean id="myBean" class="ee.jakarta.tck.pages.spec.core_syntax.actions.setproperty.MiscBean" />
<jsp:setProperty name="myBean" property="path" value="<%= request.getProtocol() %>" />
<jsp:getProperty name="myBean" property="path" />

</body>
</html>
