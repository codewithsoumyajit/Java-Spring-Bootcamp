Here’s an **enhanced, clearer, and more student-friendly version** of your notes with better explanations, edge cases, and diagram-style visuals (in Markdown) 👇

---

# 📘 Day 6: Naming Conventions & Arithmetic Fundamentals 📚✨

---

## 1. Naming Conventions in Java 🏷️

### 🔍 What is Naming Convention?

A **naming convention** is a set of rules for naming variables, methods, classes, etc.

💡 Why it matters:

* ✅ Improves readability
* ✅ Makes debugging easier
* ✅ Helps teams collaborate efficiently
* ✅ Makes code self-explanatory

---

## 1.1 Case Sensitivity ⚠️

Java is **case-sensitive**, meaning:

```
name ≠ Name ≠ NAME
```

### Example:

```java
String name = "Soumyajit";
String Name = "Different";

System.out.println(name); // Soumyajit
System.out.println(Name); // Different
```

📌 **Tip:** Avoid similar-looking variable names → reduces confusion.

---

## 1.2 Allowed Characters in Variables 🔤

✅ Allowed:

* Alphabets → `a-z`, `A-Z`
* Digits → `0-9`
* Special → `$`, `_`

### Example:

```java
String myFullName = "Soumyajit";
String full_name = "Soumyajit Nandi";
String $salary = "50000";
String user1 = "User One";
```

---

## 1.3 Invalid Naming Rules ❌

```java
// ❌ Cannot start with number
// String 1name = "Invalid";

// ❌ Hyphen not allowed
// String user-name = "Invalid";

// ❌ Space not allowed
// String full name = "Invalid";
```

---

## 1.4 Reserved Keywords ❗

You **cannot use Java keywords** as variable names.

```java
// ❌ Invalid
// int class = 10;
// int if = 20;
```

📌 Examples of keywords:
`int`, `class`, `if`, `while`, `public`, `static`

---

## 1.5 Best Practices 🎯

| Type     | Convention | Example          |
| -------- | ---------- | ---------------- |
| Variable | camelCase  | `studentAge`     |
| Method   | camelCase  | `calculateSum()` |
| Class    | PascalCase | `StudentService` |
| Constant | ALL_CAPS   | `MAX_LIMIT`      |

📌 **Golden Rule:**
👉 *Write code like someone else will read it tomorrow.*

---

## 1.6 Good vs Bad Naming 💡

```java
// ❌ Bad
int x = 10;

// ✅ Good
int studentAge = 10;
```

---

# 2. Arithmetic Operators 🔢

## 2.1 Operators Overview

| Operator | Meaning        | Example       |
| -------- | -------------- | ------------- |
| `+`      | Addition       | `10 + 5 = 15` |
| `-`      | Subtraction    | `10 - 5 = 5`  |
| `*`      | Multiplication | `10 * 5 = 50` |
| `/`      | Division       | `10 / 5 = 2`  |
| `%`      | Modulus        | `10 % 3 = 1`  |

---

## 2.2 Visual Understanding 📊

### Division vs Modulus

```
7 ÷ 2 = 3 remainder 1

→ Division (/): 3
→ Modulus (%): 1
```

---

## 2.3 Integer vs Floating Division ⚠️

```java
System.out.println(7 / 2);   // 3
System.out.println(7.0 / 2); // 3.5
```

📌 Rule:

```
int / int → int
double involved → double
```

---

# 3. Operator Precedence ⚙️

## 📊 Priority Order

```
Highest Priority
   ↓
1. Unary      → ++, --
2. Multiply   → *, /, %
3. Add        → +, -
4. Assignment → =, +=, etc.
   ↓
Lowest Priority
```

---

## 📌 Diagram Example

Expression:

```java
int result = 10 + 5 * 2;
```

```
Step 1: 5 * 2 = 10
Step 2: 10 + 10 = 20
```

---

