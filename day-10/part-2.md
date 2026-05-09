# PART 2 — MEMORY & JVM (Java Strings Deep Dive)

> Understanding Strings from JVM Internals Perspective
> Learn **how memory works**, **how JVM stores Strings**, and **what actually happens internally**

---

# Table of Contents

1. JVM Memory Architecture
2. Heap Memory
3. Stack Memory
4. Method Area
5. Runtime Constant Pool
6. String Pool Internal Working
7. Bytecode Understanding
8. What Happens During String Creation
9. String Object Layout
10. Reference Variables
11. Heap Object vs Pool Object
12. String Deduplication
13. Garbage Collection & Strings
14. Interview Deep Dive Questions

---

# 1. JVM Memory Architecture

Before understanding Strings deeply, we must understand:

> Where exactly Java stores data?

Java programs run inside JVM (Java Virtual Machine).

JVM divides memory into multiple parts.

---

# JVM Memory Diagram

```text
                JVM MEMORY
------------------------------------------------
|                                              |
|  Method Area / Metaspace                     |
|   - Class Metadata                           |
|   - Runtime Constant Pool                    |
|   - Static Variables                         |
|                                              |
------------------------------------------------
|                                              |
|                 HEAP                         |
|                                              |
|   Young Gen                                 |
|   Old Gen                                   |
|                                              |
|   Objects live here                          |
|   String Pool lives here (Java 7+)          |
|                                              |
------------------------------------------------
|                                              |
|                 STACK                        |
|                                              |
|   Method Calls                               |
|   Local Variables                            |
|   Reference Variables                        |
|                                              |
------------------------------------------------
```

---

# Core Understanding

| Memory Area           | Stores                        |
| --------------------- | ----------------------------- |
| Stack                 | References + method execution |
| Heap                  | Objects                       |
| Method Area           | Class info + constants        |
| Runtime Constant Pool | Class constants               |
| String Pool           | Shared String literals        |

---

# 2. Heap Memory

Heap stores:

* Objects
* Arrays
* Instance variables
* String objects

Example:

```java
String s = new String("Java");
```

Heap contains:

```text
Heap:
    Object -> "Java"
```

Heap is:

* Shared among threads
* Managed by Garbage Collector
* Bigger than stack

---

# Important

Every object in Java is created in Heap.

Including:

```java
new String()
new Student()
new ArrayList()
```

---

# Heap Generations

Modern JVM divides heap:

```text
Heap
 ├── Young Generation
 │     ├── Eden
 │     ├── Survivor S0
 │     └── Survivor S1
 │
 └── Old Generation
```

---

# Why?

Most objects die quickly.

Example:

```java
for(int i=0; i<1000; i++) {
    String s = "temp";
}
```

Temporary objects get removed quickly.

This improves performance.

---

# 3. Stack Memory

Stack stores:

* Method calls
* Local variables
* References

Example:

```java
String s = "Java";
```

`s` itself is NOT object.

`s` is only:

```text
Reference variable
```

Stored in stack.

Actual object stored in heap/pool.

---

# Visualization

```text
STACK                     HEAP
-----                     -----
s  ---------------------> "Java"
```

---

# Stack Properties

| Feature         | Stack |
| --------------- | ----- |
| Thread-safe     | Yes   |
| Fast access     | Yes   |
| Auto cleanup    | Yes   |
| Stores objects? | No    |

---

# 4. Method Area (Metaspace)

Stores:

* Class metadata
* Static variables
* Method info
* Runtime Constant Pool

Before Java 8:

```text
Permanent Generation (PermGen)
```

After Java 8:

```text
Metaspace
```

---

# Example

```java
class Test {
    static int x = 10;
}
```

`x` stored in Method Area.

---

# 5. Runtime Constant Pool

One of the MOST IMPORTANT concepts.

---

# What is Constant Pool?

When Java compiles code:

```java
String s = "Hello";
```

Compiler stores literals inside `.class` file.

At runtime JVM loads them into:

```text
Runtime Constant Pool
```

---

# Contains

* String literals
* Numeric constants
* Method references
* Class references

---

# Example

```java
String s1 = "Java";
String s2 = "Java";
```

Only ONE literal exists.

