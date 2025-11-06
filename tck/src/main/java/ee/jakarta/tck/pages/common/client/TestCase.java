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

package ee.jakarta.tck.pages.common.client;

/**
 * This interface defines a base set of methods required used by a TS test case.
 */
public interface TestCase {

  /**
   * Executes the test case.
   *
   * @throws Exception
   *           if the test fails for any reason.
   */
  public void execute() throws Exception;

  /**
   * Sets the name of the test case.
   *
   * @param name
   *          of the test case
   */
  public void setName(String name);

  /**
   * Returns the name of this test case.
   *
   * @return test case name
   */
  public String getName();
}
