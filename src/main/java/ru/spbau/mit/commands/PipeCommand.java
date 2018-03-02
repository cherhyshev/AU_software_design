package ru.spbau.mit.commands;

import ru.spbau.mit.Environment;

import java.io.*;
import java.util.List;

public class PipeCommand {
    private List<Command> commands;

    public PipeCommand(List<Command> commands) {
        this.commands = commands;
    }


    public void run(InputStream is, OutputStream os, Environment environment)
            throws IOException {
        InputStream currentInputStream = is;
        for (int i = 0; i < commands.size() - 1; i++) {
            PipedOutputStream currentOutputStream = new PipedOutputStream();
            InputStream nextInputStream = new PipedInputStream(currentOutputStream);

            commands.get(i).run(currentInputStream, currentOutputStream, environment);
            currentOutputStream.close();

            currentInputStream = nextInputStream;
        }
        commands.get(commands.size() - 1).run(currentInputStream, os, environment);
        os.flush();
    }
}