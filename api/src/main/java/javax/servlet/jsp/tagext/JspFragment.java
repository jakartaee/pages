/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp.tagext;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.*;

/**
 * Encapsulates a portion of JSP code in an object that can be invoked as many times as needed. JSP Fragments are
 * defined using JSP syntax as the body of a tag for an invocation to a SimpleTag handler, or as the body of a
 * &lt;jsp:attribute&gt; standard action specifying the value of an attribute that is declared as a fragment, or to be
 * of type JspFragment in the TLD.
 * <p>
 * The definition of the JSP fragment must only contain template text and JSP action elements. In other words, it must
 * not contain scriptlets or scriptlet expressions. At translation time, the container generates an implementation of
 * the JspFragment abstract class capable of executing the defined fragment.
 * <p>
 * A tag handler can invoke the fragment zero or more times, or pass it along to other tags, before returning. To
 * communicate values to/from a JSP fragment, tag handlers store/retrieve values in the JspContext associated with the
 * fragment.
 * <p>
 * Note that tag library developers and page authors should not generate JspFragment implementations manually.
 * <p>
 * <i>Implementation Note</i>: It is not necessary to generate a separate class for each fragment. One possible
 * implementation is to generate a single helper class for each page that implements JspFragment. Upon construction, a
 * discriminator can be passed to select which fragment that instance will execute.
 *
 * @since JSP 2.0
 */
public abstract class JspFragment {

    /**
     * Executes the fragment and directs all output to the given Writer, or the JspWriter returned by the getOut()
     * method of the JspContext associated with the fragment if out is null.
     *
     * @param out The Writer to output the fragment to, or null if output should be sent to JspContext.getOut().
     * @throws javax.servlet.jsp.JspException Thrown if an error occured while invoking this fragment.
     * @throws javax.servlet.jsp.SkipPageException Thrown if the page that (either directly or indirectly) invoked the
     *         tag handler that invoked this fragment is to cease evaluation. The container must throw this exception if
     *         a Classic Tag Handler returned Tag.SKIP_PAGE or if a Simple Tag Handler threw SkipPageException.
     * @throws java.io.IOException If there was an error writing to the stream.
     */
    public abstract void invoke(Writer out) throws JspException, IOException;

    /**
     * Returns the JspContext that is bound to this JspFragment.
     *
     * @return The JspContext used by this fragment at invocation time.
     */
    public abstract JspContext getJspContext();

}
