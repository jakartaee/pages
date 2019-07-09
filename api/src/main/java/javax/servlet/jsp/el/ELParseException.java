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
 * Represents a parsing error encountered while parsing an EL expression.
 *
 * @deprecated As of JSP 2.1, replaced by {@link javax.el.ELException}
 * @since JSP 2.0
 */
@Deprecated
public class ELParseException extends ELException {

    private static final long serialVersionUID = 3521581805886060118L;

    /**
     * Creates an ELParseException with no detail message.
     */
    public ELParseException() {
        super();
    }

    /**
     * Creates an ELParseException with the provided detail message.
     *
     * @param pMessage the detail message
     */
    public ELParseException(String pMessage) {
        super(pMessage);
    }
}
