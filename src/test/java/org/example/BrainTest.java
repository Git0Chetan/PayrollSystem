package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BrainTest {

    private Brain brain;

    @BeforeEach
    void setUp() {
        brain = new Brain();
    }

    @Test
    @DisplayName("Should return a personalized greeting for a given name")
    void testSayHelloWithName() {
        String name = "Alice";
        String expectedGreeting = "Hello, Alice!";
        String actualGreeting = brain.sayHello(name);
        assertNotNull(actualGreeting, "Greeting should not be null");
        assertEquals(expectedGreeting, actualGreeting, "The greeting should be personalized with the provided name.");
    }

    @Test
    @DisplayName("Should return a default greeting when the name is null")
    void testSayHelloWithNullName() {
        String name = null;
        String expectedGreeting = "Hello, Brain Autoamtion!";
        String actualGreeting = brain.sayHello(name);
        assertNotNull(actualGreeting, "Greeting should not be null even for a null name");
        assertEquals(expectedGreeting, actualGreeting, "The greeting should be the default one for a null name.");
    }

    @Test
    @DisplayName("Should return a greeting for an empty name string")
    void testSayHelloWithEmptyName() {
        String name = "";
        String expectedGreeting = "Hello, !";
        String actualGreeting = brain.sayHello(name);
        assertNotNull(actualGreeting, "Greeting should not be null for an empty name");
        assertEquals(expectedGreeting, actualGreeting, "The greeting should handle an empty string as a valid name.");
    }

    @Test
    @DisplayName("Should return a greeting for a name with special characters")
    void testSayHelloWithSpecialCharactersInName() {
        String name = "J0hn D0e!";
        String expectedGreeting = "Hello, J0hn D0e!!"; // Note the extra '!' from the method
        String actualGreeting = brain.sayHello(name);
        assertNotNull(actualGreeting, "Greeting should not be null for a name with special characters");
        assertEquals(expectedGreeting, actualGreeting, "The greeting should handle names with special characters.");
    }
}