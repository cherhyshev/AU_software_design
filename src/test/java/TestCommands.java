import org.junit.jupiter.api.Test;
import ru.spbau.mit.*;
import ru.spbau.mit.commands.Command;
import ru.spbau.mit.commands.PipeCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {
    Lexer lexer = new Lexer();
    Parser parser = new Parser();
    Environment environment = new Environment();

    @Test
    void testPwd() throws IOException, CommandException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        System.setOut(new PrintStream(b, true));
        List<String> tokens = lexer.parseWords("pwd");
        List<String> tokensAfterSubstitution = lexer.substituteVariables(tokens, environment);
        List<Command> commands = parser.parseCommands(tokensAfterSubstitution);
        PipeCommand pipeCommand = new PipeCommand(commands);
        pipeCommand.run(System.in, System.out, environment);
        assertEquals(Paths.get(".").toAbsolutePath().normalize().toString(),
                b.toString().trim());

    }

    @Test
    void testEchoWcPipe() throws IOException, CommandException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        System.setOut(new PrintStream(b, true));
        List<String> tokens = lexer.parseWords("echo 123 | wc");
        List<String> tokensAfterSubstitution = lexer.substituteVariables(tokens, environment);
        List<Command> commands = parser.parseCommands(tokensAfterSubstitution);
        PipeCommand pipeCommand = new PipeCommand(commands);
        pipeCommand.run(System.in, System.out, environment);
        assertEquals("1 1 3", b.toString().trim());

    }

    @Test
    void testCatWcPipe() throws IOException, CommandException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        System.setOut(new PrintStream(b, true));
        List<String> tokens = lexer.parseWords("cat catwc | wc");
        List<String> tokensAfterSubstitution = lexer.substituteVariables(tokens, environment);
        List<Command> commands = parser.parseCommands(tokensAfterSubstitution);
        PipeCommand pipeCommand = new PipeCommand(commands);
        pipeCommand.run(System.in, System.out, environment);
        assertEquals("5 5 42", b.toString().trim());
    }
}
