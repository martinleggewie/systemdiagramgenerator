package org.codemaker.systemdiagramgenerator;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
