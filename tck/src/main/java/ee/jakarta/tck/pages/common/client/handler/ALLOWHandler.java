/*
 * Copyright (c) 2008, 2025 Oracle and/or its affiliates and others.
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

package ee.jakarta.tck.pages.common.client.handler;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.apache.commons.httpclient.Header;

public class ALLOWHandler implements Handler {

  private static final Logger LOGGER = Logger.getLogger(ALLOWHandler.class.getName());

  private static final Handler HANDLER = new ALLOWHandler();

  private ALLOWHandler() {
  }

  public static Handler getInstance() {
    return HANDLER;
  }

  @Override
  public boolean invoke(Header configuredHeader, Header responseHeader) {
    String ALLOWHeader = responseHeader.getValue().toLowerCase();
    String expectedValues = configuredHeader.getValue().toLowerCase()
        .replace(" ", "");

    LOGGER.finer("ALLOW header received: " + ALLOWHeader);

    StringTokenizer conf = new StringTokenizer(expectedValues, ",");
    while (conf.hasMoreTokens()) {
      String token = conf.nextToken();
      String token1 = token;

      if ((ALLOWHeader.indexOf(token) < 0)
          && (ALLOWHeader.indexOf(token1) < 0)) {
        LOGGER.severe("Unable to find '" + token
            + "' within the ALLOW header returned by the server.");
        return false;
      } else {
          LOGGER.finer("Found expected value, '" + token
            + "' in ALLOW header returned by server.");
      }
    }
    return true;
  }
}
