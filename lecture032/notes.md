# Java Interfaces: Comprehensive Notes
## Contract | Rules | Multiple Inheritance | Default/Static/Private Methods | Functional & Marker Interfaces

---

## Table of Contents
1. [What is an Interface?](#1-what-is-an-interface)
2. [Why Interfaces Exist](#2-why-interfaces-exist)
3. [Basic Syntax and Implementation](#3-basic-syntax-and-implementation)
4. [Rules for Interface Methods](#4-rules-for-interface-methods)
5. [Interface Variables (Constants)](#5-interface-variables-constants)
6. [Multiple Inheritance via Interfaces](#6-multiple-inheritance-via-interfaces)
7. [Interface Inheritance (interface extends interface)](#7-interface-inheritance-interface-extends-interface)
8. [Default Methods (Java 8)](#8-default-methods-java-8)
9. [Static and Private Methods](#9-static-and-private-methods)
10. [Method Resolution Rules](#10-method-resolution-rules)
11. [Functional Interfaces](#11-functional-interfaces)
12. [Marker Interfaces](#12-marker-interfaces)
13. [Interface vs Abstract Class](#13-interface-vs-abstract-class)
14. [Internal Working](#14-internal-working)
15. [Key Rules & Common Mistakes](#15-key-rules--common-mistakes)
16. [Quick Revision Cheatsheet](#16-quick-revision-cheatsheet)

---

## 1. What is an Interface?

An **interface** is a **contract**. It declares **WHAT** an object can do,
without saying **HOW** it does it.

- A **class** is a blueprint for **objects** (state + behavior).
- An **interface** is a blueprint for **behavior** (capabilities).

```java
interface Flyable {
    void fly();   // WHAT: anything Flyable can fly. HOW: not our concern here.
}
```

Any class that **implements** `Flyable` promises to provide a `fly()` method.
The compiler enforces that promise.

### Real-World Analogy
A **USB port** is an interface. It defines the shape and protocol.
A mouse, keyboard, pen drive and phone charger all plug into it,
and each works differently inside. The laptop only cares that the device
"speaks USB".

---

## 2. Why Interfaces Exist

### Reason 1: Abstraction
Interfaces are a primary tool of abstraction: callers depend on the
contract, not on a particular implementation.

### Reason 2: Loose Coupling

```java
// Tightly coupled: the caller knows the concrete class
MySQLDatabase db = new MySQLDatabase();

// Loosely coupled: the caller knows only the contract
Database db = new MySQLDatabase();
// Tomorrow: new PostgresDatabase(). Callers do not change.
```

### Reason 3: Multiple Inheritance of Behavior
A class can extend only ONE class, but can implement MANY interfaces.

### Reason 4: Polymorphism Across Unrelated Classes
A `Bird`, an `Airplane` and a `Drone` share no parent class,
but all can be `Flyable`.

---

## 3. Basic Syntax and Implementation

```java
interface Vehicle {
    void start();
    void stop();
}

class Car implements Vehicle {
    @Override
    public void start() { System.out.println("Car started with key"); }

    @Override
    public void stop()  { System.out.println("Car stopped"); }
}

class Bike implements Vehicle {
    @Override
    public void start() { System.out.println("Bike kick-started"); }

    @Override
    public void stop()  { System.out.println("Bike stopped"); }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Car();   // interface reference, concrete object
        Vehicle v2 = new Bike();

        v1.start();   // Car started with key
        v2.start();   // Bike kick-started

        // Vehicle v = new Vehicle();  // COMPILE ERROR: cannot instantiate an interface
    }
}
```

### Key Points
- Use `implements` (class to interface), not `extends`.
- A concrete class MUST implement **all** abstract methods of the interface.
- An **abstract class** may implement only some. The rest stay abstract.
- You cannot create an object of an interface, but you can create a
  **reference** of interface type.
- Through an interface reference you can call **only** the interface's
  methods (the compiler checks the reference type).

---

## 4. Rules for Interface Methods

### Rule 1: Methods are implicitly `public abstract`

```java
interface Demo {
    void show();
    // The compiler reads this as: public abstract void show();
}
```

### Rule 2: The implementing method MUST be `public`

Method overriding cannot **reduce** visibility. The interface method is
public, so the implementation must be public.

```java
class Impl implements Demo {
    void show() { }          // COMPILE ERROR: attempting to assign weaker access privileges
    public void show() { }   // correct
}
```

### Rule 3: Illegal modifiers on abstract interface methods

| Modifier | Allowed on abstract interface method? | Why |
|----------|----------------------------------------|-----|
| `private` | No | Must be visible to implementers |
| `protected` | No | Interface members are public |
| `final` | No | Must be overridden |
| `static` | No (abstract + static) | Static cannot be overridden |

### Rule 4: Interfaces have no constructors
Interfaces hold no instance state, so there is nothing to construct.

### Rule 5: An interface cannot be `final`
It exists to be implemented, so `final interface X` is illegal.

---

## 5. Interface Variables (Constants)

Every field in an interface is implicitly **`public static final`**.

```java
interface Config {
    int MAX_USERS = 100;
    // The compiler reads this as: public static final int MAX_USERS = 100;
}

System.out.println(Config.MAX_USERS);  // accessed via the interface name
// Config.MAX_USERS = 200;             // COMPILE ERROR: final
```

### Rules
- Must be **initialized at declaration** (they are `final`).
- Cannot be `private` or `protected`.
- Shared by all implementers (`static`).
- Implementing classes inherit them and can use them directly:

```java
class App implements Config {
    void show() { System.out.println(MAX_USERS); }  // direct access
}
```

### Why only constants?
Interfaces have **no instance state**. Allowing mutable shared fields would
create hidden global variables.

> Anti-pattern: a "constant interface" (an interface used only to hold
> constants and implemented just to avoid typing the name). Prefer a final
> class or an enum for constants.

---

## 6. Multiple Inheritance via Interfaces

### Why classes cannot do it: the Diamond Problem

```
        A (show())
       / \
      B   C   (both override show())
       \ /
        D extends B, C   ->  which show()? AMBIGUOUS
```

Java forbids `class D extends B, C` to avoid this ambiguity (and the
state-merging problems of multiple parents).

### Why interfaces can do it
Traditional interface methods have **no body**, so there is no conflict:

```java
interface Flyable  { void fly(); }
interface Swimmable { void swim(); }

class Duck implements Flyable, Swimmable {
    @Override public void fly()  { System.out.println("Duck flies"); }
    @Override public void swim() { System.out.println("Duck swims"); }
}
```

If two interfaces declare the **same abstract method**, one implementation
satisfies both:

```java
interface A { void show(); }
interface B { void show(); }

class C implements A, B {
    @Override public void show() { System.out.println("One method serves both"); }
}
```

A single reference can view the object through different "lenses":

```java
Duck d = new Duck();
Flyable f   = d;   // sees only fly()
Swimmable s = d;   // sees only swim()
```

---

## 7. Interface Inheritance (interface extends interface)

An interface extends another interface using **`extends`**, and can extend
**several**:

```java
interface Readable  { String read(); }
interface Writable  { void write(String data); }

interface ReadWritable extends Readable, Writable {
    void flush();
}

class FileHandle implements ReadWritable {
    public String read()            { return "data"; }
    public void write(String data)  { System.out.println("Writing " + data); }
    public void flush()             { System.out.println("Flushed"); }
}
```

`FileHandle` must implement **all three** methods (two inherited plus one
own).

### Keyword summary

| Relationship | Keyword |
|--------------|---------|
| class to class | `extends` |
| class to interface | `implements` |
| interface to interface | `extends` (multiple allowed) |
| interface to class | not possible |

---

## 8. Default Methods (Java 8)

### The Problem They Solved
Before Java 8, adding a new method to a published interface **broke every
implementing class** (they would no longer compile). Java needed to add
methods like `forEach`, `stream`, `sort` to `Collection`/`List`, which would
have broken millions of existing classes.

### The Solution: `default` methods
An interface method **with a body**, marked `default`:

```java
interface Vehicle {
    void start();

    default void honk() {                       // has a body
        System.out.println("Beep beep!");
    }
}

class Car implements Vehicle {
    public void start() { System.out.println("Car started"); }
    // honk() NOT implemented: inherited for free
}

class Truck implements Vehicle {
    public void start() { System.out.println("Truck started"); }

    @Override
    public void honk() { System.out.println("HOOONK!"); }   // may override
}

new Car().honk();     // Beep beep!
new Truck().honk();   // HOOONK!
```

### Rules
- Implicitly `public` (cannot be `private`/`protected` when marked `default`).
- Implementers may **inherit**, **override**, or (if they are an interface)
  **re-declare as abstract** the default method.
- A default method **cannot** override `equals()`, `hashCode()` or
  `toString()` of `Object`. This is a compile error. (A class always wins
  over an interface, so it would never be used.)
- A default method may call the interface's abstract methods:

```java
interface Greeter {
    String name();
    default String greet() { return "Hello, " + name(); }
}
```

---

## 9. Static and Private Methods

### 9.1 Static Methods (Java 8)
Utility methods that belong to the interface itself.

```java
interface MathUtil {
    static int square(int n) { return n * n; }
}

System.out.println(MathUtil.square(5));   // 25, called via the interface name
```

**Rules**
- Called ONLY as `InterfaceName.method()`.
- **Not inherited** by implementing classes or sub-interfaces:

```java
class Impl implements MathUtil { }
// Impl.square(5);          // COMPILE ERROR
// new Impl().square(5);    // COMPILE ERROR
```
- Cannot be overridden.

### 9.2 Private Methods (Java 9)
Share code between default/static methods **without exposing it**.

```java
interface Logger {
    default void info(String msg)  { log("INFO",  msg); }
    default void error(String msg) { log("ERROR", msg); }

    private void log(String level, String msg) {            // hidden helper
        System.out.println("[" + level + "] " + msg);
    }

    private static String timestamp() { return "now"; }     // private static also allowed
}
```

**Rules**
- `private` instance methods are callable only from default/other private
  instance methods.
- `private static` methods are callable from static and default methods.
- Must have a body. Not visible to implementers.

### Summary: what an interface can contain

| Member | Since | Modifiers (implicit) |
|--------|-------|----------------------|
| Constants | Java 1 | public static final |
| Abstract methods | Java 1 | public abstract |
| Default methods | Java 8 | public |
| Static methods | Java 8 | public (or private since 9) |
| Private methods | Java 9 | private |
| Nested types (class/interface/enum) | Java 1 | public static |

---

## 10. Method Resolution Rules

When a class inherits the same method signature from several places,
Java applies these rules **in order**:

### Rule 1: Class always wins
A method from the superclass (or the class itself) beats any interface
default.

```java
class Base { public void greet() { System.out.println("Base"); } }

interface Greeter { default void greet() { System.out.println("Greeter"); } }

class Child extends Base implements Greeter { }

new Child().greet();   // Base   (class wins)
```

### Rule 2: The most specific interface wins
If one interface extends the other and overrides the default, the
sub-interface wins.

```java
interface A { default void hello() { System.out.println("A"); } }
interface B extends A { default void hello() { System.out.println("B"); } }

class C implements A, B { }
new C().hello();       // B
```

### Rule 3: Otherwise, the class MUST override (conflict)
Two unrelated interfaces with the same default method create a compile
error until you resolve it. Use `InterfaceName.super.method()` to pick or
combine:

```java
interface Walker { default void move() { System.out.println("Walking"); } }
interface Swimmer { default void move() { System.out.println("Swimming"); } }

class Frog implements Walker, Swimmer {
    @Override
    public void move() {
        Walker.super.move();     // explicitly call Walker's version
        Swimmer.super.move();    // and Swimmer's version
    }
}
```

### Decision Flow

```
Same method inherited from multiple sources?
  |
  |- Defined in a class (own or superclass)?  -> that CLASS method wins
  |- One interface is a sub-interface of other? -> MORE SPECIFIC one wins
  `- Otherwise                                  -> COMPILE ERROR; override
                                                   and use X.super.method()
```

### Is this the Diamond Problem again?
Java avoids it by **forcing the programmer to resolve the conflict**.
Interfaces also carry no state, so only behavior needs resolving.

---

## 11. Functional Interfaces

A **functional interface** has **exactly one abstract method** (SAM: Single
Abstract Method). It is the target type for **lambda expressions** and
method references.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);          // the single abstract method
}

Calculator add      = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(3, 4));       // 7
System.out.println(multiply.calculate(3, 4));  // 12
```

### Rules
- `@FunctionalInterface` is **optional but recommended**. The compiler
  errors if the interface has zero or more than one abstract method.
- It MAY have any number of **default**, **static**, and **private** methods.
- Methods that match `Object`'s public methods (e.g. `equals`) do **not**
  count as abstract. This is why `Comparator` (which declares `compare` and
  `equals`) is still functional.

```java
@FunctionalInterface
interface Validator<T> {
    boolean validate(T value);                                   // the one abstract method

    default Validator<T> and(Validator<T> other) {               // allowed
        return v -> this.validate(v) && other.validate(v);
    }
    static <T> Validator<T> not(Validator<T> v) {                // allowed
        return x -> !v.validate(x);
    }
}
```

### Common built-in functional interfaces (java.util.function and java.lang)

| Interface | Abstract method | Meaning |
|-----------|-----------------|---------|
| `Runnable` | `void run()` | action, no input, no output |
| `Comparator<T>` | `int compare(T a, T b)` | ordering |
| `Predicate<T>` | `boolean test(T t)` | condition |
| `Function<T,R>` | `R apply(T t)` | transform |
| `Consumer<T>` | `void accept(T t)` | use a value |
| `Supplier<T>` | `T get()` | produce a value |

### Lambda vs Anonymous Class

```java
// Anonymous class (verbose)
Runnable r1 = new Runnable() {
    @Override public void run() { System.out.println("Run"); }
};

// Lambda (concise)
Runnable r2 = () -> System.out.println("Run");
```

---

## 12. Marker Interfaces

A **marker interface** has **no methods**. It only **tags** a class with a
capability that the JVM or a library checks at runtime.

| Marker | Meaning |
|--------|---------|
| `Serializable` | object may be converted to a byte stream |
| `Cloneable` | `Object.clone()` is permitted |
| `RandomAccess` | list supports fast index access (hint for algorithms) |

```java
interface Cloneable { }      // the entire definition
```

### How a marker is used

```java
// JDK-style check: Object.clone() throws CloneNotSupportedException
// if the class does not implement Cloneable.

// Your own marker:
interface Auditable { }

class Payment implements Auditable { }
class Note { }

static void save(Object o) {
    if (o instanceof Auditable) {
        System.out.println("Writing audit log for " + o.getClass().getSimpleName());
    }
}
```

### Marker Interface vs Annotation
Modern Java often uses annotations (`@Deprecated`, `@Entity`) for tagging.
Markers still have one advantage: they define a **type**, so you can use
them as a parameter type (`void save(Auditable a)`) and the compiler checks
it.

---

## 13. Interface vs Abstract Class

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| Relationship | **Can-Do** (capability/role) | **Is-A** (family/hierarchy) |
| Keyword | `implements` | `extends` |
| Multiple inheritance | A class can implement many | A class can extend only one |
| Constructors | No | Yes |
| Instance variables (state) | No (constants only) | Yes |
| Method types | abstract, default, static, private | abstract + concrete (any modifier) |
| Access modifiers on methods | public (or private helpers) | any |
| Instantiation | No | No |
| Unrelated classes can share it | Yes | No (must be in the same hierarchy) |

### How to choose

```
Do the classes form a natural family with shared STATE and code?
        YES -> abstract class        (Vehicle -> Car, Truck)
        NO  -> Do unrelated classes need the same CAPABILITY?
                YES -> interface     (Flyable: Bird, Plane, Drone)
```

They are often used **together**:

```java
abstract class Animal { protected String name; /* shared state + constructor */ }
interface Swimmable { void swim(); }
class Dog extends Animal implements Swimmable { public void swim() { } }
```

---

## 14. Internal Working

### 14.1 What javac produces
An interface compiles to its own `.class` file (flagged as an interface).
It is loaded by the JVM like any other class.

### 14.2 Constants
Interface fields are `static final`, so they are initialized once when the
interface is initialized. Compile-time constants (like `int MAX = 100`)
are inlined into the code that uses them.

### 14.3 Method calls through an interface reference

```java
Vehicle v = new Car();
v.start();
```

- **Compile time:** the compiler checks that `Vehicle` has `start()`.
- **Runtime:** the JVM looks at the **actual object (Car)** and finds
  `Car`'s `start()`. The bytecode instruction for this is `invokeinterface`,
  and the lookup is the interface counterpart of the v-table idea used for
  class overriding. This is dynamic dispatch (runtime polymorphism).

### 14.4 Default methods
A default method is stored in the interface's class file as a **non-abstract**
method. If an implementing class does not override it, the JVM resolves the
call to the interface's version during method lookup (after checking the
class chain first, which is why "class wins").

### 14.5 Lambdas
A lambda targeting a functional interface is **not** compiled to a separate
anonymous class file at compile time. The compiler emits an `invokedynamic`
instruction and the JVM creates the implementing object at runtime. This is
why lambdas generally have lower overhead than anonymous classes.

### 14.6 Why no instance state?
Because interfaces have no constructors and no instance fields, an
implementing class never inherits object state from an interface. This is
the main reason multiple interface inheritance is safe.

---

## 15. Key Rules & Common Mistakes

```java
// MISTAKE 1: weaker access when implementing
interface I { void run(); }
class A implements I { void run() { } }            // must be public
class A2 implements I { public void run() { } }    // correct

// MISTAKE 2: forgetting to implement all abstract methods
interface Shape { double area(); double perimeter(); }
class Circle implements Shape { public double area() { return 1; } }
// COMPILE ERROR: perimeter() missing (or make Circle abstract)

// MISTAKE 3: assigning to an interface constant
interface K { int MAX = 10; }
// K.MAX = 20;       // final

// MISTAKE 4: two interfaces with same default method and no override
interface X { default void m() { } }
interface Y { default void m() { } }
// class Z implements X, Y { }  // COMPILE ERROR: must override m()

// MISTAKE 5: calling an interface static method on an object
interface U { static void help() { } }
class V implements U { }
// new V().help();   // COMPILE ERROR
U.help();            // correct

// MISTAKE 6: default method overriding Object methods
interface T { default String toString() { return "x"; } }   // COMPILE ERROR

// MISTAKE 7: functional interface with 2 abstract methods
@FunctionalInterface
interface F { void a(); void b(); }                          // COMPILE ERROR

// MISTAKE 8: instantiating an interface
// Vehicle v = new Vehicle();   // COMPILE ERROR
// Correct: use an implementing class, anonymous class or lambda
```

---

## 16. Quick Revision Cheatsheet

```
INTERFACE = CONTRACT (WHAT, not HOW) = blueprint of BEHAVIOR
──────────────────────────────────────────────────────────────
 implements (class->interface)   extends (interface->interface, many OK)
 Cannot instantiate; no constructors; cannot be final

MEMBERS (implicit modifiers)
──────────────────────────────────────────────────────────────
 fields           -> public static final (must be initialized)
 abstract methods -> public abstract (implementer must be public)
 default method   -> public, has body (Java 8), may be overridden
 static method    -> called via InterfaceName only, NOT inherited (Java 8)
 private method   -> helper for default/static (Java 9)

MULTIPLE INHERITANCE
──────────────────────────────────────────────────────────────
 Classes: NO (diamond problem, state conflict)
 Interfaces: YES (no state; conflicts must be resolved)

RESOLUTION ORDER
──────────────────────────────────────────────────────────────
 1. Class (own/superclass) wins
 2. Most specific interface wins
 3. Else compile error -> override + X.super.method()

FUNCTIONAL INTERFACE
──────────────────────────────────────────────────────────────
 Exactly ONE abstract method; @FunctionalInterface recommended
 Can have default/static/private; Object methods do not count
 Target for lambdas: Runnable, Comparator, Predicate, Function,
 Consumer, Supplier

MARKER INTERFACE
──────────────────────────────────────────────────────────────
 No methods; tags capability: Serializable, Cloneable, RandomAccess
 Checked at runtime (instanceof / JVM). Gives a TYPE (annotations do not)

INTERFACE vs ABSTRACT CLASS
──────────────────────────────────────────────────────────────
 Interface:      CAN-DO, multiple, no state, no constructor
 Abstract class: IS-A, single, has state + constructor
 Use both together when needed

INTERNALS
──────────────────────────────────────────────────────────────
 Own .class file; calls via invokeinterface (runtime dispatch)
 Default methods stored as non-abstract methods in the interface
 Lambdas use invokedynamic (not an anonymous class file)
```

---

*End of Notes*
````