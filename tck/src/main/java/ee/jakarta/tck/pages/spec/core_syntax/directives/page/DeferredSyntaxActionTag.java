/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.pages.spec.core_syntax.directives.page;

import java.io.IOException;

import ee.jakarta.tck.pages.common.util.JspTestUtil;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class DeferredSyntaxActionTag extends SimpleTagSupport {

  private String literal;

  public void setLiteral(String literal) {
    this.literal = literal;
  }

  public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    try {
      out.println(literal);
    } catch (Throwable t) {
      JspTestUtil.handleThrowable(t, out, "DeferredSyntaxActionTag");
    }
  }
}
