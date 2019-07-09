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
 * <p>
 * The interface to a map between EL function names and methods.
 * </p>
 *
 * <p>
 * Classes implementing this interface may, for instance, consult tag library information to resolve the map.
 * </p>
 *
 * @deprecated As of JSP 2.1, replaced by {@link javax.el.FunctionMapper}
 * @since JSP 2.0
 */
@Deprecated
public interface FunctionMapper {
    /**
     * Resolves the specified local name and prefix into a Java.lang.Method. Returns null if the prefix and local name
     * are not found.
     * 
     * @param prefix    the prefix of the function, or "" if no prefix.
     * @param localName the short name of the function
     * @return the result of the method mapping. Null means no entry found.
     */
    public java.lang.reflect.Method resolveFunction(String prefix, String localName);
}
