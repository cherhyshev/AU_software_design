package ru.spbau.mit;

import java.util.*;
import java.io.IOException;

/**
 * Lexer class contains methods for lexical
 * division line to word, tokens and variables
 */

public class Lexer {

    private static final Set<Character> SYMBOLS = new HashSet<>(Arrays.asList(
            '\'', '"', '=', '$', '|'));

    /**
     * This method divides line to list of words and service symbols
     *
     * @param line - user-entered string
     * @return list of tokens parsed from the user-entered string
     */

    public List<String> parseWords(String line) {
        List<String> lexems = new ArrayList<>();
        String[] words = line.split(" ");
        for (String word : words) {
            StringBuilder currentSubWord = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (SYMBOLS.contains(word.charAt(i))) {
                    if (!currentSubWord.toString().isEmpty()) {
                        lexems.add(currentSubWord.toString());
                        currentSubWord = new StringBuilder();
                    }
                    lexems.add("" + word.charAt(i));
                } else {
                    currentSubWord.append(word.charAt(i));
                }
            }
            if (!currentSubWord.toString().isEmpty()) {
                lexems.add(currentSubWord.toString());
            }
            lexems.add(" ");
        }
        if (lexems.size() > 0) {
            lexems.remove(lexems.size() - 1);
        }
        return lexems;
    }

    /**
     * This method substitutes variables not from single quotes
     * and returns tokens after substitution
     *
     * @param tokens      - list of tokens
     * @param environment - working instance of the Environment class
     * @return list of substituted tokens
     * @throws IOException when no variable with such name near "$"
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