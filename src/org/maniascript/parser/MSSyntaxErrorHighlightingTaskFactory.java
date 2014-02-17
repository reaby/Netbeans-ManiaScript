package org.maniascript.parser;

import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.TaskFactory;

@MimeRegistration(mimeType="text/x-maniascript",service=TaskFactory.class)
public class MSSyntaxErrorHighlightingTaskFactory extends TaskFactory {

    @Override
    public Collection create (Snapshot snapshot) {
        return Collections.singleton (new MSSyntaxErrorHighlightingTask());
    }

}