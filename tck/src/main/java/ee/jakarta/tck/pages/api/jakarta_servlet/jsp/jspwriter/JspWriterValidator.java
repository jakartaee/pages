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
 * $Id$
 */

package ee.jakarta.tck.pages.api.jakarta_servlet.jsp.jspwriter;

import java.util.List;

import ee.jakarta.tck.pages.common.client.WebTestCase;
import ee.jakarta.tck.pages.common.client.http.HttpResponse;
import ee.jakarta.tck.pages.common.client.validation.WebValidatorBase;

import java.lang.System.Logger;

public class JspWriterValidator extends WebValidatorBase {

  private static final Logger logger = System.getLogger(JspWriterValidator.class.getName());

  private static final String UNIX_LINE_SEPARATOR = "\n";

  private static final String WIN32_LINE_SEPARATOR = "\r\n";

  private static final String EOL_HEADER = "Server-EOL";

  /**
   * This validator preprocesses the search strings of the test case. It will
   * replace all intances of <code>#eol#</code> with the line separator used by
   * the container.
   *
   * @param testCase
   *          - The test case to validate
   * @return true if the test passes, otherwise, false.
   */
  public boolean validate(WebTestCase testCase) {
    String eol = null;
    String eolToken = "#eol#";
    HttpResponse response = testCase.getResponse();
    if ("UNIX".equals(response.getResponseHeader(EOL_HEADER))) {
      // UNIX
      eol = UNIX_LINE_SEPARATOR;
    } else if ("WIN32".equals(response.getResponseHeader(EOL_HEADER))) {
      // win32
      eol = WIN32_LINE_SEPARATOR;
    } else {
      // no header sent -- default the value to the client side line.separator
      eol = System.getProperty("line.separator");
    }

    // get the search strings and replace any '|eol|' tokens with the
    // line separator of the server side
    List searchList = testCase.getSearchStrings();
    for (int i = 0, size = searchList.size(); i < size; i++) {
      String string = (String) searchList.get(i);
      logger.log(Logger.Level.TRACE, "[JspWriterValidator] Processing search string: " + string);
      for (int index = string.indexOf(eolToken); index > -1; index = string
          .indexOf(eolToken)) {
        StringBuffer sb = new StringBuffer(string);
        sb.replace(index, index + 5, eol);
        string = sb.toString();
      }
      logger.log(Logger.Level.TRACE, "[JspWriterValidator] Adding (possibly) modified "
          + "search string: " + string);
      searchList.set(i, string);
    }

    return super.validate(testCase);
  }
}
