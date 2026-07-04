# Java Constructors — Comprehensive Notes

---

## Table of Contents
1. [Default Values in Java](#1-default-values-in-java)
2. [What are Constructors?](#2-what-are-constructors)
3. [Types of Constructors](#3-types-of-constructors)
4. [Constructor Overloading](#4-constructor-overloading)
5. [The `this` Keyword](#5-the-this-keyword)
6. [Constructor Chaining](#6-constructor-chaining)
7. [Key Rules & Common Mistakes](#7-key-rules--common-mistakes)
8. [Quick Revision Cheatsheet](#8-quick-revision-cheatsheet)

---

## 1. Default Values in Java

When an object is created in Java, any instance variable that is **not explicitly initialized**
is automatically assigned a **default value** by the JVM.

### Default Value Table

| Data Type  | Default Value |
|------------|---------------|
| `int`      | `0`           |
| `float`    | `0.0f`        |
| `double`   | `0.0`         |
| `boolean`  | `false`       |
| `char`     | `'\u0000'`    |
| `String`   | `null`        |
| Any Object | `null`        |

### Example

```java
class Customer {
    String name;   // → null  (default)
    int balance;   // → 0     (default)
    int age;       // → 0     (default)
}

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer();
        System.out.println(c1.name);    // null
        System.out.println(c1.balance); // 0
        System.out.println(c1.age);     // 0
    }
}
```

> **Key Point:** Default values only apply to **instance variables** (fields).
> Local variables inside methods do **NOT** get default values — they must be
> explicitly initialized before use, or the compiler throws an error.

---

## 2. What are Constructors?

A **constructor** is a special block of code that is automatically invoked
the moment an object is created using the `new` keyword.

### Characteristics of a Constructor

- Has the **same name as the class**
- Has **no return type** — not even `void`
- Is **automatically called** at the time of object creation
- Primary purpose is to **initialize** the object's fields/state

### Syntax

```java
class ClassName {
    ClassName() {         // Constructor
        // initialization code
    }
}
```

### Example

```java
class Customer {
    String name;
    int balance;

    Customer() {
        System.out.println("Object is being created!");
        name = "Unknown";
        balance = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer();
        // Output: Object is being created!
        // c1.name    → "Unknown"
        // c1.balance → 0
    }
}
```

### Constructor vs Method — Key Differences

| Feature       | Constructor                      | Method                      |
|---------------|----------------------------------|-----------------------------|
| Name          | Same as class name               | Any valid identifier        |
| Return type   | None (not even `void`)           | Must have a return type     |
| Invocation    | Automatically on object creation | Called explicitly           |
| Purpose       | Initialize object state          | Define object behavior      |
| Inheritance   | Not inherited                    | Inherited by subclasses     |

---

## 3. Types of Constructors

### 3.1 Default Constructor

- A **no-argument** constructor with no parameters
- If the programmer does **not** define any constructor, Java's compiler
  **automatically provides** one behind the scenes
- The auto-generated default constructor simply calls `super()` internally

```java
class Customer {
    String name;
    int balance;

    // Java silently auto-generates this if you define nothing:
    // Customer() { super(); }
}

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer(); // Uses auto-generated default constructor
    }
}
```

> **Critical Rule:** The moment you define **any** constructor explicitly,
> Java **stops** providing the automatic default constructor.
> If you still need a no-arg constructor alongside a parameterized one,
> you must write it yourself.

---

### 3.2 Parameterized Constructor

- Takes **one or more parameters**
- Allows **dynamic initialization** of object fields at the time of creation
- Must be explicitly defined by the programmer

```java
class Customer {
    String name;
    int balance;

    // Parameterized Constructor
    Customer(String name, int balance) {
        this.name    = name;
        this.balance = balance;
    }
}

public class Main {
    public static void main(String[] args) {
        Customer A1 = new Customer("Rohit", 1000);
        Customer A2 = new Customer("Mohit", 2000);

        System.out.println(A1.name + " : " + A1.balance); // Rohit : 1000
        System.out.println(A2.name + " : " + A2.balance); // Mohit : 2000
    }
}
```

---

## 4. Constructor Overloading

**Constructor Overloading** means defining **multiple constructors** in the
same class, each with a **different parameter list** (different number,
type, or order of parameters).

The compiler determines which constructor to call based on the
**arguments passed** at the time of object creation.

### Example

```java
class Customer {
    String name;
    int balance;
    String email;

    // Constructor 1 — no arguments
    Customer() {
        name    = "Guest";
        balance = 0;
        email   = "N/A";
    }

    // Constructor 2 — name and balance
    Customer(String name, int balance) {
        this.name    = name;
        this.balance = balance;
        this.email   = "N/A";
    }

    // Constructor 3 — name, balance, and email
    Customer(String name, int balance, String email) {
        this.name    = name;
        this.balance = balance;
        this.email   = email;
    }
}

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer();                          // Calls Constructor 1
        Customer c2 = new Customer("Rohit", 1000);            // Calls Constructor 2
        Customer c3 = new Customer("Mohit", 2000, "m@x.com"); // Calls Constructor 3
    }
}
```

### Why Use Constructor Overloading?

- Provides **flexibility** — objects can be created in multiple ways
- Avoids forcing the caller to always supply every field
- Makes the class more **user-friendly** and adaptable

---

## 5. The `this` Keyword

`this` is a **reference variable** that holds the address of the
**current object** — i.e., the object on which the method or
constructor was called.

### Problem Without `this`

When a constructor parameter has the **same name** as an instance variable,
a naming conflict arises. The local parameter shadows the field:

```java
class Customer {
    String name;
    int balance;

    Customer(String name, int balance) {
        name    = name;    // ❌ Assigns parameter to itself — field untouched
        balance = balance; // ❌ Same problem
    }
}
```

### Solution With `this`

```java
class Customer {
    String name;
    int balance;

    Customer(String name, int balance) {
        this.name    = name;    // ✅ this.name  → instance field
        this.balance = balance; // ✅ balance    → local parameter
    }
}
```

`this.name` explicitly refers to the **instance variable**, while
`name` (without `this`) refers to the **local parameter**.

### All Uses of `this`

| Usage            | Purpose                                                    |
|------------------|------------------------------------------------------------|
| `this.field`     | Resolve conflict between instance variable & local variable|
| `this.method()`  | Call another instance method of the same class             |
| `this()`         | Call another constructor of the same class (chaining)      |
| `return this`    | Return the current object (used in method chaining)        |

### Example — `this` for Field Resolution

```java
class Complex {
    int real;
    int imaginary;

    void setData(int real, int imaginary) {
        this.real      = real;      // instance var = parameter
        this.imaginary = imaginary;
    }

    void display() {
        System.out.println(this.real + " + " + this.imaginary + "i");
    }
}

public class Main {
    public static void main(String[] args) {
        Complex c = new Complex();
        c.setData(3, 4);
        c.display(); // 3 + 4i
    }
}
```

---

## 6. Constructor Chaining

**Constructor Chaining** is the technique of calling one constructor
from another constructor **within the same class** using `this()`.

### Why Use Constructor Chaining?

- Avoids **code duplication** across multiple constructors
- Centralizes initialization logic in one place
- Keeps the code **clean and maintainable**

### The Golden Rule

> `this()` must always be the **very first statement** inside the constructor.
> If it is not the first line, the code will **not compile**.

### Example

```java
class Customer {
    String name;
    int balance;
    String email;

    // Most complete constructor — does the actual work
    Customer(String name, int balance, String email) {
        this.name    = name;
        this.balance = balance;
        this.email   = email;
        System.out.println("Full constructor called");
    }

    // Chains to the 3-arg constructor with a default email
    Customer(String name, int balance) {
        this(name, balance, "not_provided@email.com"); // ✅ Must be first line
        System.out.println("2-arg constructor called");
    }

    // Chains to the 2-arg constructor with defaults
    Customer() {
        this("Guest", 0); // ✅ Must be first line
        System.out.println("No-arg constructor called");
    }
}

public class Main {
    public static void main(String[] args) {
        Customer c = new Customer();
        /*
         Output:
         Full constructor called
         2-arg constructor called
         No-arg constructor called
        */
        System.out.println(c.name);    // Guest
        System.out.println(c.balance); // 0
        System.out.println(c.email);   // not_provided@email.com
    }
}
```

### Execution Flow of Constructor Chaining

```
new Customer()
    └──► Customer()          → calls this("Guest", 0)
              └──► Customer(String, int)  → calls this("Guest", 0, "not_provided@email.com")
                        └──► Customer(String, int, String)  → executes field assignments
                   ← returns back to 2-arg constructor (prints)
         ← returns back to no-arg constructor (prints)
```

---

## 7. Key Rules & Common Mistakes

### Rules Summary

| Rule | Detail |
|------|--------|
| Same name as class | Constructor name must exactly match the class name |
| No return type | Not even `void` — adding one makes it a regular method |
| Auto-invoked | Called automatically with `new ClassName()` |
| Overloadable | You can have multiple constructors with different parameters |
| `this()` must be first | Constructor chaining call must be the first statement |
| No explicit default after custom | Defining any constructor removes the compiler-provided default |
| Cannot be `static` | Constructors belong to objects, not the class itself |
| Cannot be `abstract` or `final` | These modifiers are not applicable to constructors |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Return type makes it a method, not a constructor
class Customer {
    void Customer() { } // This is a METHOD named Customer, NOT a constructor
}

// ❌ MISTAKE 2 — this() is not the first statement
class Customer {
    Customer() {
        System.out.println("Hello"); // Something before this()
        this("Rohit", 1000);         // ❌ Compile error
    }
    Customer(String name, int bal) { }
}

// ❌ MISTAKE 3 — Circular constructor chaining (infinite loop → compile error)
class Customer {
    Customer() {
        this(0);     // calls Customer(int)
    }
    Customer(int x) {
        this();      // calls Customer() again → ❌ Compile error: recursive constructor
    }
}

// ✅ CORRECT — this() as first statement
class Customer {
    String name;
    Customer() {
        this("Default"); // ✅ First line
    }
    Customer(String name) {
        this.name = name;
    }
}
```

---

## 8. Quick Revision Cheatsheet

```
CONSTRUCTORS IN JAVA
─────────────────────────────────────────────────────────────
 Definition   → Special method to initialize objects
 Name         → Must match the class name exactly
 Return type  → NONE (not even void)
 Called by    → JVM automatically when 'new' is used

TYPES
─────────────────────────────────────────────────────────────
 Default       → No-arg; auto-provided if no constructor exists
 Parameterized → Takes arguments; programmer-defined
 Overloaded    → Multiple constructors with different signatures

THIS KEYWORD
─────────────────────────────────────────────────────────────
 this.field    → Refers to instance variable (resolves name clash)
 this.method() → Calls another method of the same object
 this()        → Calls another constructor (must be FIRST LINE)

CONSTRUCTOR CHAINING
─────────────────────────────────────────────────────────────
 → One constructor calls another using this()
 → this() MUST be the very first statement
 → Circular chaining is NOT allowed (compile error)
 → Avoids code duplication; centralizes initialization logic

DEFAULT VALUES (when fields are not initialized)
─────────────────────────────────────────────────────────────
 int / long / short / byte → 0
 float / double            → 0.0
 boolean                   → false
 char                      → '\u0000'
 String / Object           → null
```

---

*End of Notes*