/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.maniascript;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author reaby
 */
public class AutoComplitionItem implements CompletionItem {

    private int dotOffset;
    protected String text;
    private static ImageIcon fieldIcon = new ImageIcon(ImageUtilities.loadImage("org/maniascript/mplanet16.png"));
    private int caretOffset;
    private int type;
    protected String text2;
    private Color fieldColor;
    
    public AutoComplitionItem(String text, String text2, int dotOffset, int caretOffset, int type) {
        this.text = text;
        this.text2 = text2;
        this.dotOffset = dotOffset;
        this.caretOffset = caretOffset;
        switch (type) {
            case 0:
                fieldColor = Color.decode("0x0000B2");
                break;
            case 1:
                fieldColor = Color.decode("0x00B200");
                break;
            case 2:
                fieldColor = Color.decode("0xB20000");
                break;
            default:
                fieldColor = Color.decode("0x0000B2");
                break;
        }
    }

    @Override
    public void defaultAction(JTextComponent component) {
        try {
            StyledDocument doc = (StyledDocument) component.getDocument();
            //Here we remove the characters starting at the start offset
            //and ending at the point where the caret is currently found:
            doc.remove(dotOffset, caretOffset - dotOffset);
            doc.insertString(dotOffset, text, null);
            Completion.get().hideAll();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void processKeyEvent(KeyEvent evt) {
    }

    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(text, null, graphics, font);
    }

    @Override
    public void render(Graphics g, Font defaultFont, Color defaultColor, Color backgroundColor, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(fieldIcon, text, text2, g, defaultFont, (selected ? Color.white : fieldColor), width, height, selected);
    }

    @Override
    public CompletionTask createDocumentationTask() {
        return null;
    }

    @Override
    public CompletionTask createToolTipTask() {
        return null;
    }

    @Override
    public boolean instantSubstitution(JTextComponent component) {
        return false;
    }

    @Override
    public int getSortPriority() {
        return 0;
    }

    @Override
    public CharSequence getSortText() {
        return text;
    }

    @Override
    public CharSequence getInsertPrefix() {
        return text;
    }
}
