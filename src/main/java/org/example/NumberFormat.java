package org.example;

public class NumberFormat {
    /**
     * Computes the factorial of a non-negative integer n.
     * Throws IllegalArgumentException for negative input.
     */
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    /**
     * Computes the greatest common divisor (GCD) of a and b using the Euclidean algorithm.
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    /**
     * Computes the least common multiple (LCM) of a and b.
     * Returns 0 if either input is 0.
     */
    public static long lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs((long) a / gcd(a, b) * b);
    }

    /**
     * Checks whether n is a prime number.
     */
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Computes the binomial coefficient C(n, k) = n! / (k!(n-k)!)
     * Uses a multiplicative approach to minimize overflow risk.
     */
    public static long binomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        k = Math.min(k, n - k);
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + i) / i;
        }
        return result;
    }
}