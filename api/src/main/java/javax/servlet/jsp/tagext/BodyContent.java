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

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import javax.servlet.jsp.*;

/**
 * An encapsulation of the evaluation of the body of an action so it is available to a tag handler. BodyContent is a
 * subclass of JspWriter.
 *
 * <p>
 * Note that the content of BodyContent is the result of evaluation, so it will not contain actions and the like, but
 * the result of their invocation.
 * 
 * <p>
 * BodyContent has methods to convert its contents into a String, to read its contents, and to clear the contents.
 *
 * <p>
 * The buffer size of a BodyContent object is unbounded. A BodyContent object cannot be in autoFlush mode. It is not
 * possible to invoke flush on a BodyContent object, as there is no backing stream.
 *
 * <p>
 * Instances of BodyContent are created by invoking the pushBody and popBody methods of the PageContext class. A
 * BodyContent is enclosed within another JspWriter (maybe another BodyContent object) following the structure of their
 * associated actions.
 *
 * <p>
 * A BodyContent is made available to a BodyTag through a setBodyContent() call. The tag handler can use the object
 * until after the call to doEndTag().
 */
public abstract class BodyContent extends JspWriter {

    /**
     * Protected constructor.
     *
     * Unbounded buffer, no autoflushing.
     *
     * @param e the enclosing JspWriter
     */
    protected BodyContent(JspWriter e) {
        super(UNBOUNDED_BUFFER, false);
        this.enclosingWriter = e;
    }

    /**
     * Redefined flush() so it is not legal.
     *
     * <p>
     * It is not valid to flush a BodyContent because there is no backing stream behind it.
     *
     * @throws IOException always thrown
     */
    @Override
    public void flush() throws IOException {
        throw new IOException("Illegal to flush within a custom tag");
    }

    /**
     * Clear the body without throwing any exceptions.
     */
    public void clearBody() {
        try {
            this.clear();
        } catch (IOException ex) {
            // TODO -- clean this one up.
            throw new Error("internal error!;");
        }
    }

    /**
     * Return the value of this BodyContent as a Reader.
     *
     * @return the value of this BodyContent as a Reader
     */
    public abstract Reader getReader();

    /**
     * Return the value of the BodyContent as a String.
     *
     * @return the value of the BodyContent as a String
     */
    public abstract String getString();

    /**
     * Write the contents of this BodyContent into a Writer. Subclasses may optimize common invocation patterns.
     *
     * @param out The writer into which to place the contents of this body evaluation
     * @throws IOException if an I/O error occurred while writing the contents of this BodyContent to the given Writer
     */
    public abstract void writeOut(Writer out) throws IOException;

    /**
     * Get the enclosing JspWriter.
     *
     * @return the enclosing JspWriter passed at construction time
     */
    public JspWriter getEnclosingWriter() {
        return enclosingWriter;
    }

    // private fields

    private JspWriter enclosingWriter;
}
