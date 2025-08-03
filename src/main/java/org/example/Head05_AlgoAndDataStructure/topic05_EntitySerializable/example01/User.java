package org.example.Head05_AlgoAndDataStructure.topic05_EntitySerializable.example01;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{name='" + name + '\'' + ", age=" + age + '}';
    }
}
