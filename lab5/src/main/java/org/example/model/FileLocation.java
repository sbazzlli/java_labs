package org.example.model;

import java.io.Serializable;
import java.nio.file.Path;

public record FileLocation(Path path) implements Serializable {
    public FileLocation {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
