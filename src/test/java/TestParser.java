import org.junit.Test;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.Parser;

import java.io.*;

import static org.junit.Assert.*;

public class TestParser {
    private Parser parser = new Parser();
    private Lexer lexer = new Lexer();

    @Test(expected = IOException.class)
    public void testEmptyString() throws IOException {
        parser.parseCommands(lexer.parseWords(" "));
    }

    @Test
    public void testNonEmptyString() throws IOException {
        assertFalse(parser.parseCommands(lexer.parseWords("pwd")).isEmpty());
    }

}
