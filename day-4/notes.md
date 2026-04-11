# 📚 Java Primitive Data Types & Variables

## 🤔 What are Data Types?
Data types define what kind of values a variable can hold - numbers, text, true/false, etc.

## 📦 What is a Variable?

A **variable** is a named container that stores a value in computer memory. Think of it like a labeled box where you can put something inside.

### Variable Declaration Syntax:
```java
dataType variableName = value;
```

### Real-World Examples:
```java
int age = 25;                    // Variable 'age' stores the number 25
String name = "John Doe";        // Variable 'name' stores text
double height = 5.9;             // Variable 'height' stores a decimal number
boolean isStudent = true;        // Variable 'isStudent' stores true/false
```

### Why Variables?
- **Store data** - Keep values in memory to use later
- **Give data meaning** - `age` is clearer than just `25`
- **Reuse values** - Use the same variable multiple times in your code
- **Update values** - Change what's stored in the variable

### Variable Naming Rules:
- ✅ Can contain: letters, numbers, underscores `_`, dollar sign `$`
- ❌ Cannot start with: a number
- 🟢 Must follow: camelCase convention (first word lowercase, rest uppercase)

```java
// ✅ Good variable names
int myAge = 25;
String firstName = "John";
boolean isPassed = true;
double pricePerItem = 9.99;

// ❌ Bad variable names
int 1age = 25;              // ❌ Starts with number
String first-name = "John"; // ❌ Contains hyphen
int MYAGE = 25;             // ❌ All caps (reserved for constants)
```

## 🔢 Primitive Data Types - Whole Numbers (Integers)

### 🎯 Byte
- Stores small whole numbers
- Range: **-128 to 127**
- We showed the value and its minimum/maximum limits using `Byte.MAX_VALUE` and `Byte.MIN_VALUE`

```java
byte age = -128;
System.out.println(age);               // Output: -128
System.out.println(Byte.MAX_VALUE);    // Output: 127
System.out.println(Byte.MIN_VALUE);    // Output: -128
```

### 📊 Short
- Stores larger whole numbers than byte
- Range: **-32,768 to 32,767**
- We assigned 20000 and printed its MAX and MIN values to see its range

```java
short num = 20000;
System.out.println(Short.MAX_VALUE);    // Output: 32767
System.out.println(Short.MIN_VALUE);    // Output: -32768
```

### ✨ Int (Default for Whole Numbers)
- Default type for whole numbers in Java
- Stores even larger values than short
- Range: **-2,147,483,648 to 2,147,483,647**
- We printed `Integer.MAX_VALUE` and `Integer.MIN_VALUE` to show the range

```java
System.out.println(Integer.MAX_VALUE);    // Output: 2147483647
System.out.println(Integer.MIN_VALUE);    // Output: -2147483648
```

### 🔳 Long (For Very Large Numbers)
- For very large numbers that exceed int range
- Range: **-9,223,372,036,854,775,808 to 9,223,372,036,854,775,807**
- ⚠️ **Why 'l' suffix is needed:** By default, Java treats all whole numbers as `int` type. When you write a number like 2147483648, Java tries to treat it as an int, but it's too large to fit. Adding the 'l' suffix tells Java: "This is a long number, not an int"
- We printed `Long.MAX_VALUE` and `Long.MIN_VALUE` to show the exact range limits

```java
// Without 'l' suffix → ERROR! (too large for int)
// long number = 2147483648;    // ❌ Compilation error!

// With 'l' suffix → Works! ✅
long number = 2147483648l;
System.out.println(Long.MAX_VALUE);    // Output: 9223372036854775807
System.out.println(Long.MIN_VALUE);    // Output: -9223372036854775808
```

## 🟰 Primitive Data Types - Decimal Numbers

### 🌊 Float (Single Precision)
- For decimal/fractional numbers (like 3.14, 10.5, etc.)
- **Range:** Approximately **1.4E-45 to 3.4E+38**
  - E-45 means: 0.00000000000000000000000000000000000000000014 (1.4 × 10^-45) - extremely small!
  - E+38 means: 340,000,000,000,000,000,000,000,000,000,000,000,000 (3.4 × 10^38) - extremely large!
  - The "E" is scientific notation - used because these numbers are too long to write
- **Precision:** Can store about **6-7 decimal digits** accurately
- ⚠️ **Why 'f' suffix is needed:** By default, Java treats all decimals as `double` type. Adding the 'f' suffix tells Java: "This is a float, not a double"

```java
// Without 'f' suffix → Treated as double ❌
double result = 10.1234667654523654255;    // Less accurate, more memory

// With 'f' suffix → Treated as float ✅
float num1 = 10.1234667654523654255f;
System.out.println(num1);                  // Output: 10.123467
System.out.println(Float.MAX_VALUE);       // Output: 3.4028235E38
System.out.println(Float.MIN_VALUE);       // Output: 1.4E-45
```

