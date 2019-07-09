/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp;

/**
 * Exception to be used by a Tag Handler to indicate some unrecoverable error. This error is to be caught by the top
 * level of the JSP page and will result in an error page.
 */
public class JspTagException extends JspException {

    private static final long serialVersionUID = 1546743964929435607L;

    /**
     * Constructs a new JspTagException with the specified message. The message can be written to the server log and/or
     * displayed for the user.
     * 
     * @param msg a <code>String</code> specifying the text of the exception message
     */
    public JspTagException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new JspTagException with no message.
     */
    public JspTagException() {
        super();
    }

    /**
     * Constructs a new JspTagException when the JSP Tag needs to throw an exception and include a message about the
     * "root cause" exception that interfered with its normal operation, including a description message.
     *
     *
     * @param message   a <code>String</code> containing the text of the exception message
     *
     * @param rootCause the <code>Throwable</code> exception that interfered with the JSP Tag's normal operation, making
     *                  this JSP Tag exception necessary
     *
     * @since JSP 2.0
     */
    public JspTagException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new JSP Tag exception when the JSP Tag needs to throw an exception and include a message about the
     * "root cause" exception that interfered with its normal operation. The exception's message is based on the
     * localized message of the underlying exception.
     *
     * <p>
     * This method calls the <code>getLocalizedMessage</code> method on the <code>Throwable</code> exception to get a
     * localized exception message. When subclassing <code>JspTagException</code>, this method can be overridden to
     * create an exception message designed for a specific locale.
     *
     * @param rootCause the <code>Throwable</code> exception that interfered with the JSP Tag's normal operation, making
     *                  the JSP Tag exception necessary
     *
     * @since JSP 2.0
     */
    public JspTagException(Throwable rootCause) {
        super(rootCause);
    }

}
