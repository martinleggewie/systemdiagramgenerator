package org.codemaker.systemdiagramgenerator;

import java.io.IOException;
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

    int maxSysNameLength =
            dependies.getSysSet().stream().max(Comparator.comparing(sys -> sys.getName().length())).get().getName().length();

    for (Sys sys : dependies.getSysSet()) {
      String sysName = sys.getName();
      String pumlCompatibleSysName = sysName.replace("-", "");
      builder.append(String.format("rectangle %-" + maxSysNameLength + "s as %s\n", pumlCompatibleSysName, "\"" + sysName + "\""));
    }

    builder.append("\n");
    for (Dependy dependy : dependies.getDependySet()) {
      builder.append(String.format("%" + maxSysNameLength + "s --> %s\n", dependy.getFrom().getName().replace("-", ""), dependy.getTo().getName().replace("-", "")));
    }
    builder.append("\n");
    builder.append("@enduml");

    return builder.toString();
  }

}
