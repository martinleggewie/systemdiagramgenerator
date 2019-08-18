package org.codemaker.systemdiagramgenerator;

import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;

/**
 * Represents one dependency in the whole dependency graph. I abbreviated it to "Dependy" because "Dependency" is used
 * in other contexts as well, and this could have led to confusion.
 */
class Dependy {

  private Sys from;
  private Sys to;
  private MigrationState migrationState;

  Dependy(Sys from, Sys to, MigrationState migrationState) {
    this.from = from;
    this.to = to;
    this.migrationState = migrationState;
  }

  Sys getFrom() {
    return (Sys) SerializationUtils.clone(from);
  }

  Sys getTo() {
    return (Sys) SerializationUtils.clone(to);
  }

  MigrationState getMigrationState() {
    return migrationState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Dependy dependy = (Dependy) o;
    return from.equals(dependy.from) && to.equals(dependy.to) && migrationState == dependy.migrationState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, migrationState);
  }
}
