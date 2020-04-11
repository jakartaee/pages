/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.apache.jasper.runtime;

import java.lang.IllegalStateException;
import java.io.PrintWriter;
import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.JspWriter;

import org.glassfish.jsp.api.ByteWriter;

/**
 * ServletResponseWrapper used by the JSP 'include' action.
 *
 * This wrapper response object is passed to RequestDispatcher.include(), so that the output of the included resource is
 * appended to that of the including page.
 *
 * @author Pierre Delisle
 */

public class ServletResponseWrapperInclude extends HttpServletResponseWrapper {

    /**
     * PrintWriter which appends to the JspWriter of the including page.
     */
    private PrintWriter printWriter;

    private JspWriter jspWriter;

    // START CR 6466049
    /**
     * Indicates whether or not the wrapped JspWriter can be flushed.
     */
    private boolean canFlushWriter;
    // END CR 6466049

    public ServletResponseWrapperInclude(ServletResponse response, JspWriter jspWriter) {
        super((HttpServletResponse) response);

        this.jspWriter = jspWriter;
        if (jspWriter instanceof JspWriterImpl && ((JspWriterImpl) jspWriter).shouldOutputBytes()) {
            this.printWriter = new PrintWriterWrapper((JspWriterImpl) jspWriter);
        } else {
            this.printWriter = new PrintWriter(jspWriter);
        }

        // START CR 6466049
        this.canFlushWriter = (jspWriter instanceof JspWriterImpl);
        // END CR 6466049
    }

    /**
     * Returns a wrapper around the JspWriter of the including page.
     */
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        throw new IllegalStateException();
    }

    /**
     * Clears the output buffer of the JspWriter associated with the including page.
     */
    public void resetBuffer() {
        try {
            jspWriter.clearBuffer();
        } catch (IOException ioe) {
        }
    }

    // START CR 6421712
    /**
     * Flush the wrapper around the JspWriter of the including page.
     */
    public void flushBuffer() throws IOException {
        printWriter.flush();
    }
    // END CR 6421712

    // START CR 6466049
    /**
     * Indicates whether or not the wrapped JspWriter can be flushed. (BodyContent objects cannot be flushed)
     */
    public boolean canFlush() {
        return canFlushWriter;
    }
    // END CR 6466049

    // START PWC 6512276
    /**
     * Are there any data to be flushed ?
     */
    public boolean hasData() {
        if (!canFlushWriter || ((JspWriterImpl) jspWriter).hasData()) {
            return true;
        }

        return false;
    }
    // END PWC 6512276

    static private class PrintWriterWrapper extends PrintWriter implements ByteWriter {

        private JspWriterImpl jspWriter;

        PrintWriterWrapper(JspWriterImpl jspWriter) {
            super(jspWriter);
            this.jspWriter = jspWriter;
        }

        public void write(byte[] buff, int off, int len) throws IOException {
            jspWriter.write(buff, off, len);
        }
    }
}
