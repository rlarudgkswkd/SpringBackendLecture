package org.example.Head05_AlgoAndDataStructure.topic02_dataStructureSummary.example04_queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.offer("A"); // 추가
        queue.offer("B"); // 추가
        System.out.println(queue.poll()); // 출력: A
        System.out.println(queue.peek()); // 출력: B
    }
}
