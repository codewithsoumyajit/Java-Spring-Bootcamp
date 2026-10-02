# Java I/O Architecture — Comprehensive Notes
## Standard I/O | Streams | Reader Classes | BufferedReader | Scanner

---

## Table of Contents
1. [What is I/O?](#1-what-is-io)
2. [Types of I/O in Java](#2-types-of-io-in-java)
3. [Console I/O — The Big Picture](#3-console-io--the-big-picture)
4. [Understanding System.out.println()](#4-understanding-systemoutprintln)
5. [The System Class](#5-the-system-class)
6. [System.err — Error Stream](#6-systemerr--error-stream)
7. [Streams of Data](#7-streams-of-data)
8. [InputStream & OutputStream Hierarchy](#8-inputstream--outputstream-hierarchy)
9. [Taking Input via System.in.read()](#9-taking-input-via-systemin-read)
10. [Reader Class & Why It Exists](#10-reader-class--why-it-exists)
11. [BufferedReader — Performance Optimization](#11-bufferedreader--performance-optimization)
12. [InputStreamReader — The Bridge](#12-inputstreamreader--the-bridge)
13. [Taking Input Using BufferedReader](#13-taking-input-using-bufferedreader)
14. [Limitations of BufferedReader](#14-limitations-of-bufferedreader)
15. [Scanner Class](#15-scanner-class)
16. [Scanner vs BufferedReader — Complete Comparison](#16-scanner-vs-bufferedreader--complete-comparison)
17. [Key Rules & Common Mistakes](#17-key-rules--common-mistakes)
18. [Quick Revision Cheatsheet](#18-quick-revision-cheatsheet)

---

## 1. What is I/O?

**I/O** stands for **Input/Output** — the mechanism by which
a program communicates with the outside world.

- **Input** → data coming INTO the program
  (keyboard, file, network, database)
- **Output** → data going OUT of the program
  (console, file, network, printer)

### Why I/O Matters

Every real-world program needs I/O. A program that takes no
input and produces no output is useless. Java's I/O system is
one of the most well-designed — and most misunderstood — parts
of the language.

---

## 2. Types of I/O in Java

```
JAVA I/O TYPES
│
├── Console I/O
│       → Keyboard input, terminal/console output
│       → System.in, System.out, System.err
│
├── File I/O
│       → Read from / write to files on disk
│       → FileInputStream, FileOutputStream, FileReader, FileWriter
│
├── Network I/O
│       → Communication over sockets and HTTP
│       → Socket, ServerSocket, URLConnection
│
└── Memory I/O
        → Read from / write to byte arrays in memory
        → ByteArrayInputStream, ByteArrayOutputStream
```

This lecture focuses on **Console I/O** — the foundation
that all other I/O types are built upon.

---

## 3. Console I/O — The Big Picture

When you run a Java program from a terminal:

```
KEYBOARD  →  System.in  →  [YOUR JAVA PROGRAM]  →  System.out  →  CONSOLE SCREEN
                                                 →  System.err  →  CONSOLE SCREEN (error)
```

The operating system provides three **standard streams** to
every process when it starts:

| Stream | Java Name | Type | Direction |
|--------|-----------|------|-----------|
| Standard Input | `System.in` | `InputStream` | Keyboard → Program |
| Standard Output | `System.out` | `PrintStream` | Program → Console |
| Standard Error | `System.err` | `PrintStream` | Program → Console (errors) |

These three are automatically available in every Java program —
no import, no setup. The JVM wires them up at startup.

---

## 4. Understanding System.out.println()

### Decomposing the Call

`System.out.println("Hello")` has four parts:

```
System   .   out   .   println("Hello")
  │            │              │
  │            │              └── method call on out object
  │            └── static field of type PrintStream
  └── a class in java.lang (auto-imported)
```

### What is `System`?

`System` is a **final class** in `java.lang` package.
It cannot be instantiated. All its members are `static`.

```java
// Simplified view of the System class:
public final class System {
    public static final PrintStream out; // standard output
    public static final PrintStream err; // standard error
    public static final InputStream in;  // standard input

    // Private constructor — cannot instantiate
    private System() { }

    // Static methods
    public static void exit(int status) { }
    public static long currentTimeMillis() { }
    public static void gc() { }
    // ... more
}
```

### What is `out`?

`out` is a **static field** of type `PrintStream` inside System.
It is initialized by the JVM to point to the console output.

```java
// Proof that out is a PrintStream:
PrintStream ps = System.out; // compiles perfectly
ps.println("Hello from PrintStream reference");
```

### What is `PrintStream`?

`PrintStream` is a class in `java.io` that wraps an
`OutputStream` and provides convenient `print()`, `println()`,
and `printf()` methods. It handles converting any Java type
to its string representation for display.

```
PrintStream hierarchy:
    Object
      └── OutputStream (abstract)
              └── FilterOutputStream
                      └── PrintStream  ← System.out is this type
```

### PrintStream — All print() Overloads

`println()` is overloaded for every type so you never need to
manually convert before printing:

```java
System.out.println(42);         // int
System.out.println(3.14);       // double
System.out.println(true);       // boolean
System.out.println('A');        // char
System.out.println("Hello");    // String
System.out.println(new Object()); // Object — calls toString()

// printf — formatted output
System.out.printf("Name: %-10s Age: %3d GPA: %.2f%n",
                  "Rohit", 21, 9.2);
// Output: Name: Rohit       Age:  21 GPA: 9.20

// print vs println
System.out.print("No newline");  // stays on same line
System.out.println("With newline"); // adds \n at end
```

### The Full Chain — What Happens When You Call println()

```
System.out.println("Hello")
         │
         ▼
   PrintStream.println(String s)
         │
         ▼
   Internally converts to bytes using charset encoding (default UTF-8)
         │
         ▼
   Calls underlying OutputStream.write(byte[])
         │
         ▼
   OS writes bytes to standard output file descriptor (fd 1)
         │
         ▼
   Terminal receives bytes and displays characters on screen
```

---

## 5. The System Class

### Key Static Members

```java
// 1. Exit the JVM
System.exit(0);    // 0 = normal exit
System.exit(1);    // non-zero = error exit

// 2. Current time in milliseconds since Jan 1, 1970
long start = System.currentTimeMillis();
// ... do work ...
long end   = System.currentTimeMillis();
System.out.println("Time taken: " + (end - start) + "ms");

// 3. Current time in nanoseconds (more precise)
long nanoStart = System.nanoTime();

// 4. Suggest garbage collection (JVM decides whether to honor it)
System.gc();

// 5. Get environment variable
String path = System.getenv("PATH");

// 6. Get system property
String javaVersion = System.getProperty("java.version");
String osName      = System.getProperty("os.name");

// 7. Array copy (fastest way to copy arrays — native method)
int[] src  = {1, 2, 3, 4, 5};
int[] dest = new int[5];
System.arraycopy(src, 0, dest, 0, 5);
```

### Redirecting Standard Streams

The standard streams can be reassigned programmatically:

```java
import java.io.*;

// Redirect System.out to a file
PrintStream fileOut = new PrintStream(new File("output.log"));
System.setOut(fileOut);
System.out.println("This goes to file, not console");

// Redirect back
System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

// Similarly for System.in and System.err
System.setIn(new FileInputStream("input.txt"));
System.setErr(new PrintStream(new File("errors.log")));
```

---

## 6. System.err — Error Stream

### What is System.err?

`System.err` is also a `PrintStream` — identical to `System.out`
in type. The difference is its **purpose and file descriptor**:

- `System.out` → writes to **stdout** (file descriptor 1)
- `System.err` → writes to **stderr** (file descriptor 2)

### Why Two Output Streams?

Separating normal output from error output allows:
1. **Redirection**: `java MyApp > output.txt` captures stdout
   but still shows errors on terminal
2. **Logging tools**: can categorize normal vs error messages
3. **Visibility**: errors appear immediately even when stdout
   is redirected to a file

```java
// Normal output — goes to stdout
System.out.println("Processing started...");
System.out.println("Processing complete.");

// Error output — goes to stderr
System.err.println("ERROR: File not found!");
System.err.println("WARN: Connection timeout, retrying...");

// In terminal — running the program:
// java MyApp > output.txt
// → output.txt contains: "Processing started..." and "Processing complete."
// → terminal SHOWS: "ERROR: File not found!" (stderr not redirected)
```

### When to Use err vs out

| Situation | Use |
|-----------|-----|
| Normal program output | `System.out` |
| Error messages | `System.err` |
| Debug/warning messages | `System.err` |
| Results / data output | `System.out` |

---

## 7. Streams of Data

### What is a Stream?

In Java I/O, a **stream** is a **sequential flow of data** —
bytes arriving one after another in order.

Think of a stream like water in a pipe:
- Data flows in one direction
- You process it as it arrives
- You cannot go backward (unless specifically designed to)

```
INPUT STREAM:
  Keyboard → [b][y][t][e][s][...] → Your Program
  (bytes flow from source to program)

OUTPUT STREAM:
  Your Program → [b][y][t][e][s][...] → Console/File
  (bytes flow from program to destination)
```

### Everything is Bytes

At the lowest level, ALL Java I/O is byte-based.
Characters, strings, integers — everything becomes bytes
before being sent through a stream.

```
"Hello" → [72][101][108][108][111] → bytes → transmitted
  H         e     l     l    o

72 = ASCII/UTF-8 code for 'H'
101 = ASCII/UTF-8 code for 'e'
... and so on
```

---

## 8. InputStream & OutputStream Hierarchy

### InputStream — Abstract Base for All Input

```
InputStream (abstract) — java.io
    │
    ├── FileInputStream       → reads bytes from a file
    ├── ByteArrayInputStream  → reads bytes from a byte array in memory
    ├── FilterInputStream     → wrapper/decorator base
    │       ├── BufferedInputStream → adds buffering for performance
    │       └── DataInputStream     → reads primitive types
    └── ObjectInputStream     → reads serialized Java objects
```

`System.in` is an instance of `InputStream` (specifically a
`FileInputStream` wrapped around file descriptor 0 at the OS level).

### OutputStream — Abstract Base for All Output

```
OutputStream (abstract) — java.io
    │
    ├── FileOutputStream      → writes bytes to a file
    ├── ByteArrayOutputStream → writes bytes to memory
    ├── FilterOutputStream    → wrapper/decorator base
    │       ├── BufferedOutputStream → adds buffering
    │       ├── DataOutputStream     → writes primitive types
    │       └── PrintStream          ← System.out and System.err are this
    └── ObjectOutputStream    → writes serialized Java objects
```

### Key Abstract Methods

```java
// InputStream must implement:
abstract int read() throws IOException;
// Returns next byte (0-255), or -1 if end of stream

// OutputStream must implement:
abstract void write(int b) throws IOException;
// Writes one byte (lowest 8 bits of b)
```

### The Decorator Pattern in Java I/O

Java I/O is built on the **Decorator Pattern** — you wrap
streams inside other streams to add functionality:

```java
// Layer 1: Raw bytes from file
InputStream raw = new FileInputStream("data.txt");

// Layer 2: Add buffering for performance
InputStream buffered = new BufferedInputStream(raw);

// Layer 3: Add ability to read data types
DataInputStream typed = new DataInputStream(buffered);

// Now read structured data efficiently
int id   = typed.readInt();
double price = typed.readDouble();
```

---

## 9. Taking Input via System.in.read()

### The Raw Approach

`System.in` is an `InputStream`. Its `read()` method reads
**one byte at a time** and returns it as an `int` (0–255),
or `-1` at end of stream.

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.print("Enter a character: ");

        // read() returns the ASCII/Unicode value of the byte
        int byteValue = System.in.read();

        System.out.println("Byte value: " + byteValue);
        System.out.println("Character: " + (char) byteValue);
    }
}

// If user types 'A' and presses Enter:
// Byte value: 65
// Character: A
// (The Enter key also sends bytes: 13 and 10 — \r and \n)
```

### Reading Multiple Bytes

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.print("Type something: ");

        int data;
        StringBuilder sb = new StringBuilder();

        // Read until newline or end of stream
        while ((data = System.in.read()) != -1) {
            char c = (char) data;
            if (c == '\n') break;   // stop at Enter key
            sb.append(c);
        }

        System.out.println("You typed: " + sb.toString());
    }
}
```

### Problems with System.in.read() Directly

| Problem | Detail |
|---------|--------|
| One byte at a time | Extremely slow — one OS call per byte |
| Only reads bytes | Must manually cast to char |
| No line reading | Must manually handle \n, \r\n |
| No parsing | Cannot directly get int, double, etc. |
| Byte vs char confusion | ASCII works, but Unicode breaks |

This is why higher-level classes like `BufferedReader`
and `Scanner` exist.

---

## 10. Reader Class & Why It Exists

### The Byte vs Character Problem

`InputStream` works with **bytes** (8-bit values, 0–255).
This works for ASCII characters (English letters, numbers).

But modern text can be:
- Hindi: अ, आ (needs 2–3 bytes in UTF-8)
- Chinese: 中, 文 (needs 3 bytes in UTF-8)
- Emoji: 😀 (needs 4 bytes in UTF-8)

A single character can be **multiple bytes**.
Reading byte by byte and casting to `char` breaks for
non-ASCII text.

### The Solution — Reader Hierarchy

Java introduced a parallel hierarchy of **character-based**
streams (as opposed to byte-based):

```
Reader (abstract) — java.io         ← character-based input
    │
    ├── InputStreamReader   → converts byte stream to char stream
    │       └── FileReader  → reads chars from file
    └── BufferedReader      → adds line-buffering for performance

Writer (abstract) — java.io         ← character-based output
    │
    ├── OutputStreamWriter  → converts char stream to byte stream
    │       └── FileWriter  → writes chars to file
    └── BufferedWriter      → adds buffering for performance
    └── PrintWriter         → like PrintStream but for chars
```

### Reader vs InputStream

| Feature | InputStream | Reader |
|---------|-------------|--------|
| Unit | byte (8-bit) | char (16-bit Unicode) |
| Good for | Binary data, raw bytes | Text data |
| Encoding aware | No | Yes |
| Key method | `read()` → int (0-255) | `read()` → int (0-65535) |

---

## 11. BufferedReader — Performance Optimization

### The Performance Problem Without Buffering

When you read one character at a time from the OS:

```
WITHOUT buffering:
  Program requests char → OS call → get 1 char → return
  Program requests char → OS call → get 1 char → return
  Program requests char → OS call → get 1 char → return
  ... 1000 times for a 1000-char line

  1000 OS system calls for one line!
  OS calls are EXPENSIVE — they involve context switching
  between user space and kernel space.
```

### How BufferedReader Solves This

```
WITH BufferedReader (default buffer = 8192 chars):
  Program requests char → BufferedReader checks internal buffer
                        → Buffer is EMPTY → ONE OS call → fills buffer
                          with 8192 chars from OS
                        → returns first char from buffer

  Program requests char → BufferedReader checks buffer
                        → Buffer has chars → return next char
                          (NO OS call!)

  ... repeats until buffer empty → one more OS call

  For 1000-char line: possibly just 1 OS call instead of 1000!
```

### BufferedReader Key Methods

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

// Read one character
int charValue = br.read();       // returns char as int, -1 at end

// Read one WHOLE LINE — most useful method
String line = br.readLine();     // returns null at end of stream

// Read into char array
char[] buffer = new char[100];
int charsRead = br.read(buffer, 0, 100);

// Skip characters
br.skip(5); // skip next 5 characters

// Check if ready (data available)
boolean ready = br.ready();

// Close when done
br.close();
```

### Internal Buffer Visualization

```
KEYBOARD INPUT:  "Hello World\n"
                  ↓ (typed by user, buffered by OS)

BufferedReader Internal Buffer (8192 chars):
┌─────────────────────────────────────────────────────┐
│ H │ e │ l │ l │ o │   │ W │ o │ r │ l │ d │\n│...  │
└─────────────────────────────────────────────────────┘
  ↑
  cursor position

readLine() call:
  → reads chars until \n
  → returns "Hello World" (without the \n)
  → cursor moves past \n
  → next readLine() starts from there
```

---

## 12. InputStreamReader — The Bridge

### Why InputStreamReader Exists

`System.in` is an `InputStream` (byte-based).
`BufferedReader` requires a `Reader` (char-based) as input.

You cannot directly pass an `InputStream` to `BufferedReader`.
You need a **bridge** that converts bytes → chars.

That bridge is `InputStreamReader`.

```
System.in          InputStreamReader        BufferedReader
(InputStream)  →   (byte→char bridge)  →   (adds buffering)
 bytes out          chars out               lines out
```

### InputStreamReader — Charset Handling

`InputStreamReader` is responsible for converting bytes to
characters using a **character encoding**:

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

// Default encoding (usually UTF-8 on modern systems)
InputStreamReader isr1 = new InputStreamReader(System.in);

// Explicit UTF-8 encoding — RECOMMENDED for portability
InputStreamReader isr2 = new InputStreamReader(
    System.in, StandardCharsets.UTF_8);

// Can also specify encoding by name
InputStreamReader isr3 = new InputStreamReader(
    System.in, "UTF-8");
```

### The Complete Chain Explained

```java
BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in));
```

```
System.in
    ↓  raw bytes from keyboard
InputStreamReader
    ↓  decodes bytes to chars using charset (e.g., UTF-8)
BufferedReader
    ↓  buffers chars; provides readLine(), efficient reading
Your Code
    ↓  works with Strings and chars
```

Each layer adds one responsibility:
- `System.in` → source of raw bytes
- `InputStreamReader` → decodes bytes to characters
- `BufferedReader` → buffers and provides line-level reading

---

## 13. Taking Input Using BufferedReader

### Setup and Basic Usage

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        // Create the chain
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in));

        // Read a String line
        System.out.print("Enter your name: ");
        String name = br.readLine();
        System.out.println("Hello, " + name);

        // Read and parse an integer
        System.out.print("Enter your age: ");
        int age = Integer.parseInt(br.readLine().trim());
        System.out.println("Age: " + age);

        // Read and parse a double
        System.out.print("Enter your GPA: ");
        double gpa = Double.parseDouble(br.readLine().trim());
        System.out.println("GPA: " + gpa);

        br.close();
    }
}
```

### Reading Multiple Values from One Line

```java
import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in));

        // Input: "10 20 30" (space-separated on one line)

        // Method 1 — split()
        String line = br.readLine();
        String[] parts = line.split(" ");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        int c = Integer.parseInt(parts[2]);
        System.out.println("Sum: " + (a + b + c));

        // Method 2 — StringTokenizer (faster than split)
        String line2 = br.readLine();
        StringTokenizer st = new StringTokenizer(line2);
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        System.out.println("Product: " + (x * y));

        br.close();
    }
}
```

### Competitive Programming Template

```java
import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    // Static BufferedReader at class level — reused across methods
    static BufferedReader br = new BufferedReader(
                                   new InputStreamReader(System.in));
    static StringTokenizer st;

    // Utility: read next token (word/number)
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    static int     nextInt()    throws IOException { return Integer.parseInt(next()); }
    static long    nextLong()   throws IOException { return Long.parseLong(next()); }
    static double  nextDouble() throws IOException { return Double.parseDouble(next()); }
    static String  nextLine()   throws IOException { return br.readLine(); }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextInt();
        }
        // process...
        long sum = 0;
        for (int x : arr) sum += x;
        System.out.println(sum);
    }
}
```

---

## 14. Limitations of BufferedReader

### Limitation 1 — No Built-in Type Parsing

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

// Cannot directly read an int
int n = br.readInt(); // ❌ method does not exist

// Must always: read line → parse manually
int n = Integer.parseInt(br.readLine().trim()); // ✅ verbose
```

### Limitation 2 — Must Handle IOException

Every `BufferedReader` operation throws a **checked exception**
that you must explicitly handle:

```java
// Either declare throws:
public static void main(String[] args) throws IOException { }

// Or wrap in try-catch:
try {
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
```

### Limitation 3 — No Tokenization

Reading space-separated values requires manual splitting:

```java
// Scanner can do:
int a = sc.nextInt();
int b = sc.nextInt();

// BufferedReader needs:
StringTokenizer st = new StringTokenizer(br.readLine());
int a = Integer.parseInt(st.nextToken());
int b = Integer.parseInt(st.nextToken());
```

### Limitation 4 — No Pattern Matching

`Scanner` has `hasNextInt()`, `hasNextDouble()` etc.
`BufferedReader` has none of these convenience methods.

---

## 15. Scanner Class

### What is Scanner?

`Scanner` is a class in `java.util` (not `java.io`) designed
to make parsing text input easy. It tokenizes input (breaks it
into pieces by whitespace by default) and parses each token
into the requested type.

### Scanner Setup

```java
import java.util.Scanner;

// Most common — reading from keyboard
Scanner sc = new Scanner(System.in);

// Reading from a file
Scanner sc2 = new Scanner(new File("input.txt"));

// Reading from a String
Scanner sc3 = new Scanner("10 20 30 Hello 3.14");
```

### Scanner — All Key Methods

```java
Scanner sc = new Scanner(System.in);

// Reading different types
String word   = sc.next();        // reads next token (word)
String line   = sc.nextLine();    // reads entire line
int    num    = sc.nextInt();     // reads next int
long   big    = sc.nextLong();    // reads next long
double dec    = sc.nextDouble();  // reads next double
float  f      = sc.nextFloat();   // reads next float
boolean b     = sc.nextBoolean(); // reads "true" or "false"

// Checking before reading (avoids exceptions)
if (sc.hasNextInt())    int n = sc.nextInt();
if (sc.hasNextDouble()) double d = sc.nextDouble();
if (sc.hasNextLine())   String s = sc.nextLine();

// Change delimiter (default is whitespace)
sc.useDelimiter(",");  // now splits by comma instead of space

// Close when done
sc.close();
```

### Common Scanner Example

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        System.out.print("Enter your GPA: ");
        double gpa = sc.nextDouble();

        System.out.printf("Name: %s | Age: %d | GPA: %.2f%n",
                          name, age, gpa);
        sc.close();
    }
}
```

### The nextLine() Trap After nextInt()

```java
Scanner sc = new Scanner(System.in);

int age = sc.nextInt();       // reads "25", leaves "\n" in buffer
String name = sc.nextLine();  // reads the leftover "\n" — gets empty String!
// name is "" instead of the actual name

// FIX — consume the leftover newline
int age2 = sc.nextInt();
sc.nextLine();                // consume the \n
String name2 = sc.nextLine(); // now reads actual name correctly
```

### How Scanner Works Internally

```
INPUT STREAM (keyboard):  "42 Hello 3.14\n"
                               ↓
Scanner Internal Buffer:
  ["42", "Hello", "3.14"]   ← tokenized by whitespace
       ↓
sc.nextInt()    → reads "42"  → parses to int 42
sc.next()       → reads "Hello" → returns String "Hello"
sc.nextDouble() → reads "3.14" → parses to double 3.14
```

Scanner uses **regular expressions internally** to tokenize
and validate input — this is part of why it is slower.

---

## 16. Scanner vs BufferedReader — Complete Comparison

### Feature Comparison

| Feature | Scanner | BufferedReader |
|---------|---------|----------------|
| Package | `java.util` | `java.io` |
| Primary use | Parsing typed input | Efficient text reading |
| Reads types directly | ✅ nextInt(), nextDouble() | ❌ must parse manually |
| Reads full line | ✅ nextLine() | ✅ readLine() |
| Buffering | Internal (smaller) | Yes (8192 chars default) |
| Thread-safe | ✅ Yes (synchronized) | ❌ Not synchronized |
| IOException handling | ❌ Not required (catches internally) | ✅ Required (checked) |
| Tokenization | ✅ Built-in by whitespace/regex | ❌ Manual (split/StringTokenizer) |
| Pattern matching | ✅ hasNextInt(), findInLine() | ❌ None |
| Performance | Slower (regex overhead) | Faster |
| Best for | Beginners, simple programs | High-performance, competitive programming |

### Performance Difference Explained

```
Scanner performance cost:
  ┌──────────────────────────────────────────────────┐
  │ 1. Read from stream                              │
  │ 2. Apply regex to find token boundaries          │
  │ 3. Validate token matches expected type (regex)  │
  │ 4. Parse token to requested type                 │
  │ 5. Synchronize (thread-safe overhead)            │
  └──────────────────────────────────────────────────┘

BufferedReader + parseInt performance cost:
  ┌──────────────────────────────────────────────────┐
  │ 1. Read from buffer (mostly memory, fast)        │
  │ 2. Integer.parseInt() — simple, fast             │
  └──────────────────────────────────────────────────┘
```

### When to Use Which

```
Use SCANNER when:
  ✅ Learning / beginner code
  ✅ Simple programs with few inputs
  ✅ Need convenient type parsing without boilerplate
  ✅ Interactive user input with validation (hasNextInt etc.)
  ✅ Parsing files with mixed types

Use BUFFEREDREADER when:
  ✅ Competitive programming (speed critical)
  ✅ Reading large volumes of input (thousands of lines)
  ✅ File processing with large text files
  ✅ Performance-sensitive applications
  ✅ When you need maximum throughput
```

### Speed Test — Actual Benchmark

For reading 1,000,000 integers:
- `Scanner`: ~2–3 seconds
- `BufferedReader + parseInt`: ~0.3–0.5 seconds
- **BufferedReader is roughly 5–10x faster**

---

## 17. Key Rules & Common Mistakes

### Common Mistakes

```java
// ❌ MISTAKE 1 — Scanner nextLine() after nextInt()
Scanner sc = new Scanner(System.in);
int n = sc.nextInt();
String s = sc.nextLine(); // reads the leftover \n — empty string!

// ✅ FIX
int n2 = sc.nextInt();
sc.nextLine();            // consume leftover newline
String s2 = sc.nextLine(); // now reads correctly

// ❌ MISTAKE 2 — Not handling IOException with BufferedReader
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String line = br.readLine(); // ❌ must handle IOException

// ✅ FIX
public static void main(String[] args) throws IOException {
    String line = br.readLine(); // ✅ declared in signature
}

// ❌ MISTAKE 3 — Not parsing BufferedReader output
BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
int num = br2.readLine(); // ❌ readLine returns String, not int

// ✅ FIX
int num2 = Integer.parseInt(br2.readLine().trim()); // ✅

// ❌ MISTAKE 4 — Using System.out and System.err interchangeably
System.out.println("Error: file not found"); // ❌ use err for errors

// ✅ FIX
System.err.println("Error: file not found"); // ✅

// ❌ MISTAKE 5 — Not closing resources
Scanner sc2 = new Scanner(System.in);
// ... use it ...
// forgot sc2.close() — minor for System.in but bad practice

// ✅ FIX — use try-with-resources
try (Scanner sc3 = new Scanner(System.in)) {
    // use sc3
} // auto-closed
```

---

## 18. Quick Revision Cheatsheet

```
JAVA I/O FUNDAMENTALS
──────────────────────────────────────────────────────────────
 System.in   → InputStream  (bytes from keyboard)
 System.out  → PrintStream  (bytes to console stdout)
 System.err  → PrintStream  (bytes to console stderr)
 All three auto-initialized by JVM at startup

SYSTEM CLASS
──────────────────────────────────────────────────────────────
 final class, java.lang, cannot instantiate
 All members static
 Key methods: exit(), currentTimeMillis(), gc(),
              arraycopy(), getenv(), getProperty()
 Can redirect: setIn(), setOut(), setErr()

STREAMS
──────────────────────────────────────────────────────────────
 All I/O = sequential flow of bytes
 InputStream  → abstract base for all byte input
 OutputStream → abstract base for all byte output
 Built on Decorator Pattern — wrap streams for extra features
 InputStream.read() → one byte (0-255), -1 at end
 OutputStream.write(int b) → one byte

READER HIERARCHY (character-based)
──────────────────────────────────────────────────────────────
 Reader        → abstract base for char input
 InputStreamReader → converts byte stream to char stream
 BufferedReader → wraps Reader, adds buffer + readLine()
 Why needed: multi-byte Unicode chars break byte-by-byte reading

BUFFEREDREADER
──────────────────────────────────────────────────────────────
 Setup: new BufferedReader(new InputStreamReader(System.in))
 Buffer: 8192 chars by default
 Key method: readLine() → returns String, null at end
 Must handle: IOException (checked)
 Performance: 5-10x faster than Scanner for large input
 Limitation: no direct type parsing, no tokenization

INPUTSTREAMREADER
──────────────────────────────────────────────────────────────
 Bridge between InputStream (bytes) and Reader (chars)
 Handles charset encoding (default UTF-8 on modern JVMs)
 Use StandardCharsets.UTF_8 for explicit portable encoding

SCANNER
──────────────────────────────────────────────────────────────
 Package: java.util (not java.io!)
 Setup: new Scanner(System.in)
 Key methods: next(), nextLine(), nextInt(), nextDouble(),
              nextLong(), nextBoolean(), hasNextInt(), etc.
 Trap: nextLine() after nextInt() reads leftover \n
 Fix: call sc.nextLine() once to consume the \n
 Slower: uses regex internally, thread-safe overhead

SCANNER vs BUFFEREDREADER
──────────────────────────────────────────────────────────────
 Scanner        → convenient, slower, java.util, no IOException
 BufferedReader → fast, verbose, java.io, needs IOException

 Use Scanner:       beginners, simple programs, few inputs
 Use BufferedReader: competitive prog, large inputs, performance

SYSTEM.OUT vs SYSTEM.ERR
──────────────────────────────────────────────────────────────
 Both are PrintStream
 out → stdout (fd 1) → normal program output
 err → stderr (fd 2) → errors and warnings
 Separate so stdout can be redirected without losing errors
```

---

*End of Notes*
```