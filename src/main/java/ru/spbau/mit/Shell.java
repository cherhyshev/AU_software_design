package ru.spbau.mit;

import ru.spbau.mit.commands.Command;
import ru.spbau.mit.commands.PipeCommand;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The Shell class provides command line interface
 */
public final class Shell {
    /**
     * This method launches shell
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Environment environment = new Environment();
        String lastLine = in.nextLine();
        while (!lastLine.equals("exit")) {
            try {
                List<String> tokens = lexer.parseWords(lastLine);
                List<String> tokensAfterSubstitution = lexer.substituteVariables(tokens, environment);
                List<Command> commands = parser.parseCommands(tokensAfterSubstitution);
                PipeCommand pipeCommand = new PipeCommand (commands);
                pipeCommand.run(System.in, System.out, environment);
            } catch (IOException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
            lastLine = in.nextLine();
        }
    }
}