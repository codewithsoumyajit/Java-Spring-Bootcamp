# 📘 Java Notes — Class Variables vs Instance Variables

---

# 📂 Package Structure

```text
com.soumyajit.javabootcamp
│
├── ClassAndInstance.java
├── Student.java
└── College.java
```

---

# ✅ Refactored Code

---

# 1️⃣ `ClassAndInstance.java`

```java
package com.soumyajit.javabootcamp;

/*
    Demonstrates:
    ----------------
    ✔ Static (Class) Variable
    ✔ Accessing static variable directly inside static method
*/

public class ClassAndInstance {

    // Static/Class Variable
    static String name = "Soumyajit";

    public static void main(String[] args) {

        // Accessing static variable directly
        // because main() is also static
        System.out.println(name);

        // Another valid way:
        // System.out.println(ClassAndInstance.name);
    }
}
```

---

# 2️⃣ `Student.java`

```java
package com.soumyajit.javabootcamp;

/*
    Student class demonstrates:
    ----------------------------
    ✔ Static/Class Variable
    ✔ Instance Variables
*/

public class Student {

    // Static/Class Variable
    // Shared among all objects
    static String name = "Soumyajit";

    // Instance Variable
    // Separate copy for every object
    int age = 25;

    // Instance Variable
    String collegeName = "XYZ";
}
```

---

# 3️⃣ `College.java`

```java
package com.soumyajit.javabootcamp;

import java.util.Arrays;

/*
    Objective:
    -----------
    Access variables of Student class
    and display their values.
*/

public class College {

    public static void main(String[] args) {

        // Creating object of Student class
        Student student = new Student();

        // Accessing static/class variable
        System.out.println(student.name);

        // Better approach for static variable:
        // System.out.println(Student.name);

        // Accessing instance variables
        System.out.println(student.age);
        System.out.println(student.collegeName);

        // Array Example
        int[] arr = {1, 2, 3, 4, 5};

        // Convert array to string representation
        System.out.println(Arrays.toString(arr));
    }
}
```

---

# 📖 Detailed Notes

---

# 🧠 What is a Class Variable?

A **class variable** is declared using the `static` keyword.

```java
static String name = "Soumyajit";
```

---

## 🔹 Features of Class Variables

| Feature           | Description          |
| ----------------- | -------------------- |
| Belongs to        | Class                |
| Memory Allocation | Once per class       |
| Shared?           | Yes                  |
| Accessed By       | Class name preferred |
| Keyword Used      | `static`             |

---

# 📌 Memory Representation

```text
Class Area
-------------------
name = "Soumyajit"
-------------------
```

Only ONE copy exists.

---

# ✅ Accessing Static Variable

## Preferred Way

```java
System.out.println(Student.name);
```

## Possible But Not Recommended

```java
Student s = new Student();
System.out.println(s.name);
```

Why not recommended?

Because `name` belongs to the class, not the object.

---

# 🧠 What is an Instance Variable?

Variables declared without `static`.

```java
int age = 25;
String collegeName = "XYZ";
```

---

## 🔹 Features of Instance Variables

| Feature           | Description                     |
| ----------------- | ------------------------------- |
| Belongs to        | Object                          |
| Memory Allocation | Every object gets separate copy |
| Shared?           | No                              |
| Accessed By       | Object reference                |
| Keyword Used      | No keyword                      |

---

# 📌 Memory Representation

```text
student ─────► Object
               -------------------
               age = 25
               collegeName = XYZ
               -------------------
```

Each object gets its own copy.

---

# 🏗️ Object Creation

```java
Student student = new Student();
```

---

## 🔹 What Happens Internally?

### Step 1️⃣

Memory allocated for object.

### Step 2️⃣

Instance variables initialized.

### Step 3️⃣

Reference stored in `student`.

---

# 📌 Difference Between Class Variable and Instance Variable

| Class Variable          | Instance Variable        |
| ----------------------- | ------------------------ |
| Uses `static` keyword   | No `static` keyword      |
| Belongs to class        | Belongs to object        |
| Shared among objects    | Separate for each object |
| Created once            | Created every object     |
| Access using class name | Access using object      |

---

# 🎯 Why `main()` Accesses Static Variables Directly?

```java
public static void main(String[] args)
```

Since `main()` is static:

✅ It can directly access static members.

❌ It cannot directly access instance variables.

---

# ❌ Invalid Example

```java
public static void main(String[] args) {
    System.out.println(age); // ERROR
}
```

Because `age` is non-static.

---

# ✅ Correct Way

```java
Student s = new Student();
System.out.println(s.age);
```

---

# 📦 Arrays Utility Example

```java
int[] arr = {1,2,3,4,5};
System.out.println(Arrays.toString(arr));
```

---

# 🔹 Why Use `Arrays.toString()`?

Printing array directly:

```java
System.out.println(arr);
```

Output:

```text
[I@36baf30c
```

Not readable.

---

# ✅ Using `Arrays.toString()`

Output:

```text
[1, 2, 3, 4, 5]
```

---

# 📌 Import Statement

```java
import java.util.Arrays;
```

Used to access utility methods of Arrays class.

---

# 🚀 Final Output

```text
Soumyajit
25
XYZ
[1, 2, 3, 4, 5]
```

---

# 🔥 Interview Questions

---

# ❓ What is a static variable?

A variable shared among all objects and belongs to the class.

---

# ❓ Can static variables be accessed using objects?

✅ Yes

But ❌ not recommended.

---

# ❓ Why can't static methods access instance variables directly?

Because instance variables belong to objects, and static methods belong to class.

---

# ❓ How many copies of static variables exist?

Only ONE copy.

---

# ❓ How many copies of instance variables exist?

One copy per object.

---

# 🏁 Conclusion

This example demonstrates:

- ✅ Static/Class Variables
- ✅ Instance Variables
- ✅ Object Creation
- ✅ Memory Allocation
- ✅ Arrays Utility Class
- ✅ Accessing Variables Properly
- ✅ Difference Between Static and Non-Static Members
