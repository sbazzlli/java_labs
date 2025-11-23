package org.example;

public class Person {
    public int age;
    public String name;
    public String surname;

    public Person(int age, String name, String surname) {
        this.age = age;
        this.name = name;
        this.surname = surname;
    }

    public Person() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return age == person.age &&
                name.equals(person.name) &&
                surname.equals(person.surname);
    }
}