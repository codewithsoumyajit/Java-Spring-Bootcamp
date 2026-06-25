# 🔀 Conditional Statements in Java — Complete Deep Dive

## 📚 Table of Contents

1. [Introduction](#1-introduction-to-conditional-statements)
2. [Foundation Concepts](#2-foundation-concepts)
3. [Boolean Fundamentals](#3-boolean-fundamentals)
4. [Relational Operators](#4-relational-operators)
5. [Logical Operators](#5-logical-operators)
6. [The `if` Statement](#6-the-if-statement)
7. [`if-else` Statement](#7-if-else-statement)
8. [`else-if` Ladder](#8-else-if-ladder)
9. [Nested `if`](#9-nested-if)
10. [`switch` Statement](#10-switch-statement)
11. [Modern Switch (Java 14+)](#11-modern-switch-java-14)
12. [Ternary Operator](#12-ternary-operator)
13. [Short‑Circuit Evaluation](#13-short-circuit-evaluation)
14. [Difference Between `&&` and `&`](#14-difference-between--and-)
15. [Common Beginner Mistakes](#15-common-beginner-mistakes)
16. [Best Practices](#16-best-practices)
17. [JVM Internal Perspective](#17-jvm-internal-perspective)
18. [String Comparison](#18-string-comparison)
19. [Null Safety](#19-null-safety)
20. [Advanced Switch Features](#20-advanced-switch-features)
21. [Real Industry Examples](#21-real-industry-examples)
22. [Decision Tree Thinking](#22-decision-tree-thinking)
23. [Dry Run Practice](#23-dry-run-practice)
24. [Edge Cases](#24-edge-cases)
25. [Interview Questions](#25-interview-questions)
26. [Practice Problems](#26-practice-problems)
27. [Mini Projects](#27-mini-projects)
28. [Learning Strategy](#28-learning-strategy)
29. [Final Mental Model](#29-final-mental-model)
30. [Complete Combined Example](#30-complete-combined-example)

---

## 1. Introduction to Conditional Statements

Conditional statements are the **decision‑making backbone** of any program.  
They allow you to:

- ✔️ Execute specific code **only when a condition is true**
- ✔️ Choose between **multiple execution paths**
- ✔️ Control the program flow **dynamically**

Without conditionals, programs would be a straight line of instructions – no logic, no intelligence.

> 💡 **Real‑world analogy**  
> *“If it is raining → take umbrella, else → do not take umbrella”* – that’s exactly how conditional statements work in Java.

---

## 2. Foundation Concepts

Before diving into conditionals, you must understand:

- 🔹 **Boolean values** (`true` / `false`)
- 🔹 **Relational operators** (`==`, `!=`, `>`, `<`, `>=`, `<=`)
- 🔹 **Logical operators** (`&&`, `||`, `!`)
- 🔹 **Expressions** that evaluate to a boolean
- 🔹 **Truth evaluation** – every condition ultimately returns `true` or `false`

---

## 3. Boolean Fundamentals

In Java, a condition **MUST** return either `true` or `false`.

```java
int age = 20;
System.out.println(age >= 18);   // true
```

The expression `20 >= 18` evaluates to the boolean value `true`.

---

## 4. Relational Operators

Used to compare two values.

| Operator | Meaning               | Example  | Result (if `a=10, b=20`) |
|----------|-----------------------|----------|---------------------------|
| `==`     | Equal to              | `a == b` | `false`                   |
| `!=`     | Not equal to          | `a != b` | `true`                    |
| `>`      | Greater than          | `a > b`  | `false`                   |
| `<`      | Less than             | `a < b`  | `true`                    |
| `>=`     | Greater than or equal | `a >= b` | `false`                   |
| `<=`     | Less than or equal    | `a <= b` | `true`                    |

```java
int a = 10;
int b = 20;
System.out.println(a < b);   // true
```

---

## 5. Logical Operators

Combine multiple conditions to form a single boolean result.

| Operator | Name        | Meaning                                 |
|----------|-------------|-----------------------------------------|
| `&&`     | Logical AND | **Both** conditions must be `true`      |
| `\|\|`     | Logical OR  | **At least one** condition must be `true` |
| `!`      | Logical NOT | Reverses the boolean value              |

### 🔹 Logical AND (`&&`)

| Condition A | Condition B | Result |
|-------------|-------------|--------|
| true        | true        | true   |
| true        | false       | false  |
| false       | true        | false  |
| false       | false       | false  |

```java
int age = 20;
boolean hasId = true;
System.out.println(age >= 18 && hasId);  // true
```

### 🔹 Logical OR (`||`)

| Condition A | Condition B | Result |
|-------------|-------------|--------|
| true        | true        | true   |
| true        | false       | true   |
| false       | true        | true   |
| false       | false       | false  |

```java
System.out.println(10 > 5 || 20 < 10);  // true
```

### 🔹 Logical NOT (`!`)

| Original | Result |
|----------|--------|
| true     | false  |
| false    | true   |

```java
boolean isRaining = true;
System.out.println(!isRaining);  // false
```

---

## 6. The `if` Statement

### ✏️ Syntax

```java
if (condition) {
    // code block
}
```

### ⚙️ How it works

1. JVM evaluates the **condition**
2. If `true` → code block **executes**
3. If `false` → code block is **skipped**

```
        Condition
            |
      ---------------
      |             |
    true          false
      |             |
 Execute Block     Skip
```

### 📌 Example

```java
int age = 19;
if (age >= 18) {
    System.out.println("Eligible to vote");
}
```

> ⚠️ **Important rule** – The condition **must** be a boolean expression.  
> ✅ Correct: `if (age > 18)`  
> ❌ Wrong: `if (age)`  (not allowed in Java, unlike C/C++)

---

## 7. `if-else` Statement

When there are **two possible outcomes**.

```java
if (condition) {
    // true block
} else {
    // false block
}
```

```
          Condition
              |
       ----------------
       |              |
     true          false
       |              |
    if block      else block
```

```java
int number = 7;
if (number % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

---

## 8. `else-if` Ladder

For **multiple conditions**, checked from top to bottom.  
The **first true** condition is executed, the rest are skipped.

```java
if (condition1) {
    // block 1
} else if (condition2) {
    // block 2
} else if (condition3) {
    // block 3
} else {
    // default block
}
```

```
Condition 1?  yes → Block 1
   | no
Condition 2?  yes → Block 2
   | no
Condition 3?  yes → Block 3
   | no
Else Block
```

```java
int marks = 82;
if (marks >= 90) {
    System.out.println("Grade A");
} else if (marks >= 75) {
    System.out.println("Grade B");
} else if (marks >= 50) {
    System.out.println("Grade C");
} else {
    System.out.println("Fail");
}
// Output: Grade B
```

---

## 9. Nested `if`

An `if` statement **inside another `if`**. Useful for layered decisions.

```java
if (condition1) {
    if (condition2) {
        // code
    }
}
```

```
Age >= 18 ?
    | yes
Has License ?
    | yes → Can Drive
```

```java
int age = 22;
boolean hasLicense = true;
if (age >= 18) {
    if (hasLicense) {
        System.out.println("Can drive");
    }
}
```

> 🏭 **Industry use:** authentication, payment validation, role‑based access, OTP checks.

---

## 10. `switch` Statement

Use `switch` when comparing **one variable** against **many fixed values**.

### Traditional Syntax

```java
switch(expression) {
    case value1:
        // code
        break;
    case value2:
        // code
        break;
    default:
        // code
}
```

- JVM evaluates the expression
- Jumps directly to the matching `case`
- `break` prevents **fall‑through**

```java
int day = 2;
switch(day) {
    case 1: System.out.println("Monday"); break;
    case 2: System.out.println("Tuesday"); break;
    default: System.out.println("Invalid");
}
```

### 🔻 Fall‑Through (when `break` is missing)

```java
int n = 1;
switch(n) {
    case 1: System.out.println("One");
    case 2: System.out.println("Two");
    case 3: System.out.println("Three");
}
// Output:
// One
// Two
// Three
```

---

## 11. Modern Switch (Java 14+)

Enhanced switch with **arrow (`->`)** syntax – cleaner and safer.

```java
switch(day) {
    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
    default -> System.out.println("Invalid");
}
```

✅ No `break` needed  
✅ Fewer bugs  
✅ Can be used as an **expression**

```java
String result = switch(day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

### Multi‑line case with `yield`

```java
String result = switch(day) {
    case 1 -> {
        System.out.println("Processing...");
        yield "Monday";
    }
    default -> "Unknown";
};
```

---

## 12. Ternary Operator

A **short form** of `if-else`, ideal for simple assignments.

```java
condition ? valueIfTrue : valueIfFalse;
```

```java
int age = 20;
String result = (age >= 18) ? "Adult" : "Minor";
System.out.println(result);
```

> 💡 Internally equivalent to:
> ```java
> if (age >= 18) result = "Adult";
> else result = "Minor";
> ```

**Use it for** simple conditions, **avoid** for complex business logic.

---

## 13. Short‑Circuit Evaluation

An essential behaviour of `&&` and `||`.

- **`&&`** – if the first condition is `false`, the second is **never evaluated**.
- **`||`** – if the first condition is `true`, the second is **skipped**.

```java
int a = 10, b = 0;
if (b != 0 && a / b > 2) {
    System.out.println("Hello");
}
// No division‑by‑zero error, because (b != 0) is false,
// so a / b is never reached!
```

---

## 14. Difference Between `&&` and `&`

| Operator | Type                      | Short‑Circuit |
|----------|---------------------------|---------------|
| `&&`     | Logical AND               | Yes           |
| `&`      | Bitwise AND / Logical AND | No            |

```java
// & evaluates both sides always
if (false & expensiveMethod()) { ... }   // expensiveMethod() IS called
if (false && expensiveMethod()) { ... }  // expensiveMethod() is NOT called
```

---

## 15. Common Beginner Mistakes

| Mistake | Wrong | Correct |
|---------|-------|---------|
| Assignment instead of comparison | `if (a = 5)` | `if (a == 5)` |
| Missing braces (only first line is part of `if`) | `if (x>0) print("Pos"); print("Done");` | Use `{ }` for multiple statements |
| Wrong condition order in `else‑if` | Grading order messed up | Place most specific first |
| Deep nesting (pyramid code) | 4+ levels of ifs | Refactor with methods or early returns |

---

## 16. Best Practices

✨ **Use meaningful variable names**  
```java
// Bad
if (a > b)

// Good
if (userAge >= minimumAge)
```

✨ **Avoid deep nesting** – extract methods or use early returns.

✨ **Keep conditions simple**  
```java
boolean isAdult = age >= 18;
boolean hasPermission = true;
if (isAdult && hasPermission) { ... }
```

✨ **Always use braces** `{ }` even for a single statement.

---

## 17. JVM Internal Perspective

When compiled, conditionals turn into **bytecode jump instructions**:

```text
COMPARE x, 5
JUMP_IF_FALSE <label>
```

The CPU uses branching, labels, and jump instructions – that’s why conditionals are extremely fast.

---

## 18. String Comparison

> ⚠️ **Never use `==` for String content!**

```java
// ❌ Wrong
if (name == "John")

// ✅ Correct
if (name.equals("John"))
```

- `==` compares **references** (memory addresses)
- `.equals()` compares **actual text**

---

## 19. Null Safety

When comparing a possibly `null` variable, calling `.equals()` directly will cause a `NullPointerException`.

```java
// Dangerous – if name is null, it crashes
if (name.equals("John"))
```

### ✅ Safe approach

```java
if ("John".equals(name))   // never throws NPE
```

---

## 20. Advanced Switch Features

Modern Java supports:

- **Strings** and **enums** as case labels
- **Multiple labels** in one case
- **Pattern matching** (Java 17+ preview, final in 21)

```java
switch(day) {
    case "SATURDAY", "SUNDAY" -> System.out.println("Weekend");
    default -> System.out.println("Weekday");
}
```

### Pattern Matching (Java 17+)

```java
Object obj = "Hello";
switch(obj) {
    case String s -> System.out.println(s.length());
    default -> System.out.println("Unknown");
}
```

---

## 21. Real Industry Examples

🔐 **Authentication**  
```java
if (username.equals(dbUser) && password.equals(dbPassword))
```

🏦 **Banking**  
```java
if (balance >= withdrawAmount)
```

🛒 **E‑Commerce**  
```java
if (cartValue > 5000)   // free delivery
```

🛡️ **Spring Security**  
```java
if (user.hasRole("ADMIN"))
```

---

## 22. Decision Tree Thinking

Visualise every conditional as a tree of decisions.

```
Is user logged in?
    ├── yes → Is user admin?
    │           ├── yes → Admin Dashboard
    │           └── no  → User Dashboard
    └── no  → Login Page
```

Draw decision trees on paper before coding complex logic.

---

## 23. Dry Run Practice

Trace the code step‑by‑step with sample values.

```java
int x = 10;
if (x > 5) {
    System.out.println("A");
} else {
    System.out.println("B");
}
```

**Dry run:**
```
Step 1: evaluate x > 5  →  10 > 5  →  true
Step 2: execute if block → print "A"
```

---

## 24. Edge Cases

- **Floating‑point comparison**  
  Avoid `if (a == b)` for `double`/`float`. Use `Math.abs(a - b) < 0.0001` instead.

- **Integer overflow**  
  Large values can wrap around and give unexpected results.

- **Null references**  
  Always guard against `null` before calling methods.

---

## 25. Interview Questions

| Question | Answer |
|----------|--------|
| Difference between `if` and `switch`? | `if` works with complex ranges, `switch` with fixed values; `switch` can be faster with many branches. |
| Why `break` in `switch`? | To stop fall‑through. |
| `==` vs `.equals()`? | Reference vs. content comparison. |
| What is short‑circuiting? | Skipping unnecessary condition evaluations for efficiency and safety. |

---

## 26. Practice Problems

### 🟢 Beginner
- Check if a number is even or odd
- Determine positive, negative, or zero
- Largest of two numbers
- Voting eligibility

### 🟡 Intermediate
- Grading system (A‑F)
- Leap year checker
- Traffic light simulator
- Simple calculator

### 🔴 Advanced
- ATM withdrawal with balance check
- Login system with max attempts
- Tax calculator (income slabs)
- Role‑based permission engine

---

## 27. Mini Projects

Build these to master conditionals:

- 📊 **Student Result System** (marks → grade, pass/fail)
- 🏧 **ATM Machine** (balance, withdraw, deposit)
- 🍔 **Food Ordering Logic** (menu selection, discounts)
- 🔑 **Authentication System** (username, password, lockout)
- 🛂 **Access Control Engine** (roles, permissions)

---

## 28. Learning Strategy

Best order to learn:

1. Boolean basics
2. Relational operators
3. Logical operators
4. `if`
5. `if-else`
6. `else-if` ladder
7. Nested `if`
8. `switch`
9. Ternary operator
10. Advanced concepts (short‑circuit, modern switch, etc.)

---

## 29. Final Mental Model

Conditional statements are **decision‑making mechanisms**.  
Everything in software depends on them:

- authentication
- payments
- APIs
- security rules
- AI branching
- database queries
- OS kernels

```text
Should I execute this code?  ←  if / else / switch
```

Master them, and you’ve mastered the logic of programming itself.

---

## 30. Complete Combined Example

```java
public class Main {
    public static void main(String[] args) {
        int age = 25;
        boolean hasLicense = true;
        String vehicleType = "CAR";

        if (age >= 18 && hasLicense) {
            switch (vehicleType) {
                case "BIKE" -> System.out.println("Can ride bike");
                case "CAR"  -> System.out.println("Can drive car");
                default     -> System.out.println("Unknown vehicle");
            }
        } else {
            System.out.println("Not eligible to drive");
        }
    }
}
```

---

### ⭐ Key Takeaways

| Concept               | Quick Reminder                                             |
|-----------------------|------------------------------------------------------------|
| Boolean expressions   | Always evaluate to `true` or `false`                       |
| Short‑circuit         | `&&` and `||` skip unnecessary checks                      |
| String comparison     | Use `.equals()`, not `==`                                  |
| Null‑safe comparison  | `"literal".equals(variable)`                               |
| Modern switch         | Arrow (`->`) syntax, no `break`, can yield values          |
| Ternary               | `condition ? trueVal : falseVal`                           |
| Fall‑through          | Missing `break` in traditional switch continues to next case |

Happy Coding! 🚀