---

# Why?

Optimization.

Instead of creating:

```text
1000 "Java" objects
```

JVM reuses same object.

This saves:

* Memory
* GC time
* CPU

---

# 6. String Pool Internal Working

MOST IMPORTANT STRING TOPIC.

---

# What is String Pool?

A special memory area where JVM stores String literals.

Also called:

```text
String Constant Pool (SCP)
```

---

# Location

| Java Version | Location |
| ------------ | -------- |
| Java 6       | PermGen  |
| Java 7+      | Heap     |

---

# Example

```java
String s1 = "Java";
String s2 = "Java";
```

---

# Internal Working

Step 1:

JVM checks pool:

```text
Is "Java" already present?
```

If NO:

```text
Create object in pool
```

If YES:

```text
Reuse existing object
```

---

# Memory Visualization

```text
STACK                    STRING POOL
-----                    ------------
s1 --------------------> "Java"
s2 --------------------> ↑
```

Both references point to SAME object.

---

# Comparison

```java
System.out.println(s1 == s2);
```

Output:

```text
true
```

Because same reference.

---

# With new Keyword

```java
String s1 = "Java";
String s2 = new String("Java");
```

---

# Memory

```text
POOL:
   "Java"

HEAP:
   new String object

STACK:
   s1 -> pool object
   s2 -> heap object
```

---

# Comparison

```java
System.out.println(s1 == s2);
```

Output:

```text
false
```

Different objects.

---

# intern() Method

```java
String s = new String("Java");
String p = s.intern();
```

`intern()` returns pooled reference.

---

# Internal Mechanism

```text
If string exists in pool:
    return pooled reference

Else:
    add to pool
```

---

# 7. Bytecode Understanding

Java source code converts into:

```text
Bytecode
```

Stored in:

```text
.class file
```

---

# Example

```java
String s = "Java";
```

---

# Simplified Bytecode

```text
LDC "Java"
ASTORE 1
```

---

# Meaning

| Instruction | Meaning         |
| ----------- | --------------- |
| LDC         | Load constant   |
| ASTORE      | Store reference |

---

# Another Example

```java
String s = new String("Java");
```

Bytecode:

```text
NEW java/lang/String
DUP
LDC "Java"
INVOKESPECIAL
```

---

# Why Important?

Interviewers ask:

> Difference between literal and new String internally?

Bytecode reveals everything.

---

# 8. What Happens During String Creation

---

# Case 1 — Literal

```java
String s = "Java";
```

---

# Internal Steps

### Step 1

Compiler checks constant pool.

### Step 2

If `"Java"` absent:

```text
Create pooled object
```

### Step 3

Reference assigned.

---

# Final Memory

```text
STACK            STRING POOL
s -------------> "Java"
```

---

# Case 2 — new String()

```java
String s = new String("Java");
```

---

# Internal Steps

### Step 1

Check pool for `"Java"`

### Step 2

If absent → create in pool

### Step 3

Create NEW object in heap

### Step 4

Reference points to heap object

---

# Final Memory

```text
POOL:
   "Java"

HEAP:
   "Java"

STACK:
   s -> heap object
```

---

# Important Interview Point

```java
new String("Java")
```

Creates TWO objects potentially.

---

# 9. String Object Layout

A String internally is NOT just text.

---

# Old Java (Before Java 9)

Internally:

```java
char[] value
int hash
```

---

# Modern Java (Java 9+)

Uses:

```java
byte[] value
byte coder
int hash
```

---

# Why Changed?

Optimization.

Most strings contain ASCII characters.

ASCII needs:

```text
1 byte
```

Instead of:

```text
2-byte char
```

This feature called:

```text
Compact Strings
```

---

# Internal Structure

```text
String Object
-------------------
Object Header
byte[] value
byte coder
int hash
```

---

# Hash Caching

```java
String s = "Java";
s.hashCode();
```

Hash computed once and cached.

Why?

Strings heavily used in:

* HashMap
* HashSet
* Caching
* Databases

Caching improves performance.

---

# 10. Reference Variables

Critical concept.

---

# Example

```java
String s = "Java";
```

`s` is NOT object.

`s` is:

```text
Reference variable
```

