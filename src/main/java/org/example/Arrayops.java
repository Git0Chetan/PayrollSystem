package org.example;

public class Arrayops {

    public static int max(int[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array is empty");
        int max = arr[0];
        for (int v : arr) if (v > max) max = v;
        return max;
    }

    public static int min(int[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array is empty");
        int min = arr[0];
        for (int v : arr) if (v < min) min = v;
        return min;
    }

    public static double average(int[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array is empty");
        long sum = 0;
        for (int v : arr) sum += v;
        return (double) sum / arr.length;
    }

    public static boolean contains(int[] arr, int target) {
        if (arr == null) return false;
        for (int v : arr) if (v == target) return true;
        return false;
    }

    public static int sum(int[] arr) {
        if (arr == null) return 0;
        int s = 0;
        for (int v : arr) s += v;
        return s;
    }
}
