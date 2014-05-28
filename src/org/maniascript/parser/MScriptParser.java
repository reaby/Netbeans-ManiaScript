package org.maniascript.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.maniascript.jccparser.ManiaScriptParser;

public class MScriptParser extends Parser {

    private Snapshot snapshot;
    private ManiaScriptParser javaParser;

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
        this.snapshot = snapshot;
        Reader reader = new StringReader(snapshot.getText().toString());
        javaParser = new ManiaScriptParser(reader);
        //try {
        javaParser.ReInit(reader);
        //} catch (org.maniascript.jccparser.ParseException ex) {
        //    Logger.getLogger (MScriptParser.class.getName()).log (Level.WARNING, null, ex);
        // }
    }

    @Override
    public Parser.Result getResult(Task task) {
        return new MSParserResult(snapshot, javaParser);
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

    public static class MSParserResult extends Result {
        private ManiaScriptParser javaParser;
        private boolean valid = true;

        MSParserResult(Snapshot snapshot, ManiaScriptParser javaParser) {
            super(snapshot);
            this.javaParser = javaParser;
        }

        public ManiaScriptParser getJavaParser() throws org.netbeans.modules.parsing.spi.ParseException {
            if (!valid) {
                throw new org.netbeans.modules.parsing.spi.ParseException();
            }
            return javaParser;
        }

        @Override
        protected void invalidate() {
            valid = false;
        }

    }

}
