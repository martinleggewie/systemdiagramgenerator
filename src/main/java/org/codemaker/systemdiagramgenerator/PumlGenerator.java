package org.codemaker.systemdiagramgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PumlGenerator {

    private DependyMatrix dependyMatrix;
    private String filename;

    public PumlGenerator(DependyMatrix dependyMatrix, String filename) {
        this.dependyMatrix = dependyMatrix;
        this.filename = filename;
    }

    String generate() throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        writer.write(String.format("@startuml %s\n", new File(filename).getName()));
        writer.newLine();
        for (Sys sys : dependyMatrix.getSyses()) {
            writer.write(String.format("rectangle %s\n", sys.getName().replace("-", "")));
        }
        int maxSysNameLength = dependyMatrix.getSyses().stream().max(Comparator.comparing(sys -> sys.getName().length())).get().getName().length();

        writer.newLine();
        for (Dependy dependy : dependyMatrix.getDependies()) {
            writer.write(String.format("%" +maxSysNameLength + "s --> %s\n", dependy.getFrom().getName().replace("-", ""), dependy.getTo().getName().replace("-", "")));
        }
        writer.newLine();
        writer.write("@enduml");

        writer.close();

        return stringWriter.getBuffer().toString();
    }

}
