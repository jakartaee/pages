/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp.el;

/**
 * Represents any of the exception conditions that arise during the operation evaluation of the evaluator.
 *
 * @deprecated As of JSP 2.1, replaced by {@link javax.el.ELException}
 * @since JSP 2.0
 */
@Deprecated
public class ELException extends Exception {
    private static final long serialVersionUID = -3920470039225321534L;

    // -------------------------------------
    // Member variables
    // -------------------------------------

    private Throwable mRootCause;


    /**
     * Creates an ELException with no detail message.
     */
    public ELException() {
        super();
    }


    /**
     * Creates an ELException with the provided detail message.
     *
     * @param pMessage the detail message
     */
    public ELException(String pMessage) {
        super(pMessage);
    }


    /**
     * Creates an ELException with the given root cause.
     *
     * @param pRootCause the originating cause of this exception
     */
    public ELException(Throwable pRootCause) {
        super(pRootCause.getLocalizedMessage());
        mRootCause = pRootCause;
    }


    /**
     * Creates an ELException with the given detail message and root cause.
     *
     * @param pMessage   the detail message
     * @param pRootCause the originating cause of this exception
     */
    public ELException(String pMessage, Throwable pRootCause) {
        super(pMessage);
        mRootCause = pRootCause;
    }


    /**
     * Returns the root cause.
     *
     * @return the root cause of this exception
     */
    public Throwable getRootCause() {
        return mRootCause;
    }
}
