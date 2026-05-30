# 🔁 Java Loops — Complete Deep Dive Notes (Beginner → Senior Developer)

---

# 📚 Table of Contents

1. Introduction to Loops
2. Why Loops Exist
3. Real World Analogy
4. Internal Working of Loops
5. Types of Loops in Java
6. while Loop
7. do-while Loop
8. for Loop
9. Enhanced for-each Loop
10. Loop Control Statements
11. Infinite Loops
12. Nested Loops
13. Labeled Loops
14. Loop Internals (Bytecode & JVM)
15. Performance Considerations
16. Common Interview Questions
17. Common Mistakes
18. Best Practices
19. Loop Patterns
20. Advanced Concepts
21. Time Complexity of Loops
22. When to Use Which Loop
23. Summary Cheat Sheet

---

# 1️⃣ Introduction to Loops

A loop is a control flow structure that repeatedly executes a block of code until a specified condition becomes false.

Without loops:

```java
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
```

With loops:

```java
for(int i = 0; i < 5; i++) {
    System.out.println("Hello");
}
```

Output:

```text
Hello
Hello
Hello
Hello
Hello
```

---

# 2️⃣ Why Loops Exist

Imagine you need to:

* Process 1 million records
* Read a file line by line
* Traverse an array
* Generate reports
* Process database rows

Writing code repeatedly is impossible.

Loops solve:

✅ Repetition

✅ Automation

✅ Scalability

✅ Cleaner Code

---

# 3️⃣ Real World Analogy

Imagine a security guard checking IDs.

```text
Check Person 1
Check Person 2
Check Person 3
...
Check Person N
```

Instead:

```text
WHILE people are in queue:
    Check ID
```

That's exactly how loops work.

---

# 4️⃣ Internal Working of Loops

Every loop consists of 4 components:

```text
Initialization
Condition
Execution
Update
```

Visual Flow:

```text
Initialization
      ↓
 Condition?
   /     \
True     False
 ↓          ↓
Execute    Exit
 ↓
Update
 ↓
Condition Again
```

---

# 5️⃣ Types of Loops in Java

Java provides:

| Loop     | Condition Check  |
| -------- | ---------------- |
| while    | Before execution |
| do-while | After execution  |
| for      | Before execution |
| for-each | Iteration based  |

---

# 6️⃣ while Loop

## Syntax

```java
while(condition) {
    // code
}
```

---

## Example

```java
int i = 1;

while(i <= 5) {
    System.out.println(i);
    i++;
}
```

Output:

```text
1
2
3
4
5
```

---

## Internal Execution

### Step 1

```java
i = 1
```

### Step 2

```java
i <= 5
```

True

### Step 3

Execute body

```java
System.out.println(i);
```

### Step 4

```java
i++;
```

### Repeat

Until condition becomes false.

---

## Memory Representation

```text
Stack Memory

i = 1
```

Each iteration:

```text
i = 2
i = 3
i = 4
i = 5
i = 6
```

Condition fails.

Loop exits.

---

## When to Use

Use when number of iterations is unknown.

Examples:

```java
Reading File
Polling API
Waiting for Input
Game Loops
```

---

# 7️⃣ do-while Loop

## Syntax

```java
do {
    // code
}
while(condition);
```

---

## Important Property

Body executes at least once.

---

## Example

```java
int i = 10;

do {
    System.out.println(i);
} while(i < 5);
```

Output:

```text
10
```

Even though condition is false.

---

## Why?

Execution order:

```text
Execute Body
      ↓
Check Condition
      ↓
Repeat?
```

---

## Internal Flow

```text
Body
 ↓
Condition
 ↓
Body
 ↓
Condition
```

---

## Use Cases

Menu Systems

```java
do {
   showMenu();
} while(choice != 0);
```

ATM Systems

Game Loops

User Input Validation

---

# 8️⃣ for Loop

Most commonly used loop.

---

## Syntax

```java
for(initialization;
    condition;
    update) {

}
```

---

## Example

```java
for(int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

Output:

```text
1
2
3
4
5
```

---

## Internal Conversion

Compiler conceptually treats:

```java
for(int i=1; i<=5; i++)
```

as:

```java
int i = 1;

while(i <= 5) {
    System.out.println(i);
    i++;
}
```

---

## Execution Order

```text
Initialization (once)

