package com.example;

import org.junit.jupiter.api.*;

public class GreetingTest {
    Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all tests");
    }

    @BeforeEach
    void setUp() {
        greeting = new Greeting();
        System.out.println("Before each test");
    }

    @Test
    public void testHelloWorld() {
        String result = greeting.helloWorld();
        System.out.println(result);
    }

    @Test
    void helloWorld1() {
        System.out.println(greeting.helloWorld("John"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll() {
         System.out.println("After all tests");
    }
}
