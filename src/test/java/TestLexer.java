import org.junit.Test;
import ru.spbau.mit.Lexer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class TestLexer {
    private Lexer lexer = new Lexer();

    @Test
    public void testEmptyString() {
        assertTrue(lexer.parseWords("").isEmpty());
    }

    @Test
    public void testSymbols() {
        assertEquals("[a,  , =,  , \", \",  , |,  , 1234567890asdfghjkl;;zxcvbnm,../qwertyuiop[]]",
                lexer.parseWords("a = \"\" | 1234567890asdfghjkl;;zxcvbnm,../qwertyuiop[]").toString());
    }

}
