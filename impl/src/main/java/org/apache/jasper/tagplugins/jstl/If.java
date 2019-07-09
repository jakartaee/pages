/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.tagplugins.jstl;

import org.apache.jasper.compiler.tagplugin.*;

public final class If implements TagPlugin {

    public void doTag(TagPluginContext ctxt) {
	String condV = ctxt.getTemporaryVariableName();
	ctxt.generateJavaSource("boolean " + condV + "=");
	ctxt.generateAttribute("test");
	ctxt.generateJavaSource(";");
	if (ctxt.isAttributeSpecified("var")) {
	    String scope = "PageContext.PAGE_SCOPE";
	    if (ctxt.isAttributeSpecified("scope")) {
		String scopeStr = ctxt.getConstantAttribute("scope");
		if ("request".equals(scopeStr)) {
		    scope = "PageContext.REQUEST_SCOPE";
		} else if ("session".equals(scopeStr)) {
		    scope = "PageContext.SESSION_SCOPE";
		} else if ("application".equals(scopeStr)) {
		    scope = "PageContext.APPLICATION_SCOPE";
		}
	    }
	    ctxt.generateJavaSource("_jspx_page_context.setAttribute(");
	    ctxt.generateAttribute("var");
	    ctxt.generateJavaSource(", new Boolean(" + condV + ")," + scope + ");");
	}
	ctxt.generateJavaSource("if (" + condV + "){");
	ctxt.generateBody();
	ctxt.generateJavaSource("}");
    }
}