### 🌟 Double (Double Precision) - DEFAULT!
- Also for decimal numbers, but MORE PRECISE than float
- This is the **default** for decimal numbers in Java
- **Range:** Approximately **4.9E-324 to 1.7E+308**
  - E-324 means: extremely small decimal (4.9 × 10^-324) - even smaller than float!
  - E+308 means: extremely large number (1.7 × 10^308) - much larger than float!
  - Much larger range than float!
- **Precision:** Can store about **15-16 decimal digits** accurately (much better than float's 6-7 digits)
- ✅ **No suffix needed:** By default, whenever you write a decimal like 10.5 or 3.14, Java automatically treats it as double
- Used for scientific calculations and banking (better accuracy)

```java
// Without 'f' suffix → Automatically treated as double ✅
double num2 = 10.1234667654523654255;     // More precise and accurate!
System.out.println(num2);                  // Output: 10.123466765452365
System.out.println(Double.MAX_VALUE);      // Output: 1.7976931348623157E308
System.out.println(Double.MIN_VALUE);      // Output: 4.9E-324
```

## 🔤 Primitive Data Types - Other Types

### 🔡 Char (Character)
- Stores a single character (like 'A', 'b', '5', '@')
- Range: 0 to 65,535 (Unicode characters)
- We didn't put any value here yet

```java
char letter = 'A';
char digit = '5';
char symbol = '@';
```

### ✔️ Boolean (True/False)
- Only two values: **true** or **false**
- Used for conditions and decisions
- Takes 1 bit (implementation dependent)

```java
boolean b = true;       // ✅ True
boolean b2 = false;      // ❌ False
boolean isStudent = true;
if (isStudent) {
    System.out.println("Welcome to class!");
}
```

## 🔬 Understanding Scientific Notation (E notation)

When dealing with very large or very small decimal numbers, Java uses scientific notation to show ranges:

### 📈 What does "E" mean?
- **E+** means: "multiply by 10 to the power of..." (makes number BIGGER) 📈
  - 1.4E+2 = 1.4 × 10² = 140
  - 3.4E+38 = 3.4 × 10³⁸ = 340,000,000,000,000,000,000,000,000,000,000,000,000

- **E-** means: "divide by 10 to the power of..." (makes number SMALLER) 📉
  - 1.4E-2 = 1.4 × 10⁻² = 0.014
  - 4.9E-324 = 4.9 × 10⁻³²⁴ = an EXTREMELY tiny number with hundreds of zeros!

### 🧮 How to Calculate E Notation

**🔼 For E+ (Large numbers) - Move decimal RIGHT:**
```
3.4E+5 = 340,000
  ↓ Move decimal 5 places to the right
  3.4 → 34 → 340 → 3400 → 34000 → 340000

1.7E+10 = 17,000,000,000
  ↓ Move decimal 10 places to the right
```

**🔽 For E- (Small numbers) - Move decimal LEFT:**
```
1.4E-3 = 0.0014
  ↓ Move decimal 3 places to the left
  1.4 → 0.14 → 0.014 → 0.0014

4.9E-4 = 0.00049
  ↓ Move decimal 4 places to the left
```

### 📊 Summary of Ranges

| Type | Range | Precision |
|------|-------|-----------|
| Float | ±1.4E-45 to ±3.4E+38 | ~6-7 decimal places |
| Double | ±4.9E-324 to ±1.7E+308 | ~15-16 decimal places |

## 🎯 Key Takeaways

### 1️⃣ **Default Types in Java:**
```java
int num = 100;          // 🟢 All whole numbers default to int
double decimal = 3.14;  // 🟢 All decimals default to double
```
- By default, Java treats all whole numbers as `int`
- By default, Java treats all decimals as `double`

### 2️⃣ **When Suffixes are Needed:**
```java
long bigNumber = 2147483648l;    // ⚠️ Need 'l' - tells Java it's long, not int
float smallDecimal = 10.5f;      // ⚠️ Need 'f' - tells Java it's float, not double
```
- Use `l` suffix for **long** numbers (example: `2147483648l`)
- Use `f` suffix for **float** numbers (example: `10.5f`)

### 3️⃣ **Choose the Right Type:**

**For Whole Numbers:**
```
byte (tiny) → short (small) → int (normal) → long (huge)
-128~127     -32K~32K       -2.1B~2.1B    -9.2Q~9.2Q
```

**For Decimals:**
```
float (less accurate)      vs      double (more accurate)
6-7 digits precision            15-16 digits precision
Faster, less memory            Slower, more memory, better for math
```

### 4️⃣ **Understanding E Notation:**
- 🔼 **E+** shows very large numbers (E+38 = 10³⁸)
- 🔽 **E-** shows very small numbers (E-45 = 10⁻⁴⁵)
- Use when numbers are too long to write normally!