## ⚠️ Always Use Parentheses

```java
int result = (10 + 5) * 2; // 30
```

---

# 4. Unary Operators 🔥

## Types

| Operator | Meaning   |
| -------- | --------- |
| `++`     | Increment |
| `--`     | Decrement |

---

## 4.1 Prefix vs Postfix (Visual) 🧠

### Prefix (++x)

```
Step 1: Increment
Step 2: Use value
```

```java
int x = 5;
System.out.println(++x); // 6
```

---

### Postfix (x++)

```
Step 1: Use value
Step 2: Increment
```

```java
int x = 5;
System.out.println(x++); // 5
System.out.println(x);   // 6
```

---

# 5. Compound Assignment 🔄

## Shortcut Table

| Expression | Equivalent  |
| ---------- | ----------- |
| `a += b`   | `a = a + b` |
| `a -= b`   | `a = a - b` |
| `a *= b`   | `a = a * b` |
| `a /= b`   | `a = a / b` |

---

## 🔍 Internal Working

```java
byte num = 10;
num += 5;
```

Internally:

```
num = (byte)(num + 5)
```

---

# 6. Implicit Casting ⚠️

## 🔥 Important Concept

```
byte/short/char → promoted to int during operations
```

---

### ❌ This fails:

```java
byte num = 10;
num = num + 5; // ERROR
```

### ✅ This works:

```java
num += 5;
```

---

# 7. Prefix vs Postfix Deep Dive 🧠

## Example:

```java
int x = 5;
int y = x++ + ++x;
```

---

## Step-by-Step Diagram 📊

```
Initial: x = 5

Step 1: x++ → use 5, then x = 6
Step 2: ++x → x = 7, use 7

Final:
y = 5 + 7 = 12
```

---

# 8. Tricky Expressions ⚠️

## Example 1:

```java
int a = 10;
a = a++ + a++;
```

### Breakdown:

```
Initial: a = 10

First a++ → use 10 → a = 11
Second a++ → use 11 → a = 12

Final:
a = 10 + 11 = 21
```

---

## Example 2:

```java
int y = 5;
y = y++ + y++;
```

```
Initial: y = 5

First y++ → use 5 → y = 6
Second y++ → use 6 → y = 7

Final:
y = 5 + 6 = 11
```

---

# 9. Combined Example 💻

```java
public class Demo {
    public static void main(String[] args) {

        int a = 10;
        a = a++ + a++;
        System.out.println(a); // 21

        int x = 5;
        System.out.println(++x * 2); // 12

        byte num = 10;
        num += 5;
        System.out.println(num); // 15
    }
}
```

---

# 10. Common Mistakes 🚨

❌ Confusing prefix & postfix
❌ Ignoring precedence
❌ Using poor variable names
❌ Expecting decimal in integer division
❌ Writing complex expressions like:

```java
a = a++ + ++a + a++;
```

📌 **Best Practice:**
👉 Break complex expressions into steps.

---

# 11. Summary Table 📋

| Concept          | Key Idea                 | Tip                 |
| ---------------- | ------------------------ | ------------------- |
| Case Sensitivity | `name ≠ Name`            | Avoid similar names |
| Variables        | `$`, `_`, digits allowed | Not at start        |
| Arithmetic       | `+ - * / %`              | Watch division      |
| Precedence       | Unary > *,/ > +,-        | Use brackets        |
| Prefix           | Change first             | Used in logic       |
| Postfix          | Use first                | Used in loops       |
| Compound         | Auto casting             | Cleaner             |
| Casting          | Small → int              | Important concept   |

---


# 🎯 Final Takeaways

✅ Java is **case-sensitive**
✅ Always use **meaningful names**
✅ Understand **operator precedence**
✅ Master **prefix vs postfix** 🔥
✅ Avoid **complex expressions in one line**
✅ Use **parentheses for clarity**
✅ Remember **implicit casting rules**

---
