/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.security;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Static class used to preload java classes when using the
 * Java SecurityManager so that the defineClassInPackage
 * RuntimePermission does not trigger an AccessControlException.
 *
 * @author Jean-Francois Arcand
 */

public final class SecurityClassLoad {

    private static Logger log=
        Logger.getLogger(SecurityClassLoad.class.getName());

    public static void securityClassLoad(ClassLoader loader){

        if( System.getSecurityManager() == null ){
            return;
        }

        String basePackage = "org.apache.jasper.";
        try {
            loader.loadClass( basePackage +
                "runtime.JspFactoryImpl$PrivilegedGetPageContext");
            loader.loadClass( basePackage +
                "runtime.JspFactoryImpl$PrivilegedReleasePageContext");

            loader.loadClass( basePackage +
                "runtime.JspRuntimeLibrary");
            loader.loadClass( basePackage +
                "runtime.JspRuntimeLibrary$PrivilegedIntrospectHelper");
            
            loader.loadClass( basePackage +
                "runtime.ServletResponseWrapperInclude");
            loader.loadClass( basePackage +
                "runtime.TagHandlerPool");
            loader.loadClass( basePackage +
                "runtime.JspFragmentHelper");

            loader.loadClass( basePackage +
                "runtime.ProtectedFunctionMapper");
            loader.loadClass( basePackage +
                "runtime.ProtectedFunctionMapper$1");
            loader.loadClass( basePackage +
                "runtime.ProtectedFunctionMapper$2"); 
            loader.loadClass( basePackage +
                "runtime.ProtectedFunctionMapper$3");
            loader.loadClass( basePackage +
                "runtime.ProtectedFunctionMapper$4"); 

            loader.loadClass( basePackage +
                "runtime.PageContextImpl");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$1");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$2");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$3");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$4");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$5");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$6");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$7");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$8");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$9");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$10");      
            loader.loadClass( basePackage +
                "runtime.PageContextImpl$11");      

            loader.loadClass( basePackage +
                "runtime.JspContextWrapper");   

            loader.loadClass( basePackage +
                "servlet.JspServletWrapper");

            loader.loadClass( basePackage +
                "runtime.JspWriterImpl$1");
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "SecurityClassLoad", ex);
        }
    }
}
