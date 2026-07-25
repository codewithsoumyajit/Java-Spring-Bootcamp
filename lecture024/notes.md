# Java Encapsulation, Packages & Inheritance — Comprehensive Notes

---

## Table of Contents
1. [Encapsulation](#1-encapsulation)
2. [Access Modifiers](#2-access-modifiers)
3. [Getters and Setters](#3-getters-and-setters)
4. [Java Packages](#4-java-packages)
5. [Inheritance](#5-inheritance)
6. [Types of Inheritance](#6-types-of-inheritance)
7. [The Diamond Problem](#7-the-diamond-problem)
8. [The `super` Keyword](#8-the-super-keyword)
9. [Key Rules & Common Mistakes](#9-key-rules--common-mistakes)
10. [Quick Revision Cheatsheet](#10-quick-revision-cheatsheet)

---

## 1. Encapsulation

Encapsulation is the OOP principle of **wrapping data (fields) and the
methods that operate on that data together into a single unit** — a class —
while **controlling access** to the internal state of that unit from the
outside world.

### Core Purpose

The two main goals of encapsulation are:

- **Data Hiding:** Prevent external code from directly accessing or
  corrupting internal object state
- **Controlled Access:** Force all interactions with the data to go
  through defined methods, where validation logic can be applied

### Real-World Analogy

Think of a **bank account**. Your balance is private — no one can walk
up and directly change it. All interactions go through controlled
operations: `deposit()`, `withdraw()`, `getBalance()`. Each of those
methods can enforce rules (e.g., no negative deposits, no overdraft).
That is encapsulation.

### Without Encapsulation — The Problem

```java
class BankAccount {
    int balance; // public by default — anyone can modify it directly
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        acc.balance = -99999; // ❌ No validation — data is corrupted
    }
}
```

### With Encapsulation — The Solution

```java
class BankAccount {
    private int balance; // hidden from outside

    public void deposit(int amount) {
        if (amount > 0) {          // validation enforced
            balance += amount;
        }
    }

    public void withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdrawal amount");
        }
    }

    public int getBalance() {
        return balance;            // read-only controlled access
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        acc.deposit(1000);
        acc.withdraw(200);
        System.out.println(acc.getBalance()); // 800
        acc.balance = -99999;                 // ❌ Compile error — private field
    }
}
```

### Features of Encapsulation

- Data and behavior are bundled in the same class
- Internal implementation can change without affecting outside code
- Improves **readability**, **maintainability**, and **security**
- Enables **validation logic** before data is modified
- Supports the principle of **least privilege** — expose only what is necessary

---

## 2. Access Modifiers

Access modifiers define the **visibility and accessibility** of classes,
fields, methods, and constructors from different parts of the program.

Java has four access modifiers:

### Visibility Table

| Modifier    | Same Class | Same Package | Subclass (diff pkg) | Everywhere |
|-------------|:----------:|:------------:|:-------------------:|:----------:|
| `private`   | ✅         | ❌           | ❌                  | ❌         |
| `default`   | ✅         | ✅           | ❌                  | ❌         |
| `protected` | ✅         | ✅           | ✅                  | ❌         |
| `public`    | ✅         | ✅           | ✅                  | ✅         |

---

### 2.1 `private` — Most Restrictive

- Accessible **only within the same class**
- Not visible to any other class, even in the same package
- The foundation of data hiding in encapsulation

```java
class Student {
    private String name;   // only Student class can access this
    private int rollNo;

    private void display() {
        System.out.println(name + " " + rollNo); // ✅ inside same class
    }
}

class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.name = "Rohit"; // ❌ Compile error — private field
        s.display();      // ❌ Compile error — private method
    }
}
```

---

### 2.2 `default` (Package-Private) — No Keyword

- When **no modifier is written**, the default access level applies
- Accessible only within **the same package**
- Not accessible from a different package, even by subclasses

```java
// File: com/college/Student.java
package com.college;

class Student {          // default class access
    String name;         // default field access
    void display() { }   // default method access
}

// File: com/other/Main.java
package com.other;
import com.college.Student;

class Main {
    public static void main(String[] args) {
        Student s = new Student(); // ❌ Student not visible outside package
    }
}
```

---

### 2.3 `protected` — Package + Subclasses

- Accessible within the **same package**
- Also accessible by **subclasses in different packages**
- Commonly used in inheritance to allow child classes controlled access
  to parent class members

```java
// File: com/college/Person.java
package com.college;

public class Person {
    protected String name; // accessible by subclasses in any package
    protected void greet() {
        System.out.println("Hello, I am " + name);
    }
}

// File: com/school/Student.java
package com.school;
import com.college.Person;

public class Student extends Person {
    public void show() {
        name = "Rohit"; // ✅ protected field accessible in subclass
        greet();        // ✅ protected method accessible in subclass
    }
}
```

---

### 2.4 `public` — Least Restrictive

- Accessible from **anywhere** — any class, any package
- Use for APIs and methods intended for external use

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b; // accessible from anywhere
    }
}
```

---

## 3. Getters and Setters

Getters and Setters are **public methods** that provide controlled
access to `private` fields. They are the standard mechanism for
implementing encapsulation in Java.

### Getter

A method that **returns the value** of a private field.
Convention: `getFieldName()`

### Setter

A method that **sets/updates the value** of a private field,
typically with validation logic.
Convention: `setFieldName(value)`

### Example

```java
class Student {
    private String name;
    private int age;
    private int marks;

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name — with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Invalid name");
        }
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Setter for age — with range validation
    public void setAge(int age) {
        if (age >= 1 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age");
        }
    }

    // Getter for marks
    public int getMarks() {
        return marks;
    }

    // Setter for marks — with bounds
    public void setMarks(int marks) {
        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        } else {
            System.out.println("Marks must be between 0 and 100");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();

        s.setName("Rohit");
        s.setAge(20);
        s.setMarks(95);

        System.out.println(s.getName());  // Rohit
        System.out.println(s.getAge());   // 20
        System.out.println(s.getMarks()); // 95

        s.setAge(-5);    // Invalid age
        s.setMarks(200); // Marks must be between 0 and 100
    }
}
```

### Read-Only and Write-Only Fields

- **Read-Only:** Provide only a getter, no setter
- **Write-Only:** Provide only a setter, no getter (rare, but valid)

```java
class Config {
    private final String appName = "MyApp";

    // Read-only — no setter provided
    public String getAppName() {
        return appName;
    }
}
```

---

## 4. Java Packages

A **package** is a namespace that groups related classes, interfaces,
and sub-packages together — similar to folders in a file system.

### Why Packages?

- **Organization:** Group related classes logically
- **Access Control:** Default and protected access depend on packages
- **Avoid Name Conflicts:** Two classes with the same name can coexist
  in different packages
- **Modularity:** Large projects become easier to navigate and maintain

### Declaring a Package

```java
// First line of the file
package com.college.management;

public class Student {
    // class body
}
```

### Importing a Package

```java
// Import a specific class
import com.college.management.Student;

// Import all classes in a package
import com.college.management.*;

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
    }
}
```

### Built-in Java Packages

| Package          | Contents                                      |
|------------------|-----------------------------------------------|
| `java.lang`      | Core classes: String, Math, Object (auto-imported) |
| `java.util`      | Collections, Scanner, Date, etc.              |
| `java.io`        | Input/Output streams                          |
| `java.net`       | Networking classes                            |
| `java.sql`       | Database connectivity (JDBC)                  |

### Package Naming Convention

- All lowercase letters
- Reverse domain name format: `com.companyname.projectname.module`
- Example: `com.google.search.engine`

---

## 5. Inheritance

Inheritance is the OOP mechanism by which one class (**child/subclass**)
acquires the **properties and behaviors** of another class
(**parent/superclass**), establishing an **"is-a" relationship**.

### Core Syntax

```java
class Parent {
    // fields and methods
}

class Child extends Parent {
    // inherits Parent's fields and methods
    // can also add its own
}
```

### Why Inheritance?

- **Code Reusability:** Write common logic once in the parent class;
  all child classes inherit it automatically
- **Eliminates Redundancy:** No need to duplicate shared fields/methods
  across multiple classes
- **Extensibility:** Child classes can add new features or override
  existing ones
- **Maintainability:** A change in the parent class propagates to all
  child classes automatically

### Real-World Example

```java
// Parent Class
class Animal {
    String name;
    int age;

    void eat() {
        System.out.println(name + " is eating");
    }

    void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Child Class — inherits eat() and sleep() from Animal
class Dog extends Animal {
    void bark() {
        System.out.println(name + " is barking"); // name inherited from Animal
    }
}

// Child Class — inherits eat() and sleep() from Animal
class Cat extends Animal {
    void meow() {
        System.out.println(name + " is meowing");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.name = "Bruno";
        d.eat();   // Inherited — "Bruno is eating"
        d.sleep(); // Inherited — "Bruno is sleeping"
        d.bark();  // Own method — "Bruno is barking"

        Cat c = new Cat();
        c.name = "Whiskers";
        c.eat();   // Inherited
        c.meow();  // Own method
    }
}
```

### What is Inherited?

| Member               | Inherited? |
|----------------------|------------|
| `public` fields      | ✅ Yes     |
| `protected` fields   | ✅ Yes     |
| `public` methods     | ✅ Yes     |
| `protected` methods  | ✅ Yes     |
| `default` members    | ✅ Yes (same package only) |
| `private` members    | ❌ No      |
| Constructors         | ❌ No (but callable via `super()`) |

---

## 6. Types of Inheritance

### 6.1 Single Inheritance

One child class inherits from exactly one parent class.

```
    Animal
      │
      ▼
     Dog
```

```java
class Animal { void eat() { } }
class Dog extends Animal { void bark() { } }
```

---

### 6.2 Multi-level Inheritance

A chain of inheritance — a class inherits from a class that itself
inherits from another class.

```
    Animal
      │
      ▼
     Dog
      │
      ▼
   Labrador
```

```java
class Animal {
    void eat() { System.out.println("Eating"); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking"); }
}

class Labrador extends Dog {
    void color() { System.out.println("Golden"); }
}

public class Main {
    public static void main(String[] args) {
        Labrador l = new Labrador();
        l.eat();   // ✅ from Animal
        l.bark();  // ✅ from Dog
        l.color(); // ✅ from Labrador
    }
}
```

---

### 6.3 Hierarchical Inheritance

Multiple child classes inherit from a single parent class.

```
       Animal
      /      \
    Dog       Cat
```

```java
class Animal {
    void eat() { System.out.println("Eating"); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking"); }
}

class Cat extends Animal {
    void meow() { System.out.println("Meowing"); }
}
```

---

### 6.4 Multiple Inheritance — NOT Supported for Classes

Multiple inheritance means one child class inheriting from **two or
more parent classes**. Java **does not support this for classes**
due to the **Diamond Problem**.

```
   ClassA    ClassB
      \       /
       ClassC       ← ❌ Not allowed in Java for classes
```

```java
class A { void show() { System.out.println("A"); } }
class B { void show() { System.out.println("B"); } }

class C extends A, B { } // ❌ Compile error — not supported
```

> **Note:** Multiple inheritance IS supported for **interfaces** in Java.
> A class can implement multiple interfaces without ambiguity issues.

---

## 7. The Diamond Problem

The Diamond Problem is the core reason Java does not allow multiple
inheritance for classes.

### The Problem

```
        A
       / \
      B   C
       \ /
        D
```

If class `B` and class `C` both inherit from `A` and override the
same method, and then class `D` inherits from both `B` and `C`,
the JVM faces an **ambiguity**: which version of the method should `D` use?

```java
class A {
    void show() { System.out.println("A"); }
}
class B extends A {
    void show() { System.out.println("B"); }
}
class C extends A {
    void show() { System.out.println("C"); }
}

// If this were allowed:
class D extends B, C {
    // Which show() does D inherit? B's or C's?
    // JVM cannot decide → Diamond Problem
}
```

### Java's Solution

- Multiple inheritance for **classes** → ❌ **Not allowed**
- Multiple inheritance for **interfaces** → ✅ **Allowed**
  (interfaces handle ambiguity via default method rules)

---

## 8. The `super` Keyword

`super` is a reference variable that refers to the **immediate parent
class object**. It is the counterpart of `this` (which refers to the
current object).

### Uses of `super`

| Usage              | Purpose                                              |
|--------------------|------------------------------------------------------|
| `super.field`      | Access parent class field (when child has same name) |
| `super.method()`   | Call parent class method (when child overrides it)   |
| `super()`          | Call parent class constructor                        |

---

### 8.1 Accessing Parent Class Fields

```java
class Animal {
    String name = "Animal";
}

class Dog extends Animal {
    String name = "Dog"; // shadows parent's name

    void display() {
        System.out.println(name);       // Dog  (current class)
        System.out.println(super.name); // Animal (parent class)
    }
}
```

---

### 8.2 Calling Parent Class Methods

```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void eat() {
        super.eat();                      // calls Animal's eat()
        System.out.println("Dog is eating"); // then adds own behavior
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.eat();
        // Output:
        // Animal is eating
        // Dog is eating
    }
}
```

---

### 8.3 Calling Parent Class Constructor with `super()`

When a child class object is created, the **parent class must also be
initialized**. `super()` is used to explicitly call the parent class
constructor from the child class constructor.

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor called");
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name);   // ✅ Must be FIRST LINE — calls Animal(String name)
        this.breed = breed;
        System.out.println("Dog constructor called");
    }

    void display() {
        System.out.println(name + " - " + breed);
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog("Bruno", "Labrador");
        d.display();
    }
}

/*
 Output:
 Animal constructor called
 Dog constructor called
 Bruno - Labrador
*/
```

### Key Rule: Constructor Call Chain

When any object is created in an inheritance chain, constructors are
called **from top to bottom** (parent first, then child):

```
new Dog("Bruno", "Labrador")
         │
         ▼
  Dog constructor starts
         │
         ▼  super(name)
  Animal constructor runs → prints "Animal constructor called"
         │
         ▼  returns to Dog
  Dog constructor continues → prints "Dog constructor called"
```

### `super()` Rules

- `super()` must be the **very first statement** in the child constructor
- If you do not write `super()` explicitly, Java inserts `super()`
  (no-arg) automatically
- If the parent has no no-arg constructor and you do not call `super(...)`
  explicitly, the code **will not compile**

```java
class Animal {
    Animal(String name) { } // no default constructor
}

class Dog extends Animal {
    Dog() {
        // Java tries to insert super() automatically
        // ❌ Compile error — Animal has no no-arg constructor
    }

    Dog(String name) {
        super(name); // ✅ Explicit call to matching parent constructor
    }
}
```

---

## 9. Key Rules & Common Mistakes

### Rules Summary

| Concept | Key Rule |
|---------|----------|
| Encapsulation | Make fields `private`; expose via `public` getters/setters |
| `private` | Only same class can access |
| `default` | Same package only |
| `protected` | Same package + subclasses in other packages |
| `public` | Accessible everywhere |
| Inheritance | Use `extends` keyword; establishes "is-a" relationship |
| `private` members | NOT inherited by child classes |
| Constructors | NOT inherited; called via `super()` |
| Multiple inheritance | NOT supported for classes; use interfaces instead |
| `super()` | Must be first line in child constructor |
| Auto `super()` | Java inserts `super()` if not explicitly written |
| Diamond Problem | Ambiguity of multi-parent inheritance; solved by disallowing it |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Accessing private field directly from outside
class Student {
    private int marks;
}
Student s = new Student();
s.marks = 95; // ❌ Compile error

// ✅ CORRECT — Use setter
s.setMarks(95);

// ❌ MISTAKE 2 — super() not on first line
class Dog extends Animal {
    Dog(String name) {
        System.out.println("Dog"); // Something before super()
        super(name);               // ❌ Compile error
    }
}

// ✅ CORRECT
class Dog extends Animal {
    Dog(String name) {
        super(name);               // ✅ First line
        System.out.println("Dog");
    }
}

// ❌ MISTAKE 3 — Assuming private members are inherited
class Animal {
    private String secret = "hidden";
}
class Dog extends Animal {
    void show() {
        System.out.println(secret); // ❌ Compile error — not inherited
    }
}

// ❌ MISTAKE 4 — Multiple class inheritance
class C extends A, B { } // ❌ Not supported in Java

// ✅ CORRECT — Use interfaces for multiple inheritance
interface A { void show(); }
interface B { void display(); }
class C implements A, B {
    public void show() { }
    public void display() { }
}
```

---

## 10. Quick Revision Cheatsheet

```
ENCAPSULATION
──────────────────────────────────────────────────────────────
 → Wrapping data + methods in a single unit (class)
 → Goal: Data hiding + controlled access
 → Make fields private; expose via public getters/setters
 → Setters allow validation before data is changed
 → Improves security, maintainability, and readability

ACCESS MODIFIERS (most → least restrictive)
──────────────────────────────────────────────────────────────
 private    → same class only
 default    → same package only (no keyword written)
 protected  → same package + subclasses in other packages
 public     → everywhere

PACKAGES
──────────────────────────────────────────────────────────────
 → Logical grouping of related classes (like folders)
 → Declared with: package com.name.module;
 → Imported with: import com.name.module.ClassName;
 → Help with organization, access control, and naming

INHERITANCE
──────────────────────────────────────────────────────────────
 → Child class acquires properties of parent class
 → Keyword: extends
 → Establishes "is-a" relationship (Dog is-a Animal)
 → private members are NOT inherited
 → Constructors are NOT inherited (use super() to call them)

TYPES OF INHERITANCE
 Single       → One child, one parent
 Multi-level  → A → B → C (chain)
 Hierarchical → One parent, many children
 Multiple     → ❌ NOT supported for classes (Diamond Problem)
                ✅ Supported for interfaces

DIAMOND PROBLEM
 → Ambiguity when two parents have same method
 → Java solves it by disallowing multiple class inheritance

SUPER KEYWORD
──────────────────────────────────────────────────────────────
 super.field    → access parent's field (when name is shadowed)
 super.method() → call parent's method (when overridden)
 super()        → call parent's constructor
                  MUST be the FIRST line in child constructor
                  Auto-inserted as super() if not written
                  Parent has no no-arg constructor? Must call
                  super(args) explicitly or code won't compile
```

---

*End of Notes*