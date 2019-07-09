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
