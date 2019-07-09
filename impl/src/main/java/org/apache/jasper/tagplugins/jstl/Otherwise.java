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

public final class Otherwise implements TagPlugin {

    public void doTag(TagPluginContext ctxt) {

	// See When.java for the reason whey "}" is need at the beginng and
	// not at the end.
	ctxt.generateJavaSource("} else {");
	ctxt.generateBody();
    }
}
