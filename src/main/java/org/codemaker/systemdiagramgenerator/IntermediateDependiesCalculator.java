package org.codemaker.systemdiagramgenerator;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class IntermediateDependiesCalculator {

  private Dependies dependies1;
  private Dependies dependies2;

  IntermediateDependiesCalculator(Dependies dependies1, Dependies dependies2) {
    this.dependies1 = dependies1;
    this.dependies2 = dependies2;
  }

  Dependies calculate() {
    Dependies result = new Dependies(dependies1.getTitle() + " --> " + dependies2.getTitle());

    // 1. Calculate which Sys objects stay, come, or go, and store this information for later use when we are about
    // to fill in the result Dependies object.
    Set<Sys> sysSet1 = dependies1.getSysSet();
    Set<Sys> sysSet2 = dependies2.getSysSet();

    Collection<Sys> sysCollectionStaying = CollectionUtils.intersection(sysSet1, sysSet2);
    Collection<Sys> sysCollectionAdded = CollectionUtils.subtract(sysSet2, sysSet1);
    Collection<Sys> sysCollectionRemoved = CollectionUtils.subtract(sysSet1, sysSet2);
    Map<String, Sys> sysMap = new HashMap<>();
    sysCollectionStaying.forEach(sys -> sysMap.put(sys.getName(), new Sys(sys.getName(), sys.getType(),
            MigrationState.STAYING)));
    sysCollectionAdded.forEach(sys -> sysMap.put(sys.getName(), new Sys(sys.getName(), sys.getType(),
            MigrationState.ABOUT_TO_BE_ADDED)));
    sysCollectionRemoved.forEach(sys -> sysMap.put(sys.getName(), new Sys(sys.getName(), sys.getType(),
            MigrationState.ABOUT_TO_BE_REMOVED)));


    // 2. Calculate which Dependy objects stay, come, or go
    Set<Dependy> dependySet1 = dependies1.getDependySet();
    Set<Dependy> dependySet2 = dependies2.getDependySet();

    Collection<Dependy> dependyCollectionStaying = CollectionUtils.intersection(dependySet2, dependySet1);
    Collection<Dependy> dependyCollectionAdded = CollectionUtils.subtract(dependySet2, dependySet1);
    Collection<Dependy> dependyCollectionRemoved = CollectionUtils.subtract(dependySet1, dependySet2);

    dependyCollectionStaying.forEach(dependy -> result.addDependy(new Dependy(sysMap.get(dependy.getFrom().getName())
            , sysMap.get(dependy.getTo().getName()), MigrationState.STAYING)));
    dependyCollectionAdded.forEach(dependy -> result.addDependy(new Dependy(sysMap.get(dependy.getFrom().getName()),
            sysMap.get(dependy.getTo().getName()), MigrationState.ABOUT_TO_BE_ADDED)));
    dependyCollectionRemoved.forEach(dependy -> result.addDependy(new Dependy(sysMap.get(dependy.getFrom().getName())
            , sysMap.get(dependy.getTo().getName()), MigrationState.ABOUT_TO_BE_REMOVED)));

    return result;
  }
}
