package org.maniascript.lexer;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.maniascript.jcclexer.JavaCharStream;
import org.maniascript.jcclexer.ManiaScriptParserTokenManager;
import org.maniascript.jcclexer.Token;
import org.maniascript.jcclexer.TokenMgrError;

class MScriptLexer implements Lexer<MScriptTokenId> {

    private LexerRestartInfo<MScriptTokenId> info;
    private ManiaScriptParserTokenManager javaParserTokenManager;

    MScriptLexer(LexerRestartInfo<MScriptTokenId> info) {
        this.info = info;
        JavaCharStream stream = new JavaCharStream(info.input());
        javaParserTokenManager = new ManiaScriptParserTokenManager(stream);
    }

    public org.netbeans.api.lexer.Token<MScriptTokenId> nextToken() {
        Token token = null;
        // Tokens may not match while editing a file so we need to catch the exception
        try {
            token = javaParserTokenManager.getNextToken();
            if (info.input().readLength() < 1) {
                return null;
            }
            return info.tokenFactory().createToken(MScriptLanguageHierarcy.getToken(token.kind));
        } catch (TokenMgrError error) {
        }

        return null;
    }

    public Object state() {
        return null;
    }

    public void release() {
    }
}
