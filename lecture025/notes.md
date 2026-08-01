# Java Abstraction & Polymorphism — Comprehensive Notes

---

## Table of Contents
1. [Abstraction — Overview](#1-abstraction--overview)
2. [Low-Level Abstraction — Classes](#2-low-level-abstraction--classes)
3. [High-Level Abstraction — Abstract Classes](#3-high-level-abstraction--abstract-classes)
4. [High-Level Abstraction — Interfaces](#4-high-level-abstraction--interfaces)
5. [Abstract Class vs Interface](#5-abstract-class-vs-interface)
6. [Abstraction vs Encapsulation](#6-abstraction-vs-encapsulation)
7. [Polymorphism — Overview](#7-polymorphism--overview)
8. [Static Polymorphism — Method Overloading](#8-static-polymorphism--method-overloading)
9. [Dynamic Polymorphism — Method Overriding](#9-dynamic-polymorphism--method-overriding)
10. [Effect of static, final & private on Polymorphism](#10-effect-of-static-final--private-on-polymorphism)
11. [Key Rules & Common Mistakes](#11-key-rules--common-mistakes)
12. [Quick Revision Cheatsheet](#12-quick-revision-cheatsheet)

---

## 1. Abstraction — Overview

**Abstraction** is the OOP principle of exposing only the
**essential features** of an object to the outside world while
**hiding the internal implementation complexity**.

### The Core Idea

> Show **WHAT** an object does.
> Hide **HOW** it does it.

### Real-World Analogy

When you drive a car:
- You press the **accelerator** → car moves faster
- You turn the **steering wheel** → car changes direction
- You press the **brake** → car slows down

You do NOT need to know how fuel injection works, how the ECU
manages combustion, or how the ABS modulates brake pressure.
The complexity is **abstracted away** — you only interact with
a clean, simple interface.

### Levels of Abstraction in Java

```
LOW-LEVEL ABSTRACTION          HIGH-LEVEL ABSTRACTION
──────────────────────         ──────────────────────────────
Achieved through:              Achieved through:
  Regular Classes                Abstract Classes
                                 Interfaces

User interacts with            User interacts with a
simple methods without         contract (what must exist)
seeing internal logic          without seeing any implementation
```

---

## 2. Low-Level Abstraction — Classes

Even a regular class provides a basic level of abstraction by hiding
internal method implementations from the caller.

The user of a class only needs to know **what methods exist and
what they do** — not the internal logic that makes them work.

### Example

```java
class Car {
    private int fuelLevel;
    private int engineRPM;
    private boolean engineRunning;

    // User calls this simple method
    public void accelerate() {
        if (engineRunning && fuelLevel > 0) {
            engineRPM += 500;
            fuelLevel--;
            System.out.println("Car accelerating. RPM: " + engineRPM);
        }
    }

    public void startEngine() {
        engineRunning = true;
        System.out.println("Engine started");
    }

    public void brake() {
        engineRPM = Math.max(0, engineRPM - 300);
        System.out.println("Braking. RPM: " + engineRPM);
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.startEngine();  // User has NO idea what happens internally
        car.accelerate();   // Simple interface — complexity is hidden
        car.brake();
    }
}
```

The user of `Car` does not care about `fuelLevel`, `engineRPM`,
or `engineRunning` — those are internal concerns. This is
low-level abstraction.

---

## 3. High-Level Abstraction — Abstract Classes

An **abstract class** is a class that:
- Cannot be instantiated directly (no objects can be created from it)
- Can contain **abstract methods** — methods with no body/implementation
- Can also contain **concrete methods** — fully implemented methods
- Forces all subclasses to provide implementations for abstract methods

### Syntax

```java
abstract class ClassName {
    // Abstract method — no body, no implementation
    abstract returnType methodName(parameters);

    // Concrete method — has a body
    returnType concreteMethod() {
        // implementation here
    }
}
```

### Why Abstract Classes?

Abstract classes define a **contract** — they say:
*"Every class that extends me MUST implement these methods."*

This enforces consistency across all subclasses while still allowing
shared concrete behavior to live in the parent.

### Example — Shape System

```java
// Abstract class — cannot be instantiated
abstract class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    // Abstract methods — no body — subclasses MUST implement
    abstract double area();
    abstract double perimeter();

    // Concrete method — shared by all shapes
    void displayColor() {
        System.out.println("Color: " + color);
    }
}

class Circle extends Shape {
    double radius;

    Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    // MUST implement area() — forced by abstract class
    double area() {
        return 3.14159 * radius * radius;
    }

    // MUST implement perimeter()
    double perimeter() {
        return 2 * 3.14159 * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width  = width;
    }

    double area() {
        return length * width;
    }

    double perimeter() {
        return 2 * (length + width);
    }
}

public class Main {
    public static void main(String[] args) {
        // Shape s = new Shape("Red"); // ❌ Cannot instantiate abstract class

        Shape s1 = new Circle("Red", 5);
        Shape s2 = new Rectangle("Blue", 4, 6);

        s1.displayColor();              // Color: Red   (concrete method)
        System.out.println(s1.area()); // 78.53975
        System.out.println(s2.area()); // 24.0
    }
}
```

### Key Rules for Abstract Classes

| Rule | Detail |
|------|--------|
| Cannot be instantiated | `new AbstractClass()` → compile error |
| Can have abstract methods | No body, just signature + semicolon |
| Can have concrete methods | Full implementation allowed |
| Can have constructors | Called via `super()` from child |
| Can have fields | Instance and static variables allowed |
| Subclass must implement all abstract methods | Or the subclass must also be abstract |
| A class with any abstract method must be abstract | Cannot have abstract method in non-abstract class |

---

## 4. High-Level Abstraction — Interfaces

An **interface** is the **purest form of abstraction** in Java.
It defines only **what** a class must do — with zero implementation
details (prior to Java 8).

### Key Characteristics

- All methods in an interface are implicitly `public` and `abstract`
  (before Java 8)
- All fields are implicitly `public`, `static`, and `final` (constants)
- A class **implements** an interface (not extends)
- A class can implement **multiple interfaces** (solving multiple
  inheritance for behavior)
- An interface cannot be instantiated directly

### Syntax

```java
interface InterfaceName {
    // All methods are public abstract by default
    returnType methodName(parameters);

    // All fields are public static final by default
    int CONSTANT = 100;
}
```

### Example — Vehicle Interface

```java
interface Vehicle {
    // public static final by default
    int MAX_SPEED = 200;

    // public abstract by default
    void start();
    void stop();
    void accelerate(int speed);
}

class Car implements Vehicle {
    public void start() {
        System.out.println("Car started with key ignition");
    }

    public void stop() {
        System.out.println("Car stopped with brakes");
    }

    public void accelerate(int speed) {
        System.out.println("Car accelerating to " + speed + " km/h");
    }
}

class Bicycle implements Vehicle {
    public void start() {
        System.out.println("Bicycle started by pedaling");
    }

    public void stop() {
        System.out.println("Bicycle stopped with hand brakes");
    }

    public void accelerate(int speed) {
        System.out.println("Bicycle speeding up to " + speed + " km/h");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Car();
        Vehicle v2 = new Bicycle();

        v1.start();           // Car started with key ignition
        v2.start();           // Bicycle started by pedaling
        v1.accelerate(100);   // Car accelerating to 100 km/h
    }
}
```

### Implementing Multiple Interfaces

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// A class can implement multiple interfaces
class Duck implements Flyable, Swimmable {
    public void fly() {
        System.out.println("Duck is flying");
    }

    public void swim() {
        System.out.println("Duck is swimming");
    }
}
```

### Default and Static Methods in Interfaces (Java 8+)

From Java 8 onwards, interfaces can have:
- **Default methods** — concrete methods with a default implementation
- **Static methods** — utility methods that belong to the interface

```java
interface Vehicle {
    void start(); // abstract

    // Default method — provides a default implementation
    default void fuelCheck() {
        System.out.println("Checking fuel level...");
    }

    // Static method — called via interface name
    static void description() {
        System.out.println("A vehicle is a means of transport");
    }
}

class Car implements Vehicle {
    public void start() {
        System.out.println("Car started");
    }
    // fuelCheck() is inherited with default behavior
    // Can be overridden if needed
}

public class Main {
    public static void main(String[] args) {
        Car c = new Car();
        c.start();       // Car started
        c.fuelCheck();   // Checking fuel level... (default)

        Vehicle.description(); // Called on interface directly (static)
    }
}
```

---

## 5. Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Instantiation | Cannot be instantiated | Cannot be instantiated |
| Methods | Abstract + concrete | Abstract by default; default/static from Java 8 |
| Fields | Any type of fields | `public static final` only (constants) |
| Constructor | Can have constructors | Cannot have constructors |
| Inheritance | `extends` (single only) | `implements` (multiple allowed) |
| Access modifiers | Any modifier on methods | Methods are `public` by default |
| Use when | Sharing common code + enforcing contract | Defining pure capability contract |

### When to Use Which?

```
Use ABSTRACT CLASS when:
  ✅ Classes share common code (concrete methods)
  ✅ You want to provide partial implementation
  ✅ Classes have a strong "is-a" relationship
  ✅ You need constructors or non-static/non-final fields
  Example: Animal (all animals eat and sleep → concrete methods)

Use INTERFACE when:
  ✅ You want to define pure capability/behavior contracts
  ✅ Multiple unrelated classes need the same capability
  ✅ You need multiple inheritance of behavior
  ✅ You want complete abstraction of implementation
  Example: Flyable (birds, planes, drones — unrelated, but all fly)
```

---

## 6. Abstraction vs Encapsulation

These two concepts are often confused because they both involve
"hiding" something. They are however fundamentally different in
**what** they hide and **why**.

### Side-by-Side Comparison

| Aspect | Encapsulation | Abstraction |
|--------|---------------|-------------|
| **What is hidden** | Internal **data** (fields) | Internal **implementation** (how logic works) |
| **Focus** | **Data security** and access control | **Complexity hiding** — simplifying the interface |
| **Achieved through** | Access modifiers + getters/setters | Abstract classes and interfaces |
| **Question it answers** | *Who can access this data?* | *What does this object do?* |
| **Goal** | Protect data from unauthorized modification | Present a clean, simple interface to the user |

### Illustration

```java
// ENCAPSULATION — protecting the 'balance' data field
class BankAccount {
    private double balance; // hidden DATA — no direct outside access

    public void deposit(double amount) {
        if (amount > 0) balance += amount; // controlled access with rules
    }
    public double getBalance() { return balance; }
}

// ABSTRACTION — hiding HOW payment is processed
interface PaymentProcessor {
    void processPayment(double amount); // WHAT: payment must be processed
                                        // HOW: hidden inside implementation
}

class CreditCardProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        // Complex: validate card, call bank API, handle response...
        // ALL hidden from the user
        System.out.println("Processing ₹" + amount + " via Credit Card");
    }
}
```

### Simple Memory Aid

```
Encapsulation → "PROTECT my data"   → private fields + getters/setters
Abstraction   → "HIDE my complexity" → abstract classes + interfaces
```

---

## 7. Polymorphism — Overview

**Polymorphism** comes from Greek: *poly* (many) + *morphe* (forms).

In Java, it means a single method name or object reference can
**behave differently** depending on the context or the actual
object type at runtime.

### Two Types

```
POLYMORPHISM
     │
     ├── Static (Compile-Time)
     │       └── Method Overloading
     │           (resolved at COMPILE time by the compiler)
     │
     └── Dynamic (Runtime)
             └── Method Overriding
                 (resolved at RUNTIME by the JVM)
```

---

## 8. Static Polymorphism — Method Overloading

**Method Overloading** means defining **multiple methods with the same
name** in the same class, but with **different parameter lists**.

The compiler decides which method to call at **compile time** based
on the number, type, and order of arguments — hence "static" or
"compile-time" polymorphism.

### Ways to Overload

- Different **number** of parameters
- Different **types** of parameters
- Different **order** of parameters

```java
class Calculator {
    // Different number of parameters
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    // Different types of parameters
    double add(double a, double b) {
        return a + b;
    }

    // Different order of parameters
    String add(String s, int n) {
        return s + n;
    }

    String add(int n, String s) {
        return n + s;
    }
}

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println(calc.add(3, 4));         // 7       → int, int
        System.out.println(calc.add(1, 2, 3));      // 6       → int, int, int
        System.out.println(calc.add(3.5, 2.1));     // 5.6     → double, double
        System.out.println(calc.add("Score:", 10)); // Score:10 → String, int
        System.out.println(calc.add(10, "Points")); // 10Points → int, String
    }
}
```

### What Does NOT Count as Overloading

```java
class Demo {
    // ❌ NOT overloading — only return type differs
    int getValue()    { return 1; }
    double getValue() { return 1.0; } // Compile error — duplicate method

    // ❌ NOT overloading — same parameters, different parameter names only
    int add(int a, int b) { return a + b; }
    int add(int x, int y) { return x + y; } // Compile error — duplicate
}
```

> **Key Rule:** Return type alone cannot differentiate overloaded methods.
> The parameter list MUST differ.

### Automatic Type Promotion in Overloading

When no exact match exists, Java automatically **promotes** (widens)
the argument type to find the best matching method:

```
byte → short → int → long → float → double
```

```java
class Demo {
    void show(int x)    { System.out.println("int: " + x); }
    void show(double x) { System.out.println("double: " + x); }
}

public class Main {
    public static void main(String[] args) {
        Demo d = new Demo();
        d.show(5);     // int: 5    → exact match
        d.show(5.5);   // double: 5.5 → exact match
        d.show('A');   // int: 65   → char promoted to int
    }
}
```

---

## 9. Dynamic Polymorphism — Method Overriding

**Method Overriding** occurs when a **subclass provides its own
specific implementation** of a method that is already defined in
its parent class.

The JVM decides which version of the method to call at **runtime**
based on the actual object type — hence "dynamic" or "runtime"
polymorphism.

### Rules for Method Overriding

| Rule | Detail |
|------|--------|
| Same method name | Must match exactly |
| Same parameter list | Must match exactly (else it becomes overloading) |
| Same or covariant return type | Child can return a subtype of parent's return type |
| Access modifier | Child's modifier must be same or **less** restrictive |
| Cannot override `static` methods | Static methods belong to class, not object |
| Cannot override `final` methods | `final` locks the method |
| Cannot override `private` methods | Private methods are not inherited |
| `@Override` annotation | Recommended — compiler verifies you are overriding correctly |

### Example — Method Overriding

```java
class Animal {
    void makeSound() {
        System.out.println("Animal makes a generic sound");
    }

    void sleep() {
        System.out.println("Animal sleeps");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {  // Overrides Animal's makeSound()
        System.out.println("Dog barks: Woof!");
    }
    // sleep() is NOT overridden — inherited as-is from Animal
}

class Cat extends Animal {
    @Override
    void makeSound() {  // Overrides Animal's makeSound()
        System.out.println("Cat meows: Meow!");
    }
}

class Cow extends Animal {
    @Override
    void makeSound() {
        System.out.println("Cow moos: Moo!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a1 = new Dog();
        Animal a2 = new Cat();
        Animal a3 = new Cow();

        a1.makeSound(); // Dog barks: Woof!   → runtime decides
        a2.makeSound(); // Cat meows: Meow!   → runtime decides
        a3.makeSound(); // Cow moos: Moo!     → runtime decides

        a1.sleep();     // Animal sleeps      → inherited, not overridden
    }
}
```

### How Runtime Polymorphism Works

```
Compile time:
  Animal a1 = new Dog();
  a1.makeSound();
  → Compiler sees: a1 is of type Animal
  → Animal has makeSound() → OK, compiles

Runtime:
  → JVM checks actual object: it's a Dog
  → Dog has its own makeSound()
  → Calls Dog's makeSound() → "Dog barks: Woof!"
```

This is called **Dynamic Method Dispatch** — the JVM dispatches
the call to the correct method at runtime based on the actual object.

### The Power of Runtime Polymorphism

```java
public class Main {
    static void performSound(Animal a) {
        a.makeSound(); // One method call — many behaviors
    }

    public static void main(String[] args) {
        performSound(new Dog()); // Dog barks: Woof!
        performSound(new Cat()); // Cat meows: Meow!
        performSound(new Cow()); // Cow moos: Moo!

        // Process an array of different Animal types uniformly
        Animal[] animals = { new Dog(), new Cat(), new Cow(), new Dog() };
        for (Animal a : animals) {
            a.makeSound(); // Each calls its own version
        }
    }
}
```

---

## 10. Effect of `static`, `final` & `private` on Polymorphism

These three keywords each restrict or alter how methods participate
in polymorphism.

### 10.1 `static` Methods — Method Hiding, NOT Overriding

Static methods belong to the **class**, not the object.
When a child class defines a static method with the same signature
as a parent's static method, it is called **method hiding** —
not overriding. The version called depends on the **reference type**
(compile time), not the actual object (runtime).

```java
class Parent {
    static void show() {
        System.out.println("Parent static show()");
    }
}

class Child extends Parent {
    static void show() {  // This is METHOD HIDING, not overriding
        System.out.println("Child static show()");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        p.show(); // "Parent static show()" ← reference type decides (compile-time)

        Child c = new Child();
        c.show(); // "Child static show()" ← reference type is Child
    }
}
```

> With `static` methods: reference type decides (compile-time).
> With instance methods (overriding): object type decides (runtime).

---

### 10.2 `final` Methods — Cannot Be Overridden

A method marked `final` is **locked**. No subclass can override it.
This is used when you want to guarantee that a method's behavior
never changes throughout the inheritance hierarchy.

```java
class Parent {
    final void display() {
        System.out.println("Parent final display()");
    }
}

class Child extends Parent {
    @Override
    void display() { // ❌ Compile error — cannot override final method
        System.out.println("Child display()");
    }
}
```

**Why use `final` on a method?**
- Security — prevent subclasses from altering critical behavior
- Performance — JVM can inline `final` method calls (slight optimization)
- Design intent — signal that this behavior is fixed and intentional

---

### 10.3 `private` Methods — Not Inherited, Cannot Be Overridden

Private methods are **not visible** to child classes at all.
They are not inherited, so they cannot be overridden.
If a child class defines a method with the same name as a parent's
private method, it is a completely **new, unrelated method** —
not an override.

```java
class Parent {
    private void secret() {
        System.out.println("Parent private secret()");
    }

    void callSecret() {
        secret(); // Can call private method within same class
    }
}

class Child extends Parent {
    // This is NOT an override — it's a brand new method
    // The @Override annotation here would cause a compile error
    void secret() {
        System.out.println("Child secret() — completely new method");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        p.callSecret();
        // Output: "Parent private secret()"
        // Parent's private method is called — not Child's version
    }
}
```

### Summary Table

| Keyword | Can Be Overridden? | Behavior |
|---------|-------------------|----------|
| `static` | ❌ No | Method hiding — resolved at compile time by reference type |
| `final` | ❌ No | Compile error if subclass tries to override |
| `private` | ❌ No | Not inherited; child's same-name method is a new independent method |
| (none / `public` / `protected`) | ✅ Yes | True overriding — resolved at runtime by object type |

---

## 11. Key Rules & Common Mistakes

### Rules Summary

| Concept | Key Rule |
|---------|----------|
| Abstract class | Cannot instantiate; subclass must implement all abstract methods |
| Interface | All methods public abstract by default; fields are public static final |
| Overloading | Same name, different parameters; resolved at compile time |
| Overriding | Same name + same parameters; resolved at runtime |
| `@Override` | Always use it — compiler catches mistakes |
| `static` in polymorphism | Method hiding, not overriding; reference type decides |
| `final` method | Cannot be overridden in any subclass |
| `private` method | Not inherited; cannot be overridden |
| Abstract + interface | A class can extend one abstract class AND implement multiple interfaces |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Trying to instantiate an abstract class
abstract class Shape { abstract double area(); }
Shape s = new Shape(); // ❌ Compile error

// ✅ CORRECT
Shape s = new Circle(5); // Use a concrete subclass

// ❌ MISTAKE 2 — Not implementing all abstract methods
abstract class Shape { abstract double area(); abstract double perimeter(); }
class Circle extends Shape {
    double area() { return 3.14 * 5 * 5; }
    // ❌ perimeter() not implemented → Circle must also be abstract
}

// ❌ MISTAKE 3 — Thinking different return type = overloading
int getValue()    { return 1; }
double getValue() { return 1.0; } // ❌ Compile error — not valid overloading

// ❌ MISTAKE 4 — Thinking same params = overriding when it's static
class Parent { static void show() { } }
class Child extends Parent { static void show() { } }
// This is METHOD HIDING, not overriding

// ❌ MISTAKE 5 — Reducing access modifier when overriding
class Parent { public void display() { } }
class Child extends Parent {
    private void display() { } // ❌ Cannot reduce from public to private
}

// ✅ CORRECT — Can increase or keep same access
class Child extends Parent {
    public void display() { }  // ✅ Same access
}
```

---

## 12. Quick Revision Cheatsheet

```
ABSTRACTION
──────────────────────────────────────────────────────────────
 Goal    → Hide HOW; expose WHAT
 Level 1 → Regular classes (basic implementation hiding)
 Level 2 → Abstract classes (partial contract enforcement)
 Level 3 → Interfaces (pure capability contract)

 Abstract Class:
   → Has abstract methods (no body) + concrete methods
   → Cannot be instantiated
   → Subclass must implement ALL abstract methods
   → Use: extends (single only)
   → Use when: sharing common code + enforcing contract

 Interface:
   → All methods public abstract by default (pre-Java 8)
   → Fields are public static final (constants)
   → Cannot be instantiated
   → Use: implements (multiple allowed)
   → Use when: pure behavior contract, multiple inheritance

ABSTRACTION vs ENCAPSULATION
──────────────────────────────────────────────────────────────
 Encapsulation → Hides DATA       → private fields + getters/setters
 Abstraction   → Hides COMPLEXITY → abstract classes + interfaces

POLYMORPHISM
──────────────────────────────────────────────────────────────
 Static (Compile-time) → Method OVERLOADING
   → Same name, different parameters
   → Resolved by COMPILER based on argument types
   → Return type alone cannot differentiate

 Dynamic (Runtime) → Method OVERRIDING
   → Same name, same parameters, in child class
   → Resolved by JVM at RUNTIME based on actual object
   → @Override annotation recommended
   → Access modifier must be same or less restrictive

KEYWORDS AND POLYMORPHISM
──────────────────────────────────────────────────────────────
 static method  → Method HIDING (not overriding)
                  Reference type decides at compile time
 final method   → CANNOT be overridden (compile error)
 private method → NOT inherited; cannot be overridden
                  Child's same-name method is brand new

OVERLOADING vs OVERRIDING
──────────────────────────────────────────────────────────────
              Overloading          Overriding
 Location:    Same class           Parent → Child class
 Parameters:  Must differ          Must be identical
 Return type: Can differ           Must be same / covariant
 Resolution:  Compile time         Runtime
 Keyword:     None needed          @Override recommended
 static:      Can overload static  Cannot override static
```

---

*End of Notes*
