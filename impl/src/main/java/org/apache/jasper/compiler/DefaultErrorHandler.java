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

import org.apache.jasper.JasperException;

/**
 * Default implementation of ErrorHandler interface.
 *
 * @author Jan Luehe
 */
class DefaultErrorHandler implements ErrorHandler {

    /*
     * Processes the given JSP parse error.
     *
     * @param fname Name of the JSP file in which the parse error occurred
     * @param line Parse error line number
     * @param column Parse error column number
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    public void jspError(String fname, int line, int column, String errMsg,
			 Exception ex) throws JasperException {
	throw new JasperException(fname + "(" + line + "," + column + ")"
				  + " " + errMsg, ex);
    }

    /*
     * Processes the given JSP parse error.
     *
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    public void jspError(String errMsg, Exception ex) throws JasperException {
	throw new JasperException(errMsg, ex);
    }

    /*
     * Processes the given javac compilation errors.
     *
     * @param details Array of JavacErrorDetail instances corresponding to the
     * compilation errors
     */
    public void javacError(JavacErrorDetail[] details) throws JasperException {

        if (details == null) {
            return;
        }

	Object[] args = null;
        StringBuilder buf = new StringBuilder();

        for (int i=0; i < details.length; i++) {
            if (details[i].getJspBeginLineNumber() >= 0) {
                args = new Object[] {
                        Integer.valueOf(details[i].getJspBeginLineNumber()), 
                        details[i].getJspFileName() };
                buf.append(Localizer.getMessage("jsp.error.single.line.number",
                                                args));
                buf.append("\n"); 
            }

            buf.append(
                Localizer.getMessage("jsp.error.corresponding.servlet"));
            buf.append(details[i].getErrorMessage());
            buf.append("\n\n");
        }

        if (buf.length() == 0) {
            throw new JasperException(Localizer.getMessage("jsp.error.nojdk"));
        }
	throw new JasperException(
            Localizer.getMessage("jsp.error.unable.compile") + "\n\n" + buf);
    }

    /**
     * Processes the given javac error report and exception.
     *
     * @param errorReport Compilation error report
     * @param exception Compilation exception
     */
    public void javacError(String errorReport, Exception exception)
            throws JasperException {

 	throw new JasperException(
            Localizer.getMessage("jsp.error.unable.compile"), exception);
    }

}
