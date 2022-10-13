/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package jakarta.servlet.jsp.el;

import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.JspContext;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.ELException;

/**
 * Defines variable resolution behavior for scoped attributes.
 *
 * <p>
 * This resolver handles variable resolutions where <code>base</code> is <code>null</code>. It searches
 * <code>PageContext.findAttribute()</code> for a matching attribute. If not found in the case of
 * <code>setValue</code>, it will create a new attribute in the page scope with the given name.
 * </p>
 *
 * @see jakarta.el.ELResolver
 * @since JSP 2.1
 */
public class ScopedAttributeELResolver extends ELResolver {

    /**
     * If the base object is <code>null</code>, searches the page, request, session and application scopes for an
     * attribute with the given name and returns it if an attribute exists with the current name.
     *
     * <p>
     * The <code>propertyResolved</code> property of the <code>ELContext</code> object must be set to <code>true</code>
     * by this resolver before returning if a scoped attribute is matched. If this property is not <code>true</code>
     * after this method is called, the caller should ignore the return value.
     * </p>
     *
     * @param context  The context of this evaluation.
     * @param base     Only <code>null</code> is handled by this resolver. Other values will result in an immediate
     *                 return.
     * @param property The name of the scoped attribute to resolve.
     * @return If the <code>propertyResolved</code> property of <code>ELContext</code> was set to <code>true</code>,
     *         then the scoped attribute; otherwise undefined.
     * @throws NullPointerException if context is <code>null</code>
     * @throws ELException          if an exception was thrown while performing the property or variable resolution. The
     *                              thrown exception must be included as the cause property of this exception, if
     *                              available.
     */
    @Override
    public Object getValue(ELContext context, Object base, Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            if (property instanceof String) {
                String attribute = (String) property;
                PageContext ctxt = (PageContext) context.getContext(JspContext.class);
                Object value = ctxt.findAttribute(attribute);
                if (value != null) {
                    context.setPropertyResolved(true);
                }
                return value;
            }
        }
        return null;
    }

    /**
     * If the base object is <code>null</code>, returns <code>Object.class</code> to indicate that any type is valid to
     * set for a scoped attribute.
     *
     * <p>
     * The <code>propertyResolved</code> property of the <code>ELContext</code> object must be set to <code>true</code>
     * by this resolver before returning if base is <code>null</code>. If this property is not <code>true</code> after
     * this method is called, the caller should ignore the return value.
     * </p>
     *
     * @param context  The context of this evaluation.
     * @param base     Only <code>null</code> is handled by this resolver. Other values will result in an immediate
     *                 return.
     * @param property The name of the scoped attribute to resolve.
     * @return If the <code>propertyResolved</code> property of <code>ELContext</code> was set to <code>true</code>,
     *         then <code>Object.class</code>; otherwise undefined.
     * @throws NullPointerException if context is <code>null</code>
     * @throws ELException          if an exception was thrown while performing the property or variable resolution. The
     *                              thrown exception must be included as the cause property of this exception, if
     *                              available.
     */
    @Override
    public Class<Object> getType(ELContext context, Object base, Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            return Object.class;
        }
        return null;
    }

    /**
     * If the base object is <code>null</code>, sets an existing scoped attribute to the new value, or creates a new
     * scoped attribute if one does not exist by this name.
     *
     * <p>
     * If the provided attribute name matches the key of an attribute in page scope, request scope, session scope, or
     * application scope, the corresponding attribute value will be replaced by the provided value. Otherwise, a new
     * page scope attribute will be created with the given name and value.
     * </p>
     *
     * <p>
     * The <code>propertyResolved</code> property of the <code>ELContext</code> object must be set to <code>true</code>
     * by this resolver before returning if base is <code>null</code>. If this property is not <code>true</code> after
     * this method is called, the caller should ignore the return value.
     * </p>
     *
     * @param context  The context of this evaluation.
     * @param base     Only <code>null</code> is handled by this resolver. Other values will result in an immediate
     *                 return.
     * @param property The name of the scoped attribute to set.
     * @param val      The value for the scoped attribute.
     * @throws NullPointerException if context is <code>null</code>.
     * @throws ELException          if an exception was thrown while performing the property or variable resolution. The
     *                              thrown exception must be included as the cause property of this exception, if
     *                              available.
     */
    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            if (property instanceof String) {
                PageContext ctxt = (PageContext) context.getContext(JspContext.class);
                String attr = (String) property;
                if (ctxt.getAttribute(attr, PageContext.REQUEST_SCOPE) != null)
                    ctxt.setAttribute(attr, val, PageContext.REQUEST_SCOPE);
                else if (ctxt.getAttribute(attr, PageContext.SESSION_SCOPE) != null)
                    ctxt.setAttribute(attr, val, PageContext.SESSION_SCOPE);
                else if (ctxt.getAttribute(attr, PageContext.APPLICATION_SCOPE) != null)
                    ctxt.setAttribute(attr, val, PageContext.APPLICATION_SCOPE);
                else {
                    ctxt.setAttribute(attr, val, PageContext.PAGE_SCOPE);
                }
            }
        }
    }

    /**
     * If the base object is <code>null</code>, returns <code>false</code> to indicate that scoped attributes are never
     * read-only.
     *
     * <p>
     * The <code>propertyResolved</code> property of the <code>ELContext</code> object must be set to <code>true</code>
     * by this resolver before returning if base is <code>null</code>. If this property is not <code>true</code> after
     * this method is called, the caller should ignore the return value.
     * </p>
     *
     * @param context  The context of this evaluation.
     * @param base     Only <code>null</code> is handled by this resolver. Other values will result in an immediate
     *                 return.
     * @param property The name of the scoped attribute.
     * @return If the <code>propertyResolved</code> property of <code>ELContext</code> was set to <code>true</code>,
     *         then <code>false</code>; otherwise undefined.
     * @throws NullPointerException if context is <code>null</code>.
     * @throws ELException          if an exception was thrown while performing the property or variable resolution. The
     *                              thrown exception must be included as the cause property of this exception, if
     *                              available.
     */
    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
        }
        return false;
    }

    /**
     * If the base object is <code>null</code>, returns <code>String.class</code>. Otherwise, returns <code>null</code>.
     *
     * @param context The context of this evaluation.
     * @param base    Only <code>null</code> is handled by this resolver. Other values will result in a
     *                <code>null</code> return value.
     * @return <code>null</code> if base is not <code>null</code>; otherwise <code>String.class</code>.
     */
    @Override
    public Class<String> getCommonPropertyType(ELContext context, Object base) {
        if (base == null) {
            return String.class;
        }
        return null;
    }

}
