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

/**
 * Class providing details about a javac compilation error.
 *
 * @author Jan Luehe
 * @author Kin-man Chung
 */
public class JavacErrorDetail {

    private String javaFileName;
    private int javaLineNum;
    private String jspFileName;
    private int jspBeginLineNum;
    private StringBuilder errMsg;

    /**
     * Constructor.
     *
     * @param javaFileName The name of the Java file in which the 
     * compilation error occurred
     * @param javaLineNum The compilation error line number
     * @param errMsg The compilation error message
     */
    public JavacErrorDetail(String javaFileName,
			    int javaLineNum,
			    StringBuilder errMsg) {

	this.javaFileName = javaFileName;
	this.javaLineNum = javaLineNum;
	this.errMsg = errMsg;
        this.jspBeginLineNum = -1;
    }

    /**
     * Constructor.
     *
     * @param javaFileName The name of the Java file in which the 
     * compilation error occurred
     * @param javaLineNum The compilation error line number
     * @param jspFileName The name of the JSP file from which the Java source
     * file was generated
     * @param jspBeginLineNum The start line number of the JSP element
     * responsible for the compilation error
     * @param errMsg The compilation error message
     */
    public JavacErrorDetail(String javaFileName,
			    int javaLineNum,
			    String jspFileName,
			    int jspBeginLineNum,
			    StringBuilder errMsg) {

        this(javaFileName, javaLineNum, errMsg);
	this.jspFileName = jspFileName;
	this.jspBeginLineNum = jspBeginLineNum;
    }

    /**
     * Gets the name of the Java source file in which the compilation error
     * occurred.
     *
     * @return Java source file name
     */
    public String getJavaFileName() {
	return this.javaFileName;
    }

    /**
     * Gets the compilation error line number.
     * 
     * @return Compilation error line number
     */
    public int getJavaLineNumber() {
	return this.javaLineNum;
    }

    /**
     * Gets the name of the JSP file from which the Java source file was
     * generated.
     *
     * @return JSP file from which the Java source file was generated.
     */
    public String getJspFileName() {
	return this.jspFileName;
    }

    /**
     * Gets the start line number (in the JSP file) of the JSP element
     * responsible for the compilation error.
     *
     * @return Start line number of the JSP element responsible for the
     * compilation error
     */
    public int getJspBeginLineNumber() {
	return this.jspBeginLineNum;
    }

    /**
     * Gets the compilation error message.
     *
     * @return Compilation error message
     */
    public String getErrorMessage() {
	return this.errMsg.toString();
    }
}
