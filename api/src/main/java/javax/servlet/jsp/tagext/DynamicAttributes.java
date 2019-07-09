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

import javax.servlet.jsp.JspException;

/**
 * For a tag to declare that it accepts dynamic attributes, it must implement this interface. The entry for the tag in
 * the Tag Library Descriptor must also be configured to indicate dynamic attributes are accepted. <br>
 * For any attribute that is not declared in the Tag Library Descriptor for this tag, instead of getting an error at
 * translation time, the <code>setDynamicAttribute()</code> method is called, with the name and value of the attribute.
 * It is the responsibility of the tag to remember the names and values of the dynamic attributes.
 *
 * @since JSP 2.0
 */
public interface DynamicAttributes {

    /**
     * Called when a tag declared to accept dynamic attributes is passed an attribute that is not declared in the Tag
     * Library Descriptor.
     * 
     * @param uri       the namespace of the attribute, or null if in the default namespace.
     * @param localName the name of the attribute being set.
     * @param value     the value of the attribute
     * @throws JspException if the tag handler wishes to signal that it does not accept the given attribute. The
     *                      container must not call doStartTag() or doTag() for this tag.
     */
    public void setDynamicAttribute(String uri, String localName, Object value) throws JspException;

}
