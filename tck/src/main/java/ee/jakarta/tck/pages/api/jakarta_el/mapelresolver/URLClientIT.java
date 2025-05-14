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

package ee.jakarta.tck.pages.api.jakarta_el.mapelresolver;

import ee.jakarta.tck.pages.common.client.AbstractUrlClient;
import ee.jakarta.tck.pages.common.el.resolver.BarELResolver;
import ee.jakarta.tck.pages.common.el.resolver.ResolverTest;
import ee.jakarta.tck.pages.common.util.JspTestUtil;

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

    setContextRoot("/jsp_mapelresolver_web");
    setTestJsp("MapELResolverTest");
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() {

    String packagePath = URLClientIT.class.getPackageName().replace(".", "/");
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jsp_mapelresolver_web.war");
    archive.addClasses(MapELResolverTag.class,
            JspTestUtil.class,
            ResolverTest.class,
            BarELResolver.class);
    archive.setWebXML(URLClientIT.class.getClassLoader().getResource(packagePath+"/jsp_mapelresolver_web.xml"));
    archive.addAsWebInfResource(URLClientIT.class.getPackage(), "WEB-INF/mapelresolver.tld", "mapelresolver.tld");
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/MapELResolverTest.jsp")), "MapELResolverTest.jsp");

    return archive;

  }


  /* Run tests */

  // ============================================ Tests ======

  /*
   * @testName: mapElResolverTest
   *
   * @assertion_ids: EL:JAVADOC:76; EL:JAVADOC:77; EL:JAVADOC:78; EL:JAVADOC:79;
   * EL:JAVADOC:80; EL:JAVADOC:83
   *
   * @test_Strategy: Obtain an MapELResolver via the PageContext and verify that
   * API calls work as expected: setValue() getValue() getType() isReadOnly()
   * getCommonPropertyType() getFeatureDescriptors()
   */
  @Test
  public void mapElResolverTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "mapElResolverTest");
    invoke();
  }
}
