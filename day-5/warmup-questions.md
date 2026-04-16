# ☀️ Day 5 - Warmup Questions (Primitive Data Types & Variables)

## 🤔 Conceptual Questions

### 📦 Variables & Data Types Basics
1. **What is a variable and why do we use variables in programming?**
   - Hint: Think of it as a labeled container

2. **What is the difference between declaring and initializing a variable?**
   - Example: `int age;` vs `int age = 25;`

3. **Why do we need different data types? Can't we just use one type for everything?**

---

## 🔢 Integer Data Types

### 4. **How do Byte, Short, Int, and Long differ from each other?**
   - What is the primary difference between them?

5. **Why does Java need a `long` type if we already have `int`?**
   - When would you use `long` instead of `int`?

6. **What is the range of an `int` in Java and how would you display it programmatically?**

7. **Why do we need to add an 'l' suffix when assigning a large number to a `long` variable?**
   - Example: `long num = 2147483648l;` vs `long num = 2147483648;`

8. **If a number is 50,000, which integer type would you choose and why? (byte, short, int, or long)**

---

## 🟰 Decimal Data Types

### 9. **What is the main difference between `float` and `double` in Java?**

10. **Why is `double` the default floating-point type in Java instead of `float`?**

11. **When you write `3.14` in Java, what data type does it automatically become?**

12. **Why do we need to add an 'f' suffix for float but not for double?**
    - Example: `float num = 3.14f;` vs `double num = 3.14;`

13. **How many decimal digits of precision can `float` and `double` store?**
    - Which one is more precise?

14. **What does `Float.MAX_VALUE` and `Float.MIN_VALUE` tell us?**

---

## 🔤 Other Primitive Types

### 15. **What is the range of the `char` data type and what does it represent?**

16. **How many possible values can a `boolean` variable hold?**

17. **When would you use a `boolean` variable in real-world code?**

---

## 📊 Variable Naming Conventions

### 18. **What is camelCase and why should we use it for variable naming?**
    - Provide one example of good variable names and one example of bad variable names

19. **Why can't a variable name start with a number?**

20. **Which of these are valid variable names in Java?**
    ```
    a) int myAge = 25;
    b) int 1age = 25;
    c) String first-name = "John";
    d) boolean is_student = true;
    e) double price$ = 9.99;
    ```

---

## 🔭 Scientific Notation (E Notation)

### 21. **What does `E+` mean in scientific notation?**
    - Give an example showing what 2.5E+3 equals

22. **What does `E-` mean in scientific notation?**
    - Give an example showing what 1.4E-2 equals

23. **Why does Java use scientific notation for the ranges of `float` and `double`?**

24. **Convert the following to regular numbers:**
    - `3.4E+2`
    - `1.5E-3`
    - `2.0E+5`

---

## 🧩 Practical Application

### 25. **Choose the best data type for each scenario and justify your choice:**
    - A person's age
    - A person's height in meters (with decimals)
    - The population of India
    - A single grade letter (A, B, C, etc.)
    - Whether a student passed or failed
    - The exact time in nanoseconds

---

## 💡 Challenge Questions

### 26. **If you need to store a very precise decimal number for a banking application, would you use `float` or `double`? Why?**

### 27. **What would happen if you tried to assign a value larger than `Integer.MAX_VALUE` to an `int` variable without changing it to `long`?**

### 28. **Explain the difference in memory usage between `byte` and `int`. Why do we still have `byte` if `int` exists?**

### 29. **Can you store a number like 256 in a `byte` variable? Why or why not?**

### 30. **What is the difference between these two declarations?**
    ```java
    float num1 = 10.5;      // Line A
    float num2 = 10.5f;     // Line B
    ```