Condition

Body

Update

Condition

Body

Update
```

---

## Multiple Initializations

```java
for(int i=0,j=10; i<j; i++,j--) {
    System.out.println(i+" "+j);
}
```

---

## Infinite for Loop

```java
for(;;) {
    System.out.println("Running");
}
```

Equivalent:

```java
while(true)
```

---

## When to Use

Known iteration count.

Examples:

```java
Arrays
Lists
Fixed Repetitions
Algorithms
```

---

# 9️⃣ Enhanced for-each Loop

Introduced in Java 5.

---

## Syntax

```java
for(type variable : collection)
```

---

## Example

```java
int[] arr = {10,20,30};

for(int num : arr) {
    System.out.println(num);
}
```

Output:

```text
10
20
30
```

---

## Internal Conversion

Compiler converts:

```java
for(int num : arr)
```

to:

```java
for(int i=0; i<arr.length; i++) {
    int num = arr[i];
}
```

---

## Collections

```java
List<String> names = List.of("A","B","C");

for(String name : names) {
    System.out.println(name);
}
```

---

## Advantages

✅ Cleaner

✅ Readable

✅ Less Error Prone

---

## Limitation

Cannot easily access index.

---

# 🔟 Loop Control Statements

---

# break

Terminates loop immediately.

```java
for(int i=1;i<=10;i++) {

    if(i==5)
        break;

    System.out.println(i);
}
```

Output:

```text
1
2
3
4
```

---

## Internal Flow

```text
Condition
 ↓
break?
 ↓
Exit Loop
```

---

# continue

Skip current iteration.

```java
for(int i=1;i<=5;i++) {

    if(i==3)
        continue;

    System.out.println(i);
}
```

Output:

```text
1
2
4
5
```

---

## Internal Flow

```text
Condition
 ↓
continue?
 ↓
Update
 ↓
Next Iteration
```

---

# return

Exits entire method.

```java
for(int i=0;i<10;i++) {

    if(i==5)
        return;
}
```

Method stops completely.

---

# 1️⃣1️⃣ Infinite Loops

---

## while

```java
while(true) {

}
```

---

## for

```java
for(;;) {

}
```

---

## Accidental Infinite Loop

```java
int i=1;

while(i<=10) {
    System.out.println(i);
}
```

Missing:

```java
i++;
```

CPU usage becomes 100%.

---

# 1️⃣2️⃣ Nested Loops

Loop inside another loop.

---

## Example

```java
for(int i=1;i<=3;i++) {

    for(int j=1;j<=3;j++) {
        System.out.println(i+" "+j);
    }
}
```

Output:

```text
1 1
1 2
1 3
2 1
2 2
2 3
3 1
3 2
3 3
```

---

## Internal Execution

Outer loop:

```java
i=1
```

Inner loop:

```java
j=1,2,3
```

Then:

```java
i=2
```

Inner restarts.

---

## Complexity

```java
for(n)
   for(n)
```

Complexity:

```text
O(n²)
```

---

# 1️⃣3️⃣ Labeled Loops

Rare but useful.

---

## Syntax

```java
outer:

for(...) {

    for(...) {

        break outer;
    }
}
```

---

## Example

```java
outer:

for(int i=1;i<=3;i++) {

    for(int j=1;j<=3;j++) {

        if(i==2 && j==2)
            break outer;

        System.out.println(i+" "+j);
    }
}
```

---

## Why?

Break multiple loops at once.

---

# 1️⃣4️⃣ Loop Internals (JVM Perspective)

Java source:

```java
for(int i=0;i<5;i++)
```

Bytecode roughly becomes:

```text
ICONST_0
ISTORE_1

LOOP:

ILOAD_1
ICONST_5

IF_ICMPGE EXIT

BODY

IINC 1 1

GOTO LOOP

EXIT
```

---

## JVM Uses

```text
Conditional Jump Instructions
Goto Instructions
Local Variable Slots
```

Loops are fundamentally jump operations.

---

# 1️⃣5️⃣ Performance Considerations

---

## Cache Friendly Loops

Good:

```java
for(int i=0;i<arr.length;i++)
```

Sequential memory access.

CPU cache loves this.

---

## Bad Access Pattern

```java
Random Access
```

Cache misses increase.

---

## Avoid Repeated Calculations

Bad:

```java
for(int i=0;i<list.size();i++)
```

if size() is expensive.

Better:

```java
int size = list.size();

