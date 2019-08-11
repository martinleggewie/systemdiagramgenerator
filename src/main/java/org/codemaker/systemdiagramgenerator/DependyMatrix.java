package org.codemaker.systemdiagramgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DependyMatrix {

    private Set<Dependy> dependies = new HashSet<>();

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
