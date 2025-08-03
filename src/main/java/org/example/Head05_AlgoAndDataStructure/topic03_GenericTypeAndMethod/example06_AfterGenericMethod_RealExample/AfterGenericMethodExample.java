package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example06_AfterGenericMethod_RealExample;

import org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example05_BeforeGenericMethod_RealExample.User;

import java.util.List;

public class AfterGenericMethodExample {
    public static void main(String[] args) {
        List<String> strings = List.of("Apple", "Banana");
        List<Integer> numbers = List.of(1, 2, 3);
        List<User> users = List.of(new User("Alice"), new User("Bob"));

        PrinterAfterGenericMethod.printList(strings, s -> "String: " + s);
        PrinterAfterGenericMethod.printList(numbers, n -> "Integer: " + n);
        PrinterAfterGenericMethod.printList(users, u -> "User: " + u.getName());
    }
}
