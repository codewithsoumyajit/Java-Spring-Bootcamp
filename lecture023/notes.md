# Java `static` and `final` Keywords — Comprehensive Notes

---

## Table of Contents
1. [The `static` Keyword](#1-the-static-keyword)
2. [Static Variables](#2-static-variables)
3. [Static Methods](#3-static-methods)
4. [Static Blocks](#4-static-blocks)
5. [The `final` Keyword](#5-the-final-keyword)
6. [The `main()` Method — Deep Dive](#6-the-main-method--deep-dive)
7. [String[] args — Command Line Arguments](#7-string-args--command-line-arguments)
8. [Key Rules & Common Mistakes](#8-key-rules--common-mistakes)
9. [Quick Revision Cheatsheet](#9-quick-revision-cheatsheet)

---

## 1. The `static` Keyword

By default, every field and method in a class belongs to a specific
**object** (instance). The `static` keyword changes this — a static
member belongs to the **class itself**, not to any individual object.

### The Core Idea

Imagine 1000 students in a college. Each student has their own `name`
and `rollNo` — those should be instance variables. But every student
shares the same `collegeName`. Creating 1000 separate copies of
`collegeName` in heap memory is wasteful.

`static` solves this by creating **one shared copy** at the class level.

```
WITHOUT static                    WITH static
──────────────────────────────    ──────────────────────────────
Object 1: name, roll, college     Object 1: name, roll  ─┐
Object 2: name, roll, college     Object 2: name, roll  ─┼──► Class: college
Object 3: name, roll, college     Object 3: name, roll  ─┘    (ONE copy shared)

Memory wasted: 3x college         Memory saved: 1x college
```

---

## 2. Static Variables

- Declared with the `static` keyword inside a class
- Only **one copy** exists for the entire class, shared by all objects
- Stored in a special memory area called **Method Area** (not heap, not stack)
- Initialized when the **class is first loaded** by the JVM
- Accessed using the **class name**: `ClassName.variableName`

### Example

```java
class Student {
    String name;        // instance variable — unique per object
    int rollNo;         // instance variable — unique per object
    static String collegeName = "IIT Delhi"; // ONE copy for ALL students
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name   = "Rohit";
        s1.rollNo = 1;

        Student s2 = new Student();
        s2.name   = "Mohit";
        s2.rollNo = 2;

        // Both share the same collegeName
        System.out.println(Student.collegeName); // IIT Delhi
        System.out.println(s1.collegeName);      // IIT Delhi (works but not recommended)
        System.out.println(s2.collegeName);      // IIT Delhi

        // Changing via class name affects ALL objects
        Student.collegeName = "IIT Bombay";
        System.out.println(s1.collegeName); // IIT Bombay
        System.out.println(s2.collegeName); // IIT Bombay
    }
}
```

### Memory Layout with Static Variables

```
  STACK              HEAP                      METHOD AREA
┌─────────┐        ┌──────────────────┐       ┌──────────────────────┐
│  s1 ────┼───────►│ Student Object 1 │       │ Student.class        │
│  s2 ────┼───────►│ Student Object 2 │       │ collegeName:"IIT Delhi│
└─────────┘        │  name, rollNo    │       │ (shared by all)      │
                   └──────────────────┘       └──────────────────────┘
```

### Static Variable — Use Case: Counter

A very common use case is tracking how many objects of a class have
been created:

```java
class Student {
    String name;
    static int totalStudents = 0; // shared counter

    Student(String name) {
        this.name = name;
        totalStudents++; // increments shared count on every object creation
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Rohit");
        Student s2 = new Student("Mohit");
        Student s3 = new Student("Sumit");

        System.out.println(Student.totalStudents); // 3
    }
}
```

---

## 3. Static Methods

- Declared with the `static` keyword
- Belong to the **class**, not to any object
- Called using the **class name**: `ClassName.methodName()`
- No need to create an object to call a static method

### Example

```java
class Student {
    String name;
    static int totalStudents = 0;

    Student(String name) {
        this.name = name;
        totalStudents++;
    }

    // Static method
    static void markAttendance() {
        System.out.println("Attendance marked for all students");
    }

    // Static method accessing static variable
    static int getTotalStudents() {
        return totalStudents;
    }
}

public class Main {
    public static void main(String[] args) {
        new Student("Rohit");
        new Student("Mohit");

        Student.markAttendance();           // No object needed
        System.out.println(Student.getTotalStudents()); // 2
    }
}
```

### Rules for Static Methods

| Rule | Explanation |
|------|-------------|
| Can access static variables | Static method can read/write static fields directly |
| Can call other static methods | Static method can call static methods directly |
| Cannot access instance variables | Instance variables belong to objects; static methods have no object context |
| Cannot call instance methods | Same reason — no object exists at class level |
| Cannot use `this` keyword | `this` refers to the current object; static context has no object |
| Cannot use `super` keyword | `super` also requires an object context |

```java
class Demo {
    int x = 10;          // instance variable
    static int y = 20;   // static variable

    static void show() {
        System.out.println(y);    // ✅ static variable — allowed
        System.out.println(x);    // ❌ Compile error — instance variable
        System.out.println(this); // ❌ Compile error — no 'this' in static
    }
}
```

---

## 4. Static Blocks

A **static block** is a special block of code that runs **once**,
automatically, when the class is first loaded into memory by the JVM —
before any object is created and before `main()` runs.

### Syntax

```java
class ClassName {
    static {
        // code here runs once when class loads
    }
}
```

### Purpose

- Initialize static variables that require complex logic
- Perform one-time setup tasks (e.g., loading a database driver,
  reading a config file)
- Run code before any object creation is possible

### Example

```java
class Student {
    static String collegeName;
    static int totalStudents;

    // Static block — runs once when class is loaded
    static {
        System.out.println("Static block executed!");
        collegeName   = "IIT Delhi";
        totalStudents = 0;
    }

    Student() {
        totalStudents++;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("main() started");

        Student s1 = new Student();
        Student s2 = new Student();

        System.out.println(Student.collegeName);    // IIT Delhi
        System.out.println(Student.totalStudents);  // 2
    }
}

/*
 Output:
 Static block executed!      ← Runs BEFORE main() body
 main() started
 IIT Delhi
 2
*/
```

### Execution Order

```
JVM loads the class
        │
        ▼
Static variables are allocated in Method Area
        │
        ▼
Static block executes (once only)
        │
        ▼
main() method executes
        │
        ▼
Objects are created as needed
```

### Multiple Static Blocks

You can have multiple static blocks — they execute in the **order they
appear** in the source code:

```java
class Demo {
    static {
        System.out.println("Static block 1"); // runs first
    }
    static {
        System.out.println("Static block 2"); // runs second
    }
}
/*
 Output:
 Static block 1
 Static block 2
*/
```

---

## 5. The `final` Keyword

The `final` keyword is used to **restrict modification**. It can be
applied to variables, methods, and classes, each with a specific effect.

### 5.1 `final` Variables — Constants

Once a `final` variable is assigned a value, it **cannot be reassigned**.
It effectively becomes a **constant**.

```java
class Circle {
    final double PI = 3.14159; // constant — cannot be changed

    double area(double r) {
        return PI * r * r;
    }
}

public class Main {
    public static void main(String[] args) {
        Circle c = new Circle();
        System.out.println(c.area(5)); // 78.53975

        c.PI = 3.0; // ❌ Compile error: cannot assign a value to final variable PI
    }
}
```

### `static final` — Class-Level Constants

Combining `static` and `final` creates a **class-level constant** —
one shared copy that can never be changed. This is the standard Java
convention for constants, written in `ALL_CAPS`:

```java
class MathConstants {
    static final double PI       = 3.14159265;
    static final double E        = 2.71828182;
    static final int    MAX_SIZE = 100;
}

// Usage
System.out.println(MathConstants.PI); // 3.14159265
```

### 5.2 `final` Methods — Prevent Overriding

A method marked `final` **cannot be overridden** by any subclass.
Use this when you want to lock down a method's behavior.

```java
class Parent {
    final void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    void display() { // ❌ Compile error: cannot override final method
        System.out.println("Child display");
    }
}
```

### 5.3 `final` Classes — Prevent Inheritance

A class marked `final` **cannot be extended** (subclassed).
No other class can inherit from it.

```java
final class ImmutableClass {
    // This class cannot be extended
}

class SubClass extends ImmutableClass { // ❌ Compile error
}
```

> **Real-world example:** Java's built-in `String` class is `final`.
> This is why you cannot create a subclass of `String`.

### `final` Summary Table

| Applied To | Effect                                           |
|------------|--------------------------------------------------|
| Variable   | Value cannot be reassigned after initialization  |
| Method     | Method cannot be overridden in a subclass        |
| Class      | Class cannot be extended / subclassed            |

### Important Nuance — `final` with Objects

For object references, `final` prevents **reassignment of the reference**,
but does NOT prevent **modification of the object's fields**:

```java
final Customer c = new Customer("Rohit", 1000);

c = new Customer("Mohit", 2000); // ❌ Error — cannot reassign final reference
c.balance = 5000;                // ✅ Allowed — object's field can change
```

---

## 6. The `main()` Method — Deep Dive

### Full Signature

```java
public static void main(String[] args)
```

Every part of this signature has a specific and deliberate reason:

| Keyword / Part  | Reason                                                                 |
|-----------------|------------------------------------------------------------------------|
| `public`        | JVM must be able to access it from outside the class                  |
| `static`        | JVM calls it without creating an object of the class                  |
| `void`          | JVM does not expect a return value from `main()`                      |
| `main`          | The specific name the JVM looks for as the entry point                |
| `String[] args` | Accepts command-line arguments passed when running the program         |

### Why Must `main()` Be `static`?

When the JVM starts, **no objects exist yet**. The JVM needs to call
`main()` to begin execution, but it has nothing to call an instance
method on. Making `main()` static allows the JVM to call it directly
via the class name — **without needing to create an object first**.

```java
// JVM internally does something like:
ClassName.main(args); // class-level call — no object needed
```

If `main()` were not static, the JVM would face a paradox:
*"To create an object I need to run code, but to run code I need an object."*

```java
// ❌ If main() were non-static — JVM cannot call it
public class Main {
    public void main(String[] args) { // non-static
        System.out.println("Hello"); // JVM can't reach here without an object
    }
}
```

---

## 7. String[] args — Command Line Arguments

`String[] args` is an array of `String` values that captures
**arguments passed from the command line** when the Java program is run.

### How It Works

```bash
# Compiling
javac Main.java

# Running with arguments
java Main Rohit 25 Delhi
#          ↑     ↑   ↑
#        args[0] args[1] args[2]
```

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(args[0]); // Rohit
        System.out.println(args[1]); // 25
        System.out.println(args[2]); // Delhi
        System.out.println(args.length); // 3
    }
}
```

### Important Notes on `args`

- `args` is just a conventional name — you can name it anything
  (e.g., `String[] input` is equally valid)
- All arguments are received as **Strings** — convert them if needed:
  ```java
  int age = Integer.parseInt(args[1]); // "25" → 25
  ```
- If no arguments are passed, `args` is an empty array (length 0),
  not `null`
- Accessing `args[0]` when no arguments were passed throws
  `ArrayIndexOutOfBoundsException`

### Real-World Use Case

Command-line arguments are widely used in enterprise applications
(e.g., Spring Boot) to pass **configuration values** like database
URLs, port numbers, or environment names at startup —
without hardcoding them into the source code:

```bash
java -jar app.jar --server.port=8080 --spring.profiles.active=prod
```

---

## 8. Key Rules & Common Mistakes

### Rules Summary

| Keyword | Key Rules |
|---------|-----------|
| `static` variable | One shared copy; stored in Method Area; access via class name |
| `static` method | No `this`/`super`; can only access static members directly |
| `static` block | Runs once on class load; before `main()` and object creation |
| `final` variable | Must be initialized once; cannot be reassigned after that |
| `final` method | Cannot be overridden in subclass |
| `final` class | Cannot be extended/subclassed |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Accessing instance variable from static method
class Demo {
    int x = 5;
    static void show() {
        System.out.println(x); // ❌ Compile error — x is not static
    }
}

// ❌ MISTAKE 2 — Using 'this' in static context
class Demo {
    static void show() {
        System.out.println(this); // ❌ Compile error — no 'this' in static
    }
}

// ❌ MISTAKE 3 — Reassigning a final variable
final int MAX = 100;
MAX = 200; // ❌ Compile error — cannot assign to final variable

// ❌ MISTAKE 4 — Assuming static block runs after main()
// Static block ALWAYS runs before main() — on class load

// ✅ CORRECT — Proper static method usage
class MathHelper {
    static int add(int a, int b) {
        return a + b; // only uses parameters — no instance state needed
    }
}
int result = MathHelper.add(3, 4); // 7
```

---

## 9. Quick Revision Cheatsheet

```
STATIC KEYWORD
──────────────────────────────────────────────────────────────
 static variable  → One copy shared by all objects (class-level)
                    Stored in Method Area, not heap
                    Access via: ClassName.variableName

 static method    → Called without creating an object
                    Access via: ClassName.methodName()
                    CANNOT use: this, super, instance variables,
                                instance methods

 static block     → Runs ONCE when class is first loaded by JVM
                    Runs BEFORE main() and before any object creation
                    Used for: one-time static initialization logic

FINAL KEYWORD
──────────────────────────────────────────────────────────────
 final variable   → Constant; cannot be reassigned after init
 final method     → Cannot be overridden in a subclass
 final class      → Cannot be extended (e.g., String is final)
 final + object   → Reference cannot change; fields still can

 static + final   → Class-level constant (e.g., PI, MAX_SIZE)
                    Convention: write in ALL_CAPS

MAIN METHOD
──────────────────────────────────────────────────────────────
 public static void main(String[] args)

 public  → JVM needs access from outside
 static  → JVM calls it WITHOUT creating an object
 void    → JVM expects no return value
 args    → String array of command-line arguments
           Empty array (not null) if no args are passed
           All args are Strings; parse if other type is needed
           Used in enterprise apps (Spring Boot) for config

EXECUTION ORDER ON CLASS LOAD
──────────────────────────────────────────────────────────────
 1. Static variables allocated in Method Area
 2. Static block(s) execute (in order of appearance)
 3. main() method begins execution
 4. Objects are created as needed during runtime
```

---

*End of Notes*