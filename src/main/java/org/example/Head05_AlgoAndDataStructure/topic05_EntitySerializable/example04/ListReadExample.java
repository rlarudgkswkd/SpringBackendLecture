package org.example.Head05_AlgoAndDataStructure.topic05_EntitySerializable.example04;

import org.example.Head05_AlgoAndDataStructure.topic05_EntitySerializable.example01.User;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.List;

public class ListReadExample {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser"))) {
            List<User> loadedList = (List<User>) ois.readObject();
            System.out.println("역직렬화된 리스트:");
            for (User user : loadedList) {
                System.out.println(user);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}