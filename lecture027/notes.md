# Java Nested Classes — Comprehensive Notes
## Static Nested | Inner | Local | Anonymous Classes

---

## Table of Contents
1. [What Are Nested Classes & Why Use Them](#1-what-are-nested-classes--why-use-them)
2. [Types of Nested Classes](#2-types-of-nested-classes)
3. [Static Nested Classes](#3-static-nested-classes)
4. [Real-World Use Cases of Static Nested Classes](#4-real-world-use-cases-of-static-nested-classes)
5. [Inner Classes](#5-inner-classes)
6. [Memory Representation of Inner Class Objects](#6-memory-representation-of-inner-class-objects)
7. [Why Old Java Restricted Static Members in Inner Classes](#7-why-old-java-restricted-static-members-in-inner-classes)
8. [Local Classes](#8-local-classes)
9. [Effective Final Rule](#9-effective-final-rule)
10. [Anonymous Classes](#10-anonymous-classes)
11. [When to Use Each — Real System Guide](#11-when-to-use-each--real-system-guide)
12. [Key Rules & Common Mistakes](#12-key-rules--common-mistakes)
13. [Quick Revision Cheatsheet](#13-quick-revision-cheatsheet)

---

## 1. What Are Nested Classes & Why Use Them

### Definition

A **nested class** is a class defined **inside another class**.
The outer class is called the **enclosing class**.

```java
class Outer {
    // Nested class — defined inside Outer
    class Inner {
    }
}
```

### Why Nest Classes?

#### Reason 1 — Logical Grouping
When a class is only useful to one other class, nesting it keeps
related code together and signals that relationship clearly.

```java
// Without nesting — confusing namespace
class Engine { }        // Is this for Car? Plane? Boat?
class Car { }

// With nesting — crystal clear
class Car {
    class Engine { }    // Engine belongs to Car — obvious
}
```

#### Reason 2 — Encapsulation
A nested class can be made `private` — completely hidden from the
outside world. This is stronger encapsulation than any top-level
class can achieve.

```java
class LinkedList {
    private class Node {   // Node is an implementation detail
        int data;           // Nobody outside LinkedList needs to see Node
        Node next;
    }
}
```

#### Reason 3 — Cleaner Code / Avoiding Pollution
Instead of creating a full top-level class for something used only
once or only in one place, a nested class keeps the namespace clean.

#### Reason 4 — Access to Outer Class Members
Non-static nested classes (inner classes) can directly access
**all** members of the outer class — even private ones.
This eliminates unnecessary getter/setter plumbing.

---

## 2. Types of Nested Classes

```
NESTED CLASSES
│
├── STATIC NESTED CLASS
│       → Declared with static keyword
│       → No implicit reference to outer object
│       → Like a top-level class but scoped inside outer
│
└── NON-STATIC NESTED CLASS (Inner Classes)
        │
        ├── INNER CLASS (Member Inner Class)
        │       → Declared at class level, no static keyword
        │       → Has implicit reference to outer class object
        │       → Can access all outer members (even private)
        │
        ├── LOCAL CLASS
        │       → Declared INSIDE a method
        │       → Visible only within that method
        │       → Can access effectively final local variables
        │
        └── ANONYMOUS CLASS
                → No name — declared and instantiated in one expression
                → Used for one-time implementations
                → Common with interfaces and abstract classes
```

### Quick Comparison Table

| Type | Where Defined | `static`? | Outer Object Needed? | Access Outer Members? |
|------|--------------|-----------|---------------------|----------------------|
| Static Nested | Class level | Yes | No | Static members only |
| Inner Class | Class level | No | Yes | All (even private) |
| Local Class | Inside method | No | Yes (if non-static method) | Effectively final locals |
| Anonymous Class | Inline expression | No | Yes (if non-static) | Effectively final locals |

---

## 3. Static Nested Classes

### Definition

A static nested class is declared with the `static` keyword inside
another class. It behaves like a regular top-level class but is
**namespaced inside the outer class**.

### Key Characteristics

- Does **NOT** have an implicit reference to the outer class object
- Can be instantiated **without** an outer class object
- Can only directly access **static members** of the outer class
- Can have its own static and instance members
- Accessed via: `OuterClass.NestedClass`

### Syntax

```java
class Outer {
    private static int outerStaticField = 100;
    private int outerInstanceField = 200;

    static class StaticNested {
        void display() {
            System.out.println(outerStaticField);    // ✅ static — accessible
            // System.out.println(outerInstanceField); // ❌ instance — not accessible
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // No Outer object needed
        Outer.StaticNested nested = new Outer.StaticNested();
        nested.display();
    }
}
```

### Full Example — Builder Pattern

The most famous real-world use of static nested classes is the
**Builder Pattern**:

```java
class Person {
    // Final fields — set once via builder
    private final String firstName;
    private final String lastName;
    private final int    age;
    private final String email;
    private final String phone;

    // Private constructor — only Builder can call it
    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName  = builder.lastName;
        this.age       = builder.age;
        this.email     = builder.email;
        this.phone     = builder.phone;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int    getAge()       { return age; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }

    @Override
    public String toString() {
        return "Person{name='" + firstName + " " + lastName
             + "', age=" + age
             + ", email='" + email + "'"
             + ", phone='" + phone + "'}";
    }

    // ══════════════════════════════
    // STATIC NESTED BUILDER CLASS
    // ══════════════════════════════
    public static class Builder {
        // Required fields
        private final String firstName;
        private final String lastName;

        // Optional fields — have defaults
        private int    age   = 0;
        private String email = "";
        private String phone = "";

        // Builder constructor requires mandatory fields
        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName  = lastName;
        }

        // Each setter returns Builder for chaining
        public Builder age(int age) {
            if (age < 0 || age > 150)
                throw new IllegalArgumentException("Invalid age");
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            if (email != null && email.contains("@"))
                this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        // Terminal method — creates the Person
        public Person build() {
            return new Person(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Clean, readable object construction
        Person p1 = new Person.Builder("Rohit", "Sharma")
                              .age(25)
                              .email("rohit@email.com")
                              .phone("9876543210")
                              .build();

        // Only mandatory fields
        Person p2 = new Person.Builder("Priya", "Patel")
                              .build();

        System.out.println(p1);
        System.out.println(p2);
    }
}
```

### Why Builder Uses Static Nested?

- Builder needs to be instantiated **before** the Person object exists
  (`new Person.Builder(...)` before `.build()`)
- If Builder were an inner class, it would require a Person object
  to exist first — circular dependency
- Static nested class solves this perfectly — no outer object needed

---

## 4. Real-World Use Cases of Static Nested Classes

### Use Case 1 — Builder Pattern
As shown above — `Person.Builder`, `StringBuilder`, `AlertDialog.Builder`
in Android, `Request.Builder` in OkHttp.

### Use Case 2 — Entry in Collections

Java's `Map.Entry<K,V>` is a static nested interface inside `Map`:

```java
Map<String, Integer> map = new HashMap<>();
map.put("Java", 1);
map.put("Python", 2);

// Map.Entry — static nested interface
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " → " + entry.getValue());
}
```

### Use Case 3 — Grouping Related Config/Constants

```java
class HttpClient {
    // Static nested class for configuration
    public static class Config {
        int timeoutSeconds = 30;
        int maxRetries     = 3;
        boolean followRedirects = true;
        String userAgent   = "MyApp/1.0";

        public Config timeout(int seconds) {
            this.timeoutSeconds = seconds;
            return this;
        }

        public Config retries(int n) {
            this.maxRetries = n;
            return this;
        }
    }

    private Config config;

    public HttpClient(Config config) {
        this.config = config;
    }
}

// Clean usage
HttpClient client = new HttpClient(
    new HttpClient.Config()
        .timeout(10)
        .retries(5)
);
```

### Use Case 4 — Node in Data Structures

```java
class LinkedList<T> {
    // Static nested — Node does not need LinkedList instance
    private static class Node<T> {
        T    data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }
}
```

---

## 5. Inner Classes

### Definition

An **inner class** (non-static member class) is defined at class
level **without** the `static` keyword. It has an **implicit
reference** to the enclosing outer class object.

### Key Characteristics

- **Requires** an outer class object to exist before it can be created
- Has **direct access** to ALL outer class members — including `private`
- Each inner class object secretly holds a reference (`Outer.this`)
  to the outer object that created it
- Cannot have static members (in older Java — see Section 7)

### Syntax

```java
class Outer {
    private int x = 10;
    private String secret = "hidden data";

    class Inner {
        void display() {
            // Direct access to outer's private members
            System.out.println("x = " + x);
            System.out.println("secret = " + secret);
            System.out.println("Outer ref = " + Outer.this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Outer outer = new Outer();          // Step 1: create outer
        Outer.Inner inner = outer.new Inner(); // Step 2: create inner
        inner.display();
    }
}
```

### Full Example — Iterator Pattern

The classic use of inner classes is implementing `Iterator`:

```java
class NumberCollection {
    private int[] numbers;
    private int   size;

    public NumberCollection(int capacity) {
        numbers = new int[capacity];
        size    = 0;
    }

    public void add(int n) {
        if (size < numbers.length) numbers[size++] = n;
    }

    // Inner class — Iterator
    // Needs direct access to outer's private array
    class NumberIterator {
        private int cursor = 0;

        public boolean hasNext() {
            return cursor < size; // accesses outer's size directly
        }

        public int next() {
            if (!hasNext()) throw new RuntimeException("No more elements");
            return numbers[cursor++]; // accesses outer's numbers directly
        }

        public void reset() {
            cursor = 0;
        }
    }

    // Factory method to get an iterator
    public NumberIterator iterator() {
        return new NumberIterator();
    }
}

public class Main {
    public static void main(String[] args) {
        NumberCollection col = new NumberCollection(5);
        col.add(10);
        col.add(20);
        col.add(30);

        NumberCollection.NumberIterator it = col.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
```

### Name Conflict Resolution with Outer Members

```java
class Outer {
    int x = 100;

    class Inner {
        int x = 200; // same name as outer's x

        void show() {
            System.out.println(x);          // 200 — inner's x
            System.out.println(this.x);     // 200 — inner's x
            System.out.println(Outer.this.x); // 100 — outer's x
        }
    }
}
```

---

## 6. Memory Representation of Inner Class Objects

### What Actually Happens in Memory

When an inner class object is created, the JVM adds a **hidden
synthetic field** — a reference to the outer class object.
This is done automatically by the compiler.

```java
class Outer {
    private int value = 42;

    class Inner {
        void show() {
            System.out.println(value); // How does Inner access value?
        }
    }
}
```

The compiler transforms the Inner class to approximately:

```java
// What compiler ACTUALLY generates (simplified):
class Outer$Inner {
    // HIDDEN field — secretly added by compiler
    private final Outer this$0;  // reference to outer object

    // Constructor takes outer reference
    Outer$Inner(Outer outer) {
        this.this$0 = outer;
    }

    void show() {
        // Compiler transforms: value → this$0.value
        System.out.println(this$0.value);
    }
}
```

### Memory Diagram

```
STACK                   HEAP
─────────────           ──────────────────────────────────────────

outer ref ──────────►  Outer Object
                        ┌─────────────────────┐
                        │ value: 42            │
                        │ (other fields)       │
                        └─────────────────────┘
                                 ▲
inner ref ──────────►  Inner Object          │
                        ┌─────────────────────┤
                        │ this$0 ─────────────┘  (hidden ref to Outer)
                        │ (inner fields)       │
                        └─────────────────────┘
```

### Consequences of the Hidden Reference

#### Memory Leak Risk

```java
class Activity {    // Imagine this is an Android Activity (large object)
    private byte[] largeData = new byte[1024 * 1024]; // 1MB

    class DataProcessor {
        // This inner class secretly holds a reference to Activity
        // Even if Activity is "done", it CANNOT be garbage collected
        // as long as any DataProcessor object exists
        void process() {
            System.out.println("Processing...");
        }
    }
}

// Memory leak scenario:
Activity act = new Activity();
Activity.DataProcessor processor = act.new DataProcessor();

act = null; // We think Activity is eligible for GC

// BUT — processor still holds a hidden reference to the Activity object!
// Activity's 1MB cannot be freed. Memory leak!
processor.process(); // still works — because Activity object still alive
```

#### Solution — Use Static Nested Class When Outer Reference Not Needed

```java
class Activity {
    private byte[] largeData = new byte[1024 * 1024];

    // Static nested — NO hidden outer reference
    static class DataProcessor {
        void process() {
            System.out.println("Processing...");
        }
    }
}

Activity act = new Activity();
Activity.DataProcessor processor = new Activity.DataProcessor();

act = null;       // Activity NOW eligible for GC ✅
processor.process(); // DataProcessor has no reference to Activity
```

---

## 7. Why Old Java Restricted Static Members in Inner Classes

### The Old Rule (Pre-Java 16)

In Java versions before 16, **inner classes could not have static
members** (static fields or static methods). This caused confusion
because static nested classes COULD have them.

### Why This Restriction Existed

The reasoning was based on the **object lifecycle model**:

```
CORE ARGUMENT:
  Static members belong to the CLASS — they exist independently
  of any object and have a single shared copy.

  Inner class objects are TIED to a specific outer object.
  They cannot exist without an outer object.

  If inner class had static members:
  → Static member should exist ONCE (class-level)
  → But inner class concept implies: tied to outer object instance
  → How many copies of the static member exist?
    One per outer object? That is not what static means.
    One globally? But inner class is not independent.

  This conceptual tension led Java designers to say:
  "Inner classes cannot have static members."
```

### Illustration of the Conceptual Problem

```java
class Outer {
    class Inner {
        // OLD JAVA: ❌ This was NOT allowed
        static int counter = 0; // How many copies? One? One per Outer?

        // If you create:
        // Outer o1 = new Outer();
        // Outer o2 = new Outer();
        // Outer.Inner i1 = o1.new Inner();
        // Outer.Inner i2 = o2.new Inner();
        // counter belongs to Inner class globally
        // But Inner instances are tied to DIFFERENT Outer instances
        // This creates semantic confusion
    }
}
```

### The Modern Resolution (Java 16+)

Java 16 lifted this restriction. Inner classes can now have
static members because the language specification was clarified
to allow this with consistent semantics:

```java
// Java 16+ — ALLOWED
class Outer {
    class Inner {
        static int counter = 0;  // ✅ Java 16+ allows this

        static void staticMethod() {  // ✅ Java 16+ allows this
            System.out.println("Static in inner class");
        }

        Inner() {
            counter++;
        }
    }
}
```

### Static Constants Were Always Allowed (Compile-Time Constants)

```java
class Outer {
    class Inner {
        // ✅ Always allowed even in old Java — it is a compile-time constant
        // compiler inlines this, no true "static field" issue
        static final int MAX = 100;
        static final String NAME = "Inner";
    }
}
```

---

## 8. Local Classes

### Definition

A **local class** is a class declared **inside a method** (or a
constructor or initializer block). It is visible only within
that method — just like a local variable.

### Key Characteristics

- Defined inside a method body with the `class` keyword
- Visible **only within the enclosing method**
- Can access:
  - All members of the enclosing class (via outer reference)
  - Local variables of the method that are **effectively final**
- Cannot have `static` members (except compile-time constants)
- Cannot have access modifiers (`public`, `private`, etc.)

### Syntax

```java
class Outer {
    void someMethod() {
        int localVar = 10; // must be effectively final

        // Local class — defined inside method
        class LocalHelper {
            void assist() {
                System.out.println("LocalVar: " + localVar); // ✅
            }
        }

        LocalHelper helper = new LocalHelper(); // create inside same method
        helper.assist();

        // LocalHelper is NOT accessible here: (outside someMethod)
    }
}
```

### Full Example — Validation Processor

```java
class OrderProcessor {

    void processOrder(String orderId, double amount, String currency) {

        // Local class — only needed in this method
        class OrderValidator {
            private String errors = "";

            boolean validateOrderId() {
                if (orderId == null || orderId.trim().isEmpty()) {
                    errors += "OrderId cannot be empty. ";
                    return false;
                }
                if (!orderId.startsWith("ORD-")) {
                    errors += "OrderId must start with ORD-. ";
                    return false;
                }
                return true;
            }

            boolean validateAmount() {
                if (amount <= 0) {
                    errors += "Amount must be positive. ";
                    return false;
                }
                if (amount > 1_000_000) {
                    errors += "Amount exceeds maximum limit. ";
                    return false;
                }
                return true;
            }

            boolean validateCurrency() {
                String[] valid = {"INR", "USD", "EUR", "GBP"};
                for (String c : valid) {
                    if (c.equals(currency)) return true;
                }
                errors += "Invalid currency: " + currency + ". ";
                return false;
            }

            boolean isValid() {
                return validateOrderId()
                    && validateAmount()
                    && validateCurrency();
            }

            String getErrors() { return errors; }
        }

        // Use the local class
        OrderValidator validator = new OrderValidator();
        if (validator.isValid()) {
            System.out.println("Order " + orderId
                + " for " + currency + " " + amount + " is valid!");
            // proceed with processing
        } else {
            System.out.println("Validation failed: " + validator.getErrors());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor();
        processor.processOrder("ORD-001", 5000.0, "INR");
        processor.processOrder("", -100, "INVALID");
    }
}
```

### When Local Classes Make Sense

- When a helper class is needed **only inside one specific method**
- When the logic is complex enough to warrant a named class
  (unlike anonymous class which has no name and limited reuse)
- When you need to instantiate the helper **multiple times** within
  the method

---

## 9. Effective Final Rule

### What Is "Effectively Final"?

A local variable is **effectively final** if:
- It is declared `final`, OR
- Its value is **never changed after initialization**

The compiler treats it as if it were declared `final`, even if
the `final` keyword is not explicitly written.

### Why This Rule Exists

When a local class (or anonymous class or lambda) captures a
local variable, the method's stack frame may be gone by the
time the inner class uses that variable.

The inner class gets a **copy** of the variable's value at the
time of capture. If the variable could change after capture,
the inner class would have a stale copy — creating subtle bugs.

Java prevents this by requiring captured variables to be
effectively final.

```
STACK (during method execution)       HEAP (inner class object)
──────────────────────────────        ─────────────────────────
method frame:                          LocalClass object:
  int count = 5;  ──── captured ────►   copied value: 5
  (method finishes, frame destroyed)
                                        (still lives in heap)
                                        uses its copy: 5 ✅
```

If `count` changed to 10 after capture but the copy says 5 —
which value should the inner class use? Java avoids this ambiguity
by requiring the variable to never change.

### Examples

```java
class Demo {
    void method() {

        // CASE 1 — Explicitly final
        final int a = 10;

        // CASE 2 — Effectively final (never reassigned)
        int b = 20; // never reassigned below — effectively final

        // CASE 3 — NOT effectively final
        int c = 30;
        c = 40; // reassigned — NOT effectively final

        class LocalClass {
            void show() {
                System.out.println(a); // ✅ explicitly final
                System.out.println(b); // ✅ effectively final
                System.out.println(c); // ❌ COMPILE ERROR
                // Variable c is accessed from within inner class,
                // needs to be final or effectively final
            }
        }

        new LocalClass().show();
    }
}
```

### Common Workaround for Non-Final Variables

```java
class Demo {
    void method() {
        int[] counter = {0}; // array reference is effectively final
                             // even though array CONTENTS can change

        class LocalClass {
            void increment() {
                counter[0]++;          // ✅ modifying array content — allowed
                System.out.println(counter[0]);
            }
        }

        LocalClass lc = new LocalClass();
        lc.increment(); // 1
        lc.increment(); // 2
        lc.increment(); // 3
    }
}
```

---

## 10. Anonymous Classes

### Definition

An **anonymous class** is a class that is:
- **Declared and instantiated in a single expression**
- Has **no name**
- Must **extend a class or implement an interface**
- Used for **one-time, single-use implementations**

### Syntax

```java
// Extending a class anonymously
SuperClass obj = new SuperClass() {
    // override methods here
    @Override
    void method() {
        System.out.println("Anonymous implementation");
    }
};

// Implementing an interface anonymously
Interface obj = new Interface() {
    // implement methods here
    @Override
    public void doSomething() {
        System.out.println("Done");
    }
};
```

### Example 1 — Implementing an Abstract Class

```java
abstract class Greeting {
    abstract void greet(String name);

    void printLine() {
        System.out.println("─".repeat(30));
    }
}

public class Main {
    public static void main(String[] args) {

        // Anonymous class extending Greeting
        Greeting formal = new Greeting() {
            @Override
            void greet(String name) {
                printLine();                    // can call concrete methods
                System.out.println("Good day, " + name + ". Welcome.");
                printLine();
            }
        };

        // Different anonymous class — same abstract class
        Greeting casual = new Greeting() {
            @Override
            void greet(String name) {
                System.out.println("Hey " + name + "! Wassup!");
            }
        };

        formal.greet("Mr. Sharma");
        casual.greet("Rohit");
    }
}
```

### Example 2 — Implementing an Interface

```java
interface Comparator<T> {
    int compare(T a, T b);
}

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(
            Arrays.asList("Rohit", "Priya", "Amit", "Zara", "Bob")
        );

        // Anonymous class implementing Comparator
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length(); // sort by length
            }
        });

        System.out.println(names); // [Bob, Amit, Zara, Rohit, Priya]

        // Another anonymous class — sort alphabetically
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });

        System.out.println(names); // [Amit, Bob, Priya, Rohit, Zara]
    }
}
```

### Example 3 — GUI Event Handling (Classic Use)

```java
// In Swing/AWT — classic anonymous class usage for event listeners
/*
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked!");
        // handle the click
    }
});
*/
```

### Anonymous Class vs Lambda

Java 8 introduced lambdas, which replaced many anonymous class
uses — but ONLY for **functional interfaces** (interfaces with
exactly one abstract method):

```java
// Anonymous class — verbose
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running...");
    }
};

// Lambda — concise (same thing)
Runnable r2 = () -> System.out.println("Running...");

// Both produce equivalent results
r1.run();
r2.run();
```

Anonymous classes are still needed when:
- Implementing an interface with **multiple abstract methods**
- Extending an **abstract class** (not an interface)
- Needing to maintain **state** (fields) in the implementation
- Needing to override methods from a **concrete class**

---

## 11. When to Use Each — Real System Guide

### Decision Framework

```
DO YOU NEED A HELPER CLASS?
│
├── Is it related to the outer class?
│       YES → Consider nesting
│       NO  → Make it a top-level class
│
└── Does it need an outer class OBJECT to exist?
        │
        NO → Static Nested Class
        │    Examples: Builder, Node, Config, Entry
        │
        YES → Inner class family
              │
              ├── Used in MULTIPLE methods?
              │       YES → Inner Class (member level)
              │             Examples: Iterator, EventHandler
              │
              ├── Used in ONE method only?
              │       YES → Local Class
              │             Examples: Validator, Formatter, Processor
              │
              └── Used ONCE, one-time implementation?
                      YES → Anonymous Class
                            Examples: Callback, EventListener,
                                      one-time Comparator, Runnable
```

### Usage Frequency in Real Systems

| Type | Frequency | Where You See It |
|------|-----------|-----------------|
| Static Nested | Very Common | Builder pattern, data structure nodes, Map.Entry, config classes |
| Inner Class | Common | Iterator implementations, event handlers, internal helpers |
| Local Class | Rare | Complex single-method validators, specialized formatters |
| Anonymous Class | Was Common, now Less | Pre-Java 8 callbacks; still used for multi-method interfaces |

### Real Framework Examples

| Framework | Nested Class Usage |
|-----------|-------------------|
| Java Collections | `Map.Entry` (static nested interface) |
| Android SDK | `AlertDialog.Builder` (static nested) |
| OkHttp | `Request.Builder` (static nested) |
| Lombok | Generates Builder as static nested |
| Java Swing | Anonymous `ActionListener` implementations |
| Java Streams | Anonymous `Comparator` implementations |

---

## 12. Key Rules & Common Mistakes

### Rules Summary

| Type | Key Rules |
|------|-----------|
| Static Nested | No outer object needed; accesses only static outer members |
| Inner Class | Outer object required; accesses all outer members; hidden this$0 |
| Local Class | Method-scoped; captures effectively final variables only |
| Anonymous Class | No name; one-time use; must extend or implement |
| Effective Final | Variable never reassigned after init = effectively final |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Instantiating inner class without outer
Outer.Inner inner = new Outer.Inner(); // ❌ compile error
// ✅ CORRECT
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner(); // ✅

// ❌ MISTAKE 2 — Modifying local variable captured by local/anonymous class
void method() {
    int count = 0;
    Runnable r = new Runnable() {
        public void run() {
            count++; // ❌ count is not effectively final
        }
    };
}
// ✅ CORRECT — use array trick or AtomicInteger
int[] count = {0};
Runnable r = new Runnable() {
    public void run() {
        count[0]++; // ✅
    }
};

// ❌ MISTAKE 3 — Memory leak with inner class
class Outer {
    byte[] data = new byte[10 * 1024 * 1024]; // 10MB
    class Inner { }
}
// Storing Inner object keeps Outer alive — memory leak!
// ✅ Use static nested if you don't need outer reference

// ❌ MISTAKE 4 — Anonymous class for functional interface (verbose)
Runnable r = new Runnable() {
    public void run() { System.out.println("Run"); }
};
// ✅ Use lambda for single-method interfaces
Runnable r = () -> System.out.println("Run");
```

---

## 13. Quick Revision Cheatsheet

```
NESTED CLASS TYPES
──────────────────────────────────────────────────────────────
 Static Nested  → static keyword; no outer object needed;
                  only accesses static outer members;
                  instantiate: new Outer.Nested()

 Inner Class    → no static; outer object REQUIRED;
                  accesses ALL outer members (even private);
                  hidden this$0 reference to outer;
                  instantiate: outer.new Inner()

 Local Class    → inside a method; method-scoped only;
                  captures effectively final local variables;
                  no access modifiers allowed

 Anonymous Class→ no name; declared + instantiated together;
                  must extend class or implement interface;
                  one-time use; replaced by lambda for SAM interfaces

WHY NEST?
──────────────────────────────────────────────────────────────
 Logical grouping → class only relevant to one other class
 Encapsulation   → can be private — stronger hiding
 Access          → inner class sees all outer private members
 Clean code      → avoid polluting top-level namespace

MEMORY & INNER CLASS
──────────────────────────────────────────────────────────────
 Compiler adds hidden field: Outer this$0 to every inner class
 Inner object holds reference to outer object
 Outer object CANNOT be GC'd while inner object lives
 → Potential memory leak if inner class outlives outer
 → Solution: use static nested when outer ref not needed

EFFECTIVE FINAL RULE
──────────────────────────────────────────────────────────────
 Local/anonymous class captures local variables by COPYING them
 Variable must be effectively final (never reassigned)
 If value could change: copy would be stale → Java forbids it
 Workaround: use int[] array or AtomicInteger for mutable state

STATIC MEMBERS IN INNER CLASSES
──────────────────────────────────────────────────────────────
 Old Java (<16): static members NOT allowed in inner classes
 Reason: inner class tied to outer object → static semantics clash
 Exception: compile-time constants (static final primitives/String)
 Java 16+: restriction lifted — static members now allowed

USE EACH WHEN:
──────────────────────────────────────────────────────────────
 Static Nested → Builder pattern, Nodes, Config, Map.Entry
 Inner Class   → Iterator, EventHandler, multi-method helpers
 Local Class   → Complex single-method logic, one-method validator
 Anonymous     → One-time callbacks, Comparators (or use lambda)

REAL FREQUENCY:
 Static Nested  → Very common in production code
 Inner Class    → Common
 Local Class    → Rare
 Anonymous      → Less common since Java 8 lambdas
```

---
