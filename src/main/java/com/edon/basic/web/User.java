package com.edon.basic.web;

public class User {
    private String name;
    private String surname;
    private int age;

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String toString() {
        return "name: " + name + " surname: " + surname + " age: " + age;
    }
}
