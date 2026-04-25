package com.soumyajit.javabootcamp;

/**
 * Demonstrates different ways of printing in Java:
 * - println
 * - print
 * - printf (formatted output)
 */
public class PrintStatement {

    public static void main(String[] args) {

        int a = 1;
        int b = 2;
        String label = "Sum";

        // -------------------------------
        // Basic println (with concatenation)
        // -------------------------------
        System.out.println("Sum: " + (a + b)); // Correct way

        // Common mistake example
        System.out.println("Sum: " + label); // Prints "Sum: Sum"

        // Correct structured output
        System.out.println(label + ": " + (a + b));

        // -------------------------------
        // print() vs println()
        // -------------------------------
        System.out.print("Hello ");
        System.out.print("World"); // Same line
        System.out.println(); // Move to next line

        // -------------------------------
        // printf (formatted printing)
        // -------------------------------
        System.out.printf("%s of %d and %d is %d%n", label, a, b, (a + b));

        // -------------------------------
        // Floating point formatting
        // -------------------------------
        double value = 12.34567;
        System.out.printf("Formatted value: %.2f%n", value);

        // -------------------------------
        // Error printing (System.err)
        // -------------------------------
        System.err.println("This is an error message");

        // -------------------------------
        // Environment Variable Example
        // -------------------------------
        String path = System.getenv("PATH");
        System.out.println("System PATH: " + path);
    }
}