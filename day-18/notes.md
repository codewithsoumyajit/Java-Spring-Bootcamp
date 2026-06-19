# 🚀 Functions in Java — Complete Deep Dive (Before OOP)

> **Prerequisite:** Arrays, Variables, Data Types, Loops, Conditions
>
> **Assumption:** We have **not learned OOP yet**, so every function in this note will be considered **static** and will be called from `main()`.

---

# 📖 Table of Contents

1. What are Functions?
2. Why Functions are Needed?
3. Anatomy of a Function
4. Types of Functions
5. Defining Functions
6. Calling Functions
7. Return Type
8. Parameters vs Arguments
9. Main Function
10. Scope of Variables
11. Function Memory Model
12. Function Call Stack
13. Function Overloading
14. varargs (`...`)
15. Chaining Functions
16. Recursive Function Calls
17. Best Practices
18. Interview Questions
19. Summary

---

# 1️⃣ What are Functions?

A **Function** is a reusable block of code designed to perform a specific task.

Instead of writing the same code repeatedly, we place it inside a function and call it whenever needed.

### Real Life Example

Imagine a Coffee Machine ☕

```
Input:
    Water
    Coffee Powder
    Sugar

          ↓

    [ Coffee Machine ]

          ↓

Output:
    Coffee
```

The machine hides all the internal steps.

Similarly:

```
Input
  ↓

Function

  ↓

Output
```

---

# Why Functions?

Without functions:

```java
System.out.println(10 + 20);
System.out.println(50 + 60);
System.out.println(100 + 200);
```

With functions:

```java
add(10, 20);
add(50, 60);
add(100, 200);
```

Benefits:

✅ Reusability

✅ Modularity

✅ Easy Maintenance

✅ Better Readability

✅ Easier Debugging

---

# 2️⃣ Anatomy of a Function

Example:

```java
public static int add(int a, int b)
{
    return a + b;
}
```

Let's break it down:

```java
public static int add(int a, int b)
```

| Part         | Meaning                      |
| ------------ | ---------------------------- |
| public       | Accessible everywhere        |
| static       | Belongs to class, not object |
| int          | Return type                  |
| add          | Function name                |
| int a, int b | Parameters                   |

---

# Visual Diagram

```text
public static int add(int a, int b)
│      │      │      │
│      │      │      └── Parameters
│      │      └───────── Function Name
│      └──────────────── Return Type
└─────────────────────── Access Modifier
```

---

# 3️⃣ Types of Functions

Functions can be classified based on:

## A. Parameters

### No Parameters

```java
static void greet()
{
    System.out.println("Hello");
}
```

---

### With Parameters

```java
static void greet(String name)
{
    System.out.println(name);
}
```

---

## B. Return Type

### No Return Value

```java
static void display()
{
    System.out.println("Java");
}
```

---

### Returning Value

```java
static int square(int x)
{
    return x * x;
}
```

---

# Four Major Categories

## 1. No Parameter + No Return

```java
static void hello()
{
    System.out.println("Hello");
}
```

---

## 2. Parameter + No Return

```java
static void printName(String name)
{
    System.out.println(name);
}
```

---

## 3. No Parameter + Return

```java
static int getNumber()
{
    return 10;
}
```

---

## 4. Parameter + Return

```java
static int add(int a, int b)
{
    return a + b;
}
```

---

# Diagram

```text
                 FUNCTIONS

                     │

       ┌─────────────┼─────────────┐

       │                           │

 Parameters                   No Parameters

       │                           │

 Return/No Return           Return/No Return
```

---

# 4️⃣ Defining Functions

Syntax:

```java
access_modifier static return_type functionName(parameters)
{
      // body
}
```

Example:

```java
static int multiply(int a, int b)
{
    return a * b;
}
```

---

# 5️⃣ Calling Functions

Function definition alone does nothing.

It must be called.

```java
static void greet()
{
    System.out.println("Hello");
}

public static void main(String[] args)
{
    greet();
}
```

Output:

```text
Hello
```

---

# Function Call Flow

```text
main()
  │
  ▼
greet()
  │
  ▼
Print Hello
  │
  ▼
Return to main()
```

---

# 6️⃣ Parameters vs Arguments

Students often confuse these.

## Parameters

Variables declared in function definition.

```java
static int add(int a, int b)
```

`a` and `b` are Parameters.

---

## Arguments

