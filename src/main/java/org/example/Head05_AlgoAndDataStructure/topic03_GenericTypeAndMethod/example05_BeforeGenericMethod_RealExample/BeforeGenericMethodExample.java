package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example05_BeforeGenericMethod_RealExample;

import java.util.List;

public class BeforeGenericMethodExample {
    public static void main(String[] args) {
        List<String> strings = List.of("Apple", "Banana");
        List<Integer> numbers = List.of(1, 2, 3);
        List<User> users = List.of(new User("Alice"), new User("Bob"));

        PrinterBeforeGenericMethod printer = new PrinterBeforeGenericMethod();
        printer.printStringList(strings);
        printer.printIntegerList(numbers);
        printer.printUserList(users);
    }
}