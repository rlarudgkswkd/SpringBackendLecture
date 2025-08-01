package org.example.Head04_Collections.topic04_LambdaExpression.example06_generatorRef;

@FunctionalInterface
interface PersonFactory {
    Person create(String name, int age);
}

class Person {
    String name;
    int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class ConstructorRefDemo {
    public static void main(String[] args) {
        // 일반 람다
        // PersonFactory factory1 = (n, a) -> new Person(n, a);

        // 생성자 참조
        PersonFactory factory2 = Person::new;

        // 익명 클래스
//        PersonFactory factory3 = new PersonFactory() {
//            @Override
//            public Person create(String name, int age) {
//                return new Person(name, age);
//            }
//        };

        Person p = factory2.create("Kim", 25);
        System.out.println("Created Person -> name: " + p.name + ", age: " + p.age);
    }
}
