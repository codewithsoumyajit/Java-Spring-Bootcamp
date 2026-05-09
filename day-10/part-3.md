# PART 3 — COMPARISON & METHODS

# 1. `==` vs `equals()`

This is one of the **MOST IMPORTANT** Java interview concepts.

Many beginners think both are same.

They are NOT.

---

# `==` → Reference Comparison

`==` checks:

> “Are both references pointing to the SAME object in memory?”

It does NOT compare actual text content.

---

## Example

```java
String a = "Java";
String b = "Java";

System.out.println(a == b);
```

Output:

```java
true
```

Why?

Because both point to the SAME String Pool object.

---

# Memory View

```text
String Pool
----------------
"Java"  ← a
          ← b
```

Same object → `true`

---

# Another Example

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
```

Output:

```java
false
```

Why?

Because `new` creates NEW objects in heap.

---

# Memory View

```text
Heap
------------
Object1 ("Java") ← a
Object2 ("Java") ← b
```

Different objects → `false`

---

# `equals()` → Content Comparison

`equals()` checks:

> “Do both Strings contain SAME characters?”

---

## Example

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a.equals(b));
```

Output:

```java
true
```

Because contents are same.

---

# Internal Working of `equals()`

Inside String class:

```java
public boolean equals(Object obj)
```

It compares:

* Length
* Each character

Character-by-character comparison.

---

# Real Comparison

```java
"JAVA"
"JAVA"
```

Java checks:

```text
J == J
A == A
V == V
A == A
```

All matched → `true`

---

# Important Interview Point

## Why String overrides `equals()`?

Because Object class default `equals()` compares references.

String needed content comparison.

So Java overrides it.

---

# Object Class Default

```java
obj1.equals(obj2)
```

Internally:

```java
return obj1 == obj2;
```

---

# String Class Override

String changed behavior to:

```java
compare actual characters
```

---

# Quick Difference Table

| Feature           | `==`      | `equals()`      |
| ----------------- | --------- | --------------- |
| Checks            | Reference | Content         |
| Memory address    | YES       | NO              |
| Characters        | NO        | YES             |
| Used for Strings? | Rarely    | Mostly          |
| Fast?             | Faster    | Slightly slower |

---

# Best Practice

✅ Use `equals()` for String comparison.

```java
if(name.equals("Soumyajit"))
```

NOT:

```java
if(name == "Soumyajit")
```

---

# Null-Safe Comparison

Danger:

```java
name.equals("Java")
```

If `name` is null:

```text
NullPointerException
```

Better:

```java
"Java".equals(name)
```

Why?

Literal is never null.

---

# 2. `equalsIgnoreCase()`

Used when case should NOT matter.

---

## Example

```java
String a = "JAVA";
String b = "java";

System.out.println(a.equalsIgnoreCase(b));
```

Output:

```java
true
```

---

# Internal Working

Java converts characters logically during comparison.

It ignores:

* uppercase
* lowercase differences

---

# Real-Life Usage

## Login systems

```java
email.equalsIgnoreCase(inputEmail)
```

Because:

```text
ABC@gmail.com
abc@gmail.com
```

Should usually be treated same.

---

# Difference

| Method             | Case Sensitive? |
| ------------------ | --------------- |
| equals()           | YES             |
| equalsIgnoreCase() | NO              |

---

# 3. `compareTo()`

Used for:

* Lexicographical comparison
* Sorting
* Dictionary order

---

# Syntax

```java
str1.compareTo(str2)
```

Returns:

| Return Value | Meaning     |
| ------------ | ----------- |
| `0`          | Same        |
| Positive     | str1 > str2 |
| Negative     | str1 < str2 |

---

# Example

```java
System.out.println("Apple".compareTo("Banana"));
```

Output:

```java
negative value
```

Because:

```text
A comes before B
```

---

# Example

```java
System.out.println("Cat".compareTo("Bat"));
```

