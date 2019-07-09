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
 * This class is used to customize the way an ExpressionEvaluator resolves variable references at evaluation time. For
 * example, instances of this class can implement their own variable lookup mechanisms, or introduce the notion of
 * "implicit variables" which override any other variables. An instance of this class should be passed when evaluating
 * an expression.
 * </p>
 *
 * <p>
 * An instance of this class includes the context against which resolution will happen
 * </p>
 *
 * @deprecated As of JSP 2.1, replaced by {@link javax.el.ELResolver}
 * @since JSP 2.0
 */
@Deprecated
public interface VariableResolver {

    /**
     * Resolves the specified variable. Returns null if the variable is not found.
     * 
     * @param pName the name of the variable to resolve
     * @return the result of the variable resolution
     *
     * @throws ELException if a failure occurred while trying to resolve the given variable
     */
    public Object resolveVariable(String pName) throws ELException;
}
