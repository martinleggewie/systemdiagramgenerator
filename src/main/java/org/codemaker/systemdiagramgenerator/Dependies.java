package org.codemaker.systemdiagramgenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Dependies {

  private String title;
  private Set<Dependy> dependySet = new HashSet<>();

  public Dependies(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  void addDependy(Dependy dependy) {
    dependySet.add(dependy);
  }

  Set<Dependy> getDependySet() {
    return new HashSet<>(dependySet);
  }

  Set<Sys> getSysSet() {
    return dependySet.stream().flatMap(d -> Stream.of(d.getFrom(), d.getTo())).collect(Collectors.toSet());
  }
}
