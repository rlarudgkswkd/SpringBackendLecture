package org.example.Head04_Collections.topic03_StreamAPI.example08_forEach;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ForEachFruitsExample {
    public static void main(String[] args) {
        List<String> fruits = List.of("Apple", "Banana", "Cherry");

        Consumer<String> printConsumer = new Consumer<String>() {
            @Override
            public void accept(String fruit) {
                System.out.println("Fruit: " + fruit);
            }
        };

        Stream<String> fruitStream = fruits.stream();
        fruitStream.forEach(printConsumer);
    }
}
