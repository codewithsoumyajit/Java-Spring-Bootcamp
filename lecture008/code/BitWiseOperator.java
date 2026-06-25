package com.soumyajit.javabootcamp;

/**
 * Demonstrates basic Bitwise Operations in Java
 * and how binary representation works internally.
 */
public class BitWiseOperator {

    public static void main(String[] args) {

        // Example numbers
        int a = 5; // binary: 00000101
        int b = 4; // binary: 00000100

        // -------------------------------
        // Bitwise AND (&)
        // Rule: 1 & 1 = 1, else 0
        // -------------------------------
        int andResult = a & b; // 5 & 4 = 4

        // -------------------------------
        // Bitwise OR (|)
        // Rule: if any bit is 1 → result is 1
        // -------------------------------
        int orResult = a | b; // 5 | 4 = 5

        // -------------------------------
        // Bitwise XOR (^)
        // Rule: different bits → 1
        // -------------------------------
        int xorResult = a ^ b; // 5 ^ 4 = 1

        // -------------------------------
        // Bitwise NOT (~)
        // Rule: flips all bits
        // ~n = -(n + 1)
        // -------------------------------
        int notResult = ~a; // ~5 = -6

        // -------------------------------
        // Left Shift (<<)
        // Rule: shifts bits left, fills 0
        // Equivalent to multiplication by 2^n
        // -------------------------------
        int leftShift = a << 1; // 5 << 1 = 10

        // -------------------------------
        // Right Shift (>>)
        // Rule: shifts right, keeps sign bit
        // Equivalent to division by 2^n
        // -------------------------------
        int rightShift = a >> 1; // 5 >> 1 = 2

        // -------------------------------
        // Unsigned Right Shift (>>>)
        // Rule: shifts right, fills with 0 (ignores sign)
        // -------------------------------
        int unsignedRightShift = -5 >>> 1;

        // -------------------------------
        // Printing Binary Representation
        // -------------------------------
        System.out.println("Binary of 5: " + Integer.toBinaryString(a));
        System.out.println("Binary of 4: " + Integer.toBinaryString(b));

        // -------------------------------
        // Printing Results
        // -------------------------------
        System.out.println("5 & 4 = " + andResult);
        System.out.println("5 | 4 = " + orResult);
        System.out.println("5 ^ 4 = " + xorResult);
        System.out.println("~5 = " + notResult);
        System.out.println("5 << 1 = " + leftShift);
        System.out.println("5 >> 1 = " + rightShift);
        System.out.println("-5 >>> 1 = " + unsignedRightShift);
    }
}