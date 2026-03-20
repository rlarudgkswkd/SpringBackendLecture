package com.example.springtdd.Head02_JUnitBasic.example01;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("두 수를 더하면 합이 반환된다")
    void add_shouldReturnSum() {
        int result = calculator.add(2, 3);

        assertEquals(5, result);
        System.out.println("CalculatorTest - add_shouldReturnSum 실행");
    }

    @Test
    @DisplayName("0으로 나누면 예외가 발생한다")
    void divide_whenDivideByZero_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10, 0)
        );

        assertEquals("0으로 나눌 수 없습니다", exception.getMessage());
        System.out.println("CalculatorTest - divide_whenDivideByZero_thenThrowsException 실행");
    }
}