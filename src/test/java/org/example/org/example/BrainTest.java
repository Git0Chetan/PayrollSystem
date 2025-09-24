package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrainTest {

    private Brain brain;

    @BeforeEach
    void setUp() {
        brain = new Brain();
    }

    @Test
    @DisplayName("Should return 'Hello, World!' for input 'World'")
    void testSayHelloWithName() {
        String name = "World";
        String expected = "Hello, World!";
        String actual = brain.sayHello(name);
        Assertions.assertEquals(expected, actual, "The greeting for a valid name should match the expected format.");
    }

    @Test
    @DisplayName("Should return 'Hello, Alice!' for input 'Alice'")
    void testSayHelloWithAnotherName() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String actual = brain.sayHello(name);
        Assertions.assertEquals(expected, actual, "The greeting for another valid name should match the expected format.");
    }

    @Test
    @DisplayName("Should return default greeting 'Hello, Brain Autoamtion!' when name is null")
    void testSayHelloWithNullName() {
        String name = null;
        String expected = "Hello, Brain Autoamtion!";
        String actual = brain.sayHello(name);
        Assertions.assertEquals(expected, actual, "The greeting for a null name should be the default message.");
    }

    @Test
    @DisplayName("Should return 'Hello, !' when name is an empty string")
    void testSayHelloWithEmptyName() {
        String name = "";
        String expected = "Hello, !";
        String actual = brain.sayHello(name);
        Assertions.assertEquals(expected, actual, "The greeting for an empty string name should include the empty string.");
    }

    @Test
    @DisplayName("Should return 'Hello, 123!' for numeric string input '123'")
    void testSayHelloWithNumericString() {
        String name = "123";
        String expected = "Hello, 123!";
        String actual = brain.sayHello(name);
        Assertions.assertEquals(expected, actual, "The greeting for a numeric string name should be correct.");
    }
}