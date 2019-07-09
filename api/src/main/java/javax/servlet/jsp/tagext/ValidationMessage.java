/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp.tagext;

/**
 * A validation message from either TagLibraryValidator or TagExtraInfo.
 * <p>
 * As of JSP 2.0, a JSP container must support a jsp:id attribute to provide higher quality validation errors. The
 * container will track the JSP pages as passed to the container, and will assign to each element a unique "id", which
 * is passed as the value of the jsp:id attribute. Each XML element in the XML view available will be extended with this
 * attribute. The TagLibraryValidator can then use the attribute in one or more ValidationMessage objects. The container
 * then, in turn, can use these values to provide more precise information on the location of an error.
 * 
 * <p>
 * The actual prefix of the <code>id</code> attribute may or may not be <code>jsp</code> but it will always map to the
 * namespace <code>http://java.sun.com/JSP/Page</code>. A TagLibraryValidator implementation must rely on the uri, not
 * the prefix, of the <code>id</code> attribute.
 */
public class ValidationMessage {

    /**
     * Create a ValidationMessage. The message String should be non-null. The value of id may be null, if the message is
     * not specific to any XML element, or if no jsp:id attributes were passed on. If non-null, the value of id must be
     * the value of a jsp:id attribute for the PageData passed into the validate() method.
     *
     * @param id      Either null, or the value of a jsp:id attribute.
     * @param message A localized validation message.
     */
    public ValidationMessage(String id, String message) {
        this.id = id;
        this.message = message;
    }

    /**
     * Get the jsp:id. Null means that there is no information available.
     *
     * @return The jsp:id information.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the localized validation message.
     *
     * @return A validation message
     */
    public String getMessage() {
        return message;
    }

    // Private data
    private String id;
    private String message;
}
