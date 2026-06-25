# ☕ JAVA NOTES — CLASS & OBJECTS

> ## Beginner → Deep Understanding → Memory Concepts → Interview Ready

---

# 📚 TABLE OF CONTENTS

1. Introduction
2. What is a Class?
3. What is an Object?
4. Why Do We Need Classes & Objects?
5. Real World Analogy
6. Structure of a Class
7. Creating Objects
8. Memory Allocation
9. Reference Variables
10. Multiple Objects
11. Object State & Behavior
12. Constructors (Basic Intro)
13. `this` Keyword (Basic Idea)
14. Difference Between Class & Object
15. Common Beginner Mistakes
16. Interview Questions
17. Summary

---

# 1️⃣ INTRODUCTION

Java is an **Object-Oriented Programming Language (OOP)**.

Everything in Java revolves around:

- Classes
- Objects

Java uses objects to model real-world things.

---

# 2️⃣ WHAT IS A CLASS?

A **Class** is a:

```text
Blueprint / Template / Design
```

used to create objects.

A class defines:

- Variables → Data / Properties
- Methods → Behaviors / Actions

---

# 🏠 Real Life Example

## Blueprint

A house blueprint contains:

- Number of rooms
- Color
- Doors
- Windows

But the blueprint itself is NOT a real house.

Similarly:

```text
Class = Blueprint
```

---

# 📌 Syntax of a Class

```java
class Student {

    // Variables
    String name;
    int age;

    // Method
    void study() {
        System.out.println(name + " is studying");
    }
}
```

---

# 3️⃣ WHAT IS AN OBJECT?

An **Object** is a:

```text
Real instance of a class
```

Objects are actual entities created using the class.

---

# 📌 Example

```java
Student s1 = new Student();
```

Now:

```text
s1
```

is an object of class `Student`.

---

# 🧠 Important Concept

A class only defines structure.

Actual memory is allocated only when object is created.

---

# 4️⃣ WHY DO WE NEED CLASSES & OBJECTS?

Without classes and objects:

❌ Code becomes repetitive  
❌ Difficult to manage  
❌ Hard to scale

Classes help in:

✅ Reusability  
✅ Organization  
✅ Real-world modeling  
✅ Code maintainability

---

# 5️⃣ REAL WORLD ANALOGY

| Real World | Java |
|---|---|
| Car Blueprint | Class |
| Real Car | Object |
| Human Design | Class |
| Actual Person | Object |

---

# 6️⃣ STRUCTURE OF A CLASS

```java
class Car {

    String brand;
    int speed;

    void drive() {
        System.out.println("Car is driving");
    }
}
```

---

# 🔍 Breakdown

| Part | Meaning |
|---|---|
| `class` | Keyword to create class |
| `Car` | Class name |
| `brand`, `speed` | Instance variables |
| `drive()` | Method |

---

# 7️⃣ CREATING OBJECTS

---

# 📌 Syntax

```java
ClassName referenceVariable = new ClassName();
```

---

# 📌 Example

```java
Car c1 = new Car();
```

---

# 🔍 Breakdown

| Part | Meaning |
|---|---|
| `Car` | Class type |
| `c1` | Reference variable |
| `new` | Allocates memory |
| `Car()` | Constructor call |

---

# 8️⃣ ACCESSING VARIABLES & METHODS

---

# 📌 Variables

```java
c1.brand = "BMW";
c1.speed = 120;
```

---

# 📌 Methods

```java
c1.drive();
```

---

# 9️⃣ COMPLETE PROGRAM

```java
class Student {

    String name;
    int age;

    void introduce() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

public class Main {

    public static void main(String[] args) {

        Student s1 = new Student();

        s1.name = "Rahul";
        s1.age = 21;

        s1.introduce();
    }
}
```

---

# 🖥️ OUTPUT

```text
Name: Rahul
Age: 21
```

---

# 🔟 MEMORY ALLOCATION

This is VERY IMPORTANT.

---

# 📌 Code

```java
Student s1 = new Student();
```

---

# 🧠 What Happens Internally?

## Step 1

```java
Student s1;
```

Reference variable is created in:

```text
Stack Memory
```

---

## Step 2

```java
new Student();
```

Object is created in:

