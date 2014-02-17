package org.maniascript.parser;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.editor.fold.Fold;
import org.netbeans.api.editor.fold.FoldHierarchy;
import org.netbeans.api.editor.fold.FoldType;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.spi.editor.fold.FoldHierarchyTransaction;
import org.netbeans.spi.editor.fold.FoldManager;
import org.netbeans.spi.editor.fold.FoldOperation;
import org.openide.util.Exceptions;
import org.maniascript.lexer.MScriptTokenId;

public class MSFoldManager implements FoldManager {

    private FoldOperation operation;
    public static final FoldType COMMENT_FOLD_TYPE = new FoldType("/*...*/");

    @Override
    public void init(FoldOperation operation) {
        this.operation = operation;
    }

    @Override
    public void initFolds(FoldHierarchyTransaction transaction) {
        FoldHierarchy hierarchy = operation.getHierarchy();
        Document document = hierarchy.getComponent().getDocument();
        TokenHierarchy<Document> hi = TokenHierarchy.get(document);
        TokenSequence<MScriptTokenId> ts = (TokenSequence<MScriptTokenId>) hi.tokenSequence();
        FoldType type = null;
        int start = 0;
        int offset = 0;
        while (ts.moveNext()) {
            offset = ts.offset();
            Token<MScriptTokenId> token = ts.token();
            MScriptTokenId id = token.id();
            if (id.name().equals("FORMAL_COMMENT") && type == null) {
                type = COMMENT_FOLD_TYPE;
                start = offset;
                try {
                    operation.addToHierarchy(type, type.toString(), false, start, offset + token.length(), 0, 0, hierarchy, transaction);
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent de, FoldHierarchyTransaction fht) {
    }

    @Override
    public void removeUpdate(DocumentEvent de, FoldHierarchyTransaction fht) {
    }

    @Override
    public void changedUpdate(DocumentEvent de, FoldHierarchyTransaction fht) {
    }

    @Override
    public void removeEmptyNotify(Fold fold) {
    }

    @Override
    public void removeDamagedNotify(Fold fold) {
    }

    @Override
    public void expandNotify(Fold fold) {
    }

    @Override
    public void release() {
    }
    
}
