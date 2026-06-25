# PART 1 — FOUNDATION OF JAVA STRINGS

> Student-Friendly + Deep Dive + Interview-Oriented
> Learn **What, Why, How, When** behind Java Strings

---

# Table of Contents

1. What is String?
2. Why Java Treats String Specially
3. String as an Object
4. Java Memory Basics
5. Heap vs Stack
6. String Constant Pool (SCP)
7. Ways to Create Strings
8. String Immutability
9. Why String is Immutable
10. String Internal Structure

---

# 1. What is String?

## Definition

A **String** in Java is a sequence of characters.

Example:

```java
String name = "Soumyajit";
```

Here:

* `Soumyajit` = sequence of characters
* Stored as a `String` object internally

---

# Real-Life Analogy

Think of a String like a **word written on paper**.

```text
"Java"
```

contains:

```text
J → a → v → a
```

---

# Important Point

In Java:

```java
String
```

is **NOT a primitive datatype**.

It is a **class**.

That means:

```java
String city = "Kolkata";
```

actually creates an object.

---

# Primitive vs String

| Primitive | String |
| --------- | ------ |
| int       | Object |
| double    | Object |
| char      | Object |
| boolean   | Object |

---

# Then Why Does String Feel Like Primitive?

Because Java gives String **special support**.

You can directly write:

```java
String s = "Hello";
```

instead of:

```java
String s = new String("Hello");
```

This is called:

# String Literal Support

---

# 2. Why Java Treats String Specially

Java treats String specially because strings are used **everywhere**.

---

# Imagine Programming Without String

Without String:

* No user input text
* No passwords
* No file names
* No URLs
* No SQL queries
* No JSON/XML
* No logs
* No APIs

Almost impossible.

---

# Why Java Optimized String

Java designers realized:

```text
Strings are used VERY frequently
```

So they added:

| Feature        | Purpose     |
| -------------- | ----------- |
| String Pool    | Save memory |
| Immutability   | Security    |
| Literal Syntax | Easy coding |
| Optimizations  | Performance |

---

# Example

```java
String a = "Java";
String b = "Java";
```

Without optimization:

* Two separate objects

With String Pool:

* Both point to SAME object

Memory saved.

---

# Special Features Given to String

| Feature              | Why              |
| -------------------- | ---------------- |
| Immutable            | Safe & secure    |
| Pooling              | Memory efficient |
| Hash caching         | Faster           |
| Thread-safe behavior | Stability        |
| Literal syntax       | Easy to use      |

---

# 3. String as Object

Many beginners think:

```java
String s = "Hello";
```

is not an object.

But internally:

```java
String s = new String("Hello");
```

Everything in Java except primitives is an object.

---

# Proof

You can call methods on String:

```java
String s = "Java";

System.out.println(s.length());
System.out.println(s.toUpperCase());
```

Methods belong to objects.

So String is definitely an object.

---

# Internal Reality

```java
String s = "Hello";
```

actually creates:

```text
Reference Variable → String Object
```

Diagram:

```text
s -----> "Hello"
```

---

# String Object Has:

| Contains              | Example         |
| --------------------- | --------------- |
| Data                  | Hello           |
| Methods               | length()        |
| Metadata              | hash            |
| Internal char storage | character array |

---

# Important Concept

```java
String s;
```

* Variable itself is NOT object
* It stores REFERENCE (address)

Like:

```text
s = remote control
object = TV
```

---

# 4. Java Memory Basics

Before String memory, students must understand memory areas.

---

# Main Memory Areas in Java

| Memory Area | Purpose                        |
| ----------- | ------------------------------ |
| Stack       | Method calls & local variables |
| Heap        | Objects                        |
| Method Area | Class metadata                 |
| String Pool | Special String storage         |

---

# Very Simplified JVM Memory

```text
JVM MEMORY
│
├── Stack Memory
│
├── Heap Memory
│
├── Method Area
│
└── String Pool
```

---

# Rule to Remember

## Objects ALWAYS go into Heap

Example:

```java
new String("Java")
```

creates object in Heap.

---

# Stack Stores

* Method execution
* Local variables
* References

Example:

```java
String s = "Java";
```

`S` goes into Stack.

Object goes into Heap/Pool.

---

# 5. Heap vs Stack

This is the MOST IMPORTANT foundation.

---

# Stack Memory

## Characteristics

* Fast
* Small
* Thread-specific
* Stores references
* Method execution memory

Example:

```java
String s = "Java";
```

Stack contains:

```text
s → reference/address
```

---

# Heap Memory

## Characteristics

* Large
* Shared memory
* Stores objects
* Slower than stack

Actual String object stored here.

---

# Visualization

```java
String s = "Java";
```

Memory:

```text
STACK                HEAP / STRING POOL
-----                ------------------
s  ---------------->   "Java"
```

---

# Another Example

```java
String a = "Hello";
String b = "Hello";
```

Memory:

```text
STACK
-----
a --------\
           \
            ---> "Hello"
           /
b --------/
```

Both point to SAME object.

---

# Why?

Because of:

# String Constant Pool

---

# 6. String Constant Pool (SCP)

## Definition

A special memory area inside Heap where Java stores String literals.

---

# Why SCP Exists

Strings are reused heavily.

Instead of creating duplicate objects:

```java
String a = "Java";
String b = "Java";
```

Java reuses same object.

---

# Memory Without SCP

```text
a → "Java"
b → "Java"
```

Two objects.

Waste of memory.

---

# Memory With SCP

```text
a ----\
       \
        ---> "Java"
       /
b ----/
```

Only ONE object.

---

# Important Rule

## String literals go into SCP

```java
String s = "Hello";
```

Stored in SCP.

---

# How JVM Checks SCP

When JVM sees:

```java
"Hello"
```

