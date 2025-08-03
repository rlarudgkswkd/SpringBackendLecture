package org.example.Head05_AlgoAndDataStructure.topic05_EntitySerializable.example02;

import org.example.Head05_AlgoAndDataStructure.topic05_EntitySerializable.example01.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectReadExample {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {
            User loadedUser = (User) ois.readObject(); // 파일에서 객체 읽기
            System.out.println("역직렬화된 객체: " + loadedUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}