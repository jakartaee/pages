/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.runtime;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

/**
 * Concrete implementation of {@link javax.el.ELContext}.
 * ELContext's constructor is protected to control creation of ELContext
 * objects through their appropriate factory methods.  This version of
 * ELContext forces construction through JspApplicationContextImpl.
 *
 * @author Mark Roth
 * @author Kin-man Chung
 */
public class ELContextImpl 
    extends ELContext
{
    /**
     * Constructs a new ELContext associated with the given ELResolver.
     */
    public ELContextImpl(ELResolver resolver) {
        this.resolver = resolver;
    }

    public ELResolver getELResolver() {
        return resolver;
    }

    public void setFunctionMapper(FunctionMapper fnMapper) {
        functionMapper = fnMapper;
    }

    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    public void setVariableMapper(VariableMapper varMapper) {
        variableMapper = varMapper;
    }

    public VariableMapper getVariableMapper() {
        return variableMapper;
    }

    private FunctionMapper functionMapper;
    private VariableMapper variableMapper;
    private ELResolver resolver;
}
