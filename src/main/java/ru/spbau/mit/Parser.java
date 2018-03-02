package ru.spbau.mit;

import ru.spbau.mit.commands.*;

import java.io.IOException;
import java.util.*;

/**
 * The Parser class contains methods for parsing list of words and service symbols
 * with substituted variables into commands and arguments
 */

public class Parser {
    private int currentLexem;

    private List<String> parseArguments(List<String> tokens) throws IOException {
        List<String> arguments = new ArrayList<>();
        List<String> currentArgument = new ArrayList<>();
        boolean inQuotes = false;
        while (!tokens.get(currentLexem).equals("|")) {
            if (tokens.get(currentLexem).equals("'") || tokens.get(currentLexem).equals("\"")) {
                inQuotes = !inQuotes;
                if (!inQuotes) {
                    arguments.add(String.join("", currentArgument));
                    currentArgument.clear();
                }
            } else {
                if (inQuotes) {
                    currentArgument.add(tokens.get(currentLexem));
                } else {
                    if (!tokens.get(currentLexem).equals(" ")) {
                        arguments.add(tokens.get(currentLexem));
                    }
                }
            }
            currentLexem++;
        }
        if (currentArgument.size() != 0) {
            arguments.add(String.join(" ", currentArgument));
        }
        return arguments;
    }

    private Command parseCommand(List<String> tokens) throws IOException {
        while (currentLexem < tokens.size() - 1 && tokens.get(currentLexem).equals(" ")) {
            currentLexem++;
        }
        if (currentLexem == tokens.size() - 1) {
            throw new IOException("Empty command");
        }
        String commandName = tokens.get(currentLexem++);

        if (tokens.get(currentLexem).equals("=")) {
            currentLexem++;
            List<String> args = parseArguments(tokens);
            args.add(0, commandName);
            return new CommandAssign(args);
        }

        List<String> args = parseArguments(tokens);
        switch (commandName) {
            case "cat":
                return new CommandCat(args);
            case "echo":
                return new CommandEcho(args);
            case "pwd":
                return new CommandPwd(args);
            case "wc":
                return new CommandWc(args);
            default:
                args.add(0, commandName);
                return new CommandExecute(args);
        }
    }

    /**
     * This method parses all commands of one line from list of tokens
     * and returns list of parsed commands
     * @param lexems
     * @return
     * @throws IOException
     */
    public List<Command> parseCommands(List<String> lexems) throws IOException {
        List<Command> commands = new ArrayList<>();
        currentLexem = 0;
        lexems.add("|");

        while (currentLexem < lexems.size()) {
            Command command = parseCommand(lexems);
            if (!lexems.get(currentLexem++).equals("|")) {
                throw new IOException("Invalid lexems");
            }
            commands.add(command);
        }

        return commands;
    }
}