/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.compiler;


import java.util.HashMap;

import org.apache.jasper.JasperException;

/**
 * Repository of {page, request, session, application}-scoped beans 
 *
 * @author Mandar Raje
 */
class BeanRepository {

    private HashMap<String, String> beanTypes;
    private ClassLoader loader;
    private ErrorDispatcher errDispatcher;

    /*
     * Constructor.
     */    
    public BeanRepository(ClassLoader loader, ErrorDispatcher err) {

        this.loader = loader;
	this.errDispatcher = err;
	beanTypes = new HashMap<String, String>();
    }
        
    public void addBean(Node.UseBean n, String s, String type, String scope)
	    throws JasperException {

	if (scope == null || scope.equals("page") ||
	    scope.equals("request") ||
	    scope.equals("session") ||
	    scope.equals("application") )
        {
	    beanTypes.put(s, type);
	} else {
	    errDispatcher.jspError(n, "jsp.error.invalid.scope", scope);
	}
	
    }
            
    public Class getBeanType(String bean) throws JasperException {
	Class clazz = null;
	try {
	    clazz = loader.loadClass (beanTypes.get(bean));
	} catch (ClassNotFoundException ex) {
	    throw new JasperException (ex);
	}
	return clazz;
    }
      
    public boolean checkVariable (String bean) {
        return beanTypes.containsKey(bean);
    }

}




