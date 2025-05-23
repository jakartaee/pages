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

<%@ taglib prefix="test" uri="/WEB-INF/el_jsp.tld" %>
<%@ page language="java" import="ee.jakarta.tck.pages.common.el.Book" %>

begin </br>
<% 
   Book novel = new Book("Moby Dick", "Herman Melville", "Random House", 1849); 
   pageContext.setAttribute("book", novel, PageContext.PAGE_SCOPE);
 %>

<test:ELDeferredValueCoercionTag intExpr="8128" bookExpr="#{book}" />
end </br>
