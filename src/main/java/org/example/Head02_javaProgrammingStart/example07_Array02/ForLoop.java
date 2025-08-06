package org.example.Head02_javaProgrammingStart.example07_Array02;

public class ForLoop {
    public static void main(String[] args) {
        int[] nums = {10, 20, 30};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("nums[" + i + "] = " + nums[i]);
        }
    }
}