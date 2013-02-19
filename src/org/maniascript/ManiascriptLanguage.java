package org.maniascript;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.maniascript.lexer.MScriptTokenId;

@LanguageRegistration(mimeType = "text/x-maniascript")
public class ManiascriptLanguage extends DefaultLanguageConfig {

    @Override
    public Language<MScriptTokenId> getLexerLanguage() {
        return MScriptTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "ManiaScript";
    }

}