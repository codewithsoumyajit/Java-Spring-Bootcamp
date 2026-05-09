# PART 4 — PERFORMANCE ENGINEERING OF STRINGS IN JAVA

---

# Why Performance Matters in Strings

Strings are everywhere in Java:

* User input
* API responses
* Logging
* Database queries
* JSON/XML generation
* File handling

A poorly written string operation can:

* Waste memory
* Slow down applications
* Increase garbage collection
* Create unnecessary objects

That’s why Java provides multiple optimizations internally.

---

# 1. String Concatenation Internals

## What is Concatenation?

Joining strings together.

```java
String s = "Hello" + " World";
```

or

```java
String s = a + b;
```

---

# What Actually Happens Internally?

Most beginners think:

```java
a + b
```

directly joins strings.

But internally Java converts it into:

```java
new StringBuilder()
    .append(a)
    .append(b)
    .toString();
```

---

# Example

```java
String name = "Soumyajit";
String msg = "Hello " + name;
```

Internally:

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello ");
sb.append(name);

String msg = sb.toString();
```

---

# Why Java Does This?

Because Strings are immutable.

This means:

```java
"a" + "b"
```

cannot modify `"a"`.

A new object must be created.

Using `StringBuilder` reduces unnecessary object creation.

---

# Deep Dive: Bytecode Level

Consider:

```java
String s = a + b + c;
```

The compiler converts it roughly into:

```java
new StringBuilder()
   .append(a)
   .append(b)
   .append(c)
   .toString();
```

This is done automatically by the compiler.

---

# Java 9+ Optimization

Before Java 9:

* Concatenation used explicit `StringBuilder`

After Java 9:

* Uses `invokedynamic`
* JVM decides best concatenation strategy dynamically

This improves:

* Performance
* Memory usage
* JIT optimization

---

# Important Interview Question

## Q: Why is `+` inside loops bad?

Because each iteration creates new objects.

Example:

```java
String s = "";

for(int i=0; i<10000; i++) {
    s = s + i;
}
```

This creates thousands of temporary strings.

Complexity becomes approximately:

```text
O(n²)
```

---

# Better Version

```java
StringBuilder sb = new StringBuilder();

for(int i=0; i<10000; i++) {
    sb.append(i);
}

String result = sb.toString();
```

Efficient:

* Less memory
* Faster
* Fewer objects

---

# 2. Compiler Optimizations

Java compiler performs many optimizations before execution.

---

# Main Optimizations

## 1. Constant Folding

## 2. Compile-time Concatenation

## 3. String Pool Reuse

## 4. Dead Code Elimination (JIT)

## 5. Escape Analysis (JVM)

---

# 3. Constant Folding

One of the most important optimizations.

---

# What is Constant Folding?

The compiler computes constant expressions during compilation instead of runtime.

---

# Example

```java
String s = "Hello" + " World";
```

Compiler converts it directly into:

```java
String s = "Hello World";
```

No concatenation happens at runtime.

---

# Another Example

```java
int x = 10 + 20 + 30;
```

Compiler stores:

```java
int x = 60;
```

---

# Why is This Important?

Reduces:

* CPU work
* Runtime operations
* Object creation

Improves:

* Startup speed
* Performance

---

# String Pool + Constant Folding

```java
String s1 = "Java" + "Programming";
String s2 = "JavaProgramming";

System.out.println(s1 == s2);
```

Output:

```text
true
```

Why?

Because compiler folded both into same pooled literal.

---

# But Runtime Values Are Different

```java
String a = "Java";

String s1 = a + "Programming";
String s2 = "JavaProgramming";

System.out.println(s1 == s2);
```

Output:

```text
false
```

Because:

* `a` is variable
* Runtime concatenation occurs
* New object created

---

# 4. StringBuilder Internals

`StringBuilder` is mutable.

---

# Internal Structure

Internally it contains:

```java
char[] value;
int count;
```

or in newer Java versions:

```java
byte[] value;
```

---

# How It Works

When you append:

```java
sb.append("Java");
```

characters are added into internal array.

No new object is created every time.

---

# Visualization

```text
Initial Capacity = 16

+---+---+---+---+---+
|   |   |   |   |   |
+---+---+---+---+---+
```

After append:

```text
J | a | v | a
```

Same array reused.

---

# Why Mutable is Faster

Immutable String:

```java
s = s + "A";
```

creates:

1. New StringBuilder
2. New internal array
3. New String

Again and again.

---

# StringBuilder:

```java
sb.append("A");
```

modifies same internal buffer.

Huge performance improvement.

---

# Important Methods

```java
append()
insert()
delete()
reverse()
replace()
capacity()
length()
```

---

# Example

```java
StringBuilder sb = new StringBuilder();

sb.append("Java");
sb.append(" Spring");

