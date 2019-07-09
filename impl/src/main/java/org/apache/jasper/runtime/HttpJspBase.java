/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.jasper.runtime;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.Localizer;

/**
 * This is the super class of all JSP-generated servlets.
 *
 * @author Anil K. Vijendran
 */
public abstract class HttpJspBase 
    extends HttpServlet 
    implements HttpJspPage 
{
    protected HttpJspBase() {
    }

    public final void init(ServletConfig config) 
	throws ServletException 
    {
        super.init(config);
	jspInit();
        _jspInit();
    }
    
    public String getServletInfo() {
	return Localizer.getMessage("jsp.engine.info");
    }

    public final void destroy() {
	jspDestroy();
	_jspDestroy();
    }

    /**
     * Entry point into service.
     */
    public final void service(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
    {
        _jspService(request, response);
    }
    
    public void jspInit() {
    }

    public void _jspInit() {
    }

    public void jspDestroy() {
    }

    protected void _jspDestroy() {
    }

    public abstract void _jspService(HttpServletRequest request, 
				     HttpServletResponse response) 
	throws ServletException, IOException;
}
