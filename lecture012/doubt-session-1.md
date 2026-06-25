# 📘 Java Notes — Constructors, Objects, and Instance Variables

## 📂 Package Declaration

```java
package com.soumyajit.javabootcamp;
```

### 🔹 Explanation

A **package** is used to organize Java classes into folders.

Here:

```java
com.soumyajit.javabootcamp
```

means the class belongs to the package:

📁 `com/soumyajit/javabootcamp`

Packages help in:

* Organizing code
* Avoiding class name conflicts
* Improving project structure

---

# 🧑‍🎓 Class: `Student1`

```java
class Student1 {
```

### 🔹 Explanation

A **class** is a blueprint for creating objects.

`Student1` represents a student having:

* a name
* an age
* behaviors like introducing itself

---

# 📌 Instance Variables

```java
String name;
int age;
```

### 🔹 Explanation

These are called **instance variables** (or object variables).

| Variable | Type     | Purpose             |
| -------- | -------- | ------------------- |
| `name`   | `String` | Stores student name |
| `age`    | `int`    | Stores student age  |

Each object gets its **own copy** of these variables.

---

# 🏗️ Constructor

```java
public Student1() {
    System.out.println("Student constructor and a Student object is created!!");
}
```

## 🔹 What is a Constructor?

A **constructor** is a special method that:

* Initializes objects
* Is automatically called when an object is created

### 🔑 Important Rules

| Rule                          | Description            |
| ----------------------------- | ---------------------- |
| Constructor name = class name | `Student1()`           |
| No return type                | Not even `void`        |
| Automatically called          | During object creation |

---

## 🔥 Constructor Execution

When this line runs:

```java
Student1 s1 = new Student1();
```

Java performs:

### Step 1️⃣

Memory is allocated for the object.

### Step 2️⃣

Default values are assigned:

| Variable | Default Value |
| -------- | ------------- |
| `name`   | `null`        |
| `age`    | `0`           |

### Step 3️⃣

Constructor executes.

Output:

```text
Student constructor and a Student object is created!!
```

---

# 🎤 Method: `introduce()`

```java
void introduce() {
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
}
```

## 🔹 Explanation

This method displays the student's details.

### Output Example

If:

```java
s1.name = "Rahul";
s1.age = 21;
```

then:

```text
Name: Rahul
Age: 21
```

---

# 🚀 Main Class

```java
public class Main {
```

This is the entry point of the Java program.

---

# ▶️ main() Method

```java
public static void main(String[] args)
```

## 🔹 Explanation

The JVM starts execution from the `main()` method.

| Keyword         | Meaning                                 |
| --------------- | --------------------------------------- |
| `public`        | Accessible from anywhere                |
| `static`        | Can run without creating object of Main |
| `void`          | Returns nothing                         |
| `String[] args` | Command-line arguments                  |

---

# 🧱 Object Creation

```java
Student1 s1 = new Student1();
```

## 🔹 Breakdown

| Part         | Meaning                  |
| ------------ | ------------------------ |
| `Student1`   | Class type               |
| `s1`         | Reference variable       |
| `new`        | Creates object in memory |
| `Student1()` | Calls constructor        |

---

# 📌 Reference Variable

```java
s1
```

`s1` does NOT store the actual object.

It stores the **reference (address)** of the object.

---

# 💡 Commented Code

```java
// s1.name = "Rahul";
// s1.age = 21;
//
// s1.introduce();
```

These lines are currently commented.

If uncommented:

## Step 1️⃣ Assign values

```java
s1.name = "Rahul";
s1.age = 21;
```

## Step 2️⃣ Call method

```java
s1.introduce();
```

## Final Output

```text
Student constructor and a Student object is created!!
Name: Rahul
Age: 21
```

---

# 📖 Important Concepts Learned

## ✅ Class

Blueprint for objects.

---

## ✅ Object

Real-world entity created from a class.

Example:

```java
Student1 s1 = new Student1();
```

---

## ✅ Constructor

Special method automatically called during object creation.

---

## ✅ Instance Variables

Variables belonging to an object.

---

## ✅ Method

Behavior/function inside a class.

---

## ✅ Reference Variable

Stores address/reference of an object.

---

# 🧠 Memory Representation

```text
s1  ─────────►  Student Object
                 ----------------
                 name = null
                 age  = 0
                 ----------------
```

After assigning values:

```text
s1  ─────────►  Student Object
                 ----------------
                 name = Rahul
                 age  = 21
                 ----------------
```

---

# 🎯 Final Complete Program

```java
package com.soumyajit.javabootcamp;

class Student1 {

    String name;
    int age;

    public Student1() {
        System.out.println("Student constructor and a Student object is created!!");
    }

    void introduce() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

public class Main {

    public static void main(String[] args) {

        Student1 s1 = new Student1();

        s1.name = "Rahul";
        s1.age = 21;

        s1.introduce();
    }
}
```

---

# ✅ Expected Output

```text
Student constructor and a Student object is created!!
Name: Rahul
Age: 21
```

---

# 🌟 Key Interview Questions

## ❓ What is a constructor?

A special method automatically called during object creation to initialize objects.

---

## ❓ Can a constructor return a value?

❌ No. Constructors do not have a return type.

---

## ❓ What happens if we don't create a constructor?

Java automatically provides a **default constructor**.

---

## ❓ Difference between method and constructor?

| Constructor          | Method           |
| -------------------- | ---------------- |
| Initializes object   | Performs actions |
| Same name as class   | Any valid name   |
| No return type       | Has return type  |
| Called automatically | Called manually  |

---

# 🏁 Conclusion

This program demonstrates:

- ✅ Object creation
- ✅ Constructor execution
- ✅ Class creation
- ✅ Instance variables
- ✅ Method calling
- ✅ Reference variables
- ✅ Default values in Java
