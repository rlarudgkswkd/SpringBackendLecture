package org.example.Head02_javaProgrammingStart.example01;

import java.util.Scanner;

public class SumCalculator {
    public static void main(String[] args) {
        int[] inputs = {10,20,30,0};
        int inputIndex = 0;

        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int number = scanner.nextInt();
        while(number !=0){
            sum += number;
            number = scanner.nextInt();
        }
        System.out.println("합계: "+sum);
        scanner.close();
    }
}
