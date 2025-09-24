package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrainTest {

    private Brain brain;

    @BeforeEach
    void setUp() {
        brain = new Brain();
    }

    @Test
    @DisplayName("Should return a personalized greeting for a valid name")
    void sayHello_withValidName_returnsPersonalizedGreeting() {
        String name = "World";
        String expected = "Hello, World!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting for a valid name should match the expected format.");
    }

    @Test
    @DisplayName("Should return a greeting for another valid name")
    void sayHello_withAnotherValidName_returnsPersonalizedGreeting() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting for another valid name should be correct.");
    }

    @Test
    @DisplayName("Should return default greeting when name is null")
    void sayHello_withNullName_returnsDefaultGreeting() {
        String expected = "Hello, Brain Autoamtion!"; // Note: "Autoamtion" is as per the provided source code
        String actual = brain.sayHello(null);
        assertEquals(expected, actual, "The greeting for a null name should be the default one.");
    }

    @Test
    @DisplayName("Should return greeting with empty name when an empty string is provided")
    void sayHello_withEmptyName_returnsGreetingWithEmptyName() {
        String name = "";
        String expected = "Hello, !";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting for an empty string name should be 'Hello, !'.");
    }

    @Test
    @DisplayName("Should return greeting with space as name when a space string is provided")
    void sayHello_withSpaceName_returnsGreetingWithSpaceName() {
        String name = " ";
        String expected = "Hello, !";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting for a space string name should be 'Hello, !'.");
    }
}