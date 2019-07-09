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

import org.apache.jasper.JasperException;
import org.apache.jasper.Options;

/**
 */
public class TextOptimizer {

    /**
     * A visitor to concatenate contiguous template texts.
     */
    static class TextCatVisitor extends Node.Visitor {

        private int textNodeCount = 0;
        private Node.TemplateText firstTextNode = null;
        private StringBuilder textBuffer;
        private final String emptyText = new String("");
        private boolean prePass;
        private boolean trim;

        public TextCatVisitor(boolean prePass, boolean trim){
            this.prePass = prePass;
            this.trim = trim;
        }

        public void doVisit(Node n) throws JasperException {
            collectText();
        }

	/*
         * The following directives are ignored in text concatenation
         * except in the pre pass phase.
         */

        public void visit(Node.PageDirective n) throws JasperException {
            if (prePass) {
                collectText();
            }
        }

        public void visit(Node.TagDirective n) throws JasperException {
            if (prePass) {
                collectText();
            }
        }

        public void visit(Node.TaglibDirective n) throws JasperException {
            if (prePass) {
                collectText();
            }
        }

        public void visit(Node.AttributeDirective n) throws JasperException {
            if (prePass) {
                collectText();
            }
        }

        public void visit(Node.VariableDirective n) throws JasperException {
            if (prePass) {
                collectText();
            }
        }

        /*
         * Don't concatenate text across body boundaries
         */
        public void visitBody(Node n) throws JasperException {
            super.visitBody(n);
            collectText();
        }

        public void visit(Node.TemplateText n) throws JasperException {

            if ((trim) && ! prePass && n.isAllSpace()) {
                n.setText(emptyText);
                return;
            }

            if (textNodeCount++ == 0) {
                firstTextNode = n;
                textBuffer = new StringBuilder(n.getText());
            } else {
                // Append text to text buffer
                textBuffer.append(n.getText());
                n.setText(emptyText);
            }
        }

        /**
         * This method breaks concatenation mode.  As a side effect it copies
         * the concatenated string to the first text node 
         */
        private void collectText() {

            if (textNodeCount > 1) {
                // Copy the text in buffer into the first template text node.
                firstTextNode.setText(textBuffer.toString());
            }
            textNodeCount = 0;
        }

    }

    public static void concatenate(Compiler compiler, Node.Nodes page)
            throws JasperException {

        Options options = compiler.getCompilationContext().getOptions();
        PageInfo pageInfo = compiler.getPageInfo();
        boolean trim =
            options.getTrimSpaces() || pageInfo.isTrimDirectiveWhitespaces();

        if (trim) {
            TextCatVisitor v = new TextCatVisitor(true, trim);
            page.visit(v);
            v.collectText();
        }
        TextCatVisitor v = new TextCatVisitor(false, trim);
        page.visit(v);

	// Cleanup, in case the page ends with a template text
        v.collectText();
    }
}
