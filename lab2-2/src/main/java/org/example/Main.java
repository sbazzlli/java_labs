package org.example;

import com.google.gson.Gson;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // a. Створити екземпляр Person
        Person original = new Person(25, "Anna", "Koval");

        // b. Конвертувати його в JSON
        Gson gson = new Gson();
        String json = gson.toJson(original);
        System.out.println("JSON: " + json);

        // c. Конвертувати назад в об’єкт
        Person restored = gson.fromJson(json, Person.class);
        System.out.println("restored object: " + restored);

        // d. Перевірити equals
        System.out.println("are they equal " + original.equals(restored));
    }
}