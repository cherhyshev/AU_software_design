package ru.spbau.mit.commands;

import ru.spbau.mit.Environment;

import java.io.*;
import java.util.List;

public class CommandExecute extends Command {

    public CommandExecute(List<String> args) {
        super(args);
    }

    @Override
    public void run(InputStream is, OutputStream os, Environment environment) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(getArgs());
        final Process process = pb.start();
        OutputStream out = new ByteArrayOutputStream();
        BufferedReader inReader = null;
        if (is != null) {
            inReader = new BufferedReader(new InputStreamReader(is));
        }
        OutputStream processOutputStream = process.getOutputStream();
        InputStream processInputStream = process.getInputStream();

        if (inReader != null) {
            try (BufferedWriter processWriter = new BufferedWriter(new OutputStreamWriter(processOutputStream))) {
                String line;
                while ((line = inReader.readLine()) != null) {
                    processWriter.write(line);
                }
                processWriter.flush();
                processWriter.close();
            } catch (IOException e) {
            }
        }

        try (BufferedReader processReader = new BufferedReader(new InputStreamReader(processInputStream))) {
            String line;
            while ((line = processReader.readLine()) != null) {
                out.write(line.getBytes());
                out.write('\n');
            }
        } catch (IOException e) {
        }

        out.flush();
        os = out;
    }
}