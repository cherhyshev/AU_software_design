package ru.spbau.mit;

/**
 * since the IOException is expensive throwing
 * this kind when command errors occur
 */

public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
