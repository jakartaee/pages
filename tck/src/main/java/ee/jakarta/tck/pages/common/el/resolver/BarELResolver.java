/*
 * Copyright (c) 2018, 2025 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package ee.jakarta.tck.pages.common.el.resolver;

import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ELResolver;
import jakarta.el.PropertyNotWritableException;

public class BarELResolver extends ELResolver {

  @Override
  public Object getValue(ELContext context, Object base, Object property)
      throws ELException {
    Object result = null;
    if (context == null)
      throw new NullPointerException();

    if (base == null) {
      // Resolving first variable (e.g. ${Bar}).
      // We only handle "Bar"
      String propertyName = (String) property;
      if (propertyName.equals("Bar")) {
        result = "Foo";
        context.setPropertyResolved(true);
      }
    }
    return result;
  }

  @Override
  public Class<?> getType(ELContext context, Object base, Object property)
      throws ELException {
    if (context == null)
      throw new NullPointerException();

    if (base == null && property instanceof String
        && property.toString().equals("Bar")) {
      context.setPropertyResolved(true);
    }

    // we never set a value
    return null;
  }

  @Override
  public void setValue(ELContext context, Object base, Object property,

      Object value) throws ELException {
    if (context == null)
      throw new NullPointerException();

    if (base == null && property instanceof String
        && property.toString().equals("Bar")) {
      context.setPropertyResolved(true);
      throw new PropertyNotWritableException();
    }
  }

  @Override
  public boolean isReadOnly(ELContext context, Object base, Object property)
      throws ELException {
    if (context == null)
      throw new NullPointerException();

    if (base == null && property instanceof String
        && property.toString().equals("Bar")) {
      context.setPropertyResolved(true);
    }

    return true;
  }

  @Override
  public Class<?> getCommonPropertyType(ELContext context, Object base) {
    if (context == null)
      throw new NullPointerException();

    if (base == null)
      return String.class;
    return null;
  }
}
