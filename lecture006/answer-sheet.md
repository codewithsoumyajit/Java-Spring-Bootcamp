# Day Assignment – Solutions

---

## Assignment 1 – Smart Data Analyzer

```java
import java.util.Scanner;

public class SmartDataAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name = sc.nextLine();
        int age = sc.nextInt();
        double heightCm = sc.nextDouble();
        double weight = sc.nextDouble();

        double heightM = heightCm / 100;
        double bmi = weight / (heightM * heightM);

        System.out.println("Hello " + name);
        System.out.println("Height in meters: " + heightM);
        System.out.println("BMI: " + bmi);
        System.out.println("Rounded BMI: " + (int)bmi);
    }
}
```

---

## Assignment 2 – Precision Loss Detector

```java
public class PrecisionLoss {
    public static void main(String[] args) {
        double value = 99.99;

        int direct = (int) value;
        int rounded = (int) (value + 0.5);

        System.out.println("Original: " + value);
        System.out.println("After direct conversion: " + direct);
        System.out.println("After rounding logic: " + rounded);
    }
}
```

---

## Assignment 3 – Grocery Billing System

```java
import java.util.Scanner;

public class GroceryBilling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double price = sc.nextDouble();
        int quantity = sc.nextInt();
        int discountPercent = sc.nextInt();

        double total = price * quantity;
        double discount = total * discountPercent / 100;
        double finalAmount = total - discount;

        System.out.println("Total: " + total);
        System.out.println("Discount: " + discount);
        System.out.println("Final Amount: " + finalAmount);
        System.out.println("Rounded Amount: " + (int) finalAmount);
    }
}
```

---

## Assignment 4 – Time Converter

```java
import java.util.Scanner;

public class TimeConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalSeconds = sc.nextInt();

        int hours = totalSeconds / 3600;
        int remaining = totalSeconds % 3600;
        int minutes = remaining / 60;
        int seconds = remaining % 60;

        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("Seconds: " + seconds);
    }
}
```

---

## Assignment 5 – Salary Calculator

```java
import java.util.Scanner;

public class SalaryCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double basic = sc.nextDouble();

        double hra = basic * 0.2;
        double da = basic * 0.1;
        double gross = basic + hra + da;
        double tax = gross * 0.05;

        System.out.println("HRA: " + hra);
        System.out.println("DA: " + da);
        System.out.println("Gross Salary: " + gross);
        System.out.println("Tax: " + tax);
        System.out.println("Rounded Tax: " + (int) tax);
    }
}
```

---

## Assignment 6 – Marks Average Trap

```java
import java.util.Scanner;

public class MarksAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m1 = sc.nextInt();
        int m2 = sc.nextInt();
        int m3 = sc.nextInt();

        double correctAvg = (m1 + m2 + m3) / 3.0;
        int wrongAvg = (m1 + m2 + m3) / 3;

        System.out.println("Correct Average: " + correctAvg);
        System.out.println("Wrong Average: " + wrongAvg);
    }
}
```

---

## Bonus – Digital Wallet System

```java
import java.util.Scanner;

public class DigitalWallet {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double balance = sc.nextDouble();
        double e1 = sc.nextDouble();
        double e2 = sc.nextDouble();

        double remaining = balance - (e1 + e2);

        System.out.println("Remaining Balance: " + remaining);
        System.out.println("Rounded Balance: " + (int) remaining);

        if (remaining < 0) {
            System.out.println("Warning: Insufficient balance");
        }
    }
}
```

---
