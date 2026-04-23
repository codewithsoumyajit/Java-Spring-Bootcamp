package com.soumyajit.javabootcamp;

public class ArithmeticOperations {
    public static void main(String[] args) {

        // 🔹 Arithmetic Operators
        int a = 10;
        int b = 5;

        System.out.println("Addition (10 + 5): " + (a + b));       // 15
        System.out.println("Subtraction (10 - 5): " + (a - b));    // 5
        System.out.println("Multiplication (10 * 5): " + (a * b)); // 50
        System.out.println("Division (10 / 5): " + (a / b));       // 2
        System.out.println("Modulus (10 % 5): " + (a % b));        // 0


        // 🔹 Operator Precedence
        int result = 10 + 5 * 2;
        System.out.println("\nPrecedence (10 + 5 * 2): " + result); // 20


        // 🔹 Unary Operator has higher precedence
        int x = 5;
        int unaryResult = ++x * 2;

        System.out.println("\nUnary (++x * 2): " + unaryResult); // 12
        System.out.println("Value of x after operation: " + x);  // 6


        // 🔹 Prefix vs Postfix
        int p = 5;

        System.out.println("\nPostfix (p++): " + p++); // prints 5
        System.out.println("Value of p after postfix: " + p); // 6

        System.out.println("Prefix (++p): " + ++p); // prints 7
        System.out.println("Value of p after prefix: " + p); // 7


        // 🔹 Compound Assignment + Implicit Casting
        byte num = 10;

        num += 5; // internally: num = (byte)(num + 5)

        System.out.println("\nCompound (num += 5): " + num); // 15


        byte b1 = 20;
        b1 *= 2; // internally: b1 = (byte)(b1 * 2)

        System.out.println("Compound (b1 *= 2): " + b1); // 40


        // 🔹 Tricky Expression (Postfix)
        int y = 10;

        y = y++ + y++;

        System.out.println("\nExpression (y = y++ + y++): " + y); // 21
    }
}