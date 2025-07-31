package org.example.Head03_oopProgramming.topic06_Enum.example01;

public class EnumBasicTest {
    public enum Level {
        LOW, MEDIUM, HIGH
    }

    public static void main(String[] args) {
        for (Level lvl : Level.values()) {
            System.out.println(lvl + " ordinal=" + lvl.ordinal());
        }
        Level today = Level.MEDIUM;
        System.out.println("name(): " + today.name());
    }
}