Output:

```java
positive value
```

---

# Internal Working

Java compares Unicode values character-by-character.

---

# Unicode Comparison

```text
A = 65
B = 66
```

So:

```java
"A".compareTo("B")
```

Becomes:

```text
65 - 66 = -1
```

---

# Important

```java
"abc".compareTo("ABC")
```

NOT equal.

Because Unicode differs.

---

# Case-Insensitive Version

```java
compareToIgnoreCase()
```

---

# Used In

* Sorting arrays
* TreeSet
* TreeMap
* Dictionary ordering
* Searching

---

# 4. Common String Methods

---

# `length()`

Returns number of characters.

```java
String s = "Java";

System.out.println(s.length());
```

Output:

```java
4
```

---

# Internal Working

Actually returns:

```java
value.length
```

from internal char/byte array.

O(1) operation.

Very fast.

---

# `charAt()`

Gets character at index.

```java
System.out.println("Java".charAt(2));
```

Output:

```java
v
```

---

# Internal Working

Accesses array index internally.

```text
J a v a
0 1 2 3
```

---

# `substring()`

Extracts part of String.

---

## Example

```java
String s = "Programming";

System.out.println(s.substring(3));
```

Output:

```java
gramming
```

---

## Example

```java
System.out.println(s.substring(0, 6));
```

Output:

```java
Progra
```

---

# Rule

```java
substring(start, end)
```

* start included
* end excluded

---

# Internal Working

Modern Java creates NEW String object.

Older Java shared memory.

(Java 7 update changed this behavior.)

---

# `contains()`

Checks existence.

```java
"Java Programming".contains("Program")
```

Output:

```java
true
```

---

# `startsWith()`

```java
"Java".startsWith("Ja")
```

---

# `endsWith()`

```java
"Java".endsWith("va")
```

---

# `indexOf()`

Returns first occurrence index.

```java
"banana".indexOf('a')
```

Output:

```java
1
```

---

# `lastIndexOf()`

Searches from end.

```java
"banana".lastIndexOf('a')
```

Output:

```java
5
```

---

# `replace()`

```java
String s = "Java";

System.out.println(s.replace('a', 'o'));
```

Output:

```java
Jovo
```

---

# IMPORTANT

String is immutable.

Original remains unchanged.

---

# `toUpperCase()`

```java
"java".toUpperCase()
```

→ `JAVA`

---

# `toLowerCase()`

```java
"JAVA".toLowerCase()
```

→ `java`

---

# `trim()`

Removes leading/trailing spaces.

```java
"  Java  ".trim()
```

---

# Java 11 Extra

```java
strip()
```

Unicode-aware trimming.

Better than `trim()`.

---

# `split()`

Splits String into array.

```java
String data = "A,B,C";

String[] arr = data.split(",");
```

---

# Memory View

```text
arr[0] → "A"
arr[1] → "B"
arr[2] → "C"
```

---

# Regex Support

```java
split("\\s+")
```

Splits multiple spaces.

---

# `isEmpty()`

Checks length == 0.

```java
"".isEmpty()
```

---

# `isBlank()` (Java 11)

Checks whitespace-only Strings.

```java
"   ".isBlank()
```

Returns:

```java
true
```

---

# Difference

| Method    | Spaces Allowed? |
| --------- | --------------- |
| isEmpty() | NO              |
| isBlank() | YES             |

---

# 5. Method Chaining

Calling multiple methods continuously.

---

# Example

```java
String result =
    " java programming "
        .trim()
        .toUpperCase()
        .replace("PROGRAMMING", "JAVA");
```

Output:

```java
JAVA JAVA
```

---

# Why It Works

Because String methods return NEW String objects.

---

# Flow

```text
Original
   ↓
trim()
   ↓
NEW String
   ↓
toUpperCase()
   ↓
NEW String
   ↓
replace()
```

---

# Internal Concept

Immutability enables safe chaining.

