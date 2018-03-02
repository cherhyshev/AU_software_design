package ru.spbau.mit;

import java.util.*;
import java.io.IOException;

/**
 * Lexer class contains methods for lexical
 * division line to word, tokens and variables
 */

public class Lexer {

    private static final Set<Character> SYMBOLS = new HashSet<>(Arrays.asList(
            new Character[]{'\'', '"', '=', '$', '|'}
    ));

    /**
     * This method divides line to list of words and service symbols
     * @param line
     * @return
     * @throws IOException
     */

    public List<String> parseWords(String line) {
        List<String> lexems = new ArrayList<>();
        String[] words = line.split(" ");
        for (String word : words) {
            String currentSubWord = "";
            for (int i = 0; i < word.length(); i++) {
                if (SYMBOLS.contains(word.charAt(i))) {
                    if (!currentSubWord.equals("")) {
                        lexems.add(currentSubWord);
                        currentSubWord = "";
                    }
                    lexems.add("" + word.charAt(i));
                } else {
                    currentSubWord += word.charAt(i);
                }
            }
            if (!currentSubWord.equals("")) {
                lexems.add(currentSubWord);
            }
            lexems.add(" ");
        }
        lexems.remove(lexems.size() - 1);
        return lexems;
    }

    /**
     * This method substitutes variables not from single quotes
     * and returns tokens after substitution
     * @param tokens
     * @param environment
     * @return
     * @throws IOException
     */

    public List<String> substituteVariables(List<String> tokens, Environment environment) throws IOException {
        List<String> substitutedLexems = new ArrayList<>();

        boolean inSingleQuotes = false;
        boolean isLastTokenSubstitution = false;
        for (String token : tokens) {
            if (token.equals("'")) {
                inSingleQuotes = !inSingleQuotes;
                substitutedLexems.add("'");
            } else {
                if (token.equals("$")) {
                    isLastTokenSubstitution = true;
                } else {
                    if (isLastTokenSubstitution) {
                        if (inSingleQuotes) {
                            substitutedLexems.add("$" + token);
                        } else {
                            String value = environment.getValue(token);
                            if (value == null) {
                                throw new IOException("No such variable");
                            }
                            substitutedLexems.addAll(parseWords(value));
                        }
                        isLastTokenSubstitution = false;
                    } else {
                        substitutedLexems.add(token);
                    }
                }
            }
        }

        return substitutedLexems;
    }
}