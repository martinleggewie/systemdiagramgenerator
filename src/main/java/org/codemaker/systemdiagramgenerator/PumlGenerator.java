package org.codemaker.systemdiagramgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Comparator;

class PumlGenerator {

  private Dependies dependies;

  PumlGenerator(Dependies dependies) {
    this.dependies = dependies;
  }

  String generate() throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("@startuml %s\n", dependies.getTitle()));
    builder.append("\n");

    int maxSysNameLength = dependies.getSyses().stream().max(Comparator.comparing(sys -> sys.getName().length())).get().getName().length();

    for (Sys sys : dependies.getSyses()) {
      String sysName = sys.getName();
      String pumlCompatibleSysName = sysName.replace("-", "");
      builder.append(String.format("rectangle %-" + maxSysNameLength + "s as %s\n", pumlCompatibleSysName, "\"" + sysName + "\""));
    }

    builder.append("\n");
    for (Dependy dependy : dependies.getDependies()) {
      builder.append(String.format("%" + maxSysNameLength + "s --> %s\n", dependy.getFrom().getName().replace("-", ""), dependy.getTo().getName().replace("-", "")));
    }
    builder.append("\n");
    builder.append("@enduml");

    return builder.toString();
  }

}
