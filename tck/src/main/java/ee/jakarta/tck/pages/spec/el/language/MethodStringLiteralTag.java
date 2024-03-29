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

package ee.jakarta.tck.pages.spec.el.language;

import java.io.IOException;

import ee.jakarta.tck.pages.common.util.JspTestUtil;

import jakarta.el.ELContext;
import jakarta.el.MethodExpression;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class MethodStringLiteralTag extends SimpleTagSupport {

  private static final String EXPECTED_VALUE = "literal";

  private MethodExpression mexp;

  public void setLiteralMethExpr(MethodExpression mexp) {
    this.mexp = mexp;
  }

  public void doTag() throws JspException, IOException {

    JspWriter out = getJspContext().getOut();
    ELContext elContext = getJspContext().getELContext();

    try {
      Object result = mexp.invoke(elContext, null);
      if (!(result instanceof String)) {
        out.println("Test FAILED.  Return value is not a String:\n");
        out.println(result.getClass() + "\n");
        return;
      }
      String resultStr = (String) result;
      if (!(resultStr.equals(EXPECTED_VALUE))) {
        out.println("Test FAILED.  Incorrect return value.\n");
        out.println("Expected value = " + EXPECTED_VALUE + "\n");
        out.println("Value returned = " + resultStr + "\n");
        return;
      }
      out.println("Test PASSED.");

    } catch (Throwable t) {
      out.println("Test FAILED: Exception in tag handler\n");
      JspTestUtil.handleThrowable(t, out, "MethodStringLiteralTag");
    }
  }
}
