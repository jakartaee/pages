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

public final class When implements TagPlugin {

    public void doTag(TagPluginContext ctxt) {
	// Get the parent context to determine if this is the first <c:when>
	TagPluginContext parentContext = ctxt.getParentContext();
	if (parentContext == null) {
	    ctxt.dontUseTagPlugin();
	    return;
	}

	if ("true".equals(parentContext.getPluginAttribute("hasBeenHere"))) {
	    ctxt.generateJavaSource("} else if(");
	    // See comment below for the reason we generate the extra "}" here.
	}
	else {
	    ctxt.generateJavaSource("if(");
	    parentContext.setPluginAttribute("hasBeenHere", "true");
	}
	ctxt.generateAttribute("test");
	ctxt.generateJavaSource("){");
	ctxt.generateBody();

	// We don't generate the closing "}" for the "if" here because there
	// may be whitespaces in between <c:when>'s.  Instead we delay
	// generating it until the next <c:when> or <c:otherwise> or
	// <c:choose>
    }
}
