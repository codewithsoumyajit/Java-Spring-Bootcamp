# Java File Structure, Wrapper Classes, Integer Caching,
# Abstract Classes & POJO — Comprehensive Notes

---

## Table of Contents
1. [Java File Structure](#1-java-file-structure)
2. [Wrapper Classes](#2-wrapper-classes)
3. [Autoboxing and Unboxing](#3-autoboxing-and-unboxing)
4. [Integer Class & JVM Caching](#4-integer-class--jvm-caching)
5. [Abstract Classes — Interview Deep Dive](#5-abstract-classes--interview-deep-dive)
6. [POJO Classes](#6-pojo-classes)
7. [Key Rules & Common Mistakes](#7-key-rules--common-mistakes)
8. [Quick Revision Cheatsheet](#8-quick-revision-cheatsheet)

---

## 1. Java File Structure

### The Golden Rule

> **One public class per `.java` file, and the file name must
> exactly match the public class name.**

### Why Does This Rule Exist?

When the JVM needs to run a Java program, it must locate the
`main()` method — the entry point. The JVM uses the file system
to find classes. If multiple public classes existed in one file,
the JVM would not know which file to look in for a given class.

By enforcing one public class per file with a matching name,
the JVM can always find any class by simply looking for
`ClassName.java` → compiles to → `ClassName.class`.

### File Structure Rules

| Rule | Detail |
|------|--------|
| One public class per file | Only one class can be `public` |
| File name = public class name | `Student.java` must contain `public class Student` |
| Multiple non-public classes | Allowed in same file but rarely recommended |
| Package declaration | Must be first line (before imports) |
| Import statements | Come after package, before class |

### Standard Java File Layout

```java
// 1. Package declaration — FIRST (optional but standard)
package com.company.project.module;

// 2. Import statements — SECOND
import java.util.List;
import java.util.ArrayList;

// 3. Public class — matches file name exactly
public class Student {

    // 4. Fields (instance variables)
    private String name;
    private int age;

    // 5. Constructors
    public Student(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    // 6. Methods
    public String getName() { return name; }
    public int getAge()     { return age; }

    // 7. main() — entry point (only in the class JVM should start from)
    public static void main(String[] args) {
        Student s = new Student("Rohit", 20);
        System.out.println(s.getName());
    }
}

// 8. Non-public helper class — allowed in same file
class Helper {
    static void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
}
```

### How JVM Finds and Executes Code

SOURCE FILE COMPILATION EXECUTION
───────────── ─────────── ─────────────────────
Student.java → javac → Student.class
│
▼
JVM loads Student.class
│
▼
Finds main() method
│
▼
Begins execution


### What Happens with Multiple Classes

```java
// File: Demo.java

public class Demo {           // ✅ public — file name MUST be Demo.java
    public static void main(String[] args) {
        Helper h = new Helper();
        h.assist();
    }
}

class Helper {                // ✅ non-public — allowed in same file
    void assist() {
        System.out.println("Helping!");
    }
}

// public class AnotherPublic { } // ❌ COMPILE ERROR — two public classes
```

---

## 2. Wrapper Classes

### What Are Wrapper Classes?

Java has 8 primitive types (`int`, `double`, `char`, etc.).
These are NOT objects — they are raw values stored directly in memory.

**Wrapper classes** are object representations of each primitive type.
They wrap the primitive inside an object, giving it all the capabilities
of a Java object.

### Primitive to Wrapper Mapping

| Primitive | Wrapper Class | Size |
|-----------|---------------|------|
| `byte`    | `Byte`        | 8 bit |
| `short`   | `Short`       | 16 bit |
| `int`     | `Integer`     | 32 bit |
| `long`    | `Long`        | 64 bit |
| `float`   | `Float`       | 32 bit |
| `double`  | `Double`      | 64 bit |
| `char`    | `Character`   | 16 bit |
| `boolean` | `Boolean`     | 1 bit |

### Why Do Wrapper Classes Exist?

#### Reason 1 — Java Collections Only Work with Objects

```java
// ❌ CANNOT store primitives in collections
List<int> numbers = new ArrayList<int>(); // compile error

// ✅ Must use wrapper class
List<Integer> numbers = new ArrayList<Integer>(); // works perfectly
```

#### Reason 2 — Null Representation

```java
int x = null;      // ❌ primitives cannot be null
Integer y = null;  // ✅ wrapper objects CAN be null
// Useful when representing "no value" or optional data
```

#### Reason 3 — Utility Methods

```java
// Integer class provides many useful static methods
String s   = "42";
int num    = Integer.parseInt(s);        // String → int
String str = Integer.toString(42);       // int → String
int max    = Integer.max(10, 20);        // maximum of two
int min    = Integer.min(10, 20);        // minimum of two
String bin = Integer.toBinaryString(10); // "1010"
String hex = Integer.toHexString(255);   // "ff"
int bits   = Integer.bitCount(7);        // number of 1 bits

System.out.println(Integer.MAX_VALUE); // 2147483647
System.out.println(Integer.MIN_VALUE); // -2147483648
```

#### Reason 4 — Generics

```java
// Generics require object types — primitives not allowed
class Box<T> {
    T value;
    Box(T value) { this.value = value; }
}

Box<Integer> intBox    = new Box<>(42);   // ✅ wrapper
Box<Double>  doubleBox = new Box<>(3.14); // ✅ wrapper
// Box<int> — ❌ not allowed
```

### Creating Wrapper Objects

```java
// Method 1 — Constructor (deprecated since Java 9)
Integer a = new Integer(42);  // ⚠️ deprecated

// Method 2 — valueOf() — RECOMMENDED (uses cache internally)
Integer b = Integer.valueOf(42);

// Method 3 — Autoboxing (compiler does it automatically)
Integer c = 42; // compiler converts to Integer.valueOf(42)

// Converting back to primitive
int x = b.intValue();      // explicit unboxing
int y = b;                 // autoboxing — compiler inserts intValue()
```

---

## 3. Autoboxing and Unboxing

### What is Autoboxing?

**Autoboxing** is the **automatic conversion** of a primitive type
to its corresponding wrapper class object, done by the Java compiler.

### What is Unboxing?

**Unboxing** is the **automatic conversion** of a wrapper object
back to its primitive type, done by the Java compiler.

### The Compiler's Role

You write simple code — the compiler inserts the conversion calls:

```java
// WHAT YOU WRITE:
int x = 5;
Integer y = x;   // autoboxing
int z = y;       // unboxing

// WHAT COMPILER ACTUALLY GENERATES:
int x = 5;
Integer y = Integer.valueOf(x);   // autoboxing
int z = y.intValue();             // unboxing
```

### Autoboxing in Collections

```java
List<Integer> list = new ArrayList<>();

// Autoboxing — primitives automatically converted when added
list.add(10);   // compiler: list.add(Integer.valueOf(10))
list.add(20);
list.add(30);

// Unboxing — Integer automatically converted when retrieved into int
int sum = 0;
for (int num : list) {      // compiler: num = element.intValue()
    sum += num;
}
System.out.println(sum); // 60
```

### Autoboxing in Arithmetic

```java
Integer a = 10;
Integer b = 20;

// Unboxing happens automatically for arithmetic
Integer sum = a + b;  // compiler: Integer.valueOf(a.intValue() + b.intValue())
System.out.println(sum); // 30
```

### The NullPointerException Trap

This is the most dangerous consequence of autoboxing/unboxing:

```java
Integer value = null;  // wrapper can be null
int result = value;    // ❌ NullPointerException at runtime!
// Compiler generates: value.intValue() — calling method on null object

// Real scenario where this ACTUALLY happens
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 100);

// scores.get("Bob") returns null — Bob doesn't exist
int bobScore = scores.get("Bob"); // ❌ NullPointerException!

// ✅ Safe approach — always check for null
Integer bobScoreWrapped = scores.get("Bob");
if (bobScoreWrapped != null) {
    int safe = bobScoreWrapped;
}
```

### Performance Consideration

```java
// ❌ BAD — autoboxing inside loop creates many Integer objects
Long sum = 0L;
for (long i = 0; i < 1_000_000; i++) {
    sum += i; // unboxing + addition + autoboxing every iteration
              // creates 1 million Long objects in heap — slow!
}

// ✅ GOOD — use primitive for performance
long sum = 0L;
for (long i = 0; i < 1_000_000; i++) {
    sum += i; // pure primitive arithmetic — fast
}
```

---

## 4. Integer Class & JVM Caching

### The Surprising Behavior

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);      // true  ← SURPRISING!

Integer c = 128;
Integer d = 128;
System.out.println(c == d);      // false ← expected for objects
System.out.println(c.equals(d)); // true  ← correct value comparison
```

### Why Does This Happen? — Integer Cache

The JVM maintains an **Integer cache** for values between **-128 and 127**.

When `Integer.valueOf(n)` is called (which autoboxing uses):
- If `n` is between -128 and 127 → returns a **cached object**
  from a pre-allocated pool
- If `n` is outside that range → creates a **new object** in heap

```java
// Internal implementation of Integer.valueOf() (simplified):
public static Integer valueOf(int i) {
    if (i >= -128 && i <= 127) {
        return IntegerCache.cache[i + 128]; // return CACHED object
    }
    return new Integer(i); // create NEW object
}
```

### Memory Diagram

JVM STARTUP — Integer Cache Pre-allocated in Heap
┌─────────────────────────────────────────────────┐
│ IntegerCache (created when Integer class loads) │
│ cache[-128] → Integer(-128) │
│ cache[-127] → Integer(-127) │
│ ... │
│ cache[127] → Integer(127) │
└─────────────────────────────────────────────────┘

Integer a = 127; → a points to cache[255] (127+128)
Integer b = 127; → b points to SAME cache[255]
a == b → SAME object in heap → true

Integer c = 128; → new Integer(128) in heap (address X)
Integer d = 128; → new Integer(128) in heap (address Y)
c == d → different objects → false


### Why Cache -128 to 127?

- These small integer values are used extremely frequently in programs
  (loop counters, flags, small quantities)
- Caching them saves heap allocations and garbage collection overhead
- The range was chosen based on common programming patterns
- This is a JVM optimization — defined in the Java Language Specification

### The Right Way to Compare Wrapper Objects

```java
Integer x = 500;
Integer y = 500;

// ❌ WRONG — compares references (memory addresses)
if (x == y) { }        // false — different objects

// ✅ CORRECT — compares values
if (x.equals(y)) { }   // true — same value

// ✅ ALSO CORRECT — unbox first, then compare primitives
if (x.intValue() == y.intValue()) { }

// ✅ BEST — use primitives when you just need the value
int a = x, b = y;
if (a == b) { }        // true — primitive comparison
```

### Cache Applies to Other Wrapper Types Too

| Wrapper   | Cached Range |
|-----------|-------------|
| `Integer` | -128 to 127 |
| `Long`    | -128 to 127 |
| `Short`   | -128 to 127 |
| `Byte`    | -128 to 127 (entire range) |
| `Character`| 0 to 127   |
| `Boolean` | `true` and `false` (always cached) |
| `Float`   | No cache |
| `Double`  | No cache |

---

## 5. Abstract Classes — Interview Deep Dive

### Core Review

An abstract class is a class declared with the `abstract` keyword
that cannot be instantiated and may contain abstract methods
(methods without a body).

### Interview Question 1 — Can Abstract Classes Have Constructors?

**Answer: YES — and they MUST if their fields need initialization.**

```java
abstract class Vehicle {
    String brand;
    int year;

    // ✅ Abstract class CAN and SHOULD have constructors
    Vehicle(String brand, int year) {
        this.brand = brand;
        this.year  = year;
        System.out.println("Vehicle constructor called");
    }

    abstract void start();
}

class Car extends Vehicle {
    int doors;

    Car(String brand, int year, int doors) {
        super(brand, year); // MUST call abstract class constructor
        this.doors = doors;
        System.out.println("Car constructor called");
    }

    @Override
    void start() {
        System.out.println(brand + " car starting...");
    }
}

public class Main {
    public static void main(String[] args) {
        Car c = new Car("Toyota", 2023, 4);
        c.start();
        // Output:
        // Vehicle constructor called
        // Car constructor called
        // Toyota car starting...
    }
}
```

**Why?** Even though you cannot create a `Vehicle` object directly,
when you create a `Car` object, the `Vehicle` part of it must still
be initialized. The constructor runs via `super()` from the child.

### Interview Question 2 — Can Abstract Classes Have Static Methods?

**Answer: YES — static methods are class-level and fully independent
of object creation.**

```java
abstract class MathHelper {
    // ✅ Abstract class CAN have static methods
    static int square(int n) {
        return n * n;
    }

    static double circleArea(double radius) {
        return 3.14159 * radius * radius;
    }

    abstract double compute(double input);
}

public class Main {
    public static void main(String[] args) {
        // Call static method WITHOUT creating an object
        System.out.println(MathHelper.square(5));       // 25
        System.out.println(MathHelper.circleArea(3.0)); // 28.27431
    }
}
```

### Interview Question 3 — Can an Abstract Class Be `final`?

**Answer: NO — and the reason is logical.**

`final` class = cannot be extended.
`abstract` class = MUST be extended to be useful.

These two are **contradictory**. A `final abstract` class could never
be instantiated (abstract) AND could never be extended (final),
making it completely useless.

```java
// ❌ COMPILE ERROR — contradictory modifiers
final abstract class Demo {
    abstract void show();
}
// Error: illegal combination of modifiers: abstract and final
```

### Interview Question 4 — Can Abstract Methods Be `static`?

**Answer: NO — static methods cannot be overridden (only hidden),
and abstract methods exist specifically to be overridden.**

```java
abstract class Demo {
    // ❌ COMPILE ERROR
    abstract static void show();
    // Error: illegal combination of modifiers: abstract and static
}
```

**Why?** Abstract methods need to be overridden in subclasses.
Static methods belong to the class and are resolved at compile time.
These two concepts are fundamentally incompatible.

### Interview Question 5 — Can Abstract Methods Be `private`?

**Answer: NO — private methods are not inherited, so they cannot
be overridden. Abstract methods MUST be overridden.**

```java
abstract class Demo {
    // ❌ COMPILE ERROR
    private abstract void show();
    // Error: illegal combination of modifiers: abstract and private
}
```

### Interview Question 6 — Can Abstract Class Have `main()` Method?

**Answer: YES — and it can be run directly.**

```java
abstract class Demo {
    abstract void display();

    // ✅ Fully valid — main() is static, does not need object
    public static void main(String[] args) {
        System.out.println("main() in abstract class works!");
        // But cannot do: new Demo() — still cannot instantiate
    }
}
```

### Interview Question 7 — What If Subclass Does Not Implement
All Abstract Methods?

**Answer: The subclass must ALSO be declared abstract.**

```java
abstract class Shape {
    abstract double area();
    abstract double perimeter();
}

// ✅ Option 1 — implement ALL abstract methods
class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }
    double area()      { return 3.14 * r * r; }
    double perimeter() { return 2 * 3.14 * r; }
}

// ✅ Option 2 — implement SOME and declare as abstract
abstract class Polygon extends Shape {
    int sides;
    double perimeter() { return sides * 10; } // implements one
    // area() still abstract — so Polygon must be abstract too
}

// ❌ Option 3 — implement NONE and not abstract → COMPILE ERROR
class Triangle extends Shape {
    // forgot to implement area() and perimeter()
    // Error: Triangle is not abstract and does not override
    //        abstract method area() in Shape
}
```

### Complete Abstract Class Rules Table

| Question | Answer |
|----------|--------|
| Can have constructor? | ✅ YES |
| Can have static methods? | ✅ YES |
| Can have concrete methods? | ✅ YES |
| Can have instance variables? | ✅ YES |
| Can have static variables? | ✅ YES |
| Can be `final`? | ❌ NO — contradiction |
| Can abstract method be `static`? | ❌ NO — contradiction |
| Can abstract method be `private`? | ❌ NO — contradiction |
| Can abstract method be `final`? | ❌ NO — contradiction |
| Can have `main()`? | ✅ YES |
| Can be instantiated? | ❌ NO |
| Must subclass implement all methods? | ✅ YES (or be abstract) |

---

## 6. POJO Classes

### What is a POJO?

**POJO = Plain Old Java Object**

A POJO is a simple Java class that:
- Contains **private fields** (data)
- Has a **no-argument constructor**
- Has **public getters and setters** for each field
- Does NOT extend any specific framework class
- Does NOT implement any framework interface
- Has no special annotations or requirements

The term was coined to contrast against heavy EJB (Enterprise Java
Beans) objects that required extending framework-specific classes.

### Standard POJO Structure

```java
public class Student {

    // 1. Private fields
    private int id;
    private String name;
    private double gpa;
    private String email;

    // 2. No-argument constructor (REQUIRED by many frameworks)
    public Student() { }

    // 3. Parameterized constructor (optional but useful)
    public Student(int id, String name, double gpa, String email) {
        this.id    = id;
        this.name  = name;
        this.gpa   = gpa;
        this.email = email;
    }

    // 4. Getters — read access to private fields
    public int    getId()    { return id; }
    public String getName()  { return name; }
    public double getGpa()   { return gpa; }
    public String getEmail() { return email; }

    // 5. Setters — write access with optional validation
    public void setId(int id) {
        if (id > 0) this.id = id;
    }
    public void setName(String name) {
        if (name != null && !name.isEmpty()) this.name = name;
    }
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 10.0) this.gpa = gpa;
    }
    public void setEmail(String email) {
        if (email != null && email.contains("@")) this.email = email;
    }

    // 6. toString() — useful for logging and debugging
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name
             + "', gpa=" + gpa + ", email='" + email + "'}";
    }

    // 7. equals() and hashCode() — for comparison and collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return id == s.id && email.equals(s.email);
    }

    @Override
    public int hashCode() {
        return 31 * id + email.hashCode();
    }
}
```

### Why POJOs Are Important in Frameworks

#### Spring Boot — Dependency Injection & REST APIs

```java
// Spring Boot uses POJOs as request/response bodies
// Jackson library maps JSON ↔ POJO automatically

@RestController
public class StudentController {

    @GetMapping("/student")
    public Student getStudent() {
        // Spring automatically converts this POJO to JSON
        return new Student(1, "Rohit", 9.2, "rohit@email.com");
        // Response: {"id":1,"name":"Rohit","gpa":9.2,"email":"..."}
    }

    @PostMapping("/student")
    public String addStudent(@RequestBody Student student) {
        // Spring automatically converts incoming JSON to POJO
        System.out.println(student.getName()); // "Rohit"
        return "Saved!";
    }
}
```

#### Hibernate / JPA — Object-Relational Mapping

```java
// Hibernate maps POJO fields to database table columns
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_name")
    private String name;

    private double gpa;

    // No-arg constructor REQUIRED by Hibernate
    public Student() { }

    // Getters and Setters REQUIRED by Hibernate
    // (Hibernate uses reflection to call them)
    public int    getId()   { return id; }
    public String getName() { return name; }
    // ...
}
```

**Why does Hibernate need no-arg constructor and getters/setters?**
Hibernate uses Java Reflection to:
1. Create an empty object with `Student.class.newInstance()`
   (needs no-arg constructor)
2. Call `setName("Rohit")` to populate fields from database rows
   (needs setters)
3. Call `getName()` to read values when saving
   (needs getters)

### Anemic vs Rich Domain Models

| Model Type | Description | Example |
|------------|-------------|---------|
| **Anemic** | Only data — no business logic in the class | POJO with just getters/setters |
| **Rich Domain** | Data + business logic together | Methods that enforce rules |

```java
// ANEMIC Model — just a data bag
class Order {
    private double totalAmount;
    private String status;
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double amount) { this.totalAmount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    // No logic — business rules live somewhere else
}

