import org.junit.Test;
import ru.spbau.mit.CommandException;
import ru.spbau.mit.Environment;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.Parser;
import ru.spbau.mit.commands.Command;
import ru.spbau.mit.commands.CommandGrep;
import ru.spbau.mit.commands.PipeCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestCommands {
    private Lexer lexer = new Lexer();
    private Parser parser = new Parser();
    private Environment environment = new Environment();

    @Test
    public void testPwd() throws IOException, CommandException {
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
    public void testEchoWcPipe() throws IOException, CommandException {
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
    public void testCatWcPipe() throws IOException, CommandException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        System.setOut(new PrintStream(b, true));
        List<String> tokens = lexer.parseWords("cat catwc | wc");
        List<String> tokensAfterSubstitution = lexer.substituteVariables(tokens, environment);
        List<Command> commands = parser.parseCommands(tokensAfterSubstitution);
        PipeCommand pipeCommand = new PipeCommand(commands);
        pipeCommand.run(System.in, System.out, environment);
        assertEquals("5 5 42", b.toString().trim());
    }

    @Test
    public void simpleString() throws Exception {
        List<String> myArgs = new ArrayList<String>();
        myArgs.add("str");
        final CommandGrep command = new CommandGrep(myArgs);
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        data.write("string\nnewline\nsubstring\n".getBytes());
        data.flush();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        command.run(new ByteArrayInputStream(data.toByteArray()), outputStream, null);
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        assertEquals(0, errorStream.size());
        assertEquals("string\nsubstring\n", outputStream.toString(String.valueOf(Charset.defaultCharset())));
    }

    @Test
    public void caseInsensitiveParam() throws Exception {
        List<String> myArgs = new ArrayList<String>(Arrays.asList("-i", "Str"));
        final CommandGrep command = new CommandGrep(myArgs);
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        data.write("string\nnewline\nsUbsTring\n".getBytes());
        data.flush();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        command.run(new ByteArrayInputStream(data.toByteArray()), outputStream, null);
        assertEquals("string\nsUbsTring\n", outputStream.toString(String.valueOf(Charset.defaultCharset())));
    }

    @Test
    public void wholeWordsParam() throws Exception {
        List<String> myArgs = new ArrayList<String>(Arrays.asList("-w", "str"));
        final CommandGrep command = new CommandGrep(myArgs);
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        data.write("string\nsub str ing\nsubstring\n".getBytes());
        data.flush();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        command.run(new ByteArrayInputStream(data.toByteArray()), outputStream, null);
        assertEquals("sub str ing\n", outputStream.toString(String.valueOf(Charset.defaultCharset())));
    }

    @Test
    public void additionalLinesParam() throws Exception {
        List<String> myArgs = new ArrayList<String>(Arrays.asList("-A", "1", "abc"));
        final CommandGrep command = new CommandGrep(myArgs);
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        data.write("abc\ndef\nghi\n".getBytes());
        data.flush();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        command.run(new ByteArrayInputStream(data.toByteArray()), outputStream, null);
        assertEquals("abc\ndef\n", outputStream.toString(String.valueOf(Charset.defaultCharset())));
    }
}
