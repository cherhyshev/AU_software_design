import org.junit.Before;
import org.junit.jupiter.api.Test;
import ru.spbau.mit.Environment;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.Parser;

import static org.junit.jupiter.api.Assertions.*;

public class TestLexer {
    Lexer lexer = new Lexer();

    @Test
    void testEmptyString() {
        assertTrue(lexer.parseWords("").isEmpty());
    }

    @Test
    void testSymbols() {
        assertEquals("[a,  , =,  , \", \",  , |,  , 1234567890asdfghjkl;;zxcvbnm,../qwertyuiop[]]",
                lexer.parseWords("a = \"\" | 1234567890asdfghjkl;;zxcvbnm,../qwertyuiop[]").toString());
    }

}
