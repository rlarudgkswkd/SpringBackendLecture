package com.example.springtdd.Head02_JUnitBasic.example04;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifecycleTest {

    private int counter = 0;

    @BeforeAll
    void beforeAll() {
        System.out.println("BeforeAll 실행");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach 실행");
    }

    @Test
    void test1() {
        counter++;
        System.out.println("test1 counter = " + counter);
        assertEquals(1, counter);
    }

    @Test
    void test2() {
        counter++;
        System.out.println("test2 counter = " + counter);
        assertEquals(2, counter);
    }

    @AfterEach
    void afterEach() {
        System.out.println("AfterEach 실행");
    }

    @AfterAll
    void afterAll() {
        System.out.println("AfterAll 실행");
    }
}
