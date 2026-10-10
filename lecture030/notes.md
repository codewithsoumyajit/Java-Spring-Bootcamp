# Java Object Class — Comprehensive Notes
## toString | equals | hashCode | getClass | clone | Thread Methods

---

## Table of Contents
1. [What is the Object Class?](#1-what-is-the-object-class)
2. [Object Class Hierarchy](#2-object-class-hierarchy)
3. [toString() Method](#3-tostring-method)
4. [equals() Method](#4-equals-method)
5. [hashCode() Method](#5-hashcode-method)
6. [The equals-hashCode Contract](#6-the-equals-hashcode-contract)
7. [getClass() Method](#7-getclass-method)
8. [clone() Method and Cloneable Interface](#8-clone-method-and-cloneable-interface)
9. [finalize() — Deprecated](#9-finalize--deprecated)
10. [wait(), notify(), notifyAll()](#10-wait-notify-notifyall)
11. [Primitives and the Object Class](#11-primitives-and-the-object-class)
12. [Key Rules & Common Mistakes](#12-key-rules--common-mistakes)
13. [Quick Revision Cheatsheet](#13-quick-revision-cheatsheet)

---

## 1. What is the Object Class?

The **`Object` class** is the **root of the entire Java class
hierarchy**. It lives in the `java.lang` package (auto-imported).

Every class in Java — whether you write it or it comes from the JDK —
either directly or indirectly extends `Object`. This means every Java
object ever created has access to the methods defined in `Object`.

```java
// These two are IDENTICAL from the compiler's perspective:
class Student { }
class Student extends Object { } // compiler inserts this automatically
```

### Why Does Object Exist?

Without a common root class, Java would have no way to:
- Store any object in a generic container (`Object[]`, `List<Object>`)
- Provide universal methods every object must have (`toString`, `equals`)
- Give polymorphism a universal base type

---

## 2. Object Class Hierarchy

```
java.lang.Object  ← ROOT of ALL classes
       │
       ├── String
       ├── Integer (and all wrapper classes)
       ├── ArrayList
       ├── HashMap
       ├── Thread
       ├── Exception
       │       └── RuntimeException
       │               └── NullPointerException
       │               └── IllegalArgumentException
       └── YOUR CLASSES
               └── Student
               └── Animal
                       └── Dog
                       └── Cat
```

```java
// Proof — every object IS-A Object
Student s = new Student("Rohit", 101);
Object  o = s; // compiles perfectly — Student IS-A Object

// Object reference can hold ANY Java object
Object x1 = new Student("Rohit", 101);
Object x2 = "Hello World";       // String IS-A Object
Object x3 = new ArrayList<>();   // ArrayList IS-A Object
Object x4 = new int[]{1, 2, 3};  // Even arrays ARE Objects
```

### Methods Defined in Object

| Method | Purpose |
|--------|---------|
| `toString()` | String representation of object |
| `equals(Object o)` | Logical equality comparison |
| `hashCode()` | Integer hash for use in hash-based collections |
| `getClass()` | Returns runtime class metadata |
| `clone()` | Creates a copy of the object |
| `finalize()` | Called before GC (deprecated) |
| `wait()` | Thread coordination |
| `notify()` | Thread coordination |
| `notifyAll()` | Thread coordination |

---

## 3. toString() Method

### Default Behavior

The default `toString()` in Object returns:
```
ClassName@HexHashCode
```

```java
class Student {
    String name;
    int rollNo;

    Student(String name, int rollNo) {
        this.name   = name;
        this.rollNo = rollNo;
    }
}

Student s = new Student("Rohit", 101);
System.out.println(s);            // Student@6d06d69c
System.out.println(s.toString()); // Student@6d06d69c (same thing)
// Completely useless — just class name + memory address hash
```

"This is the actual source code in Object:
```java
public String toString() {
    return getClass().getName() + "@"
           + Integer.toHexString(hashCode());
}
```

### When is toString() Called Automatically?

Java calls `toString()` implicitly in several situations:

```java
Student s = new Student("Rohit", 101);

// 1. String concatenation
String msg = "Student: " + s; // calls s.toString()

// 2. System.out.println
System.out.println(s);         // calls s.toString()

// 3. String.valueOf()
String str = String.valueOf(s); // calls s.toString()

// 4. Inside StringBuilder
StringBuilder sb = new StringBuilder();
sb.append(s);                  // calls s.toString()
```

### Overriding toString() — The Right Way

```java
class Student {
    private String name;
    private int    rollNo;
    private double cgpa;

    Student(String name, int rollNo, double cgpa) {
        this.name   = name;
        this.rollNo = rollNo;
        this.cgpa   = cgpa;
    }

    @Override
    public String toString() {
        return "Student{name='" + name
             + "', rollNo=" + rollNo
             + ", cgpa=" + cgpa + "}";
    }
}

Student s = new Student("Rohit", 101, 9.2);
System.out.println(s); // Student{name='Rohit', rollNo=101, cgpa=9.2}
```

### Best Practices for toString()

- Always include fields that identify the object meaningfully
- Use `@Override` annotation — compiler verifies correct signature
- Do NOT print sensitive data (passwords, credit card numbers)
- Keep it concise but informative

---

## 4. equals() Method

### Default Behavior — Reference Comparison

The default `equals()` in Object compares **memory addresses**
(references) — not the content of the objects.

```java
class Student {
    String name;
    int rollNo;

    Student(String name, int rollNo) {
        this.name   = name;
        this.rollNo = rollNo;
    }
}

Student s1 = new Student("Rohit", 101);
Student s2 = new Student("Rohit", 101); // same data, different object

System.out.println(s1 == s2);           // false — different memory addresses
System.out.println(s1.equals(s2));      // false — default compares address!

// Default Object.equals() source:
// public boolean equals(Object obj) {
//     return (this == obj); // reference comparison!
// }
```

"Two students with identical data are NOT considered equal
by default. That is almost never what we want."

### Overriding equals() — The Contract

When you override `equals()`, you must satisfy the
**mathematical equivalence properties**:

| Property | Meaning |
|----------|---------|
| **Reflexive** | `x.equals(x)` must be `true` |
| **Symmetric** | If `x.equals(y)` then `y.equals(x)` |
| **Transitive** | If `x.equals(y)` and `y.equals(z)` then `x.equals(z)` |
| **Consistent** | Multiple calls return same result if unchanged |
| **Null-safe** | `x.equals(null)` must return `false` (never throw NPE) |

### Complete equals() Implementation

```java
class Student {
    private String name;
    private int    rollNo;
    private double cgpa;

    Student(String name, int rollNo, double cgpa) {
        this.name   = name;
        this.rollNo = rollNo;
        this.cgpa   = cgpa;
    }

    @Override
    public boolean equals(Object obj) {

        // Step 1 — Same reference? Definitely equal (optimization)
        if (this == obj) return true;

        // Step 2 — Is obj null? Cannot be equal to null
        if (obj == null) return false;

        // Step 3 — Same class? (or use instanceof for flexibility)
        if (this.getClass() != obj.getClass()) return false;
        // Alternative: if (!(obj instanceof Student)) return false;

        // Step 4 — Cast safely (we know it is Student now)
        Student other = (Student) obj;

        // Step 5 — Compare fields that define equality
        return this.rollNo == other.rollNo
            && Double.compare(this.cgpa, other.cgpa) == 0
            && (this.name == null
                    ? other.name == null
                    : this.name.equals(other.name));
    }
}

Student s1 = new Student("Rohit", 101, 9.2);
Student s2 = new Student("Rohit", 101, 9.2);
Student s3 = new Student("Priya", 102, 8.5);

System.out.println(s1.equals(s2)); // true  — same data ✅
System.out.println(s1.equals(s3)); // false — different data ✅
System.out.println(s1.equals(null)); // false — null safe ✅
```

### equals() with getClass() vs instanceof

```java
// getClass() — strict: only same class
if (this.getClass() != obj.getClass()) return false;
// Student.equals(GraduateStudent) → false
// Even if GraduateStudent extends Student with same fields

// instanceof — flexible: accepts subclasses
if (!(obj instanceof Student)) return false;
// Student.equals(GraduateStudent) → possible true
// BUT breaks symmetry if GraduateStudent also overrides equals()

// Recommendation: use getClass() for strict equality
//                use instanceof when subclass substitution is intended
```

---

## 5. hashCode() Method

### What is hashCode()?

`hashCode()` returns an **integer** that represents the object.
This integer is used by hash-based data structures
(`HashMap`, `HashSet`, `Hashtable`) to determine where to
store and how to find objects efficiently.

### Default Behavior

The default `hashCode()` in Object returns a number derived
from the object's **memory address**. Two different objects
— even with identical data — will have different hash codes
by default.

```java
Student s1 = new Student("Rohit", 101);
Student s2 = new Student("Rohit", 101);

System.out.println(s1.hashCode()); // e.g., 366712642
System.out.println(s2.hashCode()); // e.g., 1829164700
// Different! Even though data is identical
```

### How HashMap Uses hashCode()

Understanding hashCode requires understanding how HashMap works internally:

```
HashMap has an internal array of "buckets" (default 16 slots):
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │ 8 │ 9 │10 │11 │12 │13 │14 │15 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

When you call map.put(key, value):
Step 1: Compute key.hashCode()       → say 366712642
Step 2: bucket = hashCode % 16       → 366712642 % 16 = 2
Step 3: Store in bucket 2

When you call map.get(key):
Step 1: Compute key.hashCode()       → 366712642
Step 2: bucket = hashCode % 16       → 2
Step 3: Look in bucket 2
Step 4: Compare each entry using equals()
Step 5: Return matching value
```

### Overriding hashCode()

```java
class Student {
    private String name;
    private int    rollNo;
    private double cgpa;

    Student(String name, int rollNo, double cgpa) {
        this.name   = name;
        this.rollNo = rollNo;
        this.cgpa   = cgpa;
    }

    @Override
    public int hashCode() {
        // Method 1 — Manual: combine field hash codes
        int result = 17; // prime number starting point
        result = 31 * result + rollNo;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + Double.hashCode(cgpa);
        return result;

        // Method 2 — Using Objects utility (RECOMMENDED)
        // return Objects.hash(name, rollNo, cgpa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return rollNo == other.rollNo
            && Double.compare(cgpa, other.cgpa) == 0
            && Objects.equals(name, other.name);
    }
}
```

### The Problem Without Overriding hashCode()

```java
// Only equals() overridden, NOT hashCode()
class BrokenStudent {
    String name;
    int    rollNo;

    BrokenStudent(String name, int rollNo) {
        this.name   = name;
        this.rollNo = rollNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BrokenStudent)) return false;
        BrokenStudent other = (BrokenStudent) obj;
        return rollNo == other.rollNo
            && Objects.equals(name, other.name);
    }
    // hashCode() NOT overridden — uses default (memory address)
}

// THE BUG:
Map<BrokenStudent, String> map = new HashMap<>();
BrokenStudent s1 = new BrokenStudent("Rohit", 101);
map.put(s1, "Engineering");

BrokenStudent s2 = new BrokenStudent("Rohit", 101); // same data

System.out.println(s1.equals(s2)); // true — equals says same!
System.out.println(map.get(s2));   // null! HashMap cannot find it!

// WHY?
// s1.hashCode() → 366712642 (based on s1's memory address)
// s2.hashCode() → 1829164700 (based on s2's different memory address)
// HashMap looks in bucket for s2's hash → finds nothing → returns null
// equals() was never even called because wrong bucket!
```

---

## 6. The equals-hashCode Contract

This is one of the most important contracts in Java:

```
THE CONTRACT:
  If a.equals(b) == true
  THEN a.hashCode() MUST == b.hashCode()

  If a.hashCode() == b.hashCode()
  THEN a.equals(b) MAY or MAY NOT be true (hash collision allowed)

IN PLAIN ENGLISH:
  Equal objects MUST have equal hash codes
  Objects with equal hash codes are NOT necessarily equal
```

### Visual Representation

```
EQUAL objects → MUST have SAME hashCode
a.equals(b) = true → a.hashCode() == b.hashCode() (MANDATORY)

SAME hashCode ↛ must be equal (collision allowed)
a.hashCode() == b.hashCode() → a.equals(b) may be true OR false
```

### Always Override Both Together

```java
// RULE: If you override equals() — you MUST override hashCode()
//       If you override hashCode() — you SHOULD override equals()
// They must be consistent with each other

// Using java.util.Objects (cleanest approach):
import java.util.Objects;

class Product {
    private String productId;
    private String name;
    private double price;

    Product(String productId, String name, double price) {
        this.productId = productId;
        this.name      = name;
        this.price     = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return Objects.equals(productId, other.productId); // only ID defines equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId); // consistent with equals
    }
}
```

---

## 7. getClass() Method

### What Does getClass() Return?

`getClass()` returns the **runtime Class object** representing
the actual type of the object. It reflects the object's true
type — not the reference type.

```java
class Animal { }
class Dog extends Animal { }

Animal a = new Dog(); // reference is Animal, object is Dog

System.out.println(a.getClass());         // class Dog (actual runtime type)
System.out.println(a.getClass().getName());         // "Dog"
System.out.println(a.getClass().getSimpleName());   // "Dog"
System.out.println(a.getClass().getSuperclass());   // class Animal
System.out.println(a.getClass().getPackageName());  // package name
```

### getClass() vs instanceof

```java
Animal a = new Dog();

// getClass() — exact type check (strict)
System.out.println(a.getClass() == Dog.class);    // true
System.out.println(a.getClass() == Animal.class); // false — runtime type is Dog

// instanceof — type check including hierarchy
System.out.println(a instanceof Dog);    // true
System.out.println(a instanceof Animal); // true — Dog IS-A Animal
```

### Common Uses of getClass()

```java
// 1. In equals() — strict type check
if (this.getClass() != obj.getClass()) return false;

// 2. Logging / debugging
void process(Object obj) {
    System.out.println("Processing: " + obj.getClass().getSimpleName());
}

// 3. Reflection — getting methods, fields at runtime
Class<?> cls = obj.getClass();
Method[] methods = cls.getDeclaredMethods();
Field[]  fields  = cls.getDeclaredFields();
```

---

## 8. clone() Method and Cloneable Interface

### What is clone()?

`clone()` creates and returns a **copy** of the object.
The default implementation in `Object` does a **shallow copy** —
copying primitive values and references (not the objects they point to).

### The Cloneable Interface — Marker Interface

To use `clone()`, a class must implement the `Cloneable` interface.
`Cloneable` is a **marker interface** — it has NO methods.
It simply marks the class as "safe to clone."

```java
public interface Cloneable {
    // Empty — no methods
    // Just a signal to the JVM that clone() is permitted
}
```

If you call `clone()` on a class that does NOT implement
`Cloneable`, the JVM throws `CloneNotSupportedException`.

### Implementing clone() — Shallow Copy

```java
class Student implements Cloneable {
    String name;
    int    rollNo;

    Student(String name, int rollNo) {
        this.name   = name;
        this.rollNo = rollNo;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Object's default shallow copy
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Student s1 = new Student("Rohit", 101);
        Student s2 = (Student) s1.clone(); // cast needed — returns Object

        System.out.println(s1 == s2);          // false — different objects
        System.out.println(s1.name == s2.name); // true — same String reference
                                                // (shallow copy of String reference)
        s2.name   = "Priya";
        s2.rollNo = 102;

        System.out.println(s1.name   + " " + s1.rollNo); // Rohit 101 — unchanged
        System.out.println(s2.name   + " " + s2.rollNo); // Priya 102
    }
}
```

### Shallow Copy Problem with Mutable Fields

```java
class Address {
    String city;
    Address(String city) { this.city = city; }
}

class Student implements Cloneable {
    String  name;
    Address address; // mutable object field

    Student(String name, Address address) {
        this.name    = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // SHALLOW COPY — copies address reference only!
    }
}

Student s1 = new Student("Rohit", new Address("Delhi"));
Student s2 = (Student) s1.clone();

s2.address.city = "Mumbai"; // changes shared Address object!

System.out.println(s1.address.city); // Mumbai — s1 affected! 😱
// Shallow copy — both s1 and s2 share the same Address object
```

### Deep Clone — Fixing the Problem

```java
class Address implements Cloneable {
    String city;

    Address(String city) { this.city = city; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Address has only primitive-like fields
    }
}

class Student implements Cloneable {
    String  name;
    Address address;

    Student(String name, Address address) {
        this.name    = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student cloned = (Student) super.clone(); // shallow clone first
        cloned.address = (Address) address.clone(); // deep clone mutable field
        return cloned;
    }
}

Student s1 = new Student("Rohit", new Address("Delhi"));
Student s2 = (Student) s1.clone();

s2.address.city = "Mumbai"; // changes s2's OWN Address

System.out.println(s1.address.city); // Delhi — s1 unaffected ✅
System.out.println(s2.address.city); // Mumbai ✅
```

### clone() vs Copy Constructor

Many developers prefer a **copy constructor** over `clone()`:

```java
// Copy constructor — simpler and cleaner than clone()
class Student {
    String  name;
    int     rollNo;
    Address address;

    // Normal constructor
    Student(String name, int rollNo, Address address) {
        this.name    = name;
        this.rollNo  = rollNo;
        this.address = new Address(address.city); // deep copy
    }

    // Copy constructor
    Student(Student other) {
        this.name    = other.name;
        this.rollNo  = other.rollNo;
        this.address = new Address(other.address.city); // deep copy
    }
}

// Usage — no cast, no checked exception
Student s1 = new Student("Rohit", 101, new Address("Delhi"));
Student s2 = new Student(s1); // copy constructor — cleaner!
```

| Feature | clone() | Copy Constructor |
|---------|---------|-----------------|
| Interface needed | `Cloneable` (marker) | None |
| Exception | `CloneNotSupportedException` | None |
| Cast required | Yes (returns `Object`) | No |
| Deep copy control | Manual | Manual |
| Clarity | Low | High |
| Recommendation | Avoid when possible | Preferred |

---

## 9. finalize() — Deprecated

### What Was finalize()?

`finalize()` was a method called by the Garbage Collector
**before** destroying an object — giving it a chance to clean up
resources (close files, release connections).

```java
class ResourceHolder {
    @Override
    protected void finalize() throws Throwable {
        // cleanup code here
        System.out.println("Object being garbage collected");
        super.finalize();
    }
}
```

### Why Is It Deprecated?

`finalize()` was deprecated in **Java 9** and is marked for
removal. The reasons:

1. **Unpredictable timing** — no guarantee WHEN it will be called,
   or IF it will be called at all before JVM exits
2. **Performance impact** — objects with `finalize()` take two GC
   cycles to collect instead of one
3. **Security risks** — object can be "resurrected" in finalize()
4. **Better alternatives exist** — `try-with-resources` and
   `AutoCloseable` interface

### The Modern Alternative

```java
// DEPRECATED — do not use:
// protected void finalize() throws Throwable { }

// MODERN — use AutoCloseable and try-with-resources:
class DatabaseConnection implements AutoCloseable {
    DatabaseConnection() {
        System.out.println("Connection opened");
    }

    void query(String sql) {
        System.out.println("Executing: " + sql);
    }

    @Override
    public void close() {
        System.out.println("Connection closed"); // guaranteed to run
    }
}

// try-with-resources automatically calls close():
try (DatabaseConnection conn = new DatabaseConnection()) {
    conn.query("SELECT * FROM students");
} // close() called here automatically — even if exception occurs
```

---

## 10. wait(), notify(), notifyAll()

These three methods are defined in `Object` and are used for
**thread synchronization** — coordinating communication between threads.

```java
// All three are declared in Object:
public final void wait() throws InterruptedException { }
public final void notify() { }
public final void notifyAll() { }
```

### Brief Overview (Full coverage in Multithreading lectures)

| Method | Purpose |
|--------|---------|
| `wait()` | Causes current thread to wait until another thread calls `notify()` |
| `notify()` | Wakes up ONE thread that is waiting on this object |
| `notifyAll()` | Wakes up ALL threads waiting on this object |

### Why Are They in Object and Not Thread?

Every Java object can act as a **monitor** (lock) for thread
synchronization. Since any object can be a lock, these methods
must be in `Object` so every object has them.

```java
synchronized (someObject) {
    someObject.wait();    // current thread waits, releases lock on someObject
    someObject.notify();  // wakes one waiting thread
}
```

---

## 11. Primitives and the Object Class

### Primitives Are NOT Objects

Java's primitive types (`int`, `double`, `char`, `boolean`, etc.)
are **NOT objects** and do NOT inherit from `Object`.

```java
int x = 42;
// x.toString() → COMPILE ERROR — int is not an object
// x.equals(42) → COMPILE ERROR — int is not an object
// x.hashCode() → COMPILE ERROR — int is not an object
```

### Wrapper Classes — The Object Equivalent

Every primitive has a corresponding **wrapper class** that IS
an object and DOES inherit from `Object`:

| Primitive | Wrapper Class | Inherits from Object? |
|-----------|--------------|----------------------|
| `int` | `Integer` | ✅ Yes |
| `double` | `Double` | ✅ Yes |
| `char` | `Character` | ✅ Yes |
| `boolean` | `Boolean` | ✅ Yes |
| `long` | `Long` | ✅ Yes |
| `float` | `Float` | ✅ Yes |
| `byte` | `Byte` | ✅ Yes |
| `short` | `Short` | ✅ Yes |

```java
Integer x = 42; // wrapper — IS-A Object

x.toString();    // "42" ✅
x.equals(42);    // true ✅
x.hashCode();    // 42 ✅
x.getClass();    // class java.lang.Integer ✅

// Autoboxing: int → Integer (compiler inserts Integer.valueOf())
int primitive = 42;
Integer wrapper = primitive; // autoboxed
Object obj = primitive;      // autoboxed to Integer, then stored as Object
```

---

## 12. Key Rules & Common Mistakes

### Rules Summary

| Rule | Detail |
|------|--------|
| Always override both equals() and hashCode() | If you override one, you must override both |
| equals() + hashCode() must be consistent | Equal objects must have equal hash codes |
| toString() should be informative | Include meaningful fields, not memory addresses |
| clone() needs Cloneable | Without it, CloneNotSupportedException at runtime |
| finalize() is deprecated | Use AutoCloseable + try-with-resources instead |
| Primitives have no Object class | Their wrapper classes do |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Override equals() but not hashCode()
class Student {
    String name; int rollNo;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return rollNo == s.rollNo && name.equals(s.name);
    }
    // hashCode() NOT overridden → HashMap/HashSet will NOT find equal objects
}

// ❌ MISTAKE 2 — Use == for object comparison
String s1 = new String("Hello");
String s2 = new String("Hello");
if (s1 == s2) { } // false — compares references, not content

// ✅ CORRECT
if (s1.equals(s2)) { } // true — compares content

// ❌ MISTAKE 3 — equals() not null-safe
public boolean equals(Object obj) {
    Student other = (Student) obj; // NullPointerException if obj is null!
    return name.equals(other.name);
}

// ✅ CORRECT
public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof Student)) return false;
    Student other = (Student) obj;
    return Objects.equals(name, other.name) && rollNo == other.rollNo;
}

// ❌ MISTAKE 4 — Shallow clone when deep clone needed
@Override
protected Object clone() throws CloneNotSupportedException {
    return super.clone(); // copies List reference — not the List itself!
}

// ✅ CORRECT for mutable fields
protected Object clone() throws CloneNotSupportedException {
    Student cloned = (Student) super.clone();
    cloned.courses = new ArrayList<>(courses); // deep copy mutable field
    return cloned;
}

// ❌ MISTAKE 5 — Calling clone() without implementing Cloneable
class Broken { // no implements Cloneable
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // throws CloneNotSupportedException at runtime!
    }
}
```

---

## 13. Quick Revision Cheatsheet

```
OBJECT CLASS
──────────────────────────────────────────────────────────────
 Root of ALL Java classes (java.lang.Object)
 Every class implicitly extends Object
 Provides 9 fundamental methods every object inherits

toString()
──────────────────────────────────────────────────────────────
 Default: ClassName@HexHashCode (useless)
 Override: return meaningful string with key fields
 Auto-called: string concatenation, println, String.valueOf()
 Use @Override annotation always

equals()
──────────────────────────────────────────────────────────────
 Default: reference comparison (same as ==)
 Override: compare field values for logical equality
 Contract: reflexive, symmetric, transitive, consistent, null-safe
 Steps: 1) this==obj? 2) obj null? 3) same class? 4) cast 5) compare fields
 Use Objects.equals(a, b) for null-safe field comparison

hashCode()
──────────────────────────────────────────────────────────────
 Default: based on memory address
 Used by HashMap/HashSet to find bucket
 THE CONTRACT: equals true → hashCode must be equal
               same hashCode → equals may be true or false
 Override: return Objects.hash(field1, field2, ...)
 ALWAYS override when you override equals() — and vice versa

getClass()
──────────────────────────────────────────────────────────────
 Returns runtime Class object of actual object type
 Used in equals() for strict type checking
 getClass() == exact type | instanceof = includes subclasses

clone()
──────────────────────────────────────────────────────────────
 Must implement Cloneable (marker interface — no methods)
 Default: shallow copy (copies references, not objects)
 Without Cloneable: CloneNotSupportedException at runtime
 Deep copy: clone each mutable field manually
 Prefer copy constructors over clone() for clarity

finalize()
──────────────────────────────────────────────────────────────
 DEPRECATED — do not use
 Was called before GC — unpredictable, unreliable
 Modern alternative: AutoCloseable + try-with-resources

wait() / notify() / notifyAll()
──────────────────────────────────────────────────────────────
 Thread synchronization methods — covered in Multithreading
 In Object because any object can be a monitor (lock)

PRIMITIVES AND OBJECT
──────────────────────────────────────────────────────────────
 int, double, char, boolean etc. → NOT objects, no Object methods
 Integer, Double, Character, Boolean → wrapper classes → ARE objects
 Autoboxing: int ↔ Integer conversion done by compiler automatically
```

---

*End of Notes*
