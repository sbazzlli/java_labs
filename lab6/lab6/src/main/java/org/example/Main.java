package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();

        System.out.println("--- Консольний додаток: Англо-Український перекладач ---");
        System.out.println("Використання HashMap: O(1) середня швидкість пошуку перекладу.");
        System.out.println("----------------------------------------------------------");

        System.out.println("\n*** Наповнення словника заздалегідь визначеними словами ***");
        translator.addWord("the", "цей");
        translator.addWord("a", "один");
        translator.addWord("is", "є");
        translator.addWord("it", "це");
        translator.addWord("i", "я");
        translator.addWord("you", "ти");
        translator.addWord("we", "ми");
        translator.addWord("go", "йти");
        translator.addWord("read", "читати");
        translator.addWord("book", "книга");
        translator.addWord("house", "будинок");


        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String input;


        System.out.println("\n*** Додавання слів зі словника (введіть 'exit' для продовження) ***");
        while (true) {
            System.out.print("Англійське слово (наприклад, 'dog'): ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            String enWord = input;

            System.out.print("Український переклад: ");
            String ukWord = scanner.nextLine();

            if (!enWord.trim().isEmpty() && !ukWord.trim().isEmpty()) {
                translator.addWord(enWord, ukWord);
            } else {
                System.out.println("Помилка: Слова не можуть бути порожніми. Спробуйте ще раз.");
            }
        }

        System.out.println("\n*** Переклад фрази ***");
        System.out.print("Введіть англійську фразу: ");
        String englishPhrase = scanner.nextLine();

        String ukrainianTranslation = translator.translatePhrase(englishPhrase);

        System.out.println("\n----------------------------------------------------------");
        System.out.println("Англійська фраза: " + englishPhrase);
        System.out.println("Український переклад: " + ukrainianTranslation);
        System.out.println("----------------------------------------------------------");

        System.out.println("\n[Слова в квадратних дужках не були знайдені у словнику]");

        scanner.close();
    }
}