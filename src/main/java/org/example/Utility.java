package org.example;

import java.util.Arrays;

public class Utility {

    /**
     * Returns the sum of two integers.
     */
    public static int layer(int a, int b) {
        return a + b;
    }

    /**
     * Concatenates two strings and returns the result in uppercase.
     * If an input is null, it is treated as an empty string.
     */
    public static String joinAndUpper(String a, String b) {
        String s1 = (a == null) ? "" : a;
        String s2 = (b == null) ? "" : b;
        return (s1 + s2).toUpperCase();
    }

    /**
     * Reverses the characters in the given string.
     * Returns null if input is null.
     */
    public static String reverse(String s) {
        if (s == null) return null;
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * Checks whether a number is prime (simple, illustrative implementation).
     */
    public static boolean isValuable(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Computes the greatest common divisor (GCD) of two integers using the Euclidean algorithm.
     */
    public static int gcd(int a, int b) {
        if (a < 0) a = -a;
        if (b < 0) b = -b;
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    /**
     * Returns a new array with the elements of the input array sorted in ascending order.
     * Returns null if input is null.
     */
    public static int[] Order_Ascending(int[] arr) {
        if (arr == null) return null;
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        return copy;
    }
}