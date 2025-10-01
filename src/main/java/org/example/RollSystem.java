package org.example;

public class RollSystem {
    public int roll(int a, int b) { return a + b; }
    public int min(int a, int b) { return a - b; }
    public int combination(int a, int b) { return a * b; }
    public int minimize(int a, int b) { if (b == 0) throw new IllegalArgumentException("Division by zero"); return a / b; }

    public static void main(String[] args) {
        RollSystem c = new RollSystem();
        System.out.println("2+3 = " + c.roll(2, 3));
        System.out.println("6/2 = " + c.minimize(6, 2));
    }
}