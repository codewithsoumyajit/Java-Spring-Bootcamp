# Java Immutable Classes — Comprehensive Notes
## Design Rules | Shallow vs Deep Copy | Thread Safety

---

## Table of Contents
1. [What is Immutability?](#1-what-is-immutability)
2. [Why Immutability Matters](#2-why-immutability-matters)
3. [Rules for Creating Immutable Classes](#3-rules-for-creating-immutable-classes)
4. [Rule 1 — Mark Class as final](#4-rule-1--mark-class-as-final)
5. [Rule 2 — Private and Final Fields](#5-rule-2--private-and-final-fields)
6. [Rule 3 — No Setters](#6-rule-3--no-setters)
7. [Rule 4 — Deep Copy for Mutable Fields](#7-rule-4--deep-copy-for-mutable-fields)
8. [Shallow Copy — The Problem](#8-shallow-copy--the-problem)
9. [Defensive Deep Copy — The Solution](#9-defensive-deep-copy--the-solution)
10. [Thread Safety and Immutability](#10-thread-safety-and-immutability)
11. [Real-World Examples of Immutability](#11-real-world-examples-of-immutability)
12. [Key Rules & Common Mistakes](#12-key-rules--common-mistakes)
13. [Quick Revision Cheatsheet](#13-quick-revision-cheatsheet)

---

## 1. What is Immutability?

An **immutable object** is an object whose **state cannot be
changed after it is created**.

Once you call the constructor and the object is built —
every field is locked. No method can alter the internal data.
The object looks the same from the moment of creation until
the moment it is garbage collected.

### Simple Analogy

Think of a **printed book**. Once a book is printed:
- You cannot change the words on page 50
- You cannot add a new chapter
- You cannot delete a paragraph

The content is fixed forever. That is immutability.
The book's state is set at the time of printing (construction)
and never changes after that.

### Contrast — Mutable vs Immutable

```java
// MUTABLE — state can change after creation
class MutablePoint {
    int x, y;

    MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setX(int x) { this.x = x; } // state can change
    void setY(int y) { this.y = y; } // state can change
}

MutablePoint p = new MutablePoint(3, 4);
p.setX(100); // ← original state destroyed — point is now (100, 4)

// IMMUTABLE — state cannot change after creation
final class ImmutablePoint {
    private final int x;
    private final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() { return x; } // read-only
    int getY() { return y; } // read-only
    // No setters — cannot change after construction
}

ImmutablePoint p2 = new ImmutablePoint(3, 4);
// p2.setX(100); → method does not exist — cannot change
// p2.x = 100;   → private field — cannot access
// p2 is (3, 4) forever
```

---

## 2. Why Immutability Matters

### Reason 1 — Thread Safety

In multithreaded applications, multiple threads can access
the same object simultaneously. If one thread changes the
object's state while another is reading it — you get a
**race condition** — unpredictable, hard-to-reproduce bugs.

Immutable objects eliminate this problem completely. If the
state can NEVER change, there is NOTHING to race over.
Multiple threads can read the same immutable object
simultaneously with zero synchronization needed.

```
Thread 1: reading ImmutablePoint(3, 4) → always sees (3, 4) ✅
Thread 2: reading ImmutablePoint(3, 4) → always sees (3, 4) ✅
Thread 3: reading ImmutablePoint(3, 4) → always sees (3, 4) ✅

No locks. No synchronization. No race conditions.
```

### Reason 2 — Predictability and Reliability

When you pass an immutable object to a method, you KNOW
that method cannot change it. You do not need to defensively
copy it before passing. You do not need to check if it was
modified after the call returns.

```java
// With mutable object — paranoia needed
List<String> names = new ArrayList<>(originalNames); // must copy before passing
process(names);
// Is originalNames still intact? Did process() modify it? Unknown!

// With immutable object — complete confidence
ImmutableList<String> names = ImmutableList.of("Alice", "Bob");
process(names); // names CANNOT have been changed — guaranteed
```

### Reason 3 — Safe as Map Keys and Set Elements

`HashMap` and `HashSet` use an object's `hashCode()` to
determine where to store it. If a mutable object is used
as a key and then mutated, its hash changes — and the map
can no longer find it. The object effectively disappears.

Immutable objects always have the same hash — safe as keys forever.

```java
// DANGEROUS — mutable key
StringBuilder key = new StringBuilder("hello");
Map<StringBuilder, Integer> map = new HashMap<>();
map.put(key, 42);
key.append(" world"); // mutate key after insertion
System.out.println(map.get(key)); // null! Lost forever!

// SAFE — immutable key
String immutableKey = "hello";
Map<String, Integer> map2 = new HashMap<>();
map2.put(immutableKey, 42);
// Cannot change immutableKey — always findable
System.out.println(map2.get("hello")); // 42 ✅
```

### Reason 4 — Caching and Optimization

Since immutable objects never change, they can be safely
cached and reused. This is why the JVM caches Integer values
(-128 to 127) and String literals — they are immutable.

---

## 3. Rules for Creating Immutable Classes

There are exactly **four rules** to make a class truly immutable.
Miss even one and the immutability can be broken.

```
IMMUTABILITY CHECKLIST
┌─────────────────────────────────────────────────────────┐
│  Rule 1: Mark the class as final                        │
│          → prevents subclass from breaking immutability │
│                                                         │
│  Rule 2: Make all fields private and final              │
│          → prevents direct access and reassignment      │
│                                                         │
│  Rule 3: No setter methods                              │
│          → no way to change state from outside          │
│                                                         │
│  Rule 4: Deep copy for mutable object fields            │
│          → in constructor AND in getters                │
└─────────────────────────────────────────────────────────┘
```

---

## 4. Rule 1 — Mark Class as `final`

### Why `final` on the Class?

Without `final`, someone can EXTEND your class and override
its methods to add mutability — completely breaking your
immutability guarantee.

```java
// WITHOUT final — immutability can be broken through inheritance
class "Immutable"Money {  // not final
    private final double amount;

    "Immutable"Money(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
}

// Attacker subclass — breaks immutability
class MutableMoney extends "Immutable"Money {
    private double secretAmount;

    MutableMoney(double amount) {
        super(amount);
        this.secretAmount = amount;
    }

    // Override getter to return mutable state
    @Override
    public double getAmount() { return secretAmount; }

    // Add setter — breaks immutability contract
    public void setAmount(double amount) { this.secretAmount = amount; }
}

"Immutable"Money money = new MutableMoney(100.0);
// Can be cast and mutated — immutability broken!
((MutableMoney) money).setAmount(999.0);
```

### With `final` — Inheritance Blocked

```java
// WITH final — cannot be subclassed
final class Money {
    private final double amount;

    Money(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
}

// class EvilMoney extends Money { } // ❌ COMPILE ERROR
// Cannot inherit from final 'Money'
```

---

## 5. Rule 2 — Private and Final Fields

### Why `private`?

`private` prevents any code outside the class from directly
accessing or modifying the field.

```java
final class Student {
    public final String name; // ❌ field is public

    Student(String name) { this.name = name; }
}

Student s = new Student("Rohit");
s.name = "Hacker"; // ✅ compiles! Immutability broken!
// Even though field is final... wait, actually final prevents this
// BUT: public + non-final = disaster
```

### Why `final` on Fields?

`final` ensures the field reference is assigned exactly ONCE —
in the constructor — and can never be reassigned.

```java
final class Student {
    private String name;     // private but NOT final

    Student(String name) { this.name = name; }

    // Without final, even within the class, a method could change it:
    private void corruptData() {
        this.name = null; // allowed! field is not final
    }
}
```

### Correct Approach — Both `private` AND `final`

```java
final class Student {
    private final String name;   // private: no external access
    private final int    rollNo; // final: cannot be reassigned anywhere

    Student(String name, int rollNo) {
        this.name   = name;   // assigned ONCE here
        this.rollNo = rollNo; // assigned ONCE here
        // After constructor — these fields are frozen
    }

    public String getName()  { return name; }
    public int   getRollNo() { return rollNo; }
    // No setters — read-only access only
}
```

---

## 6. Rule 3 — No Setters

### Why No Setters?

This seems obvious but is easy to accidentally violate — especially
when using IDE auto-generation that creates setters by default.

```java
final class BankAccount {
    private final String accountNumber;
    private final double balance;

    BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance       = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance()       { return balance; }

    // ❌ DO NOT ADD THESE:
    // public void setBalance(double balance) { this.balance = balance; }
    // Even if you add it: final field — compiler error!
    // final field prevents setter anyway for primitives and String

    // But for mutable object fields — setters are dangerous
    // even without violating final (you change the OBJECT, not the REFERENCE)
}
```

### Producing "Modified" Versions — Withers

Instead of setters, immutable classes often provide methods
that return a NEW object with the modification applied:

```java
final class Money {
    private final double amount;
    private final String currency;

    Money(double amount, String currency) {
        this.amount   = amount;
        this.currency = currency;
    }

    public double getAmount()   { return amount; }
    public String getCurrency() { return currency; }

    // Instead of setter — return NEW object
    public Money withAmount(double newAmount) {
        return new Money(newAmount, this.currency); // new object!
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount + other.amount, this.currency);
    }

    @Override
    public String toString() {
        return currency + " " + amount;
    }
}

// Usage:
Money price    = new Money(100.0, "INR");
Money newPrice = price.withAmount(150.0); // returns NEW object

System.out.println(price);    // INR 100.0 — UNCHANGED
System.out.println(newPrice); // INR 150.0 — new object
```

---

## 7. Rule 4 — Deep Copy for Mutable Fields

### The Critical Rule — Often Missed

Rules 1, 2, and 3 are enough when ALL fields are either:
- Primitives (`int`, `double`, `boolean`, etc.)
- Immutable objects (`String`, `Integer`, other immutable classes)

But when a field is a **mutable object** (like `Date`, `List`,
`ArrayList`, `byte[]`, custom mutable class), Rules 1-3 are
**NOT enough**. You need Rule 4.

### Why?

`final` on a field prevents **reassigning the reference**.
It does NOT prevent **modifying the object the reference points to**.

```java
final class Student {
    private final String name;
    private final List<String> courses; // mutable object!

    Student(String name, List<String> courses) {
        this.name    = name;
        this.courses = courses; // shallow copy — just the reference!
    }

    public String       getName()    { return name; }
    public List<String> getCourses() { return courses; }
}

// THE PROBLEM:
List<String> myCourses = new ArrayList<>(List.of("Java", "Python"));
Student s = new Student("Rohit", myCourses);

System.out.println(s.getCourses()); // [Java, Python]

// Mutate the original list
myCourses.add("Hacking"); // modifies the same list object!

System.out.println(s.getCourses()); // [Java, Python, Hacking] 😱
// Student's internal state changed! Immutability BROKEN!
```

This is the **shallow copy problem** — you copied the reference,
not the data. Both `myCourses` and `s.courses` point to the
SAME List object in heap.

---

## 8. Shallow Copy — The Problem

### What is a Shallow Copy?

A shallow copy copies the **reference** (memory address) of a
mutable object — not the object itself. After a shallow copy,
two references point to the SAME object.

```
SHALLOW COPY — after: this.courses = courses;

Stack                    Heap
─────                    ────
myCourses ──────────────► ArrayList object
                          ["Java", "Python"]
                                 ▲
s.courses ───────────────────────┘
(same address!)

Modifying via myCourses modifies the ArrayList.
s.courses sees the change — same object!
```

### Demonstration of the Breach

```java
// All three ways immutability can be broken without deep copy:

// BREACH 1 — via original reference retained by caller
List<String> original = new ArrayList<>(List.of("Java"));
Student s = new Student("Rohit", original);
original.add("Python");               // caller mutates!
System.out.println(s.getCourses());   // [Java, Python] — changed!

// BREACH 2 — via getter returning direct reference
List<String> stolen = s.getCourses(); // returns internal reference
stolen.add("Exploit");                // outside code mutates!
System.out.println(s.getCourses());   // [Java, Python, Exploit] — changed!

// BREACH 3 — via getter in loop
for (String course : s.getCourses()) {
    // getCourses() returned the actual internal list
    // if you call remove() anywhere, internal state changes
}
```

---

## 9. Defensive Deep Copy — The Solution

### Deep Copy in the Constructor

When a mutable object is passed to the constructor,
create a NEW copy of it — do not store the reference.

```java
// Constructor fix:
Student(String name, List<String> courses) {
    this.name    = name;
    this.courses = new ArrayList<>(courses); // ← DEEP COPY
    // Now this.courses is a NEW ArrayList with same contents
    // Caller's original list and our internal list are SEPARATE objects
}
```

### Deep Copy in the Getter

When returning a mutable field from a getter,
return a NEW copy — do not return the internal reference.

```java
// Getter fix:
public List<String> getCourses() {
    return new ArrayList<>(courses); // ← DEEP COPY
    // Caller gets a copy — modifying it does NOT affect our internal list
}
```

### Complete Correct Immutable Class

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ImmutableStudent {

    private final String       name;
    private final int          rollNo;
    private final List<String> courses;  // mutable field — needs deep copy

    // Constructor — defensive copy of mutable parameter
    public ImmutableStudent(String name, int rollNo, List<String> courses) {
        this.name   = name;
        this.rollNo = rollNo;

        // DEFENSIVE COPY in constructor
        // Do NOT store: this.courses = courses; (shallow copy — dangerous)
        // DO store a new copy:
        this.courses = new ArrayList<>(courses);
    }

    public String getName()  { return name; }   // String is immutable — safe to return
    public int   getRollNo() { return rollNo; }  // primitive — safe to return

    // DEFENSIVE COPY in getter
    // Do NOT return: return courses; (exposes internal reference — dangerous)
    // DO return a copy:
    public List<String> getCourses() {
        return new ArrayList<>(courses); // caller gets a copy
    }

    // Alternatively — return unmodifiable view
    public List<String> getCoursesView() {
        return Collections.unmodifiableList(courses);
        // throws UnsupportedOperationException if caller tries to modify
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', roll=" + rollNo
             + ", courses=" + courses + "}";
    }
}

// VERIFICATION:
public class Main {
    public static void main(String[] args) {
        List<String> myCourses = new ArrayList<>(List.of("Java", "Python"));
        ImmutableStudent s = new ImmutableStudent("Rohit", 101, myCourses);

        System.out.println("Before: " + s);

        // ATTACK 1 — mutate original list
        myCourses.add("Hacking");
        System.out.println("After attack 1: " + s);
        // Still shows original! Deep copy in constructor protected us ✅

        // ATTACK 2 — mutate returned list
        List<String> stolen = s.getCourses();
        stolen.add("More hacking");
        System.out.println("After attack 2: " + s);
        // Still shows original! Deep copy in getter protected us ✅
    }
}
```

### Deep Copy with `Date` — Another Common Case

`Date` is a classic mutable class that requires careful handling:

```java
import java.util.Date;

final class Event {
    private final String name;
    private final Date   eventDate; // Date is mutable!

    public Event(String name, Date eventDate) {
        this.name      = name;
        // Deep copy — create new Date with same millisecond value
        this.eventDate = new Date(eventDate.getTime());
    }

    public String getName() { return name; }

    public Date getEventDate() {
        // Deep copy — return new Date, not internal reference
        return new Date(eventDate.getTime());
    }

    @Override
    public String toString() {
        return "Event{name='" + name + "', date=" + eventDate + "}";
    }
}

// VERIFICATION:
public class Main {
    public static void main(String[] args) {
        Date d = new Date(2024, 1, 15); // some date
        Event e = new Event("Java Conference", d);

        System.out.println("Before: " + e);

        // ATTACK 1 — mutate original date
        d.setYear(2000);
        System.out.println("After attack 1: " + e); // unchanged ✅

        // ATTACK 2 — mutate returned date
        Date stolen = e.getEventDate();
        stolen.setYear(1900);
        System.out.println("After attack 2: " + e); // unchanged ✅
    }
}
```

### Deep Copy with Array Fields

```java
final class ImmutableMatrix {
    private final int[] data; // array is mutable!

    public ImmutableMatrix(int[] data) {
        // Arrays.copyOf creates a new array with same content
        this.data = java.util.Arrays.copyOf(data, data.length);
    }

    public int[] getData() {
        // Return copy — not internal array
        return java.util.Arrays.copyOf(data, data.length);
    }

    public int get(int index) {
        return data[index]; // safe — primitives returned by value
    }
}
```

---

## 10. Thread Safety and Immutability

### The Race Condition Problem with Mutable Objects

```
MULTITHREADED SCENARIO WITH MUTABLE OBJECT:

Thread A                    Thread B
────────                    ────────
reads balance = 1000        reads balance = 1000
calculates: 1000 - 500      calculates: 1000 - 200
writes balance = 500        writes balance = 800

RESULT: balance = 800 (Thread B overwrote Thread A's update)
Expected: 1000 - 500 - 200 = 300

This is a RACE CONDITION — data corruption in multithreading
```

### Immutability Eliminates Race Conditions

```java
// Mutable — needs synchronization (complex, error-prone)
class MutableCounter {
    private int count = 0;

    // Must synchronize to be thread-safe
    public synchronized void increment() { count++; }
    public synchronized int getCount()   { return count; }
}

// Immutable — inherently thread-safe (no synchronization needed)
final class ImmutableValue {
    private final int value;

    ImmutableValue(int value) { this.value = value; }

    public int getValue() { return value; }

    // "Increment" returns a NEW object
    public ImmutableValue increment() {
        return new ImmutableValue(value + 1);
    }
}

// Multiple threads can safely read ImmutableValue simultaneously
// No thread can modify it — nothing to synchronize
ImmutableValue v = new ImmutableValue(42);
// Thread 1: v.getValue() → always 42
// Thread 2: v.getValue() → always 42
// Thread 3: v.getValue() → always 42
// All safe — no synchronization needed
```

### Java's Immutable Classes in the JDK

Java's standard library uses immutability extensively:

| Class | Why Immutable |
|-------|--------------|
| `String` | Safe as Map keys, thread-safe sharing, string pool caching |
| `Integer`, `Long`, `Double` | Safe caching (-128 to 127 for Integer) |
| `LocalDate`, `LocalTime` | Thread-safe date/time calculations |
| `BigDecimal`, `BigInteger` | Precise, thread-safe mathematical operations |
| `URI`, `URL` (URI parts) | Safe sharing as resource identifiers |

---

## 11. Real-World Examples of Immutability

### Example 1 — String is Immutable

```java
String s1 = "Hello";
String s2 = s1.toUpperCase(); // does NOT modify s1

System.out.println(s1); // "Hello" — unchanged
System.out.println(s2); // "HELLO" — new String object

// String methods ALWAYS return new Strings — never modify the original
String s3 = s1.replace("H", "J");
System.out.println(s1); // "Hello" — still unchanged
System.out.println(s3); // "Jello" — new object
```

### Example 2 — Complete Immutable Product Class

```java
import java.util.*;

final class Product {
    private final String       productId;
    private final String       name;
    private final double       price;
    private final List<String> tags;      // mutable field
    private final Date         createdAt; // mutable field

    public Product(String productId, String name,
                   double price, List<String> tags, Date createdAt) {
        // Validate
        if (productId == null || productId.trim().isEmpty())
            throw new IllegalArgumentException("ProductId required");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");

        this.productId = productId;
        this.name      = name;
        this.price     = price;
        this.tags      = Collections.unmodifiableList(new ArrayList<>(tags));
        this.createdAt = new Date(createdAt.getTime()); // deep copy
    }

    public String       getProductId() { return productId; }
    public String       getName()      { return name; }
    public double       getPrice()     { return price; }
    public List<String> getTags()      { return tags; } // unmodifiable view
    public Date         getCreatedAt() {
        return new Date(createdAt.getTime()); // deep copy
    }

    // "With" methods return new objects
    public Product withPrice(double newPrice) {
        return new Product(productId, name, newPrice,
                           new ArrayList<>(tags),
                           new Date(createdAt.getTime()));
    }

    public Product withTag(String newTag) {
        List<String> newTags = new ArrayList<>(tags);
        newTags.add(newTag);
        return new Product(productId, name, price, newTags,
                           new Date(createdAt.getTime()));
    }

    @Override
    public String toString() {
        return "Product{id='" + productId + "', name='" + name
             + "', price=" + price + ", tags=" + tags + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return productId.equals(p.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode(); // stable — productId never changes
    }
}
```

---

## 12. Key Rules & Common Mistakes

### Rules Summary

| Rule | Purpose |
|------|---------|
| `final` class | Prevents subclass from adding mutability |
| `private final` fields | No external access, no reassignment |
| No setters | No way to change state from outside |
| Deep copy in constructor | Caller cannot mutate internal state after construction |
| Deep copy in getters | Caller cannot mutate internal state via returned reference |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Class not final
class NotTrulyImmutable {  // no final → can be subclassed and broken
    private final int value;
    NotTrulyImmutable(int v) { this.value = v; }
    public int getValue() { return value; }
}

// ❌ MISTAKE 2 — Field not final
final class AlmostImmutable {
    private int value; // not final → can be changed internally
    AlmostImmutable(int v) { this.value = v; }
    // even without setter, a private method could change this
}

// ❌ MISTAKE 3 — Shallow copy in constructor
final class WrongImmutable {
    private final List<String> items;
    WrongImmutable(List<String> items) {
        this.items = items; // ❌ shallow copy — caller can mutate!
    }
}

// ❌ MISTAKE 4 — Returning internal mutable reference in getter
final class AlsoWrong {
    private final List<String> items = new ArrayList<>();
    public List<String> getItems() {
        return items; // ❌ caller gets direct reference — can mutate!
    }
}

// ❌ MISTAKE 5 — Date not deep copied
final class DateMistake {
    private final Date date;
    DateMistake(Date d) { this.date = d; } // ❌ shallow copy
    public Date getDate() { return date; } // ❌ returns internal ref
}

// ✅ ALL CORRECT PATTERNS:
final class TrulyImmutable {
    private final String       name;   // String is immutable — safe
    private final int          value;  // primitive — safe
    private final List<String> items;  // mutable — needs deep copy
    private final Date         date;   // mutable — needs deep copy

    TrulyImmutable(String name, int value,
                   List<String> items, Date date) {
        this.name  = name;
        this.value = value;
        this.items = new ArrayList<>(items);           // deep copy ✅
        this.date  = new Date(date.getTime());          // deep copy ✅
    }

    public String       getName()  { return name; }    // immutable — safe ✅
    public int          getValue() { return value; }   // primitive — safe ✅
    public List<String> getItems() {
        return new ArrayList<>(items);                 // deep copy ✅
    }
    public Date         getDate()  {
        return new Date(date.getTime());               // deep copy ✅
    }
}
```

---

## 13. Quick Revision Cheatsheet

```
WHAT IS IMMUTABILITY?
──────────────────────────────────────────────────────────────
 Object state cannot change after construction
 Every field is set in constructor — frozen forever
 "What you create is what you get — always"

FOUR RULES (ALL required for true immutability)
──────────────────────────────────────────────────────────────
 1. final class       → prevents subclass adding mutability
 2. private final fields → no access, no reassignment
 3. No setters        → no way to modify from outside
 4. Deep copy         → for mutable fields ONLY
    a) In constructor: new ArrayList<>(param) not = param
    b) In getter:      return new ArrayList<>(field) not field

WHEN IS DEEP COPY NEEDED?
──────────────────────────────────────────────────────────────
 Needed for:  List, ArrayList, Date, byte[], int[],
              any mutable object field
 NOT needed:  String, Integer, primitives (int, double, etc.),
              other immutable classes

SHALLOW vs DEEP COPY
──────────────────────────────────────────────────────────────
 Shallow → copies reference (address)
           both variables point to SAME object
           mutation through one affects the other — DANGEROUS

 Deep    → creates NEW object with same content
           completely independent copy
           mutation of one does NOT affect the other — SAFE

THREAD SAFETY
──────────────────────────────────────────────────────────────
 Mutable object → needs synchronization in multithreading
 Immutable object → inherently thread-safe
                    multiple threads can read simultaneously
                    no locks needed, no race conditions possible

JAVA'S OWN IMMUTABLE CLASSES
──────────────────────────────────────────────────────────────
 String, Integer, Long, Double, Boolean (wrapper classes)
 BigDecimal, BigInteger
 LocalDate, LocalTime, LocalDateTime (java.time package)

BENEFITS OF IMMUTABILITY
──────────────────────────────────────────────────────────────
 ✅ Thread-safe without synchronization
 ✅ Safe to share and cache
 ✅ Reliable as Map keys and Set elements (stable hash)
 ✅ Predictable — no hidden mutations
 ✅ Simpler to reason about and test
```

---

*End of Notes*
