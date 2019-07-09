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

import java.util.HashMap;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.VariableMapper;
import javax.el.ValueExpression;


/**
 * <p>This is the implementation of VariableMapper.
 * The compiler creates an empty variable mapper when an ELContext is created.
 * The variable mapper will be updated by tag handlers, if necessary.
 * 
 * @author Kin-man Chung
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 */

public class VariableMapperImpl extends VariableMapper
{
    //-------------------------------------
    /**
     * Constructor
     */
    public VariableMapperImpl () {
        map = new HashMap<String, ValueExpression>();
    }
  
    //-------------------------------------
    /**
     * Resolves the specified variable within the given context.
     * Returns null if the variable is not found.
     */
    public ValueExpression resolveVariable (String variable) {
        return map.get(variable);
    }

    public ValueExpression setVariable(String variable,
                                       ValueExpression expression) {
        ValueExpression prev = null;
        if (expression == null) {
            map.remove(variable);
        } else {
            prev = map.get(variable);
            map.put(variable, expression);
        }
        return prev;
    }

    private HashMap<String, ValueExpression> map;
}
