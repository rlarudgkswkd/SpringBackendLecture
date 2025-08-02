package org.example.Head05_AlgoAndDataStructure.topic02_dataStructureSummary.example03_stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> oldStack = new Stack<>();
        oldStack.push(10);
        oldStack.push(20);
        System.out.println("[oldStack 결과]");
        int firstPop = oldStack.pop();
        System.out.println("oldStack 첫번째 pop: " + firstPop);  // 출력: 20
        int secondPeek = oldStack.peek();
        System.out.println("oldStack 두번째 peek: " + secondPeek); // 출력: 10
        int secondPop = oldStack.pop();
        System.out.println("oldStack 두번째 pop: " + secondPop); // 출력: 10

        Deque<Integer> stackWithArrayDeque = new ArrayDeque<>();
        stackWithArrayDeque.push(10);
        stackWithArrayDeque.push(20);
        System.out.println("\n[stackWithArrayDeque 결과]");
        System.out.println("stackWithArrayDeque pop : " + stackWithArrayDeque.pop());  // 출력: 20
        System.out.println("stackWithArrayDeque 다음요소 peek : " + stackWithArrayDeque.peek()); // 출력: 10
    }
}
