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

package ee.jakarta.tck.pages.common.client.handler;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.apache.commons.httpclient.Header;

public class SetCookieHandler implements Handler {

  private static final Logger LOGGER = Logger.getLogger(LocationHandler.class.getName());

  private static final Handler HANDLER = new SetCookieHandler();

  private static final String DELIM = "##";

  private SetCookieHandler() {
  }

  public static Handler getInstance() {
    return HANDLER;
  }

  @Override
  public boolean invoke(Header configuredHeader, Header responseHeader) {
    String setCookieHeader = responseHeader.getValue().toLowerCase();
    String expectedValues = configuredHeader.getValue().toLowerCase();

    LOGGER.finer("Set-Cookie header received: " + setCookieHeader);

    StringTokenizer conf = new StringTokenizer(expectedValues, DELIM);
    while (conf.hasMoreTokens()) {
      String token = conf.nextToken();
      String token1 = token;

      if (token.endsWith("\"") && (token.indexOf("=\"") > 1)) {
        token1 = token.replace("=\"", "=");
        token1 = token1.substring(0, token.length() - 2);
      }

      if (token.startsWith("!")) {
        String attr = token.substring(1);
        String attr1 = token1.substring(1);
        if ((setCookieHeader.indexOf(attr) > -1)
            || (setCookieHeader.indexOf(attr1) > -1)) {
          LOGGER.severe("Unexpected attribute found "
              + " Set-Cookie header.  Attribute: " + attr
              + "\nSet-Cookie header: " + setCookieHeader);
          return false;
        }
      } else {
        if ((setCookieHeader.indexOf(token) < 0)
            && (setCookieHeader.indexOf(token1) < 0)) {
          LOGGER.severe("Unable to find '" + token
              + "' within the Set-Cookie header returned by the server.");
          return false;
        } else {
          LOGGER.severe("Found expected value, '" + token
              + "' in Set-Cookie header returned by server.");
        }
      }
    }

    return true;
  }
}
