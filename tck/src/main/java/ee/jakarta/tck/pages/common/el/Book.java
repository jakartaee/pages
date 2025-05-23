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

/*
 * Simple multi-member class to be used for type coercion tests.
 */

package ee.jakarta.tck.pages.common.el;

public class Book {

  private String title;

  private String authors;

  private String publisher;

  private int year;

  public Book(String title, String authors, String publisher, int year) {
    this.title = title;
    this.authors = authors;
    this.publisher = publisher;
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthors() {
    return authors;
  }

  public String getPublisher() {
    return publisher;
  }

  public int getYear() {
    return year;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
