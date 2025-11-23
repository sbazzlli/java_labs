package org.example.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public <T extends Serializable> void writeObject(Path path, T object) throws IOException {
        try (var oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(path)))) {
            oos.writeObject(object);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T readObject(Path path) throws IOException, ClassNotFoundException {
        try (var ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(path)))) {
            return (T) ois.readObject();
        }
    }

    public void writeText(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }

    public List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public String readText(Path path) throws IOException {
        return Files.readString(path);
    }

    public boolean fileExists(Path path) {
        return Files.exists(path);
    }

    public void ensureDirectoryExists(Path path) throws IOException {
        var parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}

