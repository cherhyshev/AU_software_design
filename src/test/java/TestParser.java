import org.junit.jupiter.api.Test;
import ru.spbau.mit.Parser;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.commands.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestParser {
    Parser parser = new Parser();
    Lexer lexer = new Lexer();

    @Test
    void testEmptyString() throws IOException {
        assertThrows(IOException.class, () -> parser.parseCommands(lexer.parseWords(" ")));
    }

    @Test
    void testNonEmptyString() throws IOException {
        assertFalse(parser.parseCommands(lexer.parseWords("pwd")).isEmpty());
    }

}
