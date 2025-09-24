package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrainTest {

    @Test
    void testSayHelloWithName() {
        Brain brain = new Brain();
        String result = brain.sayHello("World");
        assertEquals("Hello, World!", result);
    }

    @Test
    void testSayHelloWithAnotherName() {
        Brain brain = new Brain();
        String result = brain.sayHello("Alice");
        assertEquals("Hello, Alice!", result);
    }

    @Test
    void testSayHelloWithNullName() {
        Brain brain = new Brain();
        String result = brain.sayHello(null);
        assertEquals("Hello, Brain Autoamtion!", result);
    }

    @Test
    void testSayHelloWithEmptyName() {
        Brain brain = new Brain();
        String result = brain.sayHello("");
        assertEquals("Hello, !", result);
    }

    @Test
    void testSayHelloWithSpecialCharactersInName() {
        Brain brain = new Brain();
        String result = brain.sayHello("J@ne D0e!");
        assertEquals("Hello, J@ne D0e!!", result);
    }
}