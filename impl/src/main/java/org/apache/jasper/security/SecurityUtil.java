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

import org.apache.jasper.Constants;

/**
 * Util class for Security related operations.
 *
 * @author Jean-Francois Arcand
 */

public final class SecurityUtil{
    
    private static boolean packageDefinitionEnabled =  
         System.getProperty("package.definition") == null ? false : true;
    
    /**
     * Return the <code>SecurityManager</code> only if Security is enabled AND
     * package protection mechanism is enabled.
     */
    public static boolean isPackageProtectionEnabled(){
        if (packageDefinitionEnabled && Constants.IS_SECURITY_ENABLED){
            return true;
        }
        return false;
    }
    
    
}
