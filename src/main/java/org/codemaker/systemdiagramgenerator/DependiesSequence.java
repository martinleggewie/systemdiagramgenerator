package org.codemaker.systemdiagramgenerator;

import java.util.ArrayList;
import java.util.List;

class DependiesSequence {

  private List<Dependies> dependiesList = new ArrayList<>();

  void addDependies(Dependies dependies) {
    dependiesList.add(dependies);
  }

  List<Dependies> getDependiesList() {
    return dependiesList;
  }
}
