/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package jakarta.servlet.jsp.tagext;

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
