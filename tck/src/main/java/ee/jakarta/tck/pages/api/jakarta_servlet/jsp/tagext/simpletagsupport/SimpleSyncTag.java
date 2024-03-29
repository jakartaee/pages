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

/*
 * @(#)SimpleSyncTag.java 1.1 11/06/02
 */

package ee.jakarta.tck.pages.api.jakarta_servlet.jsp.tagext.simpletagsupport;

import java.io.IOException;

import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * SimpleTag instance to validate variable synchronization with SimpleTag's
 * declared in the TLD.
 */
public class SimpleSyncTag extends SimpleTagSupport {

  /**
   * Default constructor.
   */
  public SimpleSyncTag() {
    super();
  }

  /**
   * Export PageContext variables to be synced with TEI declared variables.
   * 
   * @throws JspException
   *           if an error occurs
   * @throws IOException
   *           if an I/O error occurs
   */
  public void doTag() throws JspException, IOException {
    JspContext context = this.getJspContext();
    Integer begin = (Integer) context.getAttribute("begin");
    Integer end = (Integer) context.getAttribute("end");
    if (begin == null) {
      context.setAttribute("begin", Integer.valueOf(1));
    } else {
      context.setAttribute("begin", Integer.valueOf(begin.intValue() + 1));
    }
    if (end == null) {
      context.setAttribute("end", Integer.valueOf(2));
    } else {
      context.setAttribute("end", Integer.valueOf(end.intValue() + 1));
    }
  }
}