for(int i=0;i<size;i++)
```

---

# 1️⃣6️⃣ Common Interview Questions

### Difference between while and do-while?

| while           | do-while           |
| --------------- | ------------------ |
| Checks first    | Executes first     |
| May run 0 times | Runs at least once |

---

### Difference between for and while?

```text
for → Known iterations
while → Unknown iterations
```

---

### Difference between break and continue?

```text
break -> Exit loop
continue -> Skip iteration
```

---

### Can for loop be infinite?

Yes.

```java
for(;;)
```

---

# 1️⃣7️⃣ Common Mistakes

---

## Off-by-One Error

Wrong:

```java
for(int i=0;i<=arr.length;i++)
```

Exception:

```java
ArrayIndexOutOfBoundsException
```

Correct:

```java
i < arr.length
```

---

## Forgetting Update

```java
while(i<10) {

}
```

Infinite loop.

---

## Modifying Collection During for-each

```java
for(String s : list) {
    list.remove(s);
}
```

Throws:

```java
ConcurrentModificationException
```

---

# 1️⃣8️⃣ Best Practices

✅ Use for loop for counting

✅ Use while for unknown iterations

✅ Use for-each for collections

✅ Keep conditions simple

✅ Avoid deep nesting

✅ Use meaningful variable names

---

# 1️⃣9️⃣ Popular Loop Patterns

---

## Sum of Numbers

```java
int sum = 0;

for(int i=1;i<=100;i++) {
    sum += i;
}
```

---

## Factorial

```java
int fact = 1;

for(int i=1;i<=5;i++) {
    fact *= i;
}
```

---

## Reverse Number

```java
while(n > 0) {

}
```

---

## Star Pattern

```java
for(int i=1;i<=5;i++) {

    for(int j=1;j<=i;j++) {
        System.out.print("*");
    }

    System.out.println();
}
```

---

# 2️⃣0️⃣ Advanced Concepts

---

## Loop Unrolling

Compiler optimization.

Instead of:

```java
for(int i=0;i<100;i++)
```

Compiler may transform internally.

```java
i+=4
```

Reducing jump operations.

---

## JIT Optimizations

Hot loops are optimized by JVM.

Examples:

* Loop Unrolling
* Dead Code Elimination
* Escape Analysis
* Vectorization

---

## Streams Alternative

Traditional:

```java
for(int num : list)
```

Stream:

```java
list.stream()
    .forEach(System.out::println);
```

---

# 2️⃣1️⃣ Time Complexity of Loops

---

## Single Loop

```java
for(int i=0;i<n;i++)
```

Complexity:

```text
O(n)
```

---

## Nested Loop

```java
for(n)
   for(n)
```

Complexity:

```text
O(n²)
```

---

## Triple Nested

```java
for(n)
  for(n)
     for(n)
```

Complexity:

```text
O(n³)
```

---

## Halving Loop

```java
while(n > 1) {
    n /= 2;
}
```

Complexity:

```text
O(log n)
```

---

# 2️⃣2️⃣ When To Use Which Loop

| Situation            | Loop     |
| -------------------- | -------- |
| Fixed Count          | for      |
| Unknown Count        | while    |
| Execute Once Minimum | do-while |
| Collections          | for-each |
| Complex Traversal    | for      |
| File Reading         | while    |

---

# 2️⃣3️⃣ Ultimate Cheat Sheet

```java
// FOR
for(init; condition; update)

// WHILE
while(condition)

// DO WHILE
do {

} while(condition);

// FOREACH
for(Type item : collection)

// BREAK
break;

// CONTINUE
continue;

// INFINITE
for(;;)

while(true)
```

---

# 🎯 Senior Developer Takeaway

A loop is not just repetition.

At the JVM level, every loop is transformed into:

```text
Initialization
↓
Conditional Jump
↓
Execution
↓
State Update
↓
Backward Jump
```

Understanding loops deeply means understanding:

* Control Flow
* JVM Bytecode
* Time Complexity
* Memory Behavior
* CPU Cache Usage
* JIT Optimizations
* Algorithm Design

