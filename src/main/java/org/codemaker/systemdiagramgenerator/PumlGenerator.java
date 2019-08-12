package org.codemaker.systemdiagramgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Comparator;

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

        int maxSysNameLength = dependyMatrix.getSyses().stream().max(Comparator.comparing(sys -> sys.getName().length())).get().getName().length();

        for (Sys sys : dependyMatrix.getSyses()) {
            String sysName = sys.getName();
            String pumlCompatibleSysName = sysName.replace("-", "");
            writer.write(String.format("rectangle %-" + maxSysNameLength + "s as %s\n", pumlCompatibleSysName, "\"" + sysName + "\""));
        }

        writer.newLine();
        for (Dependy dependy : dependyMatrix.getDependies()) {
            writer.write(String.format("%" + maxSysNameLength + "s --> %s\n", dependy.getFrom().getName().replace("-", ""), dependy.getTo().getName().replace("-", "")));
        }
        writer.newLine();
        writer.write("@enduml");

        writer.close();

        return stringWriter.getBuffer().toString();
    }

}
