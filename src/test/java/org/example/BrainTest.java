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
    @DisplayName("should return a greeting with the provided name")
    void sayHello_withValidName_shouldReturnPersonalizedGreeting() {
        String name = "World";
        String expected = "Hello, World!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting should include the provided name.");
    }

    @Test
    @DisplayName("should return a different greeting with another valid name")
    void sayHello_withAnotherValidName_shouldReturnPersonalizedGreeting() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting should include the provided name.");
    }

    @Test
    @DisplayName("should return a default greeting when name is null")
    void sayHello_withNullName_shouldReturnDefaultGreeting() {
        String name = null;
        String expected = "Hello, Brain Autoamtion!";
        String actual = brain.sayHello(name);
        assertEquals(expected, actual, "The greeting should be the default one for a null name.");
    }
}