package org.maniascript.lexer;

import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.Language;

public class MScriptTokenId implements TokenId {

    private final String name;
    private final String primaryCategory;
    private final int id;

    MScriptTokenId(
            String name,
            String primaryCategory,
            int id) {
        this.name = name;
        this.primaryCategory = primaryCategory;
        this.id = id;
    }

    @Override
    public String primaryCategory() {
        return primaryCategory;
    }

    @Override
    public int ordinal() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    public static Language<MScriptTokenId> getLanguage() {
        return new MScriptLanguageHierarcy().language();
    } 
}