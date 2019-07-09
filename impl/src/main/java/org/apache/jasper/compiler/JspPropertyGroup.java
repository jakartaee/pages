/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.compiler;

public class JspPropertyGroup {

    private String path;
    private String extension;
    private JspProperty jspProperty;

    public JspPropertyGroup(String path, String extension,
                            JspProperty jspProperty) {
        this.path = path;
        this.extension = extension;
        this.jspProperty = jspProperty;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    public JspProperty getJspProperty() {
        return jspProperty;
    }
}
