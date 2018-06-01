package ru.spbau.mit.commands;

import ru.spbau.mit.Environment;

import java.io.*;
import java.util.List;

public class CommandPwd extends Command {
    public CommandPwd(List<String> args) {
        super(args);
    }

    @Override
    public void run(InputStream is, OutputStream os, Environment environment)
            throws IOException {
        PrintWriter out = new PrintWriter(os);
        System.out.println(getCurrentDir());
        out.println(getCurrentDir());
        os.flush();
    }
}