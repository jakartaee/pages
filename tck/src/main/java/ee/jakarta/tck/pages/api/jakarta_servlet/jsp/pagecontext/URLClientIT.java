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
package ee.jakarta.tck.pages.api.jakarta_servlet.jsp.pagecontext;


import ee.jakarta.tck.pages.common.client.AbstractUrlClient;
import ee.jakarta.tck.pages.common.util.JspTestUtil;

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

    setTestJsp("PageContextTest");
    setContextRoot("/jsp_pagecontext_web");

    }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException {

    String packagePath = URLClientIT.class.getPackageName().replace(".", "/");
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jsp_pagecontext_web.war");
    archive.addClasses(JspTestUtil.class);
    archive.setWebXML(URLClientIT.class.getClassLoader().getResource(packagePath+"/jsp_pagecontext_web.xml"));
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/PageContextTest.jsp")), "PageContextTest.jsp");
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/ErrorPage.jsp")), "ErrorPage.jsp");
    archive.add(new UrlAsset(URLClientIT.class.getClassLoader().getResource(packagePath+"/Resource.jsp")), "Resource.jsp");


    return archive;
  }


  /* Run tests */

  // ============================================ Tests ======

  /**
   * @testName: pageContextGetSessionTest
   * @assertion_ids: JSP:JAVADOC:16
   * @test_Strategy: Validate PageContext.getSession().
   */
  @Test
  public void pageContextGetSessionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetSessionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetPageTest
   * @assertion_ids: JSP:JAVADOC:17
   * @test_Strategy: Validate PageContext.getPage().
   */
  @Test
  public void pageContextGetPageTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetPageTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetRequestTest
   * @assertion_ids: JSP:JAVADOC:18
   * @test_Strategy: Validate PageContext.getRequest().
   */
  @Test
  public void pageContextGetRequestTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetRequestTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetResponseTest
   * @assertion_ids: JSP:JAVADOC:19
   * @test_Strategy: Validate PageContext.getResponse().
   */
  @Test
  public void pageContextGetResponseTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetResponseTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetExceptionTest
   * @assertion_ids: JSP:JAVADOC:20
   * @test_Strategy: Validate PageContext.getException().
   */
  @Test
  public void pageContextGetExceptionTest() throws Exception {
    TEST_PROPS.setProperty(TEST_NAME, "pageContextGetExceptionTest");
    TEST_PROPS.setProperty(REQUEST,
        "GET /jsp_pagecontext_web/PageContextTest.jsp?testname=pageContextGetExceptionTest HTTP/1.1");
    TEST_PROPS.setProperty(IGNORE_STATUS_CODE, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED (getException)");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetServletConfigTest
   * @assertion_ids: JSP:JAVADOC:21
   * @test_Strategy: Validate PageContext.getServletConfig().
   */
  @Test
  public void pageContextGetServletConfigTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetServletConfigTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetServletContextTest
   * @assertion_ids: JSP:JAVADOC:22
   * @test_Strategy: Validate PageContext.getServletContext().
   */
  @Test
  public void pageContextGetServletContextTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetServletContextTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextForwardContextPathTest
   * @assertion_ids: JSP:JAVADOC:23
   * @test_Strategy: Validate PageContext.forward() passing in a resource
   *                 identified by a context-relative path.
   */
  @Test
  public void pageContextForwardContextPathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextForwardContextPathTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextForwardPagePathTest
   * @assertion_ids: JSP:JAVADOC:24
   * @test_Strategy: Validate PageContext.forward() passing in a resource
   *                 identified by a page-relative path.
   */
  @Test
  public void pageContextForwardPagePathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextForwardPagePathTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * -- Removed due to the way we capture the exception. -- testName:
   * pageContextForwardServletExceptionTest
   *
   * @assertion_ids: JSP:JAVADOC:26
   * @test_Strategy: Validate PageContext.forward() throws a ServletException if
   *                 a ServletException occurs during the forward process.
   *
   *                 public void pageContextForwardServletExceptionTest() throws
   *                 Fault { TEST_PROPS.setProperty(APITEST,
   *                 "pageContextForwardServletExceptionTest");
   *                 TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error
   *                 page invoked"); invoke(); }
   */

  /**
   * -- Removed due to the way we capture the exception. -- testName:
   * pageContextForwardIOExceptionTest
   *
   * @assertion_ids: JSP:JAVADOC:27
   * @test_Strategy: Validate PageContext.forward() throws an IOExcpetion if an
   *                 IOException occurs during the forward process.
   *
   *                 public void pageContextForwardIOExceptionTest() throws
   *                 Fault { TEST_PROPS.setProperty(APITEST,
   *                 "pageContextForwardIOExceptionTest");
   *                 TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error
   *                 page invoked"); invoke(); }
   */

  /**
   * @testName: pageContextForwardIllegalStateExceptionTest
   * @assertion_ids: JSP:JAVADOC:29
   * @test_Strategy: Valdiate PageContext.forward() throws an
   *                 IllegalStateException if ServletResponse is not in the
   *                 proper state to perform a forward.
   */
  @Test
  public void pageContextForwardIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /jsp_pagecontext_web/PageContextTest.jsp?testname=pageContextForwardIllegalStateExceptionTest HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Arbitrary text|Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeContextPathTest
   * @assertion_ids: JSP:JAVADOC:31
   * @test_Strategy: Validate PageContext.include() where the inclusion resource
   *                 is identified by a context-relative path.
   */
  @Test
  public void pageContextIncludeContextPathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeContextPathTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludePagePathTest
   * @assertion_ids: JSP:JAVADOC:32
   * @test_Strategy: Validate PageContext.include() where the inclusion resource
   *                 is identified by a page-relative path.
   */
  @Test
  public void pageContextIncludePagePathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludePagePathTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeFlushTrueTest
   * @assertion_ids: JSP:JAVADOC:40;JSP:JAVADOC:39
   * @test_Strategy: Validate PageContext.include() with flush argument provided
   *                 and set to true. Response should be commited after the
   *                 flush. Test validates this by perfoming an action against
   *                 the response that would cause an IllegalStateException if
   *                 the response has been comitted. This also validated the
   *                 inclusion of a resource identified by a page-relative path.
   */
  @Test
  public void pageContextIncludeFlushTrueTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeFlushTrueTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "Stream was properly flushed");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeFlushFalseTest
   * @assertion_ids: JSP:JAVADOC:385;JSP:JAVADOC:38
   * @test_Strategy: Validate PageContext.include() with flush argument provided
   *                 and set to false. Response should not have been flushed.
   *                 Verify by preforming an action against the response that
   *                 will throw an IllegalStateException if the response has
   *                 been flushed to the client. This also validated the
   *                 inclusion of a resource identified by a context-relative
   *                 path.
   */
  @Test
  public void pageContextIncludeFlushFalseTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeFlushFalseTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "Test PASSED.|Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextHandlePageExceptionExcTest
   * @assertion_ids: JSP:JAVADOC:46
   * @test_Strategy: Validate PageContext.handlePageException(Exception) invokes
   *                 the defined error page.
   */
  @Test
  public void pageContextHandlePageExceptionExcTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextHandlePageExceptionExcTest");
    TEST_PROPS.setProperty(IGNORE_STATUS_CODE, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, "java.lang.RuntimeException");
    invoke();
  }

  /**
   * @testName: pageContextHandlePageExceptionThrTest
   * @assertion_ids: JSP:JAVADOC:53
   * @test_Strategy: Validate PageContext.handlePageException(Throwable) invokes
   *                 the defined error page.
   */
  @Test
  public void pageContextHandlePageExceptionThrTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextHandlePageExceptionThrTest");
    TEST_PROPS.setProperty(IGNORE_STATUS_CODE, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, "java.lang.Throwable");
    invoke();
  }

  /**
   * @testName: pageContextHandlePageExceptionExcNPETest
   * @assertion_ids: JSP:JAVADOC:51
   * @test_Strategy: Validate PageContext.handlePageException(Exception) throws
   *                 a NullPointerException if an null argument is provided.
   */
  @Test
  public void pageContextHandlePageExceptionExcNPETest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextHandlePageExceptionExcNPETest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextHandlePageExceptionThrNPETest
   * @assertion_ids: JSP:JAVADOC:58
   * @test_Strategy: Validate PageContext.handlePageException(Throwable) throws
   *                 a NullPointerException if an null argument is provided.
   */
  @Test
  public void pageContextHandlePageExceptionThrNPETest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextHandlePageExceptionThrNPETest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextPushPopBodyTest
   * @assertion_ids: JSP:JAVADOC:60;JSP:JAVADOC:61;JSP:JAVADOC:380;JSP:JAVADOC:
   *                 381
   * @test_Strategy: Validate behavior of PageContext.pushBody() and
   *                 PageContext.popBody().
   */
  @Test
  public void pageContextPushPopBodyTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextPushPopBodyTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetErrorDataTest
   * @assertion_ids: JSP:JAVADOC:62
   * @test_Strategy: Validate behavior of PageContext.getErrorData().
   */
  @Test
  public void pageContextGetErrorDataTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetErrorDataTest");
    TEST_PROPS.setProperty(IGNORE_STATUS_CODE, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, "ErrorData object obtained");
    invoke();
  }

  /**
   * @testName: pageContextIncludeIOExceptionTest
   * @assertion_ids: JSP:JAVADOC:35
   * @test_Strategy: Validate PageContext.include() throws an IOException if
   *                 included resource throws an IOException.
   */
  @Test
  public void pageContextIncludeIOExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeIOExceptionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeServletExceptionTest
   * @assertion_ids: JSP:JAVADOC:34
   * @test_Strategy: Validate PageContext.include() throws a ServletException if
   *                 target resource throws a ServletException.
   */
  @Test
  public void pageContextIncludeServletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeServletExceptionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeFlushIOExceptionTest
   * @assertion_ids: JSP:JAVADOC:43
   * @test_Strategy: Validate PageContext.include() with flush argument
   *                 provided, throws an IOException if the target resource
   *                 cannot be accessed by the caller.
   */
  @Test
  public void pageContextIncludeFlushIOExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextIncludeFlushIOExceptionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextIncludeFlushServletExceptionTest
   * @assertion_ids: JSP:JAVADOC:42
   * @test_Strategy: Validate PageContext.include() with flush argument
   *                 provided, throws a ServletException if the target resource
   *                 cannot be accessed by the caller.
   */
  @Test
  public void pageContextIncludeFlushServletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextIncludeFlushServletExceptionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetSetAttributeTest
   * @assertion_ids: JSP:JAVADOC:133;JSP:JAVADOC:138
   * @test_Strategy: Validate PageContext.getAttribute(String),
   *                 PageContext.setAttribute(String). Note: These are inherited
   *                 from JspContext.
   */
  @Test
  public void pageContextGetSetAttributeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetSetAttributeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextSetAttributeNPETest
   * @assertion_ids: JSP:JAVADOC:134;JSP:JAVADOC:396
   * @test_Strategy: Validate a NullPointerException is thrown if the attribute
   *                 name provided is null, but not when the provided value is
   *                 null. Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextSetAttributeNPETest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextSetAttributeNPETest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetSetAttributeInScopeTest
   * @assertion_ids: JSP:JAVADOC:135;JSP:JAVADOC:141
   * @test_Strategy: Validate PageContext.getAttribute() and
   *                 PageContext.setAttribute() when provided with scope
   *                 arguments. Note: These are inherited from JspContext.
   */
  @Test
  public void pageContextGetSetAttributeInScopeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetSetAttributeInScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextSetAttributeInScopeNPETest
   * @assertion_ids: JSP:JAVADOC:136;JSP:JAVADOC:397
   * @test_Strategy: Validate a NullPointerException is thrown if the attribute
   *                 name provided is null, but not when the provided value is
   *                 null. Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextSetAttributeInScopeNPETest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextSetAttributeInScopeNPETest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextSetAttributeInScopeIllegalArgumentExceptionTest
   * @assertion_ids: JSP:JAVADOC:137;JSP:JAVADOC:398
   * @test_Strategy: Validate an IllegalArgumentException is thrown if
   *                 PageContext.setAttribute() is provided an invalid scope.
   *                 Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextSetAttributeInScopeIllegalArgumentExceptionTest()
      throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextSetAttributeInScopeIllegalArgumentExcTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributeNPETest
   * @assertion_ids: JSP:JAVADOC:139;JSP:JAVADOC:142
   * @test_Strategy: Validate a NullPointerException is thrown if
   *                 PageContext.getAttriubte() is provided a null argument.
   *                 Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextGetAttributeNPETest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetAttributeNPETest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributeInScopeIllegalArgumentExceptionTest
   * @assertion_ids: JSP:JAVADOC:143
   * @test_Strategy: Validate an IllegalArgumentException is thrown if
   *                 PageContext.getAttribute() is provided an invalid value for
   *                 scope. Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextGetAttributeInScopeIllegalArgumentExceptionTest()
      throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextGetAttributeInScopeIllegalArgumentExceptionTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextFindAttributeTest
   * @assertion_ids: JSP:JAVADOC:144
   * @test_Strategy: Validate behavior of PageContext.findAttribute(). Note:
   *                 This is inherited from JspContext.
   */
  @Test
  public void pageContextFindAttributeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextFindAttributeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextRemoveAttributeTest
   * @assertion_ids: JSP:JAVADOC:145
   * @test_Strategy: Validate the behavior of PageContext.removeAttribute().
   *                 Note: This is inhertied from JspContext
   */
  @Test
  public void pageContextRemoveAttributeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextRemoveAttributeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextRemoveAttributeFromScopeTest
   * @assertion_ids: JSP:JAVADOC:146
   * @test_Strategy: Validate the behavior of PageContext.remoteAttribute() when
   *                 provided a scope. Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextRemoveAttributeFromScopeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextRemoveAttributeFromScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextRemoveAttributeFromScopeIllegalScopeTest
   * @assertion_ids: JSP:JAVADOC:392
   * @test_Strategy: Validate an IllegalArgumentException is thrown if the scope
   *                 argument is provided an illegal scope.
   */
  @Test
  public void pageContextRemoveAttributeFromScopeIllegalScopeTest()
      throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextRemoveAttributeFromScopeIllegalScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributeScopeTest
   * @assertion_ids: JSP:JAVADOC:147
   * @test_Strategy: Validate the behavior of PageContext.getAttributeScope().
   *                 Note: This is inherited from JspContext.
   */
  @Test
  public void pageContextGetAttributeScopeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetAttributeScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributeNamesInScopeTest
   * @assertion_ids: JSP:JAVADOC:148
   * @test_Strategy: Validate the behavior of
   *                 PageContext.getAttributeNamesInScope(). Note: This is
   *                 inherited from JspContext
   */
  @Test
  public void pageContextGetAttributeNamesInScopeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetAttributeNamesInScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributeNamesInScopeIllegalScopeTest
   * @assertion_ids: JSP:JAVADOC:401
   * @test_Strategy: Validate an IllegalArgumentException is thrown if the scope
   *                 argument is provided an illegal scope.
   */
  @Test
  public void pageContextGetAttributeNamesInScopeIllegalScopeTest()
      throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextGetAttributeNamesInScopeIllegalScopeTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetOutTest
   * @assertion_ids: JSP:JAVADOC:149
   * @test_Strategy: Validate the behavior of PageContext.getOut. Note: This is
   *                 inherited from JspContext.
   */
  @Test
  public void pageContextGetOutTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextGetOutTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextFindAttributeNullNameTest
   * @assertion_ids: JSP:JAVADOC:387
   * @test_Strategy: Validate a NullPointerException is thrown if a null value
   *                 is provided for the name parameter.
   */
  @Test
  public void pageContextFindAttributeNullNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextFindAttributeNullNameTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextGetAttributesScopeNullNameTest
   * @assertion_ids: JSP:JAVADOC:389
   * @test_Strategy: Validate a NullPointerException is thrown if a null value
   *                 is provided to the name argument.
   */
  @Test
  public void pageContextGetAttributesScopeNullNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST,
        "pageContextGetAttributesScopeNullNameTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextRemoveAttributeNullNameTest
   * @assertion_ids: JSP:JAVADOC:393;JSP:JAVADOC:391
   * @test_Strategy: Validate a NullPointerException is thrown if the name
   *                 argument is provided a null value.
   */
  @Test
  public void pageContextRemoveAttributeNullNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextRemoveAttributeNullNameTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }

  /**
   * @testName: pageContextSetAttributeNullValueTest
   * @assertion_ids: JSP:JAVADOC:396;JSP:JAVADOC:397
   * @test_Strategy: Validate that if a null value is provided to either
   *                 setAttribute(String, Object) or setAttribute(String,
   *                 Object, int) it has the same affect as calling
   *                 removeAttribute(String) or removeAttribute(String, int).
   */
  @Test
  public void pageContextSetAttributeNullValueTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "pageContextSetAttributeNullValueTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Error page invoked");
    invoke();
  }
}
