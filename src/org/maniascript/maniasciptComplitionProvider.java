/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.maniascript;

import java.io.*;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.openide.xml.XMLUtil;
import org.openide.util.Exceptions;
import org.openide.xml.EntityCatalog;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@MimeRegistration(mimeType = "text/x-maniascript", service = CompletionProvider.class)
public class maniasciptComplitionProvider implements CompletionProvider {

    static final Logger LOGGER = Logger.getLogger(maniasciptComplitionProvider.class.getName());
    private org.w3c.dom.Document doc;

    public maniasciptComplitionProvider() {
        try {
            doc = XMLUtil.parse(new InputSource(this.getClass().getResourceAsStream("msClasses.xml")), false, false, null, EntityCatalog.getDefault());
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    @Override
    public CompletionTask createTask(int queryType, JTextComponent jtc) {

        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return null;
        }

        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int caretOffset) {

                String filter = null;
                int startOffset = caretOffset - 1;

                try {
                    final StyledDocument bDoc = (StyledDocument) document;
                    final int lineStartOffset = getRowFirstNonWhite(bDoc, caretOffset);
                    final char[] line = bDoc.getText(lineStartOffset, caretOffset - lineStartOffset).toCharArray();
                    final int whiteOffset = indexOfWhite(line);
                    filter = new String(line, whiteOffset + 1, line.length - whiteOffset - 1);
                    if (whiteOffset > 0) {
                        startOffset = lineStartOffset + whiteOffset + 1;
                    } else {
                        startOffset = lineStartOffset;
                    }
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }





                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();
                try {
                    //LOGGER.info(filter);
                    String ffilter;
                    String[] filter2 = filter.split("\\(");

                    if (filter2.length > 1) {
                        ffilter = filter2[1];
                    } else {
                        ffilter = filter;
                    }


                    XPathExpression expr = xpath.compile("/*/*[starts-with(name(), '" + ffilter + "')]");
                    //     XPathExpression expr = xpath.compile("/cnod/buildin/*[starts-with(name(), '" + ffilter + "')]");
                    if (ffilter.contains(".")) {
                        String[] temp = ffilter.split("\\.");
                        if (temp.length > 1) {
                            LOGGER.info("//" + temp[0] + "/*[starts-with(name(), '" + temp[1] + "')]");
                            expr = xpath.compile("//" + temp[0] + "/*[starts-with(name(), '" + temp[1] + "')]");

                        }
                    }
                    if (ffilter.contains("::")) {
                        String[] temp = ffilter.split("\\:\\:");
                        if (temp.length > 1) {
                            expr = xpath.compile("//" + temp[0] + "/*[starts-with(name(), '" + temp[1] + "')]");
                        }
                    }


                    Object result = expr.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodes = (NodeList) result;
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node node = nodes.item(i);
                        String helpItem = node.getNodeName();

                        String helpType;
                        if (node.getTextContent().length() > 17) {
                            helpType = "";
                        } else {
                            helpType = node.getTextContent();
                        }

                        completionResultSet.addItem(new AutoComplitionItem(helpItem, helpType, startOffset, caretOffset, 2));

                        traverseNodes(node, startOffset, caretOffset, completionResultSet);
                        // String helpItem = node.getNodeName();
                        // completionResultSet.addItem(new AutoComplitionItem(helpItem, startOffset, caretOffset));
                        //  if (!helpItem.equals("") && helpItem.startsWith(filter)) {
                        // completionResultSet.addItem(new AutoComplitionItem(helpItem, startOffset, caretOffset));
                        //  }
                    }

                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }


                completionResultSet.finish();
            }
        }, jtc);

    }

    public void traverseNodes(Node n, int startOffset, int caretOffset, CompletionResultSet completionResultSet) {
        NodeList children = n.getChildNodes();
        Node parent = n.getParentNode();
        String parentNode;
        if (parent != null) {

            String type;
            if (parent.hasChildNodes()) {
                type = "::";
            } else {
                type = ".";
            }
            if (parent.getNodeName().contains("cnod")) {
                parentNode = "";
            } else {
                parentNode = parent.getNodeName() + type;
            }
        } else {
            parentNode = "";
        }
        if (children != null) {

            for (int i = 0; i < children.getLength(); i++) {
                Node childNode = children.item(i);
                if (childNode.getNodeName().equals("#text") || childNode.getNodeName().isEmpty()) {
                    continue;
                }
                int color = 0;
                String type;
                String helpType;
                if (childNode.getTextContent().equals("enum")) {
                    type = "::";
                    color = 1;
                    helpType = "enum";
                } else {

                    if (!childNode.getTextContent().contains("\n")) {
                        helpType = childNode.getTextContent();
                        type = ".";
                        color = 0;
                    } else {
                        helpType = "enum";
                        type = "::";
                        color = 1;
                    }
                }

                String helpItem = parentNode + n.getNodeName() + type + childNode.getNodeName();
                completionResultSet.addItem(new AutoComplitionItem(helpItem, helpType, startOffset, caretOffset, color));
                traverseNodes(childNode, startOffset, caretOffset, completionResultSet);
            }
        } else {

            if (n.getNodeName().equals("#text") || n.getNodeName().isEmpty()) {
             return;
             } 

            String helpItem = parentNode + n.getNodeName();

            String helpType;
            if (n.getTextContent().equals("enum") || n.getTextContent().isEmpty()) {
                helpType = "";
            } else {
                helpType = n.getTextContent();
            }
            LOGGER.info(helpItem);
            completionResultSet.addItem(new AutoComplitionItem(helpItem, helpType, startOffset, caretOffset, 2));
        }
        return;
    }

    @Override
    public int getAutoQueryTypes(JTextComponent component, String typedText) {
        return 0;
    }

    static int getRowFirstNonWhite(StyledDocument doc, int offset)
            throws BadLocationException {
        Element lineElement = doc.getParagraphElement(offset);
        int start = lineElement.getStartOffset();
        while (start + 1 < lineElement.getEndOffset()) {
            try {
                if (doc.getText(start, 1).charAt(0) != ' ') {
                    break;
                }
            } catch (BadLocationException ex) {
                throw (BadLocationException) new BadLocationException(
                        "calling getText(" + start + ", " + (start + 1)
                        + ") on doc of length: " + doc.getLength(), start).initCause(ex);
            }
            start++;
        }
        return start;
    }

    static int indexOfWhite(char[] line) {
        int i = line.length;
        while (--i > -1) {
            final char c = line[i];
            if (Character.isWhitespace(c)) {
                return i;
            }
        }
        return -1;
    }
}
