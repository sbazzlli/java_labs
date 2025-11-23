package org.example.service;

import org.example.model.TagFrequency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlTagCounterService {

    private static final int CONNECTION_TIMEOUT = 10000;

    public List<TagFrequency> countTagFrequencies(String url) throws IOException {
        var document = fetchDocument(url);
        var tagCounts = countTags(document);

        return tagCounts.entrySet().stream()
            .map(entry -> TagFrequency.of(entry.getKey(), entry.getValue()))
            .toList();
    }

    private Document fetchDocument(String url) throws IOException {
        try {
            return Jsoup.connect(url)
                .timeout(CONNECTION_TIMEOUT)
                .get();
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid URL: " + url, e);
        }
    }

    private Map<String, Integer> countTags(Document document) {
        var tagCounts = new HashMap<String, Integer>();

        for (var element : document.getAllElements()) {
            var tagName = element.tagName().toLowerCase();
            tagCounts.merge(tagName, 1, Integer::sum);
        }

        return tagCounts;
    }

    public List<TagFrequency> getTagsSortedByName(List<TagFrequency> frequencies) {
        return frequencies.stream()
            .sorted(Comparator.comparing(TagFrequency::tagName))
            .toList();
    }

    public List<TagFrequency> getTagsSortedByFrequency(List<TagFrequency> frequencies) {
        return frequencies.stream()
            .sorted(Comparator.comparingInt(TagFrequency::frequency)
                .thenComparing(TagFrequency::tagName))
            .toList();
    }

    public String formatTagFrequencies(List<TagFrequency> frequencies) {
        return frequencies.stream()
            .map(tf -> String.format("%-20s : %d", tf.tagName(), tf.frequency()))
            .collect(Collectors.joining("\n"));
    }
}

