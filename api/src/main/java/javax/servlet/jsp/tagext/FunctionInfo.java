/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp.tagext;

/**
 * Information for a function in a Tag Library. This class is instantiated from the Tag Library Descriptor file (TLD)
 * and is available only at translation time.
 * 
 * @since JSP 2.0
 */
public class FunctionInfo {

    /**
     * Constructor for FunctionInfo.
     *
     * @param name      The name of the function
     * @param klass     The class of the function
     * @param signature The signature of the function
     */
    public FunctionInfo(String name, String klass, String signature) {

        this.name = name;
        this.functionClass = klass;
        this.functionSignature = signature;
    }

    /**
     * The name of the function.
     *
     * @return The name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * The class of the function.
     *
     * @return The class of the function
     */
    public String getFunctionClass() {
        return functionClass;
    }

    /**
     * The signature of the function.
     *
     * @return The signature of the function
     */
    public String getFunctionSignature() {
        return functionSignature;
    }

    /*
     * fields
     */

    private String name;
    private String functionClass;
    private String functionSignature;
}
