# PART 5 — ADVANCED JVM CONCEPTS

# Advanced JVM Internals of `String`

Now we move into the **real JVM engineering side** of Java Strings.

This part explains:

* Why Strings are extremely optimized
* What JVM secretly does for performance
* Memory tricks used internally
* Garbage Collector optimizations
* Why String is treated specially by JVM

---

# 1. HashCode Caching

---

## Problem

Hashing a String repeatedly is expensive.

Example:

```java
"Soumyajit".hashCode()
```

JVM must calculate:

```text
S * 31^n + o * 31^(n-1) + ...
```

This requires looping through all characters.

If done repeatedly:

```java
map.get("username")
map.get("username")
map.get("username")
```

then performance becomes costly.

---

# JVM Optimization

Java caches the hashcode after first calculation.

Inside String class:

```java
private int hash;
```

Initially:

```text
hash = 0
```

When `hashCode()` is called:

```java
public int hashCode() {
    int h = hash;

    if(h == 0 && value.length > 0) {
        for(char c : value) {
            h = 31 * h + c;
        }
        hash = h;
    }

    return h;
}
```

---

# What Happens

## First Call

```java
String s = "Hello";

s.hashCode();
```

JVM calculates hash manually.

Stores inside:

```text
hash = 69609650
```

---

## Second Call

```java
s.hashCode();
```

No recalculation.

Directly returns cached value.

---

# Why This Works

Because String is immutable.

If String could change:

```java
s = "Hello"
```

→ later becomes

```java
s = "World"
```

cached hash would become invalid.

So:

✅ Immutable
✅ Safe caching
✅ Faster HashMap lookups

---

# Real Impact

Huge optimization for:

* HashMap
* HashSet
* Caching systems
* JSON parsing
* Spring Boot
* Database ORM
* REST APIs

---

# 2. Why String is final

---

# Declaration

```java
public final class String
```

---

# Why JVM Designers Made It final

---

## 1. Security

Imagine:

```java
class EvilString extends String {
}
```

Then someone overrides behavior:

```java
@Override
public int hashCode() {
    return 1;
}
```

This breaks:

* HashMap
* Security checks
* Class loading
* URL handling

Dangerous.

---

## 2. Immutability Guarantee

JVM assumes:

```text
String never changes
```

This allows:

* String Pool
* Hash caching
* Sharing objects
* JVM optimizations

Without `final`:

subclass may mutate internals.

Optimization becomes impossible.

---

## 3. Thread Safety

Immutable + final:

```text
No synchronization needed
```

Multiple threads safely share same String.

---

# Real JVM Dependence

Many JVM internals rely on String immutability:

* Class names
* File paths
* URLs
* Reflection
* SecurityManager
* Environment variables

If mutable:

entire JVM becomes unstable.

---

# 3. String Interning

---

# What is Interning?

Interning means:

```text
Store only ONE copy of identical strings
```

Example:

```java
String a = "Java";
String b = "Java";
```

Both reference same object.

---

# Memory View

```text
String Pool
-------------
"Java"
```

Both variables point there.

---

# Manual Interning

```java
String s1 = new String("Java");
String s2 = s1.intern();
```

---

# What Happens

## Step 1

Creates heap object:

```text
Heap:
new String("Java")
```

---

## Step 2

`intern()` checks String Pool.

If exists:

returns pooled reference.

Otherwise:

adds to pool.

---

# Example

```java
String a = new String("Hello");
String b = a.intern();

System.out.println(a == b);
```

May be:

```text
false
```

because:

* `a` → heap object
* `b` → pooled object

---

# Why JVM Uses Interning

Saves memory massively.

Imagine:

```text
"user"
```

appearing 1 million times.

Without interning:

1 million objects.

With interning:

1 shared object.

---

# Real Use Cases

* XML parsers
* JSON parsers
* Compilers
* JVM symbols
* Database column names

---

# Danger

Too much interning can increase:

* String pool pressure
* GC overhead
* lookup time

---

# 4. Native String Optimization

---

# JVM Treats String Specially

String is NOT an ordinary class.

JVM has:

```text
special bytecode optimizations
```

for String operations.

---

# Example

```java
String s = a + b;
```

Compiler converts:

```java
new StringBuilder()
    .append(a)
    .append(b)
    .toString();
```

Automatically.

---

# JVM Intrinsics

Some String methods are optimized directly in native code:

* equals()
* indexOf()
* compareTo()
* charAt()

JVM may replace Java code with CPU-level optimized instructions.

---

# Example

`String.equals()`

Can use:

* SIMD instructions
* Vectorization
* CPU memory comparison tricks

---

# Result

String operations become extremely fast.

---

# 5. Compact Strings (Java 9+)

---

# Before Java 9

String internally used:

```java
char[]
```

Each char:

```text
2 bytes
```

Even for simple ASCII:

```text
Hello
```

Memory:

```text
5 × 2 = 10 bytes
```

Wasteful.

---

# Java 9 Optimization

JVM introduced:

```java
byte[]
```

plus encoding flag.

Internally:

```java
private final byte[] value;
private final byte coder;
```

---

# Two Encodings

## LATIN1

For ASCII:

```text
1 byte per char
```

---

## UTF16

For Unicode:

```text
2 bytes per char
```

---

# Example

```java
String s = "Hello";
```

