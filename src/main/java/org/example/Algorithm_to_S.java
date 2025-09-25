package org.example;

import java.util.Scanner;

public class Algorithm_to_S {

    private static Scanner sc;

    public static void main(String[] args) {
        int i, j, Size, temp;

        sc = new Scanner(System.in);
        System.out.print("Enter the Array size = ");
        Size = sc.nextInt();

        int[] arr = new int[Size];

        System.out.format("Enter Array %d elements  = ", Size);
        for(i = 0; i < Size; i++)
        {
            arr[i] = sc.nextInt();
        }

        System.out.println("Sorting Integers using the Bubble Sort");
        for(i = 0; i < Size; i++)
        {
            for(j = 0; j < Size - i - 1; j++)
            {
                if(arr[j + 1] > (arr[j]))
                {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println(arr[j]);
        }
    }
}