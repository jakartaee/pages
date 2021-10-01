/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package jakarta.servlet.jsp.el;

import java.util.ResourceBundle;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.ELException;
import jakarta.el.PropertyNotFoundException;

/**
 * Defines variable resolution when all other resolvers fail.
 *
 * @since JSP 3.1
 */
public class NotFoundELResolver extends ELResolver {

    private static final String LSTRING_FILE = "jakarta.servlet.jsp.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    /**
     * If the base object is <code>null</code>, searches the Class and static imports for an import with the given name
     * and returns it if an import exists with the given name.
     *
     * <p>
     * The <code>propertyResolved</code> property of the <code>ELContext</code> object is always set to {@code true}
     * by this resolver before returning.
     * </p>
     *
     * @param context  The context of this evaluation.
     * @param base     Ignored
     * @param property Ignored
     * @return Always {@code null}
     * @throws NullPointerException if context is <code>null</code>
     * @throws PropertyNotFoundException
     *                              If the provided context contains a Boolean object with value {@code Boolean.TRUE} as
     *                              the value associated with the key
     *                              {@code jakarta.servlet.jsp.el.NotFoundELResolver.class}. This is to support
     *                              implementation of the {@code errorOnELNotFound} page/tag directive.
     * @throws ELException          if an exception was thrown while performing the property or variable resolution. The
     *                              thrown exception must be included as the cause property of this exception, if
     *                              available.
     */
    @Override
    public Object getValue(ELContext context, Object base, Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        Object obj = context.getContext(this.getClass());
        if (obj instanceof Boolean && ((Boolean) obj).booleanValue()) {
            throw new PropertyNotFoundException(
                    lStrings.getString("el.unknown.identifier") + " [" + property.toString() + "]");
        }
        
        context.setPropertyResolved(true);

        return null;
    }

    /**
     * Always returns {@code null} since in normal usage {@link ScopedAttributeELResolver} will handle calls to
     * {@link ELResolver#getType(ELContext, Object, Object)}.
     *
     * @param context  The context of this evaluation.
     * @param base     Ignored
     * @param property Ignored
     * @return Always {@code null}
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

        return null;
    }

    /**
     * Always a NO-OP since in normal usage {@link ScopedAttributeELResolver} will handle calls to
     * {@link ELResolver#setValue(ELContext, Object, Object, Object)}.
     * 
     * @param context  The context of this evaluation.
     * @param base     Ignored
     * @param property Ignored
     * @param val      Ignored
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
    }

    /**
     * Always returns {@code false} since in normal usage {@link ScopedAttributeELResolver} will handle calls to
     * {@link ELResolver#isReadOnly(ELContext, Object, Object)}.
     *
     * @param context  The context of this evaluation.
     * @param base     Ignored
     * @param property Ignored
     * @return Always {@code false}
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
        return false;
    }

    /**
     * Always returns {@code null} since in normal usage {@link ScopedAttributeELResolver} will handle calls to
     * {@link ELResolver#getCommonPropertyType(ELContext, Object)}.
     *
     * @param context Ignored
     * @param base    Ignored
     * 
     * @return Always {@code null}
     */
    @Override
    public Class<String> getCommonPropertyType(ELContext context, Object base) {
        return null;
    }
}
