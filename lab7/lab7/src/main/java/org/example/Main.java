package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter words with a space:");
        String words = sc.nextLine();

        // Розбиття рядка на масив слів
        String[] wordArray = words.split(" ");

        // Реалізація через lambda
        String[] result = Arrays.stream(wordArray)
                .filter(w -> w.chars().distinct().count() == w.length())
                .toArray(String[]::new);

        System.out.println(Arrays.toString(result));
    }
}