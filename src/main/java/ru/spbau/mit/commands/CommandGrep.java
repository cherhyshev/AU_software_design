package ru.spbau.mit.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.io.IOUtils;
import ru.spbau.mit.CommandException;
import ru.spbau.mit.Environment;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Команда grep,
 * поддерживающая ключи
 * -i (нечувствительность к регистру)
 * -w (поиск только слов целиком)
 * -A n (распечатать n строк после строки с совпадением)
 * поддерживающая регулярные выражения в строке поиска,
 * использующая одну из библиотек для разбора аргументов командной строки.
 */


public class CommandGrep extends Command {
    @Parameter(names = "-i", description = "case insensitive")
    private boolean caseInsensitive = false;

    @Parameter(names = "-w", description = "whole words only")
    private boolean wholeWords = false;

    @Parameter(names = "-A", description = "additional lines after match")
    private int additionalLines = 0;

    @Parameter(description = "regex [file]", required = true)
    private List<String> parameters;

    public CommandGrep(List<String> args){
        super(args);
        try {
            new JCommander(this, args.toArray(new String[0]));
        } catch (Exception e) {
            System.out.println("failed to parse grep arguments: " + e.getMessage());
        }
    }

    @Override
    public void run(InputStream is, OutputStream os, Environment environment)
            throws CommandException {
        String regex;
        final String fileName;
        if (parameters.size() == 1) {
            regex = parameters.get(0);
            fileName = null;
        } else if (parameters.size() == 2) {
            regex = parameters.get(0);
            fileName = parameters.get(1);
        } else {
            throw new CommandException("too many arguments");
        }

        if (wholeWords) {
            regex = "(?<=(^|\\W))" + regex + "(?=(\\W|$))";
        }

        final Pattern pattern;
        try {
            pattern = Pattern.compile(regex, caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
        } catch (PatternSyntaxException e) {
            System.out.println("failed to parse regex: " + e.getMessage());
            return;
        }

        try {
            final InputStream sourceStream = fileName == null
                    ? is
                    : new FileInputStream(fileName);
            final List<String> lines = IOUtils.readLines(sourceStream, Charset.defaultCharset());
            for (int i = 0; i < lines.size(); i++) {
                if (pattern.matcher(lines.get(i)).find()) {
                    for (int j = 0; j < 1 + additionalLines && i + j < lines.size(); j++) {
                        os.write(lines.get(i + j).getBytes());
                        os.write("\n".getBytes());
                        os.flush();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("failed to read file contents: " + e.getMessage());
        }
    }
}
