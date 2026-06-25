# Java Programming Assignment

## Employee Salary Report Generator

**Subject:** Java Programming
**Level:** Beginner
**Marks:** 20
**Concepts:** Data Types · Arithmetic Operators · Bitwise Operators · Locale

---

## Problem Statement

Write a Java program that reads an employee's details — `employeeId` (int), `baseSalary` (double), `hoursWorked` (int), and `countryCode` (String: `"US"`, `"IN"`, or `"DE"`) — and produces a formatted salary report.

---

## Requirements

Your program must fulfil **all** of the following:

### 1. Data Types
Use appropriate primitive types:
- `int` for `employeeId` and `hoursWorked`
- `double` for all salary values
- `boolean` for overtime eligibility
- `byte` to store the tax bracket (0, 1, or 2)

### 2. Arithmetic Operators
- Compute gross salary using:
  ```
  grossSalary = baseSalary + (hoursWorked * 1.5 * (baseSalary / 160))
  ```
- Deduct tax based on bracket:
  - Bracket 0 → 0%
  - Bracket 1 → 10%
  - Bracket 2 → 20%
- `netSalary = grossSalary − taxAmount + bonus`

### 3. Bitwise Operators
- Use `employeeId & 1` to check if the ID is odd → odd ID gets a bonus ($10 / ₹500 / €8)
- Use `hoursWorked >> 3` to determine the tax bracket (cap result at 2 using `Math.min`)
- Store permission flags in an `int` using the `|` operator:
  - Bit 0 = overtime allowed (if `hoursWorked > 160`)
  - Bit 1 = bonus eligible (if ID is odd)

### 4. Locale
Format and print the final net salary using `NumberFormat.getCurrencyInstance(Locale)`:
- `"US"` → `Locale.US`
- `"IN"` → `new Locale("en", "IN")`
- `"DE"` → `Locale.GERMANY`

---

## Program Skeleton

```java
import java.text.NumberFormat;
import java.util.Locale;

public class SalaryReport {
    public static void main(String[] args) {

        // --- inputs ---
        int    employeeId  = 101;
        double baseSalary  = 40000.0;
        int    hoursWorked = 180;
        String countryCode = "US";

        // --- bitwise: odd/even check & tax bracket ---
        boolean isOddId    = (employeeId & 1) == 1;
        byte taxBracket    = (byte) Math.min(hoursWorked >> 3, 2);
        int permissions    = 0;
        // TODO: set bit 0 and bit 1 using | operator

        // --- arithmetic: gross, tax, bonus, net ---
        // TODO: compute grossSalary, taxAmount, bonus, netSalary

        // --- locale: format currency ---
        Locale locale = /* TODO: pick based on countryCode */ null;
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        // --- output ---
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Tax Bracket : " + taxBracket);
        System.out.println("Permissions : " + Integer.toBinaryString(permissions));
        System.out.println("Net Salary  : " + fmt.format(netSalary));
    }
}
```

---

## Test Cases

### Test Case 1 — Standard
| Field        | Value                          |
|-------------|-------------------------------|
| Input        | id=101, salary=40000.0, hours=180, country="US" |
| Tax Bracket  | 2                              |
| Permissions  | 11                             |
| Net Salary   | $36,250.00                     |

> Tests: arithmetic overtime calculation, right shift for bracket, US locale formatting.

---

### Test Case 2 — Standard
| Field        | Value                          |
|-------------|-------------------------------|
| Input        | id=200, salary=30000.0, hours=40, country="IN" |
| Tax Bracket  | 0                              |
| Permissions  | 01                             |
| Net Salary   | ₹30,000.00                     |

> Tests: even ID → no bonus, 0% tax bracket, Indian locale formatting.

---

### Test Case 3 — Edge Case
| Field        | Value                          |
|-------------|-------------------------------|
| Input        | id=55, salary=50000.0, hours=8, country="DE" |
| Tax Bracket  | 1                              |
| Permissions  | 11                             |
| Net Salary   | 45.008,00 €                    |

> Tests: 10% tax deduction, odd ID bonus, German locale formatting (comma as decimal separator).

---

### Test Case 4 — Edge Case
| Field        | Value                          |
|-------------|-------------------------------|
| Input        | id=999, salary=20000.0, hours=0, country="IN" |
| Tax Bracket  | 0                              |
| Permissions  | 01                             |
| Net Salary   | ₹20,500.00                     |

> Tests: zero hours worked, odd ID → bonus only added, no overtime, Indian locale.

---

## Hints

- `hoursWorked >> 3` is equivalent to dividing hours by 8 using bit shifting.
- Use `Math.min(hoursWorked >> 3, 2)` to cap the tax bracket at 2.
- For permissions: `permissions |= 1` sets bit 0; `permissions |= 2` sets bit 1.
- Cast the bracket result to `byte` explicitly: `(byte) Math.min(...)`.
- For the Indian Rupee locale in Java: `new Locale("en", "IN")`.