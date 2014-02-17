package org.maniascript.parser;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.fold.FoldManager;
import org.netbeans.spi.editor.fold.FoldManagerFactory;

@MimeRegistration(mimeType="text/x-maniascript",service=FoldManagerFactory.class)
public class MSFoldManagerFactory implements FoldManagerFactory {

    @Override
    public FoldManager createFoldManager() {
        return new MSFoldManager();
    }
    
}
