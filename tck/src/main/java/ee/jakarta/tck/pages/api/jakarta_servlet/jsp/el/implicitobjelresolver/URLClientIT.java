/*
 * Copyright (c) 2007, 2025 Oracle and/or its affiliates and others.
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

/*
 * @(#)URLClient.java
 */

package ee.jakarta.tck.pages.api.jakarta_servlet.jsp.el.implicitobjelresolver;


import ee.jakarta.tck.pages.common.client.AbstractUrlClient;
import ee.jakarta.tck.pages.common.util.JspTestUtil;
import ee.jakarta.tck.pages.common.util.JspResolverTest;
import com.sun.ts.tests.el.common.api.resolver.BarELResolver;
import com.sun.ts.tests.el.common.api.resolver.ResolverTest;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jboss.shrinkwrap.api.asset.UrlAsset;


@ExtendWith(ArquillianExtension.class)
public class URLClientIT extends AbstractUrlClient {




  public URLClientIT() throws Exception {

    setContextRoot("/jsp_implicitobjelresolver_web");
    setTestJsp("ImplicitObjELResolverTest");

    }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException {

    String packagePath = URLClientIT.class.getPackageName().replace(".", "/");
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jsp_implicitobjelresolver_web.war");
    archive.addClasses(ImplicitObjELResolverTag.class,
            JspTestUtil.class,
            JspResolverTest.class,
            BarELResolver.class,
            ResolverTest.class);
    archive.setWebXML(URLClientIT.class.getClassLoader().getResource(packagePath+"/jsp_implicitobjelresolver_web.xml"));
    archive.addAsWebInfResource(URLClientIT.class.getPackage(), "WEB-INF/implicitobjelresolver.tld", "implicitobjelresolver.tld");
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/ImplicitObjELResolverTest.jsp")), "ImplicitObjELResolverTest.jsp");


    return archive;
  }


  /* Run tests */

  // ============================================ Tests ======

  /*
   * @testName: implicitObjElResolverTest
   *
   * @assertion_ids: JSP:JAVADOC:420; JSP:JAVADOC:421; JSP:JAVADOC:422;
   * JSP:JAVADOC:423; JSP:JAVADOC:425; JSP:JAVADOC:426
   *
   * @test_Strategy: Obtain an ImplicitObjectELResolver via the PageContext and
   * verify that API calls work as expected: setValue() setValue() throws
   * PropertyNotWritableException getValue() getType() isReadOnly()
   * getCommonPropertyType() getFeatureDescriptors()
   */
  @Test
  public void implicitObjElResolverTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "implicitObjElResolverTest");
    invoke();
  }
}
