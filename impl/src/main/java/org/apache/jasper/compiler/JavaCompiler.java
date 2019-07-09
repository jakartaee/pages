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
    
