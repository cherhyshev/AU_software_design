package ru.spbau.mit.commands;

import ru.spbau.mit.CommandException;
import ru.spbau.mit.Environment;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * The Command class set up interface for command of shell
 * what implements its interface
 */
public abstract class Command {
    private List<String> args;
    private static String currentDir = System.getProperty("user.dir");

    /**
     * This constructor constructs class from list of arguments of this command
     *
     * @param args
     */
    public Command(List<String> args) {
        this.args = args;
    }

    /**
     * This method executes command with given input/output streams and environment
     *
     * @param is
     * @param os
     * @param environment
     * @throws IOException
     */
    public abstract void run(InputStream is, OutputStream os, Environment environment)
            throws IOException, CommandException;

    /**
     * This method returns arguments of this command
     *
     * @return
     */
    public List<String> getArgs() {
        return args;
    }

    protected String getCurrentDir() {
        return currentDir;
    }

    protected void setCurrentDir(String newDir) {
        currentDir = newDir;
    }
}