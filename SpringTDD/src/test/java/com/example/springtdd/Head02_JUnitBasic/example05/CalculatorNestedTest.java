package com.example.springtdd.Head02_JUnitBasic.example05;

import com.example.springtdd.Head02_JUnitBasic.example01.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorNestedTest {

    Calculator calculator = new Calculator();

    @Nested
    @DisplayName("덧셈 테스트")
    class AddTests {

        @Test
        void addPositiveNumbers() {
            assertEquals(5, calculator.add(2, 3));
            System.out.println("AddTests - addPositiveNumbers 실행");
        }

        @Test
        void addNegativeNumbers() {
            assertEquals(-5, calculator.add(-2, -3));
            System.out.println("AddTests - addNegativeNumbers 실행");
        }
    }

    @Nested
    @DisplayName("나눗셈 테스트")
    class DivideTests {

        @Test
        void divideSuccess() {
            assertEquals(2, calculator.divide(4, 2));
            System.out.println("DivideTests - divideSuccess 실행");
        }

        @Test
        void divideByZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> calculator.divide(4, 0));
            System.out.println("DivideTests - divideByZero 실행");
        }
    }
}
