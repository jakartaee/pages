/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.pages.api.jakarta_servlet.jsp.jspapplicationcontext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.jsp.JspApplicationContext;
import jakarta.servlet.jsp.JspFactory;

/**
 * ServletContextListener that installs the FooELResolver, enabling the use of
 * ${Foo}.
 */
public class InstallFooListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent evt) {
    ServletContext context = evt.getServletContext();
    JspApplicationContext jspContext = JspFactory.getDefaultFactory()
        .getJspApplicationContext(context);
    jspContext.addELResolver(new FooELResolver());
  }

  public void contextDestroyed(ServletContextEvent evt) {
  }
}
