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
  private MigrationState migrationState;

  Sys(String name, String type, MigrationState migrationState) {
    this.name = name;
    this.type = type;
    this.migrationState = migrationState;
  }

  String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public MigrationState getMigrationState() {
    return migrationState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Sys sys = (Sys) o;
    return name.equals(sys.name) && type.equals(sys.type) && migrationState == sys.migrationState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, migrationState);
  }
}