It stores address/reference.

---

# Visualization

```text
STACK                 HEAP/POOL
s  -----------------> "Java"
```

---

# Multiple References

```java
String a = "Java";
String b = a;
```

---

# Memory

```text
a --------\
           --> "Java"
b --------/
```

Both point to same object.

---

# Changing Reference

```java
a = "Python";
```

Now:

```text
a --> "Python"
b --> "Java"
```

Because Strings immutable.

---

# 11. Heap Object vs Pool Object

---

# Pool Object

```java
String s = "Java";
```

Characteristics:

* Reusable
* Shared
* Memory optimized
* Stored in String Pool

---

# Heap Object

```java
new String("Java")
```

Characteristics:

* Separate object
* Unique memory
* Not automatically reused

---

# Comparison Table

| Feature | Pool Object | Heap Object |
|---|---|
| Reused | Yes | No |
| Memory efficient | Yes | No |
| Created using | Literal | new |
| Shared | Yes | No |

---

# 12. String Deduplication

Advanced JVM optimization.

---

# Problem

Imagine:

```java
new String("Java")
new String("Java")
new String("Java")
```

Multiple identical char arrays waste memory.

---

# Deduplication

G1 GC can optimize memory by sharing internal arrays.

---

# Example

Before:

```text
String1 -> char[] A
String2 -> char[] B
String3 -> char[] C
```

After dedup:

```text
String1 -> shared array
String2 -> shared array
String3 -> shared array
```

---

# JVM Option

```text
-XX:+UseStringDeduplication
```

Works with:

```text
G1 Garbage Collector
```

---

# Benefit

Huge memory saving in:

* Large enterprise apps
* JSON processing
* Logging systems
* Database-heavy apps

---

# 13. Garbage Collection & Strings

---

# Can Strings be Garbage Collected?

YES.

Common myth:

> Strings never die.

Wrong.

---

# Example

```java
String s = new String("Java");
s = null;
```

Now object eligible for GC.

---

# Important Exception

String literals inside pool may remain longer.

Because JVM keeps pooled strings for reuse.

---

# Weak References Example

```java
String s = new String("Java");
```

If no reference exists:

```text
Eligible for GC
```

---

# String Pool GC

Modern JVM CAN garbage collect unused pooled strings.

(Java 7+ improvements)

---

# Memory Leak Risk

Using:

```java
String.intern()
```

excessively may fill pool.

---

# 14. Interview Deep Dive Questions

---

# Q1 — Why String Pool Exists?

Answer:

* Memory optimization
* Reusability
* Faster comparison
* Reduced GC pressure

---

# Q2 — Why `==` true for literals?

Because both references point to same pooled object.

---

# Q3 — Why `new String()` bad sometimes?

Creates unnecessary heap objects.

More memory + GC overhead.

---

# Q4 — Where String Pool stored?

| Version | Location |
| ------- | -------- |
| Java 6  | PermGen  |
| Java 7+ | Heap     |

---

# Q5 — Why String immutable helps JVM memory?

Because shared pooled objects become safe.

If mutable:

```java
String s1 = "Java";
String s2 = "Java";

s1.modify();
```

Would corrupt shared data.

---

# Q6 — Difference Between Pool and Heap?

| Pool      | Heap                   |
| --------- | ---------------------- |
| Shared    | Non-shared             |
| Optimized | General object storage |
| Literals  | Objects                |

---

# Q7 — Does intern() create object?

Depends.

| Condition      | Result          |
| -------------- | --------------- |
| Exists in pool | Return existing |
| Not exists     | Add to pool     |

---

# Q8 — Why Strings use hash caching?

Improves HashMap performance dramatically.

---

# Q9 — Why Java 9 changed char[] to byte[]?

Memory optimization using Compact Strings.

---

# Q10 — Can pooled strings be garbage collected?

YES in modern JVMs.

---

# Final Mental Model

```text
String Literal
    ↓
Check Pool
    ↓
Exists? ---- YES → reuse
    ↓ NO
Create in Pool

new String()
    ↓
Always creates new heap object
```

---

# Golden Rule

```text
Literal  → reuse memory
new      → force new object
intern() → move/share in pool
```

---


