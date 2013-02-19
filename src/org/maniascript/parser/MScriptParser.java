package org.maniascript.parser;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.maniascript.jccparser.ManiaScriptParser;
import org.maniascript.jccparser.Token;

public class MScriptParser extends Parser {

    private Snapshot snapshot;
    private ManiaScriptParser javaParser;

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
        this.snapshot = snapshot;
        Reader reader = new StringReader(snapshot.getText().toString());
        javaParser = new ManiaScriptParser(reader);
        try {
            javaParser.ManiaScriptFile();
        } catch (org.maniascript.jccparser.ParseException ex) {
            Logger.getLogger(ManiaScriptParser.class.getName()).log(Level.WARNING, null, ex);
        }
    }    

    @Override
    public Result getResult(Task task) {
        return new MScriptParserResult(snapshot, javaParser);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
    }

    public static class MScriptParserResult extends Result {

        private ManiaScriptParser javaParser;
        private boolean valid = true;

        MScriptParserResult(Snapshot snapshot, ManiaScriptParser javaParser) {
            super(snapshot);
            this.javaParser = javaParser;
        }

        public ManiaScriptParser getManiaScriptParser() throws ParseException {
            if (!valid) {
                throw new ParseException();
            }
            return javaParser;
        }

        @Override
        protected void invalidate() {
            valid = false;
        }
    }
}