// RICH Domain Model — data + behavior
class Order {
    private double totalAmount;
    private String status;
    private List<OrderItem> items;

    // Business logic LIVES in the class
    public void addItem(OrderItem item) {
        items.add(item);
        totalAmount += item.getPrice();
    }

    public boolean cancel() {
        if ("SHIPPED".equals(status)) return false; // cannot cancel shipped
        this.status = "CANCELLED";
        return true;
    }

    public double applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 50) {
            throw new IllegalArgumentException("Invalid discount");
        }
        totalAmount -= totalAmount * (percentage / 100);
        return totalAmount;
    }
}
```

**When to use which?**
- **Anemic** → Simple data transfer (DTOs, API request/response bodies)
- **Rich** → Core business domain objects with complex rules

### POJO vs Related Concepts

| Term | What It Is |
|------|-----------|
| **POJO** | Plain Old Java Object — simple class, no framework dependency |
| **JavaBean** | POJO with strict rules: no-arg constructor, serializable, getters/setters |
| **DTO** | Data Transfer Object — POJO used specifically to transfer data between layers |
| **Entity** | POJO annotated with `@Entity` — maps to a database table |
| **Model** | General term for any class representing data in MVC pattern |

---

## 7. Key Rules & Common Mistakes

### File Structure

```java
// ❌ MISTAKE 1 — File name does not match public class name
// File: Main.java
public class Student { } // ❌ File name must be Student.java

