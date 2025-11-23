package org.example.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class MaxWordsLineService {

    private final FileService fileService;

    public MaxWordsLineService(FileService fileService) {
        this.fileService = fileService;
    }

    public String findLineWithMaxWords(Path path) throws IOException {
        var lines = fileService.readAllLines(path);

        return lines.stream()
            .map(line -> new LineWithWordCount(line, countWords(line)))
            .max(Comparator.comparingInt(LineWithWordCount::wordCount))
            .map(LineWithWordCount::line)
            .orElse("");
    }

    private int countWords(String line) {
        if (line == null || line.isBlank()) {
            return 0;
        }
        return (int) Arrays.stream(line.trim().split("\\s+"))
            .filter(word -> !word.isBlank())
            .count();
    }

    private record LineWithWordCount(String line, int wordCount) {}
}