Actual values passed.

```java
add(10,20);
```

10 and 20 are Arguments.

---

# Visualization

```text
Function Definition

add(int a, int b)
        ▲      ▲
        │      │
   Parameters

Function Call

add(10,20)
    ▲   ▲
    │   │
 Arguments
```

---

# 7️⃣ Return Type

A function may produce a result.

Example:

```java
static int square(int x)
{
    return x*x;
}
```

Calling:

```java
int ans = square(5);
```

Memory:

```text
square(5)

5 * 5

returns

25
```

---

# Why return is important?

Bad:

```java
static void add(int a, int b)
{
    System.out.println(a+b);
}
```

Good:

```java
static int add(int a, int b)
{
    return a+b;
}
```

Now result can be reused.

```java
int x = add(10,20);
int y = add(30,40);
```

---

# 8️⃣ Main Function

Every Java program starts from:

```java
public static void main(String[] args)
{
}
```

---

# Why Main?

JVM needs an entry point.

When JVM starts:

```text
JVM
 │
 ▼
main()
```

---

# Breakdown

```java
public static void main(String[] args)
```

| Part          | Meaning                     |
| ------------- | --------------------------- |
| public        | JVM can access it           |
| static        | JVM can call without object |
| void          | Returns nothing             |
| main          | Special name                |
| String[] args | Command line arguments      |

---

# Execution Diagram

```text
JVM Starts

    │

    ▼

main()

    │

    ▼

Other Functions

    │

    ▼

Program Ends
```

---

# 9️⃣ Scope of Variables

Scope = Region where variable is accessible.

---

## Local Scope

```java
static void test()
{
    int x = 10;
}
```

`x` exists only inside `test()`.

---

Wrong:

```java
static void test()
{
    int x = 10;
}

public static void main(String[] args)
{
    System.out.println(x);
}
```

Compile Error ❌

---

# Block Scope

```java
if(true)
{
    int x = 5;
}
```

Outside block:

```java
System.out.println(x);
```

Error ❌

---

# Scope Diagram

```text
main()
{
      a

      if()
      {
           b
      }

      c
}
```

Visibility:

```text
a -> whole main()

b -> only if block

c -> below declaration
```

---

# 1️⃣0️⃣ Function Memory Model

When function is called:

Memory is allocated in Stack.

Example:

```java
add(10,20);
```

Memory:

```text
Stack

┌──────────────┐
│ add() Frame  │
├──────────────┤
│ a = 10       │
│ b = 20       │
└──────────────┘
```

After function finishes:

```text
Frame Removed
```

---

# 1️⃣1️⃣ Function Call Stack

Example:

```java
main()
{
    fun1();
}

fun1()
{
    fun2();
}

fun2()
{
}
```

---

# Call Sequence

```text
main()
  │
  ▼
fun1()
  │
  ▼
fun2()
```

---

# Stack Growth

```text
┌─────────┐
│ fun2()  │
├─────────┤
│ fun1()  │
├─────────┤
│ main()  │
└─────────┘
```

---

# Returning Back

```text
fun2 removed

┌─────────┐
│ fun1()  │
├─────────┤
│ main()  │
└─────────┘
```

Then:

```text
fun1 removed

┌─────────┐
│ main()  │
└─────────┘
```

---

# 1️⃣2️⃣ Function Overloading

Same function name.

Different parameters.

---

Example:

```java
static int add(int a, int b)
{
    return a+b;
}

static int add(int a, int b, int c)
{
    return a+b+c;
}
```

---

Calling:

```java
add(10,20);
add(10,20,30);
```

Compiler decides which version to call.

---

# Valid Overloading

Different:

✅ Number of Parameters

```java
add(int a,int b)

add(int a,int b,int c)
```

---

✅ Type of Parameters

```java
add(int a,int b)

add(double a,double b)
```

---

✅ Order

```java
show(int,String)

show(String,int)
```

---

# Invalid Overloading

Only changing return type.

```java
int add(int a,int b)

double add(int a,int b)
```

❌ Compile Error

Why?

Because call is identical:

```java
add(10,20);
```

Compiler cannot differentiate.

---

# Overloading Resolution

Example:

```java
static void test(int x)
{
}

static void test(double x)
{
}
```

Call:

```java
test(5);
```

Compiler chooses:

```java
test(int)
```

because exact match exists.

---

