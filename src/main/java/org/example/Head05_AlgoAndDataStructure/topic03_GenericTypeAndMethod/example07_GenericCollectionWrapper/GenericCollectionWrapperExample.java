package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example07_GenericCollectionWrapper;

import java.util.List;

public class GenericCollectionWrapperExample {
    public static void main(String[] args) {
        NonEmptyStringList nonEmptyString = new NonEmptyStringList();
        nonEmptyString.add(""); // 명백하게 오류 발생 or nonEmptyString.add("안녕하세요 코드잇");

        List<String> nonEmptyStrings = nonEmptyString.getInternal();
        for (String emptyString : nonEmptyStrings) {
            System.out.println(emptyString);
        }
    }
}
