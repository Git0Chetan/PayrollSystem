package org.example;

public class Hello {
    public String sayHello(String name) {
        if (name == null) return "Hello, World!";
        return "Hello, " + name + "!";
    }
}