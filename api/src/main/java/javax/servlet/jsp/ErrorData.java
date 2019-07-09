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
 * Contains information about an error, for error pages. The information contained in this instance is meaningless if
 * not used in the context of an error page. To indicate a JSP is an error page, the page author must set the
 * isErrorPage attribute of the page directive to "true".
 *
 * @see PageContext#getErrorData
 * @since JSP 2.0
 */
public final class ErrorData {

    private Throwable throwable;
    private int statusCode;
    private String uri;
    private String servletName;

    /**
     * Creates a new ErrorData object.
     *
     * @param throwable   The Throwable that is the cause of the error
     * @param statusCode  The status code of the error
     * @param uri         The request URI
     * @param servletName The name of the servlet invoked
     */
    public ErrorData(Throwable throwable, int statusCode, String uri, String servletName) {
        this.throwable = throwable;
        this.statusCode = statusCode;
        this.uri = uri;
        this.servletName = servletName;
    }

    /**
     * Returns the Throwable that caused the error.
     *
     * @return The Throwable that caused the error
     */
    public Throwable getThrowable() {
        return this.throwable;
    }

    /**
     * Returns the status code of the error.
     *
     * @return The status code of the error
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Returns the request URI.
     *
     * @return The request URI
     */
    public String getRequestURI() {
        return this.uri;
    }

    /**
     * Returns the name of the servlet invoked.
     *
     * @return The name of the servlet invoked
     */
    public String getServletName() {
        return this.servletName;
    }
}
