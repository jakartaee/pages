/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.servlet.jsp.tagext.JspTag;

/**
 * Interface for injecting injectable resources into tag handler instances.
 *
 * @author Jan Luehe
 */
public interface ResourceInjector {

    /**
     * Instantiates and injects the given tag handler class.
     *
     * @param clazz the tag handler class to be instantiated and injected
     *
     * @throws Exception if an error has occurred during instantiation or injection
     */
    public <T extends JspTag> T createTagHandlerInstance(Class<T> clazz) throws Exception;

    /**
     * Invokes any @PreDestroy methods defined on the instance's class (and super-classes).
     *
     * @param handler The tag handler instance whose @PreDestroy methods to call
     */
    public void preDestroy(JspTag handler);
}
