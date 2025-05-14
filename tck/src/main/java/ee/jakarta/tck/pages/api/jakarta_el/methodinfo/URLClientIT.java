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

package ee.jakarta.tck.pages.api.jakarta_el.methodinfo;

import ee.jakarta.tck.pages.common.client.AbstractUrlClient;
import ee.jakarta.tck.pages.common.el.expression.ExpressionTest;
import ee.jakarta.tck.pages.common.util.JspTestUtil;
import ee.jakarta.tck.pages.common.tags.tck.SetTag;

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

    setContextRoot("/jsp_methodinfo_web");
    setTestJsp("MethodInfoTest");
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() {

    String packagePath = URLClientIT.class.getPackageName().replace(".", "/");
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jsp_methodinfo_web.war");
    archive.addClasses(MethodInfoTag.class,
              JspTestUtil.class,
              SetTag.class,
              ExpressionTest.class);
    archive.setWebXML(URLClientIT.class.getClassLoader().getResource(packagePath+"/jsp_methodinfo_web.xml"));
    archive.addAsWebInfResource(URLClientIT.class.getPackage(), "WEB-INF/methodinfo.tld", "methodinfo.tld");
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/MethodInfoTest.jsp")), "MethodInfoTest.jsp");

    return archive;

  }

  /* Run tests */

  // ============================================ Tests ======

  /*
   * @testName: methodInfoTest
   *
   * @assertion_ids: EL:JAVADOC:84; EL:JAVADOC:85; EL:JAVADOC:87; EL:JAVADOC:88;
   * EL:JAVADOC:89
   *
   * @test_Strategy: Validate the behavior of MethodExpression and MethodInfo
   * class methods: MethodExpression.getMethodInfo() MethodExpression.invoke()
   * MethodInfo.getName() MethodInfo.getReturnType() MethodInfo.getParamTypes()
   */
  @Test
  public void methodInfoTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "methodInfoTest");
    invoke();
  }
}
