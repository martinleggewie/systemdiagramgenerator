package org.codemaker.systemdiagramgenerator;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Represents one dependency in the whole dependency graph. I abbreviated it to "Dependy" because
 * "Dependency" is used in other contexts as well, and this could have led to confusion.
 */
class Dependy {

    private Sys from;
    private Sys to;

    Dependy(Sys from, Sys to) {
        this.from = from;
        this.to = to;
    }

    public Sys getFrom() {
        return (Sys) SerializationUtils.clone(from);
    }

    public Sys getTo() {
        return (Sys) SerializationUtils.clone(to);
    }
}
