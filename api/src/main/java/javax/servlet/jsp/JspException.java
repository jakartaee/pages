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
 * A generic exception known to the JSP engine; uncaught JspExceptions will result in an invocation of the errorpage
 * machinery.
 */
public class JspException extends Exception {

    private static final long serialVersionUID = -724329986255528641L;

    /**
     * Construct a JspException.
     */
    public JspException() {
    }

    /**
     * Constructs a new JSP exception with the specified message. The message can be written to the server log and/or
     * displayed for the user.
     *
     * @param msg a <code>String</code> specifying the text of the exception message
     *
     */
    public JspException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new <code>JspException</code> with the specified detail message and cause.
     *
     * @param message a <code>String</code> specifying the text of the exception message
     * @param cause   the cause which is saved for later retrieval by the {@link #getCause()} and
     *                {@link #getRootCause()} methods.
     *
     * @see java.lang.Exception#Exception(String, Throwable)
     */
    public JspException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new <code>JspException</code> with the specified cause.
     *
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} and {@link #getRootCause()}
     *              methods.
     *
     * @see java.lang.Exception#Exception(Throwable)
     */
    public JspException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the exception that caused this JSP exception.
     *
     * @return the <code>Throwable</code> that caused this JSP exception
     *
     * @deprecated As of JSP 2.1, replaced by {@link #getCause()}
     */
    @Deprecated
    public Throwable getRootCause() {
        return getCause();
    }
}