# 1️⃣3️⃣ varargs

Introduced to accept variable number of arguments.

---

Without varargs

```java
add(int a,int b)

add(int a,int b,int c)

add(int a,int b,int c,int d)
```

Many overloads needed.

---

With varargs

```java
static int add(int... nums)
{
    int sum = 0;

    for(int x : nums)
    {
        sum += x;
    }

    return sum;
}
```

---

Calling

```java
add(1,2);

add(1,2,3);

add(1,2,3,4,5);
```

All valid.

---

# What is `...` ?

Called:

### Variable Arguments

### Varargs

Syntax:

```java
int... nums
```

Compiler converts it into an array.

Conceptually:

```java
add(1,2,3)
```

becomes:

```java
add(new int[]{1,2,3});
```

---

# Memory Diagram

```text
add(1,2,3)

nums

┌───┐
│ 1 │
├───┤
│ 2 │
├───┤
│ 3 │
└───┘
```

---

# Rules of varargs

## Only One vararg

```java
fun(int... a, int... b)
```

❌ Invalid

---

## Must Be Last

```java
fun(int... x, int y)
```

❌ Invalid

Correct:

```java
fun(int y, int... x)
```

✅

---

# 1️⃣4️⃣ Chaining Functions

One function calling another.

---

Example

```java
static void A()
{
    B();
}

static void B()
{
    C();
}

static void C()
{
    System.out.println("Hello");
}
```

---

Call:

```java
A();
```

Flow:

```text
A()

↓

B()

↓

C()

↓

Print

↓

Return

↓

B()

↓

A()

↓

main()
```

---

# Complete Chaining Example

```java
public class Main
{
    static void C()
    {
        System.out.println("C");
    }

    static void B()
    {
        C();
        System.out.println("B");
    }

    static void A()
    {
        B();
        System.out.println("A");
    }

    public static void main(String[] args)
    {
        A();
    }
}
```

Output:

```text
C
B
A
```

---

# Stack Diagram

During C():

```text
┌───────┐
│ C()   │
├───────┤
│ B()   │
├───────┤
│ A()   │
├───────┤
│ main  │
└───────┘
```

---

# 1️⃣5️⃣ Recursive Functions (Preview)

A function calling itself.

```java
static void fun()
{
    fun();
}
```

Infinite recursion ❌

Eventually:

```text
StackOverflowError
```

---

Example:

```java
static void count(int n)
{
    if(n == 0)
        return;

    System.out.println(n);

    count(n-1);
}
```

Output:

```text
5
4
3
2
1
```

---

# Function Call vs Method

You may hear:

```text
Function
Method
```

Technically in Java:

Everything inside a class is called a **Method**.

Example:

```java
class Main
{
    static void greet()
    {
    }
}
```

`greet()` is technically a method.

But before OOP, we commonly say **function** because it behaves exactly like one.

---

# Best Practices

✅ Use meaningful names

```java
calculateSalary()
```

instead of

```java
cs()
```

---

✅ Keep one responsibility

Bad:

```java
calculateSalaryAndSendEmail()
```

Good:

```java
calculateSalary()

sendEmail()
```

---

✅ Prefer return values over printing

Good:

```java
return result;
```

Bad:

```java
System.out.println(result);
```

---

✅ Avoid global variables

Pass data through parameters.

---

# Interview Questions

### Q1 Why is `main()` static?

Because JVM needs to call it without creating an object.

---

### Q2 Can we overload `main()`?

Yes.

```java
public static void main(int x)
{
}
```

But JVM always starts with:

```java
public static void main(String[] args)
```

---

### Q3 Can we overload using return type only?

No.

---

### Q4 Is varargs internally an array?

Yes.

```java
int... nums
```

becomes

```java
int[] nums
```

---

### Q5 Where are local variables stored?

Inside the function's stack frame.

---

# Final Mental Model

```text
JVM
 │
 ▼
main()
 │
 ├── add()
 │
 ├── square()
 │
 ├── display()
 │
 └── calculate()

Each call creates

┌─────────────┐
│ Stack Frame │
└─────────────┘

Function Ends
      ↓

Frame Removed
```

---

# One-Line Summary

**A function (method) in Java is a reusable block of code that receives input through parameters, executes logic inside its own stack frame, optionally returns a value, supports overloading and varargs, and can call other functions through chaining while maintaining execution order using the Call Stack.** 🚀
