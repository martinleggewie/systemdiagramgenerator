package org.codemaker.systemdiagramgenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Dependies {

  private String title;
  private Set<Dependy> dependies = new HashSet<>();

  public Dependies(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  void addDependy(Dependy dependy) {
    dependies.add(dependy);
  }

  Set<Dependy> getDependies() {
    return new HashSet<>(dependies);
  }

  Set<Sys> getSyses() {
    return dependies.stream().flatMap(d -> Stream.of(d.getFrom(), d.getTo())).collect(Collectors.toSet());
  }
}
