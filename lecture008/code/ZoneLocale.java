package com.soumyajit.javabootcamp;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Demonstrates Locale-based number and currency formatting.
 * Shows how the same number is displayed differently
 * based on region.
 */
public class ZoneLocale {

    public static void main(String[] args) {

        double number = 1234567.89;

        // -------------------------------
        // Default Locale
        // -------------------------------
        System.out.printf("Default Locale: %,.2f%n", number);

        // -------------------------------
        // Different Locale Formatting
        // -------------------------------
        System.out.printf(Locale.US, "US Locale: %,.2f%n", number);
        System.out.printf(Locale.GERMANY, "Germany Locale: %,.2f%n", number);
        System.out.printf(Locale.FRANCE, "France Locale: %,.2f%n", number);

        // -------------------------------
        // Custom Locale (India Example)
        // -------------------------------
        Locale india = new Locale("en", "IN");
        System.out.printf(india, "India Locale: %,.2f%n", number);

        // -------------------------------
        // Currency Formatting
        // -------------------------------
        NumberFormat usCurrency = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat indiaCurrency = NumberFormat.getCurrencyInstance(india);

        System.out.println("US Currency: " + usCurrency.format(number));
        System.out.println("India Currency: " + indiaCurrency.format(number));

        // -------------------------------
        // Default Locale Info
        // -------------------------------
        System.out.println("Current Default Locale: " + Locale.getDefault());
    }
}