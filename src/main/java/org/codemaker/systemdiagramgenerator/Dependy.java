package org.codemaker.systemdiagramgenerator;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Represents one dependency in the whole dependency graph. I abbreviated it to "Dependy" because "Dependency" is used
 * in other contexts as well, and this could have led to confusion.
 */
class Dependy {

  private Sys from;
  private Sys to;
  private Type type;

  Dependy(Sys from, Sys to, Type type) {
    this.from = from;
    this.to = to;
    this.type = type;
  }

  Sys getFrom() {
    return (Sys) SerializationUtils.clone(from);
  }

  Sys getTo() {
    return (Sys) SerializationUtils.clone(to);
  }

  Type getType() {
    return type;
  }

  enum Type {
    ABOUT_TO_BE_ADDED, ABOUT_TO_BE_REMOVED, STAYING
  }
}