Uses:

```text
LATIN1
```

Memory reduced by 50%.

---

# Unicode Example

```java
String s = "こんにちは";
```

Uses UTF16.

---

# Huge Impact

Applications save:

* heap memory
* GC pressure
* cache usage

Especially:

* microservices
* web servers
* databases

---

# 6. String Compression

---

Compact Strings are effectively JVM-level string compression.

---

# Idea

Most real-world strings are ASCII.

Examples:

* JSON
* URLs
* HTTP headers
* SQL
* Logs

So JVM compresses them into single-byte representation.

---

# Result

Massive memory savings in enterprise applications.

---

# Example

Before Java 9:

```text
1 million ASCII strings
≈ 200MB
```

After Compact Strings:

```text
≈ 100MB
```

Huge improvement.

---

# 7. Escape Analysis

---

# What is Escape Analysis?

JVM checks:

```text
Does object escape current method?
```

If NOT:

JVM may avoid heap allocation completely.

---

# Example

```java
public String test() {
    StringBuilder sb = new StringBuilder();
    sb.append("Hello");
    return sb.toString();
}
```

Normally:

```text
StringBuilder object on heap
```

---

# JVM Optimization

If object never escapes:

JVM may:

* allocate on stack
* eliminate allocation entirely

Called:

```text
Scalar Replacement
```

---

# Impact

Temporary StringBuilder objects disappear.

Huge performance gain.

---

# Important

This is done by:

```text
JIT Compiler
```

during runtime.

NOT by javac compiler.

---

# 8. JVM String Deduplication

---

# Problem

Two different String objects may contain same content.

Example:

```java
new String("Java")
new String("Java")
```

Different objects.

Same characters.

---

# Deduplication

JVM can detect:

```text
same internal character arrays
```

and merge them.

---

# Result

Different String objects share same underlying data.

---

# Memory Benefit

Before:

```text
String A -> char[]
String B -> char[]
```

After dedup:

```text
String A -> shared char[]
String B -> shared char[]
```

---

# 9. G1GC String Deduplication

---

# Special Feature in G1 Garbage Collector

JVM can automatically deduplicate strings.

Enabled using:

```bash
-XX:+UseStringDeduplication
```

Works only with:

```text
G1GC
```

---

# JVM Process

GC scans heap.

Finds:

```text
equal character arrays
```

Merges them.

---

# Very Useful For

Applications with repeated text:

* JSON APIs
* XML systems
* Logging systems
* Big enterprise servers

---

# Cost

Deduplication itself requires CPU.

Tradeoff:

```text
More CPU
Less Memory
```

---

# 10. String Table

---

# What is String Table?

Internal JVM hash table storing interned strings.

---

# Memory Area

Modern JVM:

```text
Heap Memory
```

Older JVM:

```text
PermGen
```

(Java 6)

---

# Example

```java
String s = "Java";
```

Stored in String Table.

---

# Internal Working

JVM uses hash table:

```text
hash("Java") -> bucket
```

Fast lookup.

---

# Problem

If String Table becomes crowded:

* collisions increase
* intern() slows down

---

# Viewing Statistics

Use:

```bash
jcmd VM.stringtable
```

or

```bash
-XX:+PrintStringTableStatistics
```

---

# 11. StringTableSize Tuning

---

# JVM Parameter

```bash
-XX:StringTableSize=1000003
```

Controls:

```text
number of buckets in String Table
```

---

# Why Tune It?

Default size may be too small for:

* large enterprise apps
* compilers
* parsers
* huge caches

---

# Small Table Problem

```text
Too many collisions
```

Lookup becomes slower.

---

# Bigger Table

```text
Fewer collisions
Faster intern()
```

But:

```text
Uses more memory
```

---

# Real Production Tuning

High-scale applications sometimes tune:

```bash
-XX:StringTableSize
```

after profiling.

---

# FINAL SUMMARY

| Concept             | Purpose                      |
| ------------------- | ---------------------------- |
| HashCode Caching    | Avoid recalculating hash     |
| String final        | Security + optimization      |
| Interning           | Share identical strings      |
| Native Optimization | JVM intrinsic acceleration   |
| Compact Strings     | Reduce memory                |
| Compression         | ASCII optimization           |
| Escape Analysis     | Remove temporary allocations |
| Deduplication       | Share internal arrays        |
| G1GC Dedup          | GC-level string optimization |
| String Table        | Stores interned strings      |
| StringTableSize     | Tune lookup performance      |

---

# SUPER IMPORTANT INTERVIEW QUESTIONS

---

## Q1

Why does String cache hashcode?

---

## Q2

Why is String immutable and final?

---

## Q3

Difference between:

```java
new String("Java")
```

and

```java
"Java"
```

---

## Q4

What is String interning?

---

## Q5

What changed in Java 9 Compact Strings?

---

## Q6

What is String Deduplication?

---

## Q7

What is Escape Analysis?

---

## Q8

What is StringTableSize tuning?

---

# REAL ENGINEERING TAKEAWAY

Java String is one of the most optimized classes ever built in JVM.

It combines:

* compiler optimization
* JIT optimization
* GC optimization
* memory optimization
* CPU optimization
* runtime pooling
* deduplication
* intrinsic native acceleration

Almost every Java application performance heavily depends on String behavior.
