package org.codemaker.systemdiagramgenerator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents one system in the whole dependency graph. I have called it a "Sys" because using "System" can be quite
 * confusing because the Java System class is reachable from everywhere.
 */
class Sys implements Serializable {
  private String name;
  private String type;

  public Sys(String name, String type) {
    this.name = name;
    this.type = type;
  }

  String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Sys sys = (Sys) o;
    return name.equals(sys.name) && type.equals(sys.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  @Override
  public String toString() {
    return "Sys{" + "name='" + name + '\'' + ", type='" + type + '\'' + '}';
  }
}
