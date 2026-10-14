# Java Enums — Comprehensive Notes
## Type Safety | Internal Working | Advanced Features | Built-in Methods

---

## Table of Contents
1. [Problems with Traditional Constants](#1-problems-with-traditional-constants)
2. [What is an Enum?](#2-what-is-an-enum)
3. [Basic Enum Syntax](#3-basic-enum-syntax)
4. [Internal Working of Enum](#4-internal-working-of-enum)
5. [Enum in Switch Statements](#5-enum-in-switch-statements)
6. [Enum with Constructors, Variables & Methods](#6-enum-with-constructors-variables--methods)
7. [Abstract Methods in Enum](#7-abstract-methods-in-enum)
8. [Built-in Enum Methods](#8-built-in-enum-methods)
9. [Enum with Interface](#9-enum-with-interface)
10. [When to Use Enums](#10-when-to-use-enums)
11. [Key Rules & Common Mistakes](#11-key-rules--common-mistakes)
12. [Quick Revision Cheatsheet](#12-quick-revision-cheatsheet)

---

## 1. Problems with Traditional Constants

### Approach 1 — Using int Constants

Before enums existed (Java 1-4), developers used integer constants:

```java
// Traditional int constants
public class PaymentStatus {
    public static final int PENDING   = 0;
    public static final int SUCCESS   = 1;
    public static final int FAILED    = 2;
    public static final int REFUNDED  = 3;
}

// Usage
void processPayment(int status) {
    if (status == PaymentStatus.SUCCESS) {
        System.out.println("Payment done!");
    }
}
```

### Problems with int Constants

#### Problem 1 — No Type Safety

```java
// This compiles perfectly — NO error!
processPayment(100);   // 100 is not a valid status — but Java allows it
processPayment(-999);  // completely invalid — but compiles!
processPayment(PaymentStatus.SUCCESS); // valid

// The method signature says int — ANY int is accepted
// The compiler has NO way to restrict to only valid values
```

#### Problem 2 — Poor Readability

```java
// Reading logs or debug output:
System.out.println("Payment status: " + status);
// Output: "Payment status: 1"
// WHAT does 1 mean? Developer has to go back to the constants class to check
```

#### Problem 3 — No Namespace Protection

```java
// Two different int constants from different classes can clash
public class OrderStatus {
    public static final int PENDING = 0; // same value as PaymentStatus.PENDING
    public static final int SUCCESS = 1; // same value as PaymentStatus.SUCCESS
}

void process(int status) {
    // Is this PaymentStatus or OrderStatus? Compiler cannot tell!
    if (status == 1) { } // ambiguous
}
```

#### Problem 4 — Meaningless Arithmetic

```java
// Java allows this — mathematically valid, logically insane
int result = PaymentStatus.SUCCESS + PaymentStatus.FAILED;
// result = 3 (REFUNDED!) — compiler does not complain!
// Adding two statuses makes no sense but Java allows it
```

### Approach 2 — Using String Constants

```java
public class PaymentStatus {
    public static final String PENDING  = "PENDING";
    public static final String SUCCESS  = "SUCCESS";
    public static final String FAILED   = "FAILED";
}
```

### Problems with String Constants

```java
// Problem 1 — Still no type safety
processPayment("WHATEVER");    // compiles! Invalid value accepted
processPayment("success");     // typo — wrong case, no compile error

// Problem 2 — Performance
// String comparison uses .equals() — slower than == comparison
// String objects in heap — more memory than needed

// Problem 3 — Switch restrictions (older Java versions)
// Cannot use String in switch as reliably as enums
```

### Summary of Traditional Constants Problems

| Problem | int Constants | String Constants |
|---------|--------------|-----------------|
| Type safety | ❌ None | ❌ None |
| Compile-time validation | ❌ None | ❌ None |
| Readability in output | ❌ Just numbers | ✅ Readable |
| Performance | ✅ Fast | ❌ Slow (heap + .equals()) |
| Meaningful arithmetic | ❌ Allowed (dangerous) | ✅ Not applicable |

---

## 2. What is an Enum?

An **enum** (enumeration) is a special Java type introduced in
**Java 5** that represents a **fixed set of named constants**.

```java
enum PaymentStatus {
    PENDING, SUCCESS, FAILED, REFUNDED
}
```

### Core Benefits Over Traditional Constants

#### Benefit 1 — Complete Type Safety

```java
void processPayment(PaymentStatus status) { // takes ONLY PaymentStatus
    if (status == PaymentStatus.SUCCESS) {
        System.out.println("Payment done!");
    }
}

processPayment(PaymentStatus.SUCCESS);  // ✅ valid
processPayment(PaymentStatus.PENDING);  // ✅ valid
processPayment(100);                    // ❌ COMPILE ERROR
processPayment("SUCCESS");              // ❌ COMPILE ERROR
// Compiler ENFORCES that only valid PaymentStatus values are passed
```

#### Benefit 2 — Automatic Readable toString()

```java
PaymentStatus status = PaymentStatus.SUCCESS;
System.out.println(status); // "SUCCESS" — human readable automatically!
// No need to write toString() — built in
```

#### Benefit 3 — Works in Switch Statements

```java
switch (status) {
    case PENDING  -> System.out.println("Waiting...");
    case SUCCESS  -> System.out.println("Done!");
    case FAILED   -> System.out.println("Failed!");
    case REFUNDED -> System.out.println("Refunded!");
}
// IDE warns if you miss a case — better than magic numbers
```

---

## 3. Basic Enum Syntax

### Declaration

```java
// enum declaration — by convention, enum name is PascalCase
// constant names are ALL_CAPS (like constants)
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum LogLevel {
    DEBUG, INFO, WARNING, ERROR, CRITICAL
}

enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

### Using Enum Values

```java
public class Main {
    public static void main(String[] args) {

        // Assigning an enum value
        Day today = Day.MONDAY;

        // Printing — toString() automatically returns name
        System.out.println(today);           // MONDAY

        // Comparison — use == (not equals, though equals works too)
        if (today == Day.MONDAY) {
            System.out.println("Start of work week!");
        }

        // Enum as method parameter
        printDay(Day.SATURDAY);
    }

    static void printDay(Day day) {
        System.out.println("Today is: " + day);
    }
}
```

### Enum in a Class File

```java
// Enums can be declared:

// 1. In their own file (most common for complex enums)
// File: PaymentStatus.java
public enum PaymentStatus {
    PENDING, SUCCESS, FAILED, REFUNDED
}

// 2. Inside a class (for enums specific to that class)
public class Order {
    public enum Status { PLACED, SHIPPED, DELIVERED, CANCELLED }

    private Status status;

    public Order() { this.status = Status.PLACED; }
}

// 3. At top level alongside another class (less common)
```

---

## 4. Internal Working of Enum

### enum is a Class Under the Hood

The Java compiler transforms an enum into a regular class.
Understanding this explains ALL enum behaviors.

```java
// WHAT YOU WRITE:
enum PaymentStatus {
    PENDING, SUCCESS, FAILED, REFUNDED
}

// WHAT COMPILER GENERATES (simplified):
final class PaymentStatus extends java.lang.Enum<PaymentStatus> {

    // Each constant is a static final instance of the class itself!
    public static final PaymentStatus PENDING  = new PaymentStatus("PENDING",  0);
    public static final PaymentStatus SUCCESS  = new PaymentStatus("SUCCESS",  1);
    public static final PaymentStatus FAILED   = new PaymentStatus("FAILED",   2);
    public static final PaymentStatus REFUNDED = new PaymentStatus("REFUNDED", 3);

    // Private constructor — cannot create instances from outside
    private PaymentStatus(String name, int ordinal) {
        super(name, ordinal); // calls Enum base class constructor
    }

    // Compiler generates values() and valueOf() automatically
    public static PaymentStatus[] values() { ... }
    public static PaymentStatus valueOf(String name) { ... }
}
```

### Key Internal Facts

```
1. enum implicitly extends java.lang.Enum<E>
   → you CANNOT extend anything else in an enum
   → enum can IMPLEMENT interfaces

2. Each constant is a static final instance of the enum class
   → PaymentStatus.PENDING is an OBJECT of type PaymentStatus

3. Constructor is PRIVATE
   → you CANNOT do: new PaymentStatus() from outside
   → instances are only created inside the enum itself

4. enum is final
   → CANNOT be subclassed

5. Compiler adds:
   → ordinal (position: 0, 1, 2...)
   → name (the constant name as String)
   → values() method
   → valueOf() method
```

### Proof That Each Constant is an Object

```java
public class Main {
    public static void main(String[] args) {

        PaymentStatus p = PaymentStatus.SUCCESS;

        // getClass() proves it is an object
        System.out.println(p.getClass()); // class PaymentStatus

        // It IS-A Enum
        System.out.println(p instanceof Enum);   // true

        // It IS-A Object (Enum extends Object)
        System.out.println(p instanceof Object); // true

        // name() — returns constant name as String
        System.out.println(p.name());    // "SUCCESS"

        // ordinal() — returns position (0-based)
        System.out.println(p.ordinal()); // 1 (SUCCESS is at index 1)

        // toString() — same as name() by default
        System.out.println(p.toString()); // "SUCCESS"

        // == comparison works because there is only ONE instance per constant
        PaymentStatus p2 = PaymentStatus.SUCCESS;
        System.out.println(p == p2); // true — same singleton object!
    }
}
```

### Why == Works for Enum Comparison

Because each enum constant is a **singleton** — there is exactly
ONE instance of `PaymentStatus.SUCCESS` in the entire JVM.
When you use `==`, you are comparing references, and both
variables point to the SAME single object. This is always true
for enum constants.

---

## 5. Enum in Switch Statements

Enum and switch work naturally together:

```java
enum TrafficLight { RED, YELLOW, GREEN }

public class Main {
    public static void main(String[] args) {

        TrafficLight light = TrafficLight.RED;

        // Traditional switch
        switch (light) {
            case RED:
                System.out.println("STOP");
                break;
            case YELLOW:
                System.out.println("GET READY");
                break;
            case GREEN:
                System.out.println("GO");
                break;
        }

        // Modern switch expression (Java 14+)
        String action = switch (light) {
            case RED    -> "STOP";
            case YELLOW -> "GET READY";
            case GREEN  -> "GO";
        };
        System.out.println(action); // STOP

        // Exhaustive check — compiler warns if you miss a case
        // This is a major advantage over int/String switch
    }
}
```

---

## 6. Enum with Constructors, Variables & Methods

Since enum is a class internally, it can have fields, constructors,
and methods — making each constant carry its own data and behavior.

### Adding Fields and Constructor

```java
enum Planet {
    // Each constant calls the constructor with its values
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6),
    MARS   (6.421e+23, 3.3972e6);

    // Fields — each constant has its own values
    private final double mass;   // in kilograms
    private final double radius; // in metres

    // Constructor — called for each constant
    // MUST be private or package-private (cannot be public)
    Planet(double mass, double radius) {
        this.mass   = mass;
        this.radius = radius;
    }

    // Methods — can define any methods
    double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }

    double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }

    public double getMass()   { return mass; }
    public double getRadius() { return radius; }
}

public class Main {
    public static void main(String[] args) {
        double earthWeight = 75.0; // kg on Earth
        double mass = earthWeight / Planet.EARTH.surfaceGravity();

        for (Planet p : Planet.values()) {
            System.out.printf("Weight on %s = %.2f%n",
                              p, p.surfaceWeight(mass));
        }
        // Weight on MERCURY = 28.33
        // Weight on VENUS   = 67.89
        // Weight on EARTH   = 75.00
        // Weight on MARS    = 28.46
    }
}
```

### Practical Example — Payment Status with Code

```java
enum PaymentStatus {
    PENDING (0,  "Payment is being processed"),
    SUCCESS (1,  "Payment completed successfully"),
    FAILED  (2,  "Payment transaction failed"),
    REFUNDED(3,  "Amount has been refunded");

    private final int    code;
    private final String description;

    // Private constructor
    PaymentStatus(int code, String description) {
        this.code        = code;
        this.description = description;
    }

    public int    getCode()        { return code; }
    public String getDescription() { return description; }

    // Static method to find by code
    public static PaymentStatus fromCode(int code) {
        for (PaymentStatus status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    @Override
    public String toString() {
        return name() + "(code=" + code + ", desc='" + description + "')";
    }
}

public class Main {
    public static void main(String[] args) {

        PaymentStatus status = PaymentStatus.SUCCESS;

        System.out.println(status.getCode());        // 1
        System.out.println(status.getDescription()); // Payment completed successfully
        System.out.println(status);                  // SUCCESS(code=1, ...)

        // Look up by code (e.g., from database)
        PaymentStatus found = PaymentStatus.fromCode(2);
        System.out.println(found); // FAILED(code=2, ...)
    }
}
```

---

## 7. Abstract Methods in Enum

Each enum constant can provide its OWN implementation of an abstract method.
This is like each constant being an anonymous subclass.

```java
enum Operation {
    ADD {
        @Override
        public double apply(double x, double y) { return x + y; }

        @Override
        public String symbol() { return "+"; }
    },
    SUBTRACT {
        @Override
        public double apply(double x, double y) { return x - y; }

        @Override
        public String symbol() { return "-"; }
    },
    MULTIPLY {
        @Override
        public double apply(double x, double y) { return x * y; }

        @Override
        public String symbol() { return "*"; }
    },
    DIVIDE {
        @Override
        public double apply(double x, double y) {
            if (y == 0) throw new ArithmeticException("Division by zero");
            return x / y;
        }

        @Override
        public String symbol() { return "/"; }
    };

    // Abstract method — EVERY constant MUST implement this
    public abstract double apply(double x, double y);
    public abstract String symbol();
}

public class Main {
    public static void main(String[] args) {
        double x = 10, y = 3;

        for (Operation op : Operation.values()) {
            System.out.printf("%.1f %s %.1f = %.2f%n",
                x, op.symbol(), y, op.apply(x, y));
        }
        // 10.0 + 3.0 = 13.00
        // 10.0 - 3.0 = 7.00
        // 10.0 * 3.0 = 30.00
        // 10.0 / 3.0 = 3.33
    }
}
```

---

## 8. Built-in Enum Methods

### Methods from java.lang.Enum (inherited by all enums)

```java
enum Season { SPRING, SUMMER, AUTUMN, WINTER }

public class Main {
    public static void main(String[] args) {

        Season s = Season.SUMMER;

        // name() — returns exact constant name as String
        String name = s.name();
        System.out.println(name);          // "SUMMER"

        // ordinal() — returns 0-based position
        int position = s.ordinal();
        System.out.println(position);      // 1 (SUMMER is at index 1)

        // toString() — same as name() unless overridden
        System.out.println(s.toString());  // "SUMMER"

        // equals() — works but == is preferred for enums
        System.out.println(s.equals(Season.SUMMER));  // true
        System.out.println(s == Season.SUMMER);        // true (preferred)

        // compareTo() — compares ordinal values
        System.out.println(Season.SPRING.compareTo(Season.WINTER)); // negative (0 < 3)
        System.out.println(Season.WINTER.compareTo(Season.SPRING)); // positive (3 > 0)
    }
}
```

### Compiler-Generated Methods

```java
// values() — returns array of all enum constants in declaration order
Season[] allSeasons = Season.values();
for (Season season : allSeasons) {
    System.out.println(season.ordinal() + ": " + season);
}
// 0: SPRING
// 1: SUMMER
// 2: AUTUMN
// 3: WINTER

// valueOf(String) — converts String to enum constant
// CASE SENSITIVE — must match exactly
Season autumn = Season.valueOf("AUTUMN");
System.out.println(autumn);            // AUTUMN

// Season.valueOf("autumn") → IllegalArgumentException (wrong case)
// Season.valueOf("INVALID") → IllegalArgumentException (not a constant)
```

### EnumSet and EnumMap — Specialized Collections

```java
import java.util.*;

enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

// EnumSet — highly efficient Set for enum values (uses bit vector internally)
EnumSet<Day> weekend  = EnumSet.of(Day.SAT, Day.SUN);
EnumSet<Day> weekdays = EnumSet.range(Day.MON, Day.FRI);
EnumSet<Day> allDays  = EnumSet.allOf(Day.class);

System.out.println(weekend);  // [SAT, SUN]
System.out.println(weekdays); // [MON, TUE, WED, THU, FRI]

// EnumMap — highly efficient Map with enum keys
EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
schedule.put(Day.MON, "Team meeting");
schedule.put(Day.WED, "Code review");
schedule.put(Day.FRI, "Sprint retrospective");

for (Map.Entry<Day, String> entry : schedule.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

---

## 9. Enum with Interface

Enums can implement interfaces — giving all constants a shared
contract while allowing each to have its own implementation:

```java
interface Describable {
    String describe();
    boolean isActive();
}

enum ServerStatus implements Describable {
    RUNNING {
        @Override public String describe()  { return "Server is running normally"; }
        @Override public boolean isActive() { return true; }
    },
    STOPPED {
        @Override public String describe()  { return "Server has been stopped"; }
        @Override public boolean isActive() { return false; }
    },
    MAINTENANCE {
        @Override public String describe()  { return "Server under maintenance"; }
        @Override public boolean isActive() { return false; }
    };
}

public class Main {
    public static void main(String[] args) {
        for (ServerStatus status : ServerStatus.values()) {
            System.out.printf("%-15s | Active: %-5b | %s%n",
                status, status.isActive(), status.describe());
        }
        // RUNNING         | Active: true  | Server is running normally
        // STOPPED         | Active: false | Server has been stopped
        // MAINTENANCE     | Active: false | Server under maintenance
    }
}
```

---

## 10. When to Use Enums

### Use Enum When

```
✅ Fixed, known set of constants at compile time
   → Days of week, months, seasons

✅ Status tracking
   → Payment status, order status, server state

✅ Configuration options
   → Log levels (DEBUG, INFO, WARNING, ERROR)
   → Direction (NORTH, SOUTH, EAST, WEST)

✅ Type-safe alternatives to int/String constants
   → Any situation where you would use static final int/String

✅ Constants that carry additional data
   → Planet (with mass, radius), Operation (with symbol, logic)

✅ Switch/match patterns where exhaustiveness matters
   → Compiler warns about missing cases

✅ When you want singleton instances of a type
   → Each constant is exactly one instance — useful for patterns
```

### Do NOT Use Enum When

```
❌ Set of values is dynamic or grows at runtime
   → User-defined categories, database-driven options

❌ Values need to be extended by external code
   → Enum cannot be subclassed

❌ Very large number of constants (performance concern)
   → EnumSet/EnumMap help but consider alternatives

❌ The "constants" are really just data (use a database or config)
```

### Real-World Use Cases

```java
// ── LOG LEVELS ──
enum LogLevel { TRACE, DEBUG, INFO, WARN, ERROR, FATAL }

// ── HTTP METHODS ──
enum HttpMethod { GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS }

// ── ORDER STATUS ──
enum OrderStatus {
    PLACED, CONFIRMED, PACKED, SHIPPED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

// ── THREAD STATE (from Java itself) ──
// java.lang.Thread.State:
// NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED

// ── PLANET (from JDK documentation examples) ──
enum Planet { MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE }
```

---

## 11. Key Rules & Common Mistakes

### Rules Summary

| Rule | Detail |
|------|--------|
| Enum implicitly extends `java.lang.Enum` | Cannot extend another class |
| Enum CAN implement interfaces | Multiple interfaces allowed |
| Constructor must be private/package-private | Cannot instantiate from outside |
| Semicolon after last constant | Required when constructor/method/field follows |
| Constants declared first | Fields, constructors, methods come after |
| Abstract method forces each constant to implement | Powerful per-constant behavior |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Using ordinal() for business logic
enum Status { PENDING, SUCCESS, FAILED }

// Storing ordinal in database — DANGEROUS
int stored = Status.SUCCESS.ordinal(); // 1
// If you add a constant before SUCCESS:
// enum Status { CANCELLED, PENDING, SUCCESS, FAILED }
// Now SUCCESS.ordinal() = 2 — all stored values are WRONG!

// ✅ CORRECT — use a dedicated field
enum Status {
    PENDING(0), SUCCESS(1), FAILED(2);
    private final int code;
    Status(int code) { this.code = code; }
    public int getCode() { return code; }
}
// Reordering constants does not break stored codes

// ❌ MISTAKE 2 — valueOf() with wrong case
Season s = Season.valueOf("summer"); // IllegalArgumentException!
// valueOf is CASE SENSITIVE

// ✅ CORRECT
Season s = Season.valueOf("SUMMER"); // ✅
// Or: Season.valueOf(input.toUpperCase());

// ❌ MISTAKE 3 — Comparing with equals() instead of ==
if (status.equals(PaymentStatus.SUCCESS)) { } // works but unnecessary
// ✅ CORRECT — == is sufficient and preferred for enums
if (status == PaymentStatus.SUCCESS) { }

// ❌ MISTAKE 4 — Missing semicolon when constructor present
enum Color {
    RED, GREEN, BLUE  // ❌ no semicolon before constructor
    Color() { }       // compile error
}
// ✅ CORRECT
enum Color {
    RED, GREEN, BLUE; // ✅ semicolon required
    Color() { }
}

// ❌ MISTAKE 5 — Putting constants after methods
enum Wrong {
    public void method() { } // compile error — constants must come first
    RED, GREEN;
}
// ✅ CORRECT
enum Right {
    RED, GREEN;              // constants FIRST
    public void method() { } // then methods
}
```

---

## 12. Quick Revision Cheatsheet

```
WHAT IS ENUM?
──────────────────────────────────────────────────────────────
 Special class representing a FIXED SET of named constants
 Introduced in Java 5
 Provides type safety — compiler rejects invalid values
 Each constant is a SINGLETON object of the enum type

WHY ENUM OVER int/String CONSTANTS?
──────────────────────────────────────────────────────────────
 int constants:    no type safety, unreadable output, meaningless arithmetic
 String constants: no type safety, slower, case sensitive
 Enum:             compile-time type safety, readable, works in switch,
                   carries additional data, exhaustive switch checking

INTERNAL WORKING
──────────────────────────────────────────────────────────────
 Compiler generates: final class extending java.lang.Enum<E>
 Each constant:      public static final instance of itself
 Constructor:        always private
 Cannot:             extend another class (already extends Enum)
 Can:                implement interfaces

BUILT-IN METHODS (from java.lang.Enum)
──────────────────────────────────────────────────────────────
 name()       → exact constant name as String ("SUCCESS")
 ordinal()    → 0-based position (0, 1, 2...)
 toString()   → same as name() unless overridden
 compareTo()  → compares ordinal values
 equals()     → works; == preferred for enums
 values()     → array of all constants (compiler-generated)
 valueOf(str) → String to enum; CASE SENSITIVE; compiler-generated

ADVANCED FEATURES
──────────────────────────────────────────────────────────────
 Fields:           each constant carries its own data
 Constructor:      initializes fields for each constant
 Methods:          shared behavior or per-constant via abstract
 Abstract methods: each constant provides its own implementation
 Interfaces:       enum can implement any number of interfaces

CRITICAL RULES
──────────────────────────────────────────────────────────────
 Constants declared FIRST, then semicolon, then fields/methods
 Semicolon after last constant ONLY when fields/constructor follow
 Do NOT use ordinal() for database storage — fragile!
 Use a dedicated field (int code) instead of ordinal
 valueOf() is CASE SENSITIVE — use .toUpperCase() when needed
 == comparison is preferred over .equals() for enums

WHEN TO USE
──────────────────────────────────────────────────────────────
 ✅ Fixed known set: days, months, directions, log levels
 ✅ Status tracking: payment, order, server, thread state
 ✅ Type-safe replacement for int/String constants
 ✅ Constants with associated data or behavior
 ❌ Dynamic values that grow at runtime
 ❌ Values that need to be subclassed externally
```

---

*End of Notes*
