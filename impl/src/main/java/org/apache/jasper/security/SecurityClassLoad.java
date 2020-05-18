/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
