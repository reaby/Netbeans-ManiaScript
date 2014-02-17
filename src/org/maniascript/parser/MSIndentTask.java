package org.maniascript.parser;

import javax.swing.text.BadLocationException;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.IndentTask;
import org.openide.awt.StatusDisplayer;

public class MSIndentTask implements IndentTask {

    private Context context;

    MSIndentTask(Context context) {
        this.context = context;
    }

    @Override
    public void reindent() throws BadLocationException {
        StatusDisplayer.getDefault().setStatusText("We will indent this now...");
    }

    @Override
    public ExtraLock indentLock() {
        return null;
    }
    
}