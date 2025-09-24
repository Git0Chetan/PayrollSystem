package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrainTest {

    private final Brain brain = new Brain();

    @Test
    void testSayHelloWithName() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, ' followed by the provided name.");
    }

    @Test
    void testSayHelloWithAnotherName() {
        String name = "Bob";
        String expected = "Hello, Bob!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, ' followed by the provided name.");
    }

    @Test
    void testSayHelloWithEmptyName() {
        String name = "";
        String expected = "Hello, !";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, !' for an empty name.");
    }

    @Test
    void testSayHelloWithNullName() {
        String name = null;
        String expected = "Hello, Brain Autoamtion!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "Should return 'Hello, Brain Autoamtion!' when name is null.");
    }
}