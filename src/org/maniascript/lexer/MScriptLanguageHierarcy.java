package org.maniascript.lexer;

import java.util.*;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

import static org.maniascript.jcclexer.ManiaScriptParserConstants.*;

public class MScriptLanguageHierarcy extends LanguageHierarchy<MScriptTokenId> {

    private static List<MScriptTokenId> tokens;
    private static Map<Integer, MScriptTokenId> idToToken;

    private static void init() {
        tokens = Arrays.<MScriptTokenId>asList(new MScriptTokenId[]{
                    new MScriptTokenId("EOF", "default", EOF),
                    new MScriptTokenId("WHITESPACE", "whitespace", WHITESPACE),
                    new MScriptTokenId("SINGLE_LINE_COMMENT", "comment", SINGLE_LINE_COMMENT),
                    new MScriptTokenId("FORMAL_COMMENT", "comment", FORMAL_COMMENT),
                    new MScriptTokenId("MULTI_LINE_COMMENT", "comment", MULTI_LINE_COMMENT),
                    new MScriptTokenId("TRIPLE_QUOTE_LITERAL", "string", TRIPLE_QUOTE_LITERAL),
                    new MScriptTokenId("ASSERT", "keyword", ASSERT),
                    new MScriptTokenId("ALIAS", "operator", ALIAS),
                    new MScriptTokenId("AS", "keyword", AS),
                    new MScriptTokenId("BOOLEAN", "datatype", BOOLEAN),
                    new MScriptTokenId("BREAK", "keyword", BREAK),
                    new MScriptTokenId("CASE", "keyword", CASE),
                    new MScriptTokenId("CONST", "directive", CONST),
                    new MScriptTokenId("CONTINUE", "keyword", CONTINUE),
                    new MScriptTokenId("_DEFAULT", "keyword", _DEFAULT),
                    new MScriptTokenId("DECLARE", "keyword", DECLARE),
                    new MScriptTokenId("ELSE", "keyword", ELSE),
                    new MScriptTokenId("EXTENDS", "directive", EXTENDS),
                    new MScriptTokenId("FALSE", "datatype", FALSE),
                    new MScriptTokenId("FOR", "keyword", FOR),
                    new MScriptTokenId("FOREACH", "keyword", FOREACH),
                    new MScriptTokenId("IF", "keyword", IF),
                    new MScriptTokenId("IN", "keyword", IN),
                    new MScriptTokenId("INCLUDE", "directive", INCLUDE),
                    new MScriptTokenId("IDENT", "datatype", IDENT),
                    new MScriptTokenId("INTEGER", "datatype", INTEGER),
                    new MScriptTokenId("INT3", "datatype", INT3),
                    new MScriptTokenId("MAIN", "method", MAIN),
                    new MScriptTokenId("NOW", "datatype", NOW),
                    new MScriptTokenId("NETREAD", "datatype", NETREAD),
                    new MScriptTokenId("NETWRITE", "datatype", NETWRITE),
                    new MScriptTokenId("NULL", "datatype", NULL),
                    new MScriptTokenId("NULLID", "datatype", NULLID),
                    new MScriptTokenId("LOG", "method", LOG),
                    new MScriptTokenId("REAL", "datatype", REAL),
                    new MScriptTokenId("RETURN", "keyword", RETURN),
                    new MScriptTokenId("REQUIRECONTEXT", "directive", REQUIRECONTEXT),
                    new MScriptTokenId("SLEEP", "keyword", SLEEP),
                    new MScriptTokenId("SETTING", "directive", SETTING),
                    new MScriptTokenId("SWITCH", "keyword", SWITCH),
                    new MScriptTokenId("TRUE", "datatype", TRUE),
                    new MScriptTokenId("TEXT", "datatype", TEXT),
                    new MScriptTokenId("VEC2", "datatype", VEC2),
                    new MScriptTokenId("VEC3", "datatype", VEC3),
                    new MScriptTokenId("VOID", "datatype", VOID),
                    new MScriptTokenId("WAIT", "keyword", WAIT),
                    new MScriptTokenId("YIELD", "keyword", YIELD),
                    new MScriptTokenId("WHILE", "keyword", WHILE),
                    new MScriptTokenId("INTEGER_LITERAL", "number", INTEGER_LITERAL),
                    new MScriptTokenId("DECIMAL_LITERAL", "number", DECIMAL_LITERAL),
                    new MScriptTokenId("HEX_LITERAL", "number", HEX_LITERAL),
                    new MScriptTokenId("OCTAL_LITERAL", "number", OCTAL_LITERAL),
                    new MScriptTokenId("FLOATING_POINT_LITERAL", "number", FLOATING_POINT_LITERAL),
                    new MScriptTokenId("EXPONENT", "number", EXPONENT),
                    new MScriptTokenId("CHARACTER_LITERAL", "character", CHARACTER_LITERAL),
                    new MScriptTokenId("STRING_LITERAL", "string", STRING_LITERAL),
                    new MScriptTokenId("IDENTIFIER", "identifier", IDENTIFIER),
                    new MScriptTokenId("LETTER", "default", LETTER),
                    new MScriptTokenId("DIGIT", "default", DIGIT),
                    new MScriptTokenId("LPAREN", "separator", LPAREN),
                    new MScriptTokenId("RPAREN", "separator", RPAREN),
                    new MScriptTokenId("LBRACE", "separator", LBRACE),
                    new MScriptTokenId("RBRACE", "separator", RBRACE),
                    new MScriptTokenId("LBRACKET", "identifier", LBRACKET),
                    new MScriptTokenId("RBRACKET", "identifier", RBRACKET),
                    new MScriptTokenId("SEMICOLON", "identifier", SEMICOLON),
                    new MScriptTokenId("COMMA", "separator", COMMA),
                    new MScriptTokenId("DOT", "separator", DOT),
                    new MScriptTokenId("ASSIGN", "operator", ASSIGN),
                    new MScriptTokenId("GT", "operator", GT),
                    new MScriptTokenId("LT", "operator", LT),
                    new MScriptTokenId("BANG", "operator", BANG),
                    new MScriptTokenId("TILDE", "operator", TILDE),
                    new MScriptTokenId("HOOK", "operator", HOOK),
                    new MScriptTokenId("COLON", "operator", COLON),
                    new MScriptTokenId("DOUBLECOLON", "operator", DOUBLECOLON),
                    new MScriptTokenId("EQ", "operator", EQ),
                    new MScriptTokenId("LE", "operator", LE),
                    new MScriptTokenId("GE", "operator", GE),
                    new MScriptTokenId("NE", "operator", NE),
                    new MScriptTokenId("SC_OR", "operator", SC_OR),
                    new MScriptTokenId("SC_AND", "operator", SC_AND),
                    new MScriptTokenId("INCR", "operator", INCR),
                    new MScriptTokenId("DECR", "operator", DECR),
                    new MScriptTokenId("PLUS", "operator", PLUS),
                    new MScriptTokenId("MINUS", "operator", MINUS),
                    new MScriptTokenId("STAR", "operator", STAR),
                    new MScriptTokenId("SLASH", "operator", SLASH),
                    new MScriptTokenId("BIT_AND", "operator", BIT_AND),
                    new MScriptTokenId("BIT_OR", "operator", BIT_OR),
                    new MScriptTokenId("TEXT_APPEND", "operator", TEXT_APPEND),
                    new MScriptTokenId("REM", "operator", REM),
                    new MScriptTokenId("PLUSASSIGN", "operator", PLUSASSIGN),
                    new MScriptTokenId("MINUSASSIGN", "operator", MINUSASSIGN),
                    new MScriptTokenId("STARASSIGN", "operator", STARASSIGN),
                    new MScriptTokenId("SLASHASSIGN", "operator", SLASHASSIGN),
                    new MScriptTokenId("ANDASSIGN", "operator", ANDASSIGN),
                    new MScriptTokenId("ORASSIGN", "operator", ORASSIGN),
                    new MScriptTokenId("TEXTASSIGN", "operator", TEXTASSIGN),
                    new MScriptTokenId("REMASSIGN", "operator", REMASSIGN),
                    new MScriptTokenId("JAVACC_NUM_CHAR", "default", JAVACC_NUM_CHAR),
                    new MScriptTokenId("ALL", "default", ALL)
                });
        idToToken = new HashMap<Integer, MScriptTokenId>();
        for (MScriptTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized MScriptTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<MScriptTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<MScriptTokenId> createLexer(LexerRestartInfo<MScriptTokenId> info) {
        return new MScriptLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-maniascript";
    }
}