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

/*
 * $Id$
 */

package ee.jakarta.tck.pages.spec.core_syntax.actions.body;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.jsp.tagext.DynamicAttributes;

public class ClassicEmptyBodyTag extends BodyTagSupport
    implements DynamicAttributes {

  public void setDynamicAttribute(String s, String s1, Object o)
      throws JspException {
    // no-op
  }

  public int doEndTag() throws JspException {
    JspWriter out = pageContext.getOut();
    try {
      if (getBodyContent() == null) {
        out.println("Test PASSED");
      } else {
        out.println("Test FAILED.  The body of the action was not empty");
      }
    } catch (IOException ioe) {
      throw new JspException("Unexpected IOException!", ioe);
    }
    return EVAL_PAGE;
  }
}
