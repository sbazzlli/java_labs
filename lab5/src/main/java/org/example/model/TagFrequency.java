package org.example.model;

import java.io.Serializable;

public record TagFrequency(String tagName, int frequency)
        implements Serializable, Comparable<TagFrequency> {

    public TagFrequency {
        if (tagName == null || tagName.isBlank()) {
            throw new IllegalArgumentException("Tag name cannot be null or blank");
        }
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency cannot be negative");
        }
    }

    @Override
    public int compareTo(TagFrequency other) {
        return this.tagName.compareTo(other.tagName);
    }

    public static TagFrequency of(String tagName, int frequency) {
        return new TagFrequency(tagName, frequency);
    }
}

