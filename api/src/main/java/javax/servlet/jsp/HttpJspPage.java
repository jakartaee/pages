/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The HttpJspPage interface describes the interaction that a JSP Page Implementation Class must satisfy when using the
 * HTTP protocol.
 *
 * <p>
 * The behaviour is identical to that of the JspPage, except for the signature of the _jspService method, which is now
 * expressible in the Java type system and included explicitly in the interface.
 * 
 * @see JspPage
 */
public interface HttpJspPage extends JspPage {

    /**
     * The _jspService()method corresponds to the body of the JSP page. This method is defined automatically by the JSP
     * container and should never be defined by the JSP page author.
     * <p>
     * If a superclass is specified using the extends attribute, that superclass may choose to perform some actions in
     * its service() method before or after calling the _jspService() method. See using the extends attribute in the
     * JSP_Engine chapter of the JSP specification.
     *
     * @param request  Provides client request information to the JSP.
     * @param response Assists the JSP in sending a response to the client.
     * @throws ServletException Thrown if an error occurred during the processing of the JSP and that the container
     *                          should take appropriate action to clean up the request.
     * @throws IOException      Thrown if an error occurred while writing the response for this page.
     */
    public void _jspService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
