/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;

public class JspProperty {

    private String isXml;
    private String elIgnored;
    private String scriptingInvalid;
    private String pageEncoding;
    private String trimSpaces;
    private String poundAllowed;
    private List<String> includePrelude;
    private List<String> includeCoda;
    private String buffer;
    private String defaultContentType;
    private String errorOnUndeclaredNamespace;

    public JspProperty(String isXml,
                       String elIgnored,
                       String scriptingInvalid,
                       String trimSpaces,
                       String poundAllowed,
                       String pageEncoding,
                       List<String> includePrelude,
                       List<String> includeCoda,
                       String defaultContentType,
                       String buffer,
                       String errorOnUndeclaredNamespace) {


        this.isXml = isXml;
        this.elIgnored = elIgnored;
        this.scriptingInvalid = scriptingInvalid;
        this.trimSpaces = trimSpaces;
        this.poundAllowed = poundAllowed;
        this.pageEncoding = pageEncoding;
        this.includePrelude = includePrelude;
        this.includeCoda = includeCoda;
        this.defaultContentType = defaultContentType;
        this.buffer = buffer;
        this.errorOnUndeclaredNamespace = errorOnUndeclaredNamespace;
    }

    public String isXml() {
        return isXml;
    }

    public String isELIgnored() {
        return elIgnored;
    }

    public String isScriptingInvalid() {
        return scriptingInvalid;
    }

    public String getPageEncoding() {
        return pageEncoding;
    }

    public String getTrimSpaces() {
        return trimSpaces;
    }

    public String getPoundAllowed() {
        return poundAllowed;
    }

    public List<String> getIncludePrelude() {
        return includePrelude;
    }

    public List<String> getIncludeCoda() {
        return includeCoda;
    }

    public String getBuffer() {
        return buffer;
    }

    public String getDefaultContentType() {
        return defaultContentType;
    }

    public String errorOnUndeclaredNamespace() {
        return errorOnUndeclaredNamespace;
    }
}
