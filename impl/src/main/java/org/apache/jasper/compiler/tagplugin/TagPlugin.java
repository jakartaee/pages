/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.compiler.tagplugin;

/**
 * This interface is to be implemented by the plugin author, to supply
 * an alternate implementation of the tag handlers.  It can be used to
 * specify the Java codes to be generated when a tag is invoked.
 *
 * An implementation of this interface must be registered in a file
 * named "tagPlugins.xml" under WEB-INF.
 */

public interface TagPlugin {

    /**
     * Generate codes for a custom tag.
     * @param ctxt a TagPluginContext for accessing Jasper functions
     */
    void doTag(TagPluginContext ctxt);
}

