/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;

import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspApplicationContext;

import jakarta.el.ELResolver;
import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.ELContextListener;
import jakarta.el.ELContextEvent;

import org.apache.jasper.Constants;

/**
 * Implements jakarta.servlet.jsp.JspApplication
 */
public class JspApplicationContextImpl implements JspApplicationContext {

    public JspApplicationContextImpl(ServletContext context) {
        this.context = context;

        // Add system defined ELResolver, as defined in JSR 299
        ELResolver beanManagerELResolver = (ELResolver) context.getAttribute("org.glassfish.jsp.beanManagerELResolver");
        if (beanManagerELResolver != null) {
            elResolvers.add(beanManagerELResolver);
        }
    }

    public void addELResolver(ELResolver resolver) {
        if ("true".equals(context.getAttribute(Constants.FIRST_REQUEST_SEEN))) {
            throw new IllegalStateException("Attempt to invoke addELResolver " + "after the application has already received a request");
        }

        elResolvers.add(0, resolver);
    }

    public ExpressionFactory getExpressionFactory() {
        if (expressionFactory == null) {
            expressionFactory = ExpressionFactory.newInstance();
        }
        return expressionFactory;
    }

    public void setExpressionFactory(ExpressionFactory expressionFactory) {
        this.expressionFactory = expressionFactory;
    }

    public void addELContextListener(ELContextListener listener) {
        listeners.add(listener);
    }

    protected ELContext createELContext(ELResolver resolver) {

        ELContext elContext = new ELContextImpl(resolver);

        // Notify the listeners
        Iterator<ELContextListener> iter = listeners.iterator();
        while (iter.hasNext()) {
            ELContextListener elcl = iter.next();
            elcl.contextCreated(new ELContextEvent(elContext));
        }
        return elContext;
    }

    protected static JspApplicationContextImpl findJspApplicationContext(ServletContext context) {

        JspApplicationContextImpl jaContext = map.get(context);
        if (jaContext == null) {
            jaContext = new JspApplicationContextImpl(context);
            map.put(context, jaContext);
        }
        return jaContext;
    }

    public static void removeJspApplicationContext(ServletContext context) {
        map.remove(context);
    }

    protected Iterator<ELResolver> getELResolvers() {
        return elResolvers.iterator();
    }

    private static Map<ServletContext, JspApplicationContextImpl> map = Collections.synchronizedMap(new HashMap<ServletContext, JspApplicationContextImpl>());

    private ArrayList<ELResolver> elResolvers = new ArrayList<ELResolver>();
    private ArrayList<ELContextListener> listeners = new ArrayList<ELContextListener>();
    private ServletContext context;
    private ExpressionFactory expressionFactory;
}
