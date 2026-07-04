# Java Objects, Memory Management & Parameter Passing — Comprehensive Notes

---

## Table of Contents
1. [Object Memory & Structure](#1-object-memory--structure)
2. [How References Work — Stack vs Heap](#2-how-references-work--stack-vs-heap)
3. [Call by Value vs Call by Reference](#3-call-by-value-vs-call-by-reference)
4. [Shallow Copy vs Deep Copy](#4-shallow-copy-vs-deep-copy)
5. [Key Rules & Common Mistakes](#5-key-rules--common-mistakes)
6. [Quick Revision Cheatsheet](#6-quick-revision-cheatsheet)

---

## 1. Object Memory & Structure

When an object is created using `new`, it is stored in the **Heap memory**.
Every Java object in the heap is not just the raw field data — it carries
additional overhead managed by the JVM.

### Total Object Size Breakdown

```
┌──────────────────────────────────────┐
│         OBJECT IN HEAP MEMORY        │
├──────────────────────────────────────┤
│  Object Header (12 bytes)            │
│  ├── Mark Word     (8 bytes)         │
│  │    → locking info                 │
│  │    → synchronization info         │
│  │    → garbage collection info      │
│  │    → hash code                    │
│  └── Class Pointer (4 bytes)         │
│       → points to class metadata     │
├──────────────────────────────────────┤
│  Actual Field Data                   │
│  → int, String, boolean etc.         │
├──────────────────────────────────────┤
│  Padding (variable)                  │
│  → extra bytes to make total size    │
│    a multiple of 8                   │
└──────────────────────────────────────┘
```

### Object Header Components

| Component      | Size    | Purpose                                                  |
|----------------|---------|----------------------------------------------------------|
| Mark Word      | 8 bytes | Stores locking state, GC metadata, identity hash code    |
| Class Pointer  | 4 bytes | Points to the class definition (metadata) in the JVM    |
| **Total Header** | **12 bytes** | Fixed overhead for every object                |

### Padding

- The JVM adds **padding bytes** at the end of an object so that the
  total object size is always a **multiple of 8 bytes**
- This is done for **CPU memory alignment** — modern CPUs read memory
  in chunks (words), and aligned data is processed significantly faster
- Padding carries no meaningful data; it is purely a performance optimization

### Example — Calculating Object Size

```java
class Demo {
    int x;      // 4 bytes
    boolean b;  // 1 byte
}

/*
 Header  → 12 bytes
 int x   →  4 bytes
 boolean →  1 byte
 ─────────────────
 Total   → 17 bytes
 Padding →  7 bytes  (to reach next multiple of 8 → 24)
 ─────────────────
 Final   → 24 bytes in heap
*/
```

---

## 2. How References Work — Stack vs Heap

Understanding where things live in memory is fundamental to understanding
how Java handles objects and parameters.

### Memory Layout

```
  STACK MEMORY                    HEAP MEMORY
 (fast, thread-local)            (shared, managed by GC)
┌─────────────────┐              ┌───────────────────────┐
│  main()         │              │                       │
│  ┌───────────┐  │              │   ┌───────────────┐   │
│  │ ref c1    │──┼──────────────┼──►│ Customer Obj  │   │
│  │ (address) │  │              │   │  name: "Rohit"│   │
│  └───────────┘  │              │   │  balance: 1000│   │
│                 │              │   └───────────────┘   │
│  ┌───────────┐  │              │                       │
│  │ int x = 5 │  │              │                       │
│  │ (value)   │  │              │                       │
│  └───────────┘  │              │                       │
└─────────────────┘              └───────────────────────┘
```

### Key Rules

| Memory Area | Stores                              | Lifetime                    |
|-------------|-------------------------------------|-----------------------------|
| **Stack**   | Primitive values, reference variables| Until method returns        |
| **Heap**    | Actual objects and their field data  | Until Garbage Collector runs|

- A **reference variable** on the stack stores the **address** (memory
  location) of the actual object living in the heap
- Multiple reference variables can point to the **same object** in heap
- The reference variable itself is small (just an address); the object it
  points to can be large

```java
Customer c1 = new Customer("Rohit", 1000);
Customer c2 = c1; // c2 holds the SAME address as c1
                  // Both point to ONE object in heap
c2.balance = 9999;
System.out.println(c1.balance); // 9999 — same object was modified
```

---

## 3. Call by Value vs Call by Reference

### The Golden Rule in Java

> **Java is strictly and always Call by Value.**
> There is no true Call by Reference in Java.

This is one of the most commonly misunderstood concepts in Java.

### 3.1 Primitives — Call by Value (straightforward)

When a **primitive** is passed to a method, a **copy of the value** is made.
The original variable is completely unaffected by anything done inside the method.

```java
class Main {
    static void change(int x) {
        x = 100; // Modifies only the LOCAL copy
    }

    public static void main(String[] args) {
        int a = 10;
        change(a);
        System.out.println(a); // 10 — original unchanged
    }
}
```

```
STACK during change():
┌──────────────────┐
│ main()           │
│  a = 10          │  ← original, untouched
├──────────────────┤
│ change()         │
│  x = 10 (copy)  │  ← x becomes 100, but a is separate
└──────────────────┘
```

### 3.2 Objects — Still Call by Value (copy of reference)

When an **object** is passed to a method:
- The **reference variable** (address) is **copied** and passed — this is still call by value
- But since both the original and the copy **point to the same heap object**,
  modifying the object's fields inside the method **does** affect the original

```java
class Customer {
    int balance;
    Customer(int b) { this.balance = b; }
}

class Main {
    static void modify(Customer c) {
        c.balance = 9999; // Modifies the actual object in heap
    }

    public static void main(String[] args) {
        Customer c1 = new Customer(1000);
        modify(c1);
        System.out.println(c1.balance); // 9999 — object was changed
    }
}
```

```
STACK                         HEAP
┌──────────────┐              ┌─────────────────┐
│ main()       │              │  Customer Obj   │
│  c1 ─────────┼──────────────►  balance: 1000  │
├──────────────┤              │  (→ 9999)       │
│ modify()     │              └─────────────────┘
│  c  ─────────┼─────────────────────▲
│  (copy of    │              (same object!)
│   address)   │
└──────────────┘
```

### 3.3 Why Reassigning the Reference Does NOT Affect the Original

Even though the object's contents can be changed, **reassigning the
reference** inside a method has no effect on the caller's reference:

```java
static void tryReassign(Customer c) {
    c = new Customer(0); // Points c to a NEW object — original c1 unaffected
}

public static void main(String[] args) {
    Customer c1 = new Customer(1000);
    tryReassign(c1);
    System.out.println(c1.balance); // 1000 — c1 still points to original object
}
```

This proves Java passes a **copy of the reference**, not the reference itself.

### Summary Table

| Scenario                         | What is copied        | Original affected? |
|----------------------------------|-----------------------|--------------------|
| Passing a primitive              | The value             | No                 |
| Passing an object, modifying fields | The reference (address) | Yes (same heap object) |
| Passing an object, reassigning reference | The reference (address) | No (copy was redirected)|

---

## 4. Shallow Copy vs Deep Copy

When copying objects in Java, there are two fundamentally different approaches
with very different behaviors.

### 4.1 Shallow Copy

In a shallow copy, a **new reference variable** is created but it points to
the **same object** in heap memory. No new object is created.

```java
Customer c1 = new Customer("Rohit", 1000);
Customer c2 = c1; // Shallow copy — c2 points to the SAME heap object
```

```
STACK                   HEAP
┌──────────┐           ┌─────────────────┐
│  c1  ────┼───────────►  Customer Obj   │
│  c2  ────┼───────────►  name: "Rohit"  │
└──────────┘           │  balance: 1000  │
                       └─────────────────┘
```

**Consequence:** Modifying through `c2` changes the object that `c1` sees too —
they are literally the **same object**.

```java
c2.balance = 5000;
System.out.println(c1.balance); // 5000 — c1 is affected!
```

### 4.2 Deep Copy

In a deep copy, a **brand new object** is created in the heap and all field
values from the original object are manually copied into it. The two objects
are completely **independent**.

```java
class Customer {
    String name;
    int balance;

    Customer(String name, int balance) {
        this.name    = name;
        this.balance = balance;
    }

    // Deep copy using copy constructor
    Customer(Customer other) {
        this.name    = other.name;
        this.balance = other.balance;
    }
}

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer("Rohit", 1000);
        Customer c2 = new Customer(c1); // Deep copy — NEW object created

        c2.balance = 5000;
        System.out.println(c1.balance); // 1000 — c1 is NOT affected
        System.out.println(c2.balance); // 5000
    }
}
```

```
STACK                   HEAP
┌──────────┐           ┌─────────────────┐
│  c1  ────┼───────────►  Customer Obj 1 │
└──────────┘           │  name: "Rohit"  │
                       │  balance: 1000  │
┌──────────┐           └─────────────────┘
│  c2  ────┼───────────►  Customer Obj 2 │
└──────────┘           │  name: "Rohit"  │
                       │  balance: 5000  │
                       └─────────────────┘
```

### Shallow vs Deep Copy — Comparison Table

| Feature                  | Shallow Copy              | Deep Copy                        |
|--------------------------|---------------------------|----------------------------------|
| New object created?      | No                        | Yes                              |
| Memory in heap           | Same object               | Two separate objects             |
| Independence             | No — linked to original   | Yes — fully independent          |
| Modification impact      | Affects original          | Does NOT affect original         |
| How to create            | `c2 = c1`                 | Copy constructor / manual copy   |
| Use case                 | When sharing is intended  | When isolation is required       |

---

## 5. Key Rules & Common Mistakes

### Rules to Remember

| Rule | Detail |
|------|--------|
| Java is always Call by Value | Even objects pass a copy of the reference, not the reference itself |
| Stack stores references | The reference variable (address) lives on the stack |
| Heap stores objects | The actual object with all its fields lives in the heap |
| Shallow copy = same object | Both variables point to one heap object |
| Deep copy = new object | A new independent object is created in heap |
| Object header = 12 bytes | Every object carries this fixed overhead |
| Padding ensures multiple of 8 | JVM adds bytes for CPU alignment optimization |

### Common Mistakes

```java
// ❌ MISTAKE 1 — Thinking object reassignment inside a method affects caller
static void swap(Customer a, Customer b) {
    Customer temp = a;
    a = b;
    b = temp;
    // ❌ This swaps LOCAL copies of references only
    // The caller's c1 and c2 are completely unaffected
}

// ❌ MISTAKE 2 — Treating shallow copy as independent
Customer c1 = new Customer("Rohit", 1000);
Customer c2 = c1;       // ❌ NOT a copy — same object
c2.name = "Mohit";
System.out.println(c1.name); // "Mohit" — unexpected!

// ✅ CORRECT — Deep copy for independence
Customer c2 = new Customer(c1); // Uses copy constructor
c2.name = "Mohit";
System.out.println(c1.name); // "Rohit" — original safe
```

---

## 6. Quick Revision Cheatsheet

```
OBJECT MEMORY STRUCTURE
──────────────────────────────────────────────────────────────
 Every heap object =  Header (12 bytes)
                    + Actual field data
                    + Padding (to make total a multiple of 8)

 Mark Word    (8 bytes) → GC info, hash code, locking state
 Class Pointer (4 bytes) → points to class metadata in JVM

STACK vs HEAP
──────────────────────────────────────────────────────────────
 Stack → stores primitives & reference variables (addresses)
 Heap  → stores actual objects and their field data
 References on stack point to objects in heap

CALL BY VALUE IN JAVA (always!)
──────────────────────────────────────────────────────────────
 Primitive passed  → copy of value       → original UNAFFECTED
 Object passed     → copy of reference   → fields AFFECTED
                                           reference reassign NOT affected

 Java has NO true call by reference.
 Object "mutation effect" happens because BOTH references
 point to the SAME heap object — not because of reference passing.

SHALLOW COPY vs DEEP COPY
──────────────────────────────────────────────────────────────
 Shallow → c2 = c1         → same heap object → changes affect both
 Deep    → c2 = new C(c1)  → new heap object  → fully independent

 Use deep copy when you need two separate, independent objects.
 Use shallow copy only when intentional sharing is required.
```

---

*End of Notes*
