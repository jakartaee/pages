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

import java.io.File;
import java.io.Writer;
import java.util.List;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;

interface JavaCompiler {

    /**
     * Start Java compilation
     * @param className Name of the class under compilation
     * @param pageNode Internal form for the page, used for error line mapping
     */
    public JavacErrorDetail[] compile(String className, Node.Nodes pageNodes)
        throws JasperException;

    /**
     * Get a Writer for the Java file.
     * The writer is used by JSP compiler.  This method allows the Java
     * compiler control where the Java file should be generated so it knows how
     * to handle the input for java compilation accordingly.
     */
    public Writer getJavaWriter(String javaFileName, String javaEncoding)
        throws JasperException;

    /**
     * Remove/save the generated Java File from/to disk
     */
    public void doJavaFile(boolean keep) throws JasperException;

    /**
     * Return the time the class file was generated.
     */
    public long getClassLastModified();

    /**
     * Save the generated class file to disk, if not already done.
     */
    public void saveClassFile(String className, String classFileName);

    /**
     * Java Compiler options.
     */
    public void setClassPath(List<File> cp);
    public void setDebug(boolean debug);
    public void setExtdirs(String exts);
    public void setTargetVM(String targetVM);
    public void setSourceVM(String sourceVM);

    /**
     * Initializations
     */
    public void init(JspCompilationContext ctxt,
                     ErrorDispatcher err,
                     boolean suppressLogging);

    /**
     * Release resouces used in the current compilation
     */
    public void release();
}
    
