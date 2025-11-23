package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EnglishUkrainianTranslator {
    private Map<String, String> dictionary;

    /**
     * Конструктор, який ініціалізує словник.
     */
    public EnglishUkrainianTranslator() {
        this.dictionary = new HashMap<>();
    }

    /**
     * Додає пару (англійське слово, українське слово) до словника.
     *
     * @param englishWord   Англійське слово
     * @param ukrainianWord Український переклад
     */
    public void addWord(String englishWord, String ukrainianWord) {
        this.dictionary.put(englishWord.toLowerCase(), ukrainianWord.toLowerCase());
        System.out.println("Додано: " + englishWord + " -> " + ukrainianWord);
    }

    /**
     * Перекладає отриману англійську фразу.
     *
     * @param englishPhrase Англійська фраза для перекладу
     * @return Перекладена українська фраза
     */
    public String translatePhrase(String englishPhrase) {
        if (englishPhrase == null || englishPhrase.trim().isEmpty()) {
            return "Фраза для перекладу порожня.";
        }

        String[] words = englishPhrase.toLowerCase().split("\\s+");
        StringBuilder translation = new StringBuilder();

        for (String word : words) {
            String translatedWord = this.dictionary.get(word);

            if (translatedWord != null) {
                translation.append(translatedWord);
            } else {
                translation.append("[").append(word).append("]");
            }
            translation.append(" ");
        }

        return translation.toString().trim();
    }
}