// ❌ MISTAKE 2 — Two public classes in one file
public class A { }
public class B { } // ❌ Only one public class per file

// ✅ CORRECT
public class A { }  // in A.java
class B { }         // helper class — non-public, OK in same file
```

### Wrapper Classes & Autoboxing

```java
// ❌ MISTAKE 1 — Using == to compare wrapper objects
Integer a = 200, b = 200;
if (a == b) { }        // ❌ false — outside cache range

// ✅ CORRECT
if (a.equals(b)) { }   // true — value comparison

// ❌ MISTAKE 2 — Unboxing null wrapper
Integer x = null;
int y = x;             // ❌ NullPointerException

// ✅ CORRECT
if (x != null) int y = x;

// ❌ MISTAKE 3 — Autoboxing in tight loops (performance)
Long sum = 0L;
for (int i = 0; i < 1000000; i++) sum += i; // ❌ 1M objects created

// ✅ CORRECT
long sum = 0L;
for (int i = 0; i < 1000000; i++) sum += i; // ✅ pure primitive
```

### Abstract Classes

```java
// ❌ MISTAKE 1 — Trying to instantiate abstract class
abstract class Animal { }
Animal a = new Animal(); // ❌ Compile error

// ❌ MISTAKE 2 — abstract + final
final abstract class Demo { } // ❌ Contradiction