It checks:

```text
Already exists?
```

* YES → reuse
* NO → create new

---

# SCP Interview Question

```java
String a = "Java";
String b = "Java";

System.out.println(a == b);
```

Output:

```java
true
```

Because both references point to SAME object.

---

# But:

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
```

Output:

```java
false
```

Because `new` creates separate objects.

---

# Key Rule

| Creation Type | Memory |
| ------------- | ------ |
| Literal       | SCP    |
| new String()  | Heap   |

---

# 7. Ways to Create Strings

There are multiple ways.

---

# Method 1 — String Literal

```java
String s = "Java";
```

Most common.

Stored in SCP.

Efficient.

---

# Method 2 — Using new Keyword

```java
String s = new String("Java");
```

Creates:

1. SCP object (if not exists)
2. Separate Heap object

---

# Visualization

```text
SCP:
"Java"

Heap:
new String("Java")
```

---

# Why Use new String() Then?

Rarely needed.

Mostly for:

* Interview questions
* Explicit object creation
* Testing memory behavior

---

# Method 3 — Character Array

```java
char[] arr = {'H','e','l','l','o'};

String s = new String(arr);
```

---

# Method 4 — Byte Array

```java
byte[] arr = {65,66,67};

String s = new String(arr);

System.out.println(s);
```

Output:

```text
ABC
```

---

# Method 5 — StringBuilder/StringBuffer

```java
StringBuilder sb = new StringBuilder("Java");

String s = sb.toString();
```

---

# 8. String Immutability

## Definition

Immutable means:

# Cannot be changed after creation

---

# Example

```java
String s = "Java";

s.concat(" Programming");

System.out.println(s);
```

Output:

```text
Java
```

Students get shocked here.

---

# Why?

Because:

```java
concat()
```

does NOT modify existing object.

It creates NEW object.

---

# Correct Way

```java
s = s.concat(" Programming");
```

Now:

```text
Java Programming
```

---

# Visualization

```java
String s = "Java";

s = s.concat(" Programming");
```

Memory:

```text
OLD OBJECT:
"Java"

NEW OBJECT:
"Java Programming"
```

Reference updated.

---

# Important Rule

## Every modification creates NEW String object

Methods like:

* concat()
* replace()
* substring()
* toUpperCase()

all create new objects.

---

# 9. Why String is Immutable

This is one of the MOST IMPORTANT interview questions.

---

# Reason 1 — Security

Strings are used in:

* Passwords
* URLs
* Database connections
* File paths
* Network sockets

Imagine mutable strings:

```java
"admin"
```

could suddenly become:

```java
"hacker"
```

Dangerous.

---

# Reason 2 — String Pool Safety

If String were mutable:

```java
String a = "Java";
String b = "Java";
```

If `a` changes:

```java
a = "Python";
```

then `b` would also get affected.

Huge problem.

Immutability prevents this.

---

# Reason 3 — Thread Safety

Immutable objects are naturally thread-safe.

Multiple threads can safely use same String.

---

# Reason 4 — Hashcode Caching

Strings are heavily used as keys in:

```java
HashMap
```

Example:

```java
Map<String, Integer>
```

If String changed after hashing:

Map structure breaks.

---

# Reason 5 — Performance

Since immutable:

* JVM safely reuses objects
* Caches hashcodes
* Optimizes memory

---

# Interview Answer

## Why String is Immutable?

```text
Security
Thread Safety
String Pool Integrity
Hashcode Caching
Performance Optimization
```

---

# 10. String Internal Structure

Now the deep dive begins.

---

# Inside String Class

Simplified version:

```java
public final class String {

    private final char[] value;

}
```

---

# Important Keywords

| Keyword     | Meaning                 |
| ----------- | ----------------------- |
| final class | Cannot be inherited     |
| final array | Reference cannot change |
| private     | Hidden from outside     |

---

# Internal Storage

Earlier Java versions used:

```java
char[]
```

to store characters.

Example:

```java
"Java"
```

stored as:

```text
['J','a','v','a']
```

---

# Modern Java Optimization (Java 9+)

Java introduced:

# Compact Strings

Instead of always `char[]`, JVM may use:

```java
byte[]
```

to reduce memory.

---

# Why?

`char` uses:

```text
2 bytes
```

Many strings only need ASCII.

So Java optimized memory.

Huge improvement.

---

# Internal Concept

String contains:

| Field | Purpose           |
| ----- | ----------------- |
| value | actual characters |
| hash  | cached hashcode   |
| coder | encoding type     |

---

# Why final?

```java
private final char[] value;
```

ensures:

# Data cannot be modified

Core reason for immutability.

---

# Final Visualization

```java
String s = "Java";
```

Internally:

```text
String Object
│
├── value → ['J','a','v','a']
├── hash  → cached hashcode
└── methods
```

---

# Quick Revision

| Concept          | Key Idea               |
| ---------------- | ---------------------- |
| String           | Sequence of characters |
| String is class  | Object                 |
| Stack            | References             |
| Heap             | Objects                |
| SCP              | Shared String literals |
| Immutable        | Cannot change          |
| Literal          | Uses SCP               |
| new String()     | Creates new object     |
| Internal storage | char[] / byte[]        |

---

# Common Beginner Mistakes

| Mistake                  | Reality |
| ------------------------ | ------- |
| String is primitive      | No      |
| concat changes original  | No      |
| == compares content      | No      |
| new String uses SCP only | No      |
| Stack stores objects     | No      |

---

# Important Interview Questions

1. Why String is immutable?
2. Difference between Heap and SCP?
3. Why `==` behaves differently?
4. Why String is final?
5. Why String is heavily optimized?
6. Difference between literal and `new String()`?
7. How String internally stores data?
8. Why String Pool exists?

---