```text
Heap Memory
```

---

# 📦 Memory Diagram

```text
STACK                HEAP
-----                ----------------
s1   ------------->  Student Object
                      name = null
                      age = 0
```

---

# 🔥 Key Point

```text
Reference variable stores ADDRESS of object.
```

It does NOT store actual object.

---

# 1️⃣1️⃣ MULTIPLE OBJECTS

One class can create many objects.

---

# 📌 Example

```java
Student s1 = new Student();
Student s2 = new Student();
```

---

# 🧠 Important

Both objects have:

✅ Separate memory  
✅ Separate data

---

# 📌 Example

```java
s1.name = "Rahul";
s2.name = "Aman";
```

Each object stores its own value.

---

# 📦 Memory Visualization

```text
s1  ---> Object 1
s2  ---> Object 2
```

---

# 1️⃣2️⃣ OBJECT STATE & BEHAVIOR

Every object has:

| Concept | Meaning |
|---|---|
| State | Data/Variables |
| Behavior | Methods/Actions |

---

# 📌 Example

```java
class Dog {

    String name; // State

    void bark() { // Behavior
        System.out.println("Barking");
    }
}
```

---

# 1️⃣3️⃣ CONSTRUCTOR (BASIC INTRO)

A constructor initializes objects.

---

# 📌 Example

```java
class Student {

    String name;

    Student() {
        System.out.println("Object Created");
    }
}
```

---

# 📌 Object Creation

```java
Student s1 = new Student();
```

---

# 🖥️ OUTPUT

```text
Object Created
```

---

# 🧠 Important

Constructor name MUST match class name.

---

# 1️⃣4️⃣ BASIC IDEA OF `this`

`this` refers to current object.

---

# 📌 Example

```java
class Student {

    String name;

    void setName(String name) {
        this.name = name;
    }
}
```

---

# 🔍 Why `this`?

Without `this`:

```java
name = name;
```

Java gets confused between:

- Local variable
- Instance variable

---

# 1️⃣5️⃣ CLASS VS OBJECT

| Class | Object |
|---|---|
| Blueprint | Real entity |
| Logical | Physical |
| No memory for data | Allocates memory |
| Defines structure | Stores actual values |

---

# 📌 Example

| Concept | Example |
|---|---|
| Class | Car |
| Object | BMW Car |

---

# 1️⃣6️⃣ COMMON BEGINNER MISTAKES

---

# ❌ Mistake 1 — Forgetting `new`

```java
Student s1;
s1.name = "Rahul";
```

⚠️ Error because object not created.

---

# ✅ Correct

```java
Student s1 = new Student();
```

---

# ❌ Mistake 2 — Thinking Objects Are Variables

Objects are NOT variables.

Variables only store reference/address.

---

# ❌ Mistake 3 — Confusing Class & Object

```text
Class = Design
Object = Real thing
```

---

# 1️⃣7️⃣ INTERVIEW QUESTIONS

---

# ❓ What is a class?

A blueprint/template used to create objects.

---

# ❓ What is an object?

An instance of a class with allocated memory.

---

# ❓ Where are objects stored?

In Heap Memory.

---

# ❓ Where are reference variables stored?

In Stack Memory.

---

# ❓ What does `new` keyword do?

Creates object and allocates memory.

---

# ❓ Can one class create multiple objects?

Yes.

---

# ❓ Does class consume memory?

Only when loaded by JVM. Actual object data memory comes after object creation.

---

# 1️⃣8️⃣ FINAL SUMMARY

---

# 🧠 One-Line Definitions

## Class

```text
Blueprint of objects
```

## Object

```text
Real instance created from class
```

---

# 🚀 CORE FLOW

```text
Class → Object Creation → Memory Allocation → Data Storage → Method Usage
```

---

# 🎯 GOLDEN RULE

```text
Class defines structure,
Object stores actual data.
```

---

# 📌 MINI PRACTICE

---

# 📝 Task 1

Create a `Car` class with:

- brand
- speed
- drive() method

---

# 📝 Task 2

Create 2 objects:

```text
BMW
Audi
```

Store different speed values.

---

# 📝 Task 3

Create a `Mobile` class and print:

- Brand
- Price

using methods.

---

# 🔥 END OF NOTES