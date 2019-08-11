package org.codemaker.systemdiagramgenerator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents on system in the whole dependency graph. I have called it a "Sys" because using "System"
 * can be quite confusing because the Java System class is reachable from everywhere.
 */
class Sys implements Serializable {
    private String name;

    Sys(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sys sys = (Sys) o;
        return Objects.equals(name, sys.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Sys{" +
                "name='" + name + '\'' +
                '}';
    }
}