System.out.println(sb);
```

Output:

```text
Java Spring
```

---

# 5. StringBuffer Internals

`StringBuffer` is almost same as `StringBuilder`.

Difference:

```text
Thread Safety
```

---

# Internal Structure

Same mutable array approach.

But methods are synchronized.

Example:

```java
public synchronized StringBuffer append(String str)
```

---

# Why Synchronization Matters?

Only one thread can access method at a time.

Safe in multithreading.

But slower.

---

# Comparison

| Feature         | StringBuilder | StringBuffer |
| --------------- | ------------- | ------------ |
| Mutable         | Yes           | Yes          |
| Thread Safe     | No            | Yes          |
| Fast            | Yes           | Slower       |
| Synchronization | No            | Yes          |
| Introduced      | Java 5        | Java 1       |

---

# When to Use What?

| Situation              | Best Choice   |
| ---------------------- | ------------- |
| Single Thread          | StringBuilder |
| Multi Thread           | StringBuffer  |
| Fixed text             | String        |
| Frequent modifications | StringBuilder |

---

# 6. Why StringBuilder is Fast

Main reasons:

---

# 1. Mutable

No new object every modification.

---

# 2. Internal Array Reuse

Same memory reused.

---

# 3. No Synchronization

Unlike `StringBuffer`.

No locking overhead.

---

# 4. Reduced Garbage Collection

Fewer temporary objects.

---

# Example Benchmark Concept

## Using String

```java
String s = "";

for(int i=0; i<100000; i++) {
    s += i;
}
```

Creates huge garbage.

---

## Using StringBuilder

```java
StringBuilder sb = new StringBuilder();

for(int i=0; i<100000; i++) {
    sb.append(i);
}
```

Very efficient.

---

# Time Complexity

## String Concatenation in Loop

Approximately:

```text
O(n²)
```

---

## StringBuilder Append

Approximately:

```text
O(n)
```

---

# 7. Capacity Expansion Algorithm

This is a favorite interview topic.

---

# Default Capacity

```java
StringBuilder sb = new StringBuilder();
```

Default capacity:

```text
16
```

---

# What Happens When Capacity Exceeds?

Java creates larger array.

---

# Expansion Formula

```text
newCapacity = oldCapacity * 2 + 2
```

---

# Example

| Old Capacity | New Capacity |
| ------------ | ------------ |
| 16           | 34           |
| 34           | 70           |
| 70           | 142          |

---

# Example

```java
StringBuilder sb = new StringBuilder(5);

sb.append("HelloWorld");
```

Capacity grows automatically.

---

# Why This Strategy?

Avoids:

* Frequent resizing
* Frequent copying

Improves performance significantly.

---

# Internal Resize Process

When full:

1. New larger array created
2. Old data copied
3. Reference changed

---

# Deep Insight

Even `StringBuilder` resizing is expensive.

So if size is known:

```java
StringBuilder sb = new StringBuilder(1000);
```

preallocate capacity.

---

# 8. Performance Pitfalls

---

# Pitfall 1 — Using `+` in Loops

BAD:

```java
String s = "";

for(int i=0; i<10000; i++) {
    s += i;
}
```

---

# Pitfall 2 — Unnecessary `toString()`

BAD:

```java
StringBuilder sb = new StringBuilder();

String x = sb.toString();
```

inside loop repeatedly.

---

# Pitfall 3 — Using StringBuffer Unnecessarily

If application is single-threaded:

* synchronization overhead wastes time

Use `StringBuilder`.

---

# Pitfall 4 — Excessive Interning

```java
str.intern();
```

can:

* increase pool pressure
* increase memory usage

Use carefully.

---

# Pitfall 5 — Regex for Simple Operations

BAD:

```java
str.split(",");
```

for extremely performance-critical code.

Regex engine is expensive.

---

# Better

Manual parsing may be faster.

---

# Pitfall 6 — Repeated Immutable Modifications

BAD:

```java
str = str.replace(...);
str = str.trim();
str = str.toUpperCase();
```

Each creates new object.

---

# 9. String Pool Performance Benefits

The String Pool is a huge optimization.

---

# Main Benefits

## 1. Memory Saving

Without pool:

```java
String a = "Java";
String b = "Java";
```

Two objects would exist.

With pool:

* only one object reused.

---

# 2. Faster Comparison

Because references can be same.

```java
a == b
```

may return true for literals.

---

# 3. Reduced Garbage Collection

Fewer duplicate objects.

---

# 4. Better Cache Locality

Frequently reused strings stay in memory.

---

# Real World Example

Applications may use:

* `"GET"`
* `"POST"`
* `"OK"`
* `"ERROR"`

thousands of times.

Pool prevents duplication.

---

# Interning Performance Tradeoff

```java
str.intern();
```

adds string into pool.

Good:

* reduces duplicates

Bad:

* pool management overhead

---

# When Interning Helps

Good for:

* repeated fixed values
* parsers
* compilers
* protocol handling

---

# When It Hurts

Bad for:

* millions of unique strings
* random generated values

---

# Interview-Level Summary

| Concept               | Key Idea                      |
| --------------------- | ----------------------------- |
| String Concatenation  | Uses StringBuilder internally |
| Constant Folding      | Compile-time optimization     |
| StringBuilder         | Mutable and fast              |
| StringBuffer          | Thread-safe mutable string    |
| `+` in loops          | Very inefficient              |
| Capacity Expansion    | old * 2 + 2                   |
| String Pool           | Reuses literals               |
| Immutability          | Causes new object creation    |
| Best Performance Tool | StringBuilder                 |

---

# Golden Rule

## Use:

### `String`

When:

* data rarely changes

### `StringBuilder`

When:

* frequent modifications happen

### `StringBuffer`

When:

* thread safety is required

---

# Most Important Interview Question

## Q: Why is String immutable but StringBuilder mutable?

Because:

* String prioritizes security, pooling, hashing, thread safety
* StringBuilder prioritizes performance

Different tools for different purposes.
