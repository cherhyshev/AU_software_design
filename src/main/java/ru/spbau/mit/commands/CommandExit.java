package ru.spbau.mit.commands;

import ru.spbau.mit.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class CommandExit extends Command {
    public CommandExit(List<String> args) {
        super(args);
    }

    @Override
    public void run(InputStream is, OutputStream os, Environment environment)
            throws IOException {
        System.exit(0);
    }
}