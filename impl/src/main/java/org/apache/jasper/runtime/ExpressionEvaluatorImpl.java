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

import java.util.Iterator;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.ValueExpression;
import jakarta.el.ExpressionFactory;

import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.el.Expression;
import jakarta.servlet.jsp.el.ELException;
import jakarta.servlet.jsp.el.FunctionMapper;
import jakarta.servlet.jsp.el.ExpressionEvaluator;
import jakarta.servlet.jsp.el.VariableResolver;

/**
 * <p>This is the implementation of ExpreesioEvaluator
 * using implementation of JSP2.1.
 * 
 * @author Kin-man Chung
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 */

public class ExpressionEvaluatorImpl extends ExpressionEvaluator
{
    private PageContext pageContext;

    //-------------------------------------
    /**
     * Constructor
     */
    public ExpressionEvaluatorImpl (PageContext pageContext) {
        this.pageContext = pageContext;
    }
  
    //-------------------------------------
    public Expression parseExpression(String expression,
                                      Class expectedType,
                                      FunctionMapper fMapper )
            throws ELException {

        ExpressionFactory fac = ExpressionFactory.newInstance();
        jakarta.el.ValueExpression expr;
        ELContextImpl elContext = new ELContextImpl(null);
        jakarta.el.FunctionMapper fm = new FunctionMapperWrapper(fMapper);
        elContext.setFunctionMapper(fm);
        try {
            expr = fac.createValueExpression(
                           elContext,
                           expression, expectedType);
        } catch (jakarta.el.ELException ex) {
            throw new ELException(ex);
        }
        return new ExpressionImpl(expr, pageContext);
    }

     public Object evaluate(String expression,
                            Class expectedType,
                            VariableResolver vResolver,
                            FunctionMapper fMapper )
                throws ELException {

        ELContextImpl elContext;
        if (vResolver instanceof VariableResolverImpl) {
            elContext = (ELContextImpl) pageContext.getELContext();
        }
        else {
            // The provided variable Resolver is a custom resolver,
            // wrap it with a ELResolver 
            elContext = new ELContextImpl(new ELResolverWrapper(vResolver));
        }

        jakarta.el.FunctionMapper fm = new FunctionMapperWrapper(fMapper);
        elContext.setFunctionMapper(fm);
        ExpressionFactory fac = ExpressionFactory.newInstance();
        Object value;
        try {
            ValueExpression expr = fac.createValueExpression(
                                 elContext,
                                 expression,
                                 expectedType);
            value = expr.getValue(elContext);
        } catch (jakarta.el.ELException ex) {
            throw new ELException(ex);
        }
        return value;
    }

    static private class ExpressionImpl extends Expression {

        private ValueExpression valueExpr;
        private PageContext pageContext;

        ExpressionImpl(ValueExpression valueExpr,
                       PageContext pageContext) {
            this.valueExpr = valueExpr;
            this.pageContext = pageContext;
        }

        public Object evaluate(VariableResolver vResolver) throws ELException {

            ELContext elContext;
            if (vResolver instanceof VariableResolverImpl) {
                elContext = pageContext.getELContext();
            }
            else {
                // The provided variable Resolver is a custom resolver,
                // wrap it with a ELResolver 
                elContext = new ELContextImpl(new ELResolverWrapper(vResolver));
            }
            try {
                return valueExpr.getValue(elContext);
            } catch (jakarta.el.ELException ex) {
                throw new ELException(ex);
            }
        }
    }

    private static class FunctionMapperWrapper
        extends jakarta.el.FunctionMapper {

        private FunctionMapper mapper;

        FunctionMapperWrapper(FunctionMapper mapper) {
            this.mapper = mapper;
        }

        public java.lang.reflect.Method resolveFunction(String prefix,
                                                        String localName) {
            return mapper.resolveFunction(prefix, localName);
        }
    }

    private static class ELResolverWrapper extends ELResolver {
        private VariableResolver vResolver;

        ELResolverWrapper(VariableResolver vResolver) {
            this.vResolver = vResolver;
        }

        public Object getValue(ELContext context,
                               Object base,
                               Object property)
                throws jakarta.el.ELException {
            if (base == null) {
                context.setPropertyResolved(true);
                try {
                    return vResolver.resolveVariable(property.toString());
                } catch (ELException ex) {
                    throw new jakarta.el.ELException(ex);
                }
            }
            return null;
        }

        public Class getType(ELContext context,
                             Object base,
                             Object property)
                throws jakarta.el.ELException {
            return null;
        }

        public void setValue(ELContext context,
                             Object base,
                             Object property,
                             Object value)
                throws jakarta.el.ELException {
        }

        public boolean isReadOnly(ELContext context,
                                  Object base,
                                  Object property)
                throws jakarta.el.ELException {
            return false;
        }

        public Iterator<java.beans.FeatureDescriptor>
                getFeatureDescriptors(ELContext context, Object base) {
            return null;
        }

        public Class<?> getCommonPropertyType(ELContext context,
                                           Object base) {
            return null;
        }
    }
}
