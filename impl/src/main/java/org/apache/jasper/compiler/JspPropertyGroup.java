/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.compiler;

public class JspPropertyGroup {

    private String path;
    private String extension;
    private JspProperty jspProperty;

    public JspPropertyGroup(String path, String extension, JspProperty jspProperty) {
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
