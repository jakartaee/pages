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

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.el.VariableResolver;


/**
 * <p>This is the implementation of VariableResolver in JSP 2.0,
 * using ELResolver in JSP2.1.
 * It looks up variable references in the PageContext, and also
 * recognizes references to implicit objects.
 * 
 * @author Kin-man Chung
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 */

public class VariableResolverImpl
    implements VariableResolver
{
    private PageContext pageContext;

    //-------------------------------------
    /**
     * Constructor
     */
    public VariableResolverImpl (PageContext pageContext) {
        this.pageContext = pageContext;
    }
  
    //-------------------------------------
    /**
     * Resolves the specified variable within the given context.
     * Returns null if the variable is not found.
     */
    public Object resolveVariable (String pName)
            throws jakarta.servlet.jsp.el.ELException {

        ELContext elContext = pageContext.getELContext();
        ELResolver elResolver = elContext.getELResolver();
        try {
            return elResolver.getValue(elContext, null, pName);
        } catch (jakarta.el.ELException ex) {
            throw new jakarta.servlet.jsp.el.ELException();
        }
    }
}
