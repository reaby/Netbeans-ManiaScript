package org.maniascript.parser;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.IndentTask;

@MimeRegistration(mimeType="text/x-sj",service=IndentTask.Factory.class)
public class MSIndentTaskFactory implements IndentTask.Factory {

    @Override
    public IndentTask createTask(Context context) {
        return new MSIndentTask(context);
    }

}