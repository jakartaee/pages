/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.runtime;

import java.util.HashMap;
import jakarta.el.VariableMapper;
import jakarta.el.ValueExpression;


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
