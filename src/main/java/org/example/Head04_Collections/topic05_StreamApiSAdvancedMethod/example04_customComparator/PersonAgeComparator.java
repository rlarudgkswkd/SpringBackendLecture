package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example04_customComparator;

import java.util.Comparator;

public class PersonAgeComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // 나이를 비교할 때는 단순히 정수 비교 수행
        // p1이 작으면 음수, 같으면 0, 크면 양수 반환
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}
