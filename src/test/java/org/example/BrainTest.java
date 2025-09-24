package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrainTest {

    private Brain brain;

    @BeforeEach
    void setUp() {
        brain = new Brain();
    }

    @Test
    void testSayHelloWithName() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, [name]!' for a valid name.");
    }

    @Test
    void testSayHelloWithNullName() {
        String expected = "Hello, Brain Autoamtion!";
        String actual = brain.sayHello(null);
        assertEquals(expected, actual, "Should return default message for null name.");
    }

    @Test
    void testSayHelloWithEmptyName() {
        String name = "";
        String expected = "Hello, !";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, !' for an empty string name.");
    }

    @Test
    void testSayHelloWithSpecialCharactersInName() {
        String name = "J0hn D0e-!@#";
        String expected = "Hello, J0hn D0e-!@#!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should handle special characters in name correctly.");
    }

    @Test
    void testSayHelloWithLongName() {
        String longName = "A".repeat(1000);
        String expected = "Hello, " + longName + "!";
        String actual = brain.sayHello(longName);
        assertEquals(expected, actual, "Should handle very long names correctly.");
    }
}