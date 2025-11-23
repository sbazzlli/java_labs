package lab1.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ввід одного рядка слів
        String words = sc.nextLine();

        // Розбиття 
        String[] wordArray = words.split(" ");

        List<String> res = new ArrayList<>();

        // Перевірка кожного слова
        for (int i = 0; i < wordArray.length; i++) {
            String w = wordArray[i];
            Set<Character> set = new HashSet<>();
            for (char c : w.toCharArray()) {
                set.add(c);
            }
            if (set.size() == w.length()) {
                res.add(w);
            }
        }

        // Перетворюємо список у масив
        String[] result = res.toArray(new String[res.size()]);

        // Вивід результату
        System.out.println(Arrays.toString(result));
    }
}
