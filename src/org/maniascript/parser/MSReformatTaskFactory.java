package org.maniascript.parser;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ReformatTask;

@MimeRegistration(mimeType="text/x-maniascript",service=ReformatTask.Factory.class)
public class MSReformatTaskFactory implements ReformatTask.Factory {

    @Override
    public ReformatTask createTask(Context context) {
        return new MSReformatTask(context);
    }
    
}