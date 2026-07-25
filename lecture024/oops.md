# Object-Oriented Programming (OOP) — Foundation Notes
### A Complete Conceptual Guide Before Diving into OOP in Java

---

## Table of Contents
1. [What is Programming Paradigm?](#1-what-is-a-programming-paradigm)
2. [What is OOP?](#2-what-is-oop)
3. [Procedural vs Object-Oriented Programming](#3-procedural-vs-object-oriented-programming)
4. [Core Terminology — Class & Object](#4-core-terminology--class--object)
5. [The Four Pillars of OOP](#5-the-four-pillars-of-oop)
6. [OOP Relationships](#6-oop-relationships)
7. [Advantages of OOP](#7-advantages-of-oop)
8. [OOP in the Real World](#8-oop-in-the-real-world)
9. [Quick Revision Cheatsheet](#9-quick-revision-cheatsheet)

---

## 1. What is a Programming Paradigm?

A **programming paradigm** is a fundamental style or approach to
writing and organizing code. It defines how you think about and
structure your programs.

### Major Programming Paradigms

| Paradigm | Core Idea | Examples |
|----------|-----------|----------|
| **Procedural** | Code as a sequence of instructions / functions | C, Pascal |
| **Object-Oriented** | Code organized around objects that hold data and behavior | Java, C++, Python |
| **Functional** | Code as a set of pure mathematical functions | Haskell, Scala |
| **Declarative** | Describe WHAT to do, not HOW | SQL, HTML |

Java is primarily an **Object-Oriented** language, though modern Java
also supports functional-style programming through lambdas and streams.

---

## 2. What is OOP?

**Object-Oriented Programming (OOP)** is a programming paradigm that
organizes software design around **objects** — self-contained units
that bundle together:

- **Data** (what the object knows) → fields / attributes
- **Behavior** (what the object can do) → methods / functions

Instead of writing code as a long sequence of instructions, OOP models
your program as a collection of interacting objects — mirroring how
the real world works.

### The Central Idea

```
Real World                    OOP Equivalent
──────────────────────────────────────────────
A physical car                → An Object
Blueprint to build a car      → A Class
Color, speed, brand of car    → Fields (data)
Drive, brake, accelerate      → Methods (behavior)
```

Everything in Java revolves around classes and objects. Even the entry
point `main()` must live inside a class.

---

## 3. Procedural vs Object-Oriented Programming

Understanding why OOP was invented requires understanding the problems
with procedural programming first.

### Procedural Programming

- Program is divided into **functions/procedures**
- Data is separate from functions
- Functions share global data freely
- As programs grow, managing shared data becomes chaotic

```c
// Procedural style (C-like)
int studentName;
int studentAge;
int studentMarks;

void printStudent() { ... }
void updateMarks()  { ... }
```

### Problems with Procedural Style

- Data is exposed and can be modified from anywhere
- No clear ownership of data
- Difficult to model real-world entities naturally
- Poor scalability for large systems

### Object-Oriented Programming

- Program is divided into **objects**
- Data and behavior are **bundled together** inside objects
- Objects control access to their own data
- Real-world entities are modeled naturally

```java
// OOP style (Java)
class Student {
    String name;    // data lives INSIDE the object
    int age;
    int marks;

    void printDetails() { ... }  // behavior lives with the data
    void updateMarks(int m) { ... }
}
```

### Side-by-Side Comparison

| Feature | Procedural | Object-Oriented |
|---------|------------|-----------------|
| Basic unit | Function | Object |
| Data & behavior | Separate | Bundled together |
| Data access | Open / global | Controlled via access modifiers |
| Code reuse | Function calls | Inheritance |
| Real-world modeling | Difficult | Natural |
| Scalability | Poor for large systems | Excellent |
| Security | Low | High (data hiding) |

---

## 4. Core Terminology — Class & Object

### Class

A **class** is a **blueprint or template** that defines:
- What data (fields) an entity will hold
- What actions (methods) an entity can perform

A class itself does not occupy heap memory — it is just a definition.

```java
class Car {
    // Fields — what a Car knows about itself
    String brand;
    String color;
    int speed;

    // Methods — what a Car can do
    void accelerate() {
        speed += 10;
        System.out.println("Speed: " + speed);
    }

    void brake() {
        speed -= 10;
        System.out.println("Speed: " + speed);
    }
}
```

### Object

An **object** is a **concrete instance** of a class — a real entity
created from the blueprint that occupies memory and has its own state.

```java
public class Main {
    public static void main(String[] args) {
        // Creating objects from the Car blueprint
        Car car1 = new Car();   // Object 1
        car1.brand = "Toyota";
        car1.color = "Red";

        Car car2 = new Car();   // Object 2 — completely independent
        car2.brand = "Honda";
        car2.color = "Blue";

        car1.accelerate(); // Speed: 10
        car2.accelerate(); // Speed: 10  (independent from car1)
    }
}
```

### Class vs Object

| Aspect | Class | Object |
|--------|-------|--------|
| What it is | Blueprint / Template | Instance / Real entity |
| Memory | No heap memory (just definition) | Occupies heap memory |
| Created with | `class` keyword | `new` keyword |
| Count | One per definition | Many objects from one class |
| Example | `Car` (the concept) | `car1`, `car2` (actual cars) |

### Anatomy of an Object in Memory

```
CLASS (blueprint — no heap memory)       OBJECT (heap memory)
┌────────────────────────────┐           ┌───────────────────┐
│ class Car {                │           │ car1              │
│     String brand;          │  new Car()│  brand: "Toyota"  │
│     String color;          │ ─────────►│  color: "Red"     │
│     int speed;             │           │  speed: 0         │
│     void accelerate() { }  │           └───────────────────┘
│ }                          │           ┌───────────────────┐
└────────────────────────────┘  new Car()│ car2              │
                                ────────►│  brand: "Honda"   │
                                         │  color: "Blue"    │
                                         │  speed: 0         │
                                         └───────────────────┘
```

---

## 5. The Four Pillars of OOP

OOP is built on four fundamental principles. Together they make code
more secure, reusable, flexible, and maintainable.

```
             ┌─────────────────────────────────┐
             │        FOUR PILLARS OF OOP      │
             └─────────────────────────────────┘
                          │
        ┌─────────────────┼──────────────────┐──────────────────┐
        ▼                 ▼                  ▼                  ▼
  Encapsulation     Inheritance         Polymorphism       Abstraction
  (Data Hiding)    (Code Reuse)        (Many Forms)    (Hide Complexity)
```

---

### Pillar 1 — Encapsulation

**Definition:** Wrapping data (fields) and the methods that operate on
that data together into a single unit (class), while **controlling
access** to the internal state.

**Core Idea:** Hide the internal details; expose only what is necessary.

```java
class BankAccount {
    private double balance; // hidden — no direct outside access

    public void deposit(double amount) {
        if (amount > 0) balance += amount; // controlled access with logic
    }

    public double getBalance() {
        return balance; // read-only controlled access
    }
}
```

**Real-world analogy:** A capsule (pill) — the medicine is wrapped inside.
You take the capsule as a whole; you do not interact with the ingredients
individually.

**Benefits:**
- Data protection from unauthorized modification
- Validation logic enforced through setters
- Internal implementation can change without affecting outside code

---

### Pillar 2 — Inheritance

**Definition:** A mechanism where one class (**child/subclass**) acquires
the properties and behaviors of another class (**parent/superclass**).

**Core Idea:** Reuse existing code; extend it without rewriting it.

```java
class Animal {
    String name;
    void eat()   { System.out.println(name + " eats"); }
    void sleep() { System.out.println(name + " sleeps"); }
}

class Dog extends Animal {     // Dog inherits from Animal
    void bark() { System.out.println(name + " barks"); }
    // eat() and sleep() are inherited — no need to rewrite
}

class Cat extends Animal {     // Cat also inherits from Animal
    void meow() { System.out.println(name + " meows"); }
}
```

**Real-world analogy:** A child inherits traits from parents — eye color,
height, personality — while also developing their own unique traits.

**Benefits:**
- Eliminates code duplication
- Establishes logical relationships between classes
- Changes in parent automatically apply to all children

---

### Pillar 3 — Polymorphism

**Definition:** The ability of a single entity (method name, operator)
to take **many forms** depending on context.

**Core Idea:** One interface, multiple implementations.

**Two types in Java:**

#### Compile-Time Polymorphism (Method Overloading)
Same method name, different parameter lists — decided at compile time.

```java
class Calculator {
    int add(int a, int b)            { return a + b; }
    double add(double a, double b)   { return a + b; }
    int add(int a, int b, int c)     { return a + b + c; }
    // Same name "add" — different forms based on parameters
}
```

#### Runtime Polymorphism (Method Overriding)
Child class provides its own version of a parent class method —
decided at runtime.

```java
class Animal {
    void makeSound() { System.out.println("Some sound"); }
}
class Dog extends Animal {
    void makeSound() { System.out.println("Bark"); } // overrides parent
}
class Cat extends Animal {
    void makeSound() { System.out.println("Meow"); } // overrides parent
}

Animal a = new Dog();
a.makeSound(); // "Bark" — runtime decides which version to call
```

**Real-world analogy:** A person can be a student, an employee, and a
son simultaneously — the same person takes different roles (forms)
in different contexts.

**Benefits:**
- Flexibility and extensibility
- Code written for the parent type works with all child types
- Enables clean, scalable design patterns

---

### Pillar 4 — Abstraction

**Definition:** Hiding the **complex internal implementation** details
and exposing only the **essential, relevant features** to the user.

**Core Idea:** Show WHAT an object does; hide HOW it does it.

```java
// Abstract class — defines what must exist, hides how
abstract class Shape {
    abstract double area();   // what: every shape must have area()
                              // how: each shape calculates it differently
}

class Circle extends Shape {
    double radius;
    Circle(double r) { this.radius = r; }

    double area() { return 3.14159 * radius * radius; } // how: circle formula
}

class Rectangle extends Shape {
    double length, width;
    Rectangle(double l, double w) { length = l; width = w; }

    double area() { return length * width; } // how: rectangle formula
}

public class Main {
    public static void main(String[] args) {
        Shape s1 = new Circle(5);
        Shape s2 = new Rectangle(4, 6);

        System.out.println(s1.area()); // 78.53975 — user doesn't care HOW
        System.out.println(s2.area()); // 24.0
    }
}
```

**Real-world analogy:** When you press the accelerator in a car, you do
not need to know how fuel injection, combustion, and the drivetrain work —
you just know pressing it makes the car go faster.

**Benefits:**
- Reduces complexity for the user of a class
- Forces consistent contracts via abstract methods
- Implementation can change without affecting users

---

### The Four Pillars — Summary

| Pillar | Core Question | Key Mechanism | Primary Benefit |
|--------|--------------|---------------|-----------------|
| Encapsulation | Who can access my data? | Access modifiers + getters/setters | Security & control |
| Inheritance | Can I reuse existing code? | `extends` keyword | Code reusability |
| Polymorphism | Can one thing take many forms? | Overloading & overriding | Flexibility |
| Abstraction | What do I expose to the user? | Abstract classes & interfaces | Simplicity |

---

## 6. OOP Relationships

Objects in a system relate to each other in specific ways.
Understanding these relationships is crucial for good design.

### 6.1 "Is-A" Relationship — Inheritance

A child class IS a type of the parent class.

```
Dog IS-A Animal          ✅ Use inheritance
Car IS-A Vehicle         ✅ Use inheritance
Manager IS-A Employee    ✅ Use inheritance
```

```java
class Animal { }
class Dog extends Animal { } // Dog IS-A Animal
```

### 6.2 "Has-A" Relationship — Composition

An object CONTAINS another object as a field.

```
Car HAS-A Engine         ✅ Use composition
Student HAS-A Address    ✅ Use composition
Order HAS-A Product      ✅ Use composition
```

```java
class Engine {
    int horsepower;
    void start() { System.out.println("Engine started"); }
}

class Car {
    String brand;
    Engine engine; // Car HAS-A Engine

    Car(String brand) {
        this.brand  = brand;
        this.engine = new Engine(); // Engine is part of Car
    }

    void startCar() {
        engine.start(); // delegates to Engine
        System.out.println(brand + " is moving");
    }
}
```

### When to Use Which?

| Situation | Relationship | Approach |
|-----------|-------------|----------|
| Child IS a type of Parent | Is-A | Inheritance (`extends`) |
| Object CONTAINS another | Has-A | Composition (field) |
| "Is a" sounds natural | Is-A | Inheritance |
| "Has a" sounds natural | Has-A | Composition |

> **General Rule:** Prefer **composition over inheritance** when in doubt.
> Composition is more flexible and avoids tight coupling.

---

## 7. Advantages of OOP

### Technical Advantages

| Advantage | Explanation |
|-----------|-------------|
| **Modularity** | Each class is an independent module; changes in one do not break others |
| **Reusability** | Inheritance and composition allow code reuse without rewriting |
| **Scalability** | Large systems are easier to design and extend with OOP |
| **Maintainability** | Encapsulation means changes stay localized |
| **Security** | Data hiding prevents unauthorized access or corruption |
| **Flexibility** | Polymorphism allows the same interface to work with different types |
| **Testability** | Individual objects/classes can be tested in isolation |

### Real-World Modeling

OOP mirrors how humans naturally think about the world — in terms
of entities with properties and behaviors. A banking system has
`Account`, `Customer`, `Transaction`. An e-commerce app has `Product`,
`Cart`, `Order`, `Payment`. These map directly to classes and objects.

---

## 8. OOP in the Real World

### Example — Modelling a University System

```
┌─────────────────────────────────────────────┐
│              University System               │
├──────────────┬──────────────┬───────────────┤
│   Person     │   Course     │  Department   │
│  ─────────   │  ─────────   │  ─────────    │
│  name        │  courseId    │  deptName     │
│  age         │  courseName  │  hod          │
│  email       │  credits     │  courses[]    │
│              │              │               │
├──────────────┴──────────────┴───────────────┤
│    Student (IS-A Person)                     │
│     rollNo, semester, courses[]              │
│                                              │
│    Professor (IS-A Person)                   │
│     employeeId, department, salary           │
└─────────────────────────────────────────────┘
```

```java
class Person {
    private String name;
    private int age;
    // getters, setters
}

class Student extends Person {     // IS-A Person
    private String rollNo;
    private int semester;
    // student-specific methods
}

class Professor extends Person {   // IS-A Person
    private String employeeId;
    private Department department; // HAS-A Department
    // professor-specific methods
}
```

### Example — Every Real Java App Uses OOP

| Application | Classes (Objects) |
|-------------|-------------------|
| Banking App | Account, Customer, Transaction, Branch |
| E-commerce | Product, Cart, Order, Payment, User |
| Social Media | User, Post, Comment, Like, Message |
| Game | Player, Enemy, Weapon, Level, Score |

---

## 9. Quick Revision Cheatsheet

```
WHAT IS OOP?
──────────────────────────────────────────────────────────────
 → Programming paradigm that organizes code around OBJECTS
 → Objects = Data (fields) + Behavior (methods) bundled together
 → Models real-world entities naturally
 → Java is primarily an OOP language

CLASS vs OBJECT
──────────────────────────────────────────────────────────────
 Class  → Blueprint / Template / Definition (no heap memory)
 Object → Real instance created from class (lives in heap)
 One class → Many objects (each with independent state)
 Created with: new ClassName()

THE FOUR PILLARS
──────────────────────────────────────────────────────────────
 Encapsulation  → Bundle data + methods; hide internal state
                  Private fields + public getters/setters
                  Goal: Security and controlled access

 Inheritance    → Child class acquires parent's properties
                  Keyword: extends
                  Goal: Code reusability; "is-a" relationship

 Polymorphism   → One name, many forms
                  Overloading  = compile-time (same name, diff params)
                  Overriding   = runtime (child redefines parent method)
                  Goal: Flexibility

 Abstraction    → Hide HOW; show only WHAT
                  Abstract classes & interfaces
                  Goal: Simplicity; hide complexity from user

OOP RELATIONSHIPS
──────────────────────────────────────────────────────────────
 Is-A  → Inheritance  (Dog is-a Animal → extends)
 Has-A → Composition  (Car has-a Engine → field)
 Prefer composition over inheritance when in doubt

PROCEDURAL vs OOP
──────────────────────────────────────────────────────────────
 Procedural → Functions + separate data → hard to scale
 OOP        → Objects bundle data + behavior → scalable, secure

WHY OOP?
──────────────────────────────────────────────────────────────
 ✅ Modular code — independent, swappable parts
 ✅ Reusable code — inheritance and composition
 ✅ Secure code — encapsulation hides data
 ✅ Flexible code — polymorphism adapts to context
 ✅ Maintainable — changes stay localized
 ✅ Models real world naturally
```

---

*End of Notes — Ready to dive into OOP in Java!*