---

# Advantage

* Cleaner code
* Readable
* Functional style

---

# Disadvantage

Too much chaining creates many temporary objects.

---

# 6. String Formatting

Used for readable output.

---

# `printf()`

```java
String name = "Soumyajit";
int age = 22;

System.out.printf("Name: %s Age: %d", name, age);
```

---

# Format Specifiers

| Specifier | Meaning        |
| --------- | -------------- |
| `%s`      | String         |
| `%d`      | Integer        |
| `%f`      | Floating point |
| `%c`      | Character      |
| `%b`      | Boolean        |

---

# Floating Precision

```java
System.out.printf("%.2f", 12.4567);
```

Output:

```java
12.46
```

---

# `String.format()`

Returns formatted String.

```java
String msg =
    String.format("Hello %s", "Java");
```

---

# Why Useful

* Logs
* Reports
* Invoice systems
* Tables
* Console formatting

---

# 7. Escape Sequences

Special characters inside String literals.

---

# Common Escapes

| Escape | Meaning      |
| ------ | ------------ |
| `\n`   | New line     |
| `\t`   | Tab          |
| `\"`   | Double quote |
| `\\`   | Backslash    |
| `\'`   | Single quote |

---

# Example

```java
System.out.println("Hello\nJava");
```

Output:

```text
Hello
Java
```

---

# Tab Example

```java
System.out.println("A\tB");
```

Output:

```text
A    B
```

---

# Quote Example

```java
System.out.println("\"Java\"");
```

Output:

```text
"Java"
```

---

# Why Needed?

Without escapes:

```java
"This breaks"
```

Compiler gets confused.

Escape tells compiler:

```text
This character is special
```

---

# 8. Unicode & UTF

Java uses Unicode internally.

---

# What is Unicode?

A universal character standard.

Every character has unique number.

---

# Example

| Character | Unicode |
| --------- | ------- |
| A         | 65      |
| ₹         | 8377    |
| 中         | 20013   |

---

# Why Unicode?

Old systems only supported English.

Unicode supports:

* Hindi
* Bengali
* Chinese
* Arabic
* Emojis
* All languages

---

# Java Character Encoding

Historically:

```text
UTF-16
```

internally for Strings.

Modern Java may optimize storage using Compact Strings.

---

# UTF Meaning

UTF = Unicode Transformation Format

---

# Types

| Type   | Size         |
| ------ | ------------ |
| UTF-8  | Variable     |
| UTF-16 | 2 or 4 bytes |
| UTF-32 | 4 bytes      |

---

# UTF-8

Most popular.

Advantages:

* Space efficient
* Compatible with ASCII
* Internet standard

---

# Java Unicode Example

```java
String rupee = "₹";
String bengali = "বাংলা";

System.out.println(rupee);
System.out.println(bengali);
```

---

# Unicode Escape

Java supports:

```java
\uXXXX
```

---

# Example

```java
System.out.println("\u0041");
```

Output:

```java
A
```

Because:

```text
0041 = Unicode for A
```

---

# Emoji Example

```java
String emoji = "😀";
```

Internally may use surrogate pairs in UTF-16.

---

# Interview Deep Dive

## Why String comparison can be tricky with Unicode?

Because:

* Some characters use multiple bytes
* Some emojis use surrogate pairs
* Visual characters may differ internally

---

# Example

```text
é
```

Can exist as:

* Single Unicode character
  OR
* e + accent combination

---

# Java Solution

Unicode normalization APIs.

---

# Final Interview Summary

| Concept          | Key Point                  |
| ---------------- | -------------------------- |
| `==`             | Reference comparison       |
| `equals()`       | Content comparison         |
| `compareTo()`    | Lexicographical comparison |
| String methods   | Return new Strings         |
| Chaining         | Works due to immutability  |
| Formatting       | Readable output            |
| Escape sequences | Special characters         |
| Unicode          | Global text support        |
