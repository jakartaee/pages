/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jsp.api;

/**
 * This interface defines additional functionalities a web container can
 * provide for the response writer.  If implementated, perfermance will
 * likely to be improved.
 *
 * @author Kin-man Chung
 */
 
public interface ByteWriter {

    /**
     * Write a portion of a byte array to the output.
     *
     * @param  buff  A byte array
     * @param  off   Offset from which to start reading byte
     * @param  len   Number of bytes to write
     */
    void write(byte buff[], int off, int len)
        throws java.io.IOException;
}