// ❌ MISTAKE 3 — abstract + static on a method
abstract static void show(); // ❌ Contradiction

// ❌ MISTAKE 4 — abstract + private on a method
private abstract void show(); // ❌ Contradiction
```

---

## 8. Quick Revision Cheatsheet

JAVA FILE STRUCTURE
──────────────────────────────────────────────────────────────
One public class per .java file
File name MUST match public class name exactly
Order: package → imports → public class
JVM finds entry point via: ClassName.java → ClassName.class → main()

WRAPPER CLASSES
──────────────────────────────────────────────────────────────
Each primitive has a wrapper: int → Integer, double → Double, etc.
Why needed: Collections require objects, null support, utility methods
valueOf() → recommended way to create wrapper (uses cache)
intValue() → extract primitive from wrapper

AUTOBOXING & UNBOXING
──────────────────────────────────────────────────────────────
Autoboxing → primitive to wrapper (compiler inserts valueOf())
Unboxing → wrapper to primitive (compiler inserts intValue())
Danger → unboxing null wrapper causes NullPointerException
Performance → avoid autoboxing in tight loops

INTEGER CACHE
──────────────────────────────────────────────────────────────
JVM caches Integer objects for values -128 to 127
valueOf() returns SAME cached object for values in range
For values outside range → NEW object created each time
== compares references; equals() compares values
Always use equals() to compare wrapper objects!
Other cached types: Long, Short, Byte(-128–127), Character(0–127),
Boolean(true/false)

ABSTRACT CLASS — INTERVIEW RULES
──────────────────────────────────────────────────────────────
CAN have: constructors, static methods, concrete methods,
instance variables, static variables, main()
CANNOT: be instantiated, be final, have abstract+static method,
have abstract+private method, have abstract+final method
Subclass MUST implement all abstract methods OR be abstract itself

POJO
──────────────────────────────────────────────────────────────
Plain Old Java Object — simple class, no framework dependency
Structure: private fields + no-arg constructor + getters + setters
Used by: Spring (JSON mapping), Hibernate (DB mapping via reflection)
Hibernate needs: no-arg constructor (to create empty object),
getters/setters (to read/write via reflection)
Anemic model → data only, no business logic (DTOs)
Rich model → data + business logic (domain objects)
Related terms: JavaBean (strict POJO), DTO (data transfer),
Entity (DB-mapped POJO).