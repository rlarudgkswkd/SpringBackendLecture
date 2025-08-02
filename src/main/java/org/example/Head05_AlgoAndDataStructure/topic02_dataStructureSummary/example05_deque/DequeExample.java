package org.example.Head05_AlgoAndDataStructure.topic02_dataStructureSummary.example05_deque;

import java.util.Deque;
import java.util.LinkedList;

public class DequeExample {
    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        System.out.println(deque.removeFirst()); // 1
        System.out.println(deque.removeLast());  // 3
    }
}
