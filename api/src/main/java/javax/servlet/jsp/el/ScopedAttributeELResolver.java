/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
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

package javax.servlet.jsp.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspContext;

import javax.el.ELContext;
import javax.el.ELClass;
import javax.el.ELResolver;
import javax.el.ELException;

/**
 * Defines variable resolution behavior for scoped attributes.
 *
 * <p>
 * This resolver handles all variable resolutions (where <code>base</code> is <code>null</code>. It searches
 * <code>PageContext.findAttribute()</code> for a matching attribute. If not found, it will return <code>null</code>, or
 * in the case of <code>setValue</code> it will create a new attribute in the page scope with the given name.
 * </p>
 *
 * @see javax.el.ELResolver
 * @since JSP 2.1
 */
public class ScopedAttributeELResolver extends ELResolver {

    /**
     * If the base object is <code>null</code>, searches the page, request, session and application scopes for an
     * attribute with the given name and returns it, or <code>null</code> if no attribute exists with the current name.
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
            context.setPropertyResolved(true);
            if (property instanceof String) {
                String attribute = (String) property;
                PageContext ctxt = (PageContext) context.getContext(JspContext.class);
                Object value = ctxt.findAttribute(attribute);
                // To support reference of static fields for imported class in
                // EL 3.0, if a scoped attribute returns null, this attribute
                // is further checked to see if it is the name of an imported
                // class. If so, an ELClass instance is returned.
                // Note: the JSP spec needs to be updated for this behavior. Note
                // also that this behavior is not backward compatible with JSP 2.2
                // and a runtime switch may be needed to force backward
                // compatility.
                if (value == null) {
                    // check to see if the property is an imported class
                    if (context.getImportHandler() != null) {
                        Class<?> c = context.getImportHandler().resolveClass(attribute);
                        if (c != null) {
                            value = new ELClass(c);
                            // A possible optimization is to set the ELClass
                            // instance in an attribute map.
                        }
                    }
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
     * If the base object is <code>null</code>, returns an <code>Iterator</code> containing
     * <code>FeatureDescriptor</code> objects with information about each scoped attribute resolved by this resolver.
     * Otherwise, returns <code>null</code>.
     *
     * <p>
     * The <code>Iterator</code> returned must contain one instance of {@link java.beans.FeatureDescriptor} for each
     * scoped attribute found in any scope. Each info object contains information about a single scoped attribute, and
     * is initialized as follows:
     * </p>
     *
     * <dl>
     * <dt>displayName</dt><dd>- The name of the scoped attribute.</dd>
     * <dt>name</dt><dd>- Same as displayName property.</dd>
     * <dt>shortDescription</dt><dd>- A suitable description for the scoped attribute. Should include the attribute's current
     * scope (page, request, session, application). Will vary by implementation.</dd>
     * <dt>expert</dt><dd>- <code>false</code></dd>
     * <dt>hidden</dt><dd>- <code>false</code></dd>
     * <dt>preferred</dt><dd>- <code>true</code></dd>
     * </dl>
     * In addition, the following named attributes must be set in the returned <code>FeatureDescriptor</code>s:
     * <dl>
     * <dt>{@link ELResolver#TYPE}</dt><dd>- The current runtime type of the scoped attribute.</dd>
     * <dt>{@link ELResolver#RESOLVABLE_AT_DESIGN_TIME}</dt><dd>- <code>true</code>.</dd>
     * </dl>
     * 
     * @param context The context of this evaluation.
     * @param base    Only <code>null</code> is handled by this resolver. Other values will result in a
     *                <code>null</code> return value.
     * @return An <code>Iterator</code> containing one <code>FeatureDescriptor</code> object for each scoped attribute,
     *         or <code>null</code> if <code>base</code> is not <code>null</code>.
     */
    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        Enumeration<String> attrs;
        ArrayList<FeatureDescriptor> list = new ArrayList<>();
        PageContext ctxt = (PageContext) context.getContext(JspContext.class);

        attrs = ctxt.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
        while (attrs.hasMoreElements()) {
            String name = attrs.nextElement();
            Object value = ctxt.getAttribute(name, PageContext.PAGE_SCOPE);
            FeatureDescriptor descriptor = new FeatureDescriptor();
            descriptor.setName(name);
            descriptor.setDisplayName(name);
            descriptor.setShortDescription("page scope attribute");
            descriptor.setExpert(false);
            descriptor.setHidden(false);
            descriptor.setPreferred(true);
            descriptor.setValue("type", value.getClass());
            descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
            list.add(descriptor);
        }

        attrs = ctxt.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
        while (attrs.hasMoreElements()) {
            String name = attrs.nextElement();
            Object value = ctxt.getAttribute(name, PageContext.REQUEST_SCOPE);
            FeatureDescriptor descriptor = new FeatureDescriptor();
            descriptor.setName(name);
            descriptor.setDisplayName(name);
            descriptor.setShortDescription("request scope attribute");
            descriptor.setExpert(false);
            descriptor.setHidden(false);
            descriptor.setPreferred(true);
            descriptor.setValue("type", value.getClass());
            descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
            list.add(descriptor);
        }

        attrs = ctxt.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
        while (attrs.hasMoreElements()) {
            String name = attrs.nextElement();
            Object value = ctxt.getAttribute(name, PageContext.SESSION_SCOPE);
            FeatureDescriptor descriptor = new FeatureDescriptor();
            descriptor.setName(name);
            descriptor.setDisplayName(name);
            descriptor.setShortDescription("session scope attribute");
            descriptor.setExpert(false);
            descriptor.setHidden(false);
            descriptor.setPreferred(true);
            descriptor.setValue("type", value.getClass());
            descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
            list.add(descriptor);
        }

        attrs = ctxt.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE);
        while (attrs.hasMoreElements()) {
            String name = attrs.nextElement();
            Object value = ctxt.getAttribute(name, PageContext.APPLICATION_SCOPE);
            FeatureDescriptor descriptor = new FeatureDescriptor();
            descriptor.setName(name);
            descriptor.setDisplayName(name);
            descriptor.setShortDescription("application scope attribute");
            descriptor.setExpert(false);
            descriptor.setHidden(false);
            descriptor.setPreferred(true);
            descriptor.setValue("type", value.getClass());
            descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
            list.add(descriptor);
        }
        return list.iterator();
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
