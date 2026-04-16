# Day 5: Char Data Type & Type Casting 📚✨

---

## 1. Character (char) Primitive Data Type 🔤

### What is `char`?
**char** (character) is a primitive data type that stores a **single character**. 

- **Keyword**: `char`
- **Size**: 16 bits (2 bytes)
- **Range**: 0 to 65,535 (Unicode characters)
- **Default Value**: `\u0000` (null character)
- **Wrapped Class**: `Character`

---

## 2. Ways to Declare and Initialize Char ✍️

### 2.1 ASCII Value (Numeric Representation) 🔢

**ASCII** = American Standard Code for Information Interchange

Each character has a numeric code:
- Digits ('0'-'9'): 48-57
- Uppercase letters ('A'-'Z'): 65-90
- Lowercase letters ('a'-'z'): 97-122

```java
// ASCII Numeric Declaration
char digit = 65;              // This is 'A'
char lowercase = 97;          // This is 'a'
System.out.println(digit);    // Output: A
System.out.println(lowercase); // Output: a
```

**ASCII Table (Common Characters):**
```
Number → Character
48-57  → 0-9 (Digits)
65-90  → A-Z (Uppercase)
97-122 → a-z (Lowercase)
32     → Space
33-47  → Special characters (!@#$%^&*...)
```

---

### 2.2 Direct Character Assignment 📝

Enclose the character in **single quotes** `' '`:

```java
char letter = 'A';
char symbol = '@';
char space = ' ';
char emoji = '😊';  // Unicode emoji!

System.out.println(letter);  // Output: A
System.out.println(symbol);  // Output: @
System.out.println(emoji);   // Output: 😊
```

---

### 2.3 Unicode Escape Sequence 🌍

**Unicode** = International character encoding standard (65,536 possible characters)

Format: `\uXXXX` where X = hexadecimal digit

```java
// Unicode Declaration (Escape Sequences)
char englishA = '\u0041';     // 'A' (Latin)
char bengaliA = '\u0985';     // 'অ' (Bengali অ)
char hindiA = '\u0905';       // 'अ' (Hindi अ)
char hindiE = '\u0907';       // 'इ' (Hindi इ)
char emoji = '\u1F600';       // '😀' (Grinning Face)
char indianRupee = '\u20B9';  // '₹' (Indian Rupee)

System.out.println(englishA);   // Output: A
System.out.println(bengaliA);   // Output: অ
System.out.println(hindiA);     // Output: अ
System.out.println(emoji);      // Output: 😀
System.out.println(indianRupee); // Output: ₹
```

**Unicode Range Examples:**
- `\u0000` to `\u007F` → ASCII (English)
- `\u0980` to `\u09FF` → Bengali
- `\u0900` to `\u097F` → Devanagari (Hindi, Sanskrit, Nepali)
- `\u1F300` to `\u1F9FF` → Emojis

---

### 2.4 Escape Sequences 🎭

Special characters that need backslash:

```java
char tab = '\t';              // Tab
char newline = '\n';          // New line
char backslash = '\\';        // Backslash \
char singleQuote = '\'';      // Single quote '
char doubleQuote = '\"';      // Double quote "
char backspace = '\b';        // Backspace
char carriageReturn = '\r';   // Carriage return

// Example
System.out.println("Hello\tWorld");     // Output: Hello	World
System.out.println("Line1\nLine2");     // Output: Line1
                                        //         Line2
```

---

## 3. Type Casting Explanation 🔄

### What is Type Casting?
**Type Casting** = Converting one data type to another

### Visual Representation:

```
        ┌─────────────────────────────────────────┐
        │                                         │
        │   WIDENING CONVERSION (Automatic)      │
        │   ═════════════════════════════════     │
        │   byte → short → int → long →          │
        │       ↓                    ↓             │
        │   float → double                       │
        │                                         │
        │   char ────────→ int                    │
        │                                         │
        └─────────────────────────────────────────┘
                            ↕
        ┌─────────────────────────────────────────┐
        │                                         │
        │   NARROWING CONVERSION (Manual)        │
        │   ════════════════════════════════      │
        │   double → float → long → int →        │
        │       ↑                    ↑             │
        │           short → byte                 │
        │                                         │
        │                    int ────→ char      │
        │                                         │
        └─────────────────────────────────────────┘
```

---

## 4. Widening Conversion (🟢 LOSSLESS - Automatic)

**Widening** = Converting to a **LARGER** data type
- **Automatic** (no casting required)
- **Lossless** = No data is lost

### Why Lossless? 🎯
Larger container holds all values of smaller type without loss.

```
Concept: 🍎 (5 apples) → 📦 (Box that holds 100 apples)
Result: ✅ No loss - all 5 apples fit perfectly!
```

### Examples:

```java
// Widening Conversions (AUTOMATIC)

// char to int
char letter = 'A';
int asciiValue = letter;      // Automatic widening
System.out.println(asciiValue); // Output: 65

// byte to int
byte num1 = 10;
int num2 = num1;              // Automatic widening
System.out.println(num2);     // Output: 10

// int to long
int num3 = 50000;
long num4 = num3;             // Automatic widening
System.out.println(num4);     // Output: 50000

// int to double
int num5 = 100;
double num6 = num5;           // Automatic widening
System.out.println(num6);     // Output: 100.0

// All conversions are safe - NO DATA LOSS!
```

---

## 5. Narrowing Conversion (🔴 LOSSY - Manual)

**Narrowing** = Converting to a **SMALLER** data type
- **Manual** (explicit casting required)
- **Lossy** = Data might be lost ⚠️

### Why Lossy? ⚠️
Smaller container may NOT hold all values of larger type.

```
Concept: 📦 (100 items) → 🍎 (Box that holds 5 items)
Result: ❌ LOSS! Only 5 items fit, 95 are discarded!
```

### Examples:

```java
// Narrowing Conversions (MANUAL - with explicit casting)

// int to char (with casting)
int asciiCode = 65;
char letter = (char) asciiCode;
System.out.println(letter);   // Output: A

// int to byte (with casting)
int bigNum = 200;
byte smallNum = (byte) bigNum; // Explicit cast required
System.out.println(smallNum);  // Output: -56 (DATA LOSS!)
                               // byte range: -128 to 127
                               // 200 overflows!

// double to int (with casting)
double decimal = 45.67;
int wholePart = (int) decimal;
System.out.println(wholePart); // Output: 45 (decimal part lost!)

// long to int (with casting)
long largeNum = 1234567890L;
int num = (int) largeNum;      // Explicit cast required
System.out.println(num);       // Output: 1234567890 or overflow
```

---

## 6. Narrowing vs Widening - Side by Side Comparison 📊

| Feature | Widening (Lossless) ✅ | Narrowing (Lossy) ❌ |
|---------|----------------------|-------------------|
| **Direction** | Smaller → Larger | Larger → Smaller |
| **Required** | Automatic ✓ | Manual Casting ✗ |
| **Data Loss** | None (Lossless) | Possible (Lossy) ⚠️ |
| **Casting Operator** | Not needed | (Type) required |
| **Safety** | Safe 🟢 | Risky 🔴 |
| **Examples** | `int i = 5; long l = i;` | `long l = 5L; int i = (int) l;` |

---

## 7. Complete Char → Int & Int → Char Examples 🔄

### Char to Int (Widening - Automatic, Lossless)

```java
public class CharToInt {
    public static void main(String[] args) {
        // Widening: char → int
        char ch1 = 'A';
        char ch2 = '5';
        char ch3 = '@';
        
        // Automatic conversion (no casting needed)
        int ascii1 = ch1;  // 'A' → 65
        int ascii2 = ch2;  // '5' → 53
        int ascii3 = ch3;  // '@' → 64
        
        System.out.println("'A' has ASCII value: " + ascii1);  // Output: 65
        System.out.println("'5' has ASCII value: " + ascii2);  // Output: 53
        System.out.println("'@' has ASCII value: " + ascii3);  // Output: 64
        
        // ✅ Lossless: No data lost, all information preserved
    }
}
```

### Int to Char (Narrowing - Manual, Lossy)

```java
public class IntToChar {
    public static void main(String[] args) {
        // Narrowing: int → char
        int ascii1 = 65;     // 'A'
        int ascii2 = 97;     // 'a'
        int ascii3 = 9829;   // '♥' (Heart emoji)
        
        // Manual casting required!
        char ch1 = (char) ascii1;   // 65 → 'A'
        char ch2 = (char) ascii2;   // 97 → 'a'
        char ch3 = (char) ascii3;   // 9829 → '♥'
        
        System.out.println("ASCII 65 = " + ch1);    // Output: A
        System.out.println("ASCII 97 = " + ch2);    // Output: a
        System.out.println("ASCII 9829 = " + ch3);  // Output: ♥
        
        // ⚠️ Manual casting required (narrowing conversion)
        
        // Example of data loss:
        int tooLarge = 70000;  // Beyond char range (0-65535)
        char overflow = (char) tooLarge;  // Unpredictable result
    }
}
```

---

## 8. Practical Example: Character Classification 🎓

```java
public class CharacterClassification {
    public static void main(String[] args) {
        
        // Method 1: Using ASCII Values (Numeric Comparison)
        char input = 'B';
        int ascii = input;    // Widening: char → int
        
        if (ascii >= 65 && ascii <= 90) {
            System.out.println(input + " is an UPPERCASE letter");
            // Output: B is an UPPERCASE letter
        }
        
        // Method 2: Direct Character Comparison
        char num = '5';
        if (num >= '0' && num <= '9') {
            System.out.println(num + " is a DIGIT");
            // Output: 5 is a DIGIT
        }
        
        // Method 3: Using Character Class
        char symbol = '@';
        System.out.println("Is uppercase? " + Character.isUpperCase(symbol));     // false
        System.out.println("Is digit? " + Character.isDigit(symbol));             // false
        System.out.println("Is letter? " + Character.isLetter(symbol));           // false
    }
}
```

---

## 9. Real-World Char Usage Examples 🌟

### Example 1: Password Validation

```java
public class PasswordValidator {
    public static boolean isPasswordStrong(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);  // Get char from string
            
            // Using char comparison (widening to int for arithmetic)
            if (ch >= 'A' && ch <= 'Z') hasUppercase = true;
            if (ch >= 'a' && ch <= 'z') hasLowercase = true;
            if (ch >= '0' && ch <= '9') hasDigit = true;
        }
        
        return hasUppercase && hasLowercase && hasDigit;
    }
    
    public static void main(String[] args) {
        System.out.println(isPasswordStrong("Pass123"));    // true
        System.out.println(isPasswordStrong("password"));   // false
    }
}
```

### Example 2: Grade Assignment

```java
public class GradeAssignment {
    public static void main(String[] args) {
        int[] scores = {95, 85, 75, 65, 55};
        
        for (int score : scores) {
            char grade = getGrade(score);  // Narrowing: int → char
            System.out.println("Score: " + score + " → Grade: " + grade);
        }
    }
    
    static char getGrade(int marks) {
        if (marks >= 90) return 'A';      // 'A' = ASCII 65
        if (marks >= 80) return 'B';      // 'B' = ASCII 66
        if (marks >= 70) return 'C';      // 'C' = ASCII 67
        if (marks >= 60) return 'D';      // 'D' = ASCII 68
        return 'F';                        // 'F' = ASCII 70
    }
}
// Output:
// Score: 95 → Grade: A
// Score: 85 → Grade: B
// Score: 75 → Grade: C
// Score: 65 → Grade: D
// Score: 55 → Grade: F
```

---

## 10. Complete Casting Hierarchy 🏔️

```
WIDER (Better Container)
        │
        ↑
    double ← (larger)
        ↑
      float ← (medium)
        ↑
      long ← (larger)
        ↑
      int ← char can widen to int
        ↑
    short
        ↑
    byte ← (smallest)
        │
NARROWER (Smaller Container)

═════════════════════════════════
Widening Path (Automatic):
byte → short → int → long → float → double
    ↘ char ↗

Narrowing Path (Manual with casting):
double → float → long → int → short → byte
    ↙ char ↖
═════════════════════════════════
```

---

## 11. Summary Table 📋

| Aspect | Details |
|--------|---------|
| **Data Type** | `char` |
| **Size** | 16 bits (2 bytes) |
| **Range** | 0 to 65,535 (Unicode) |
| **Declaration Methods** | ASCII numeric, Direct char, Unicode escape, Escape sequences |
| **Widening Conversion** | char → int (Automatic, Lossless) ✅ |
| **Narrowing Conversion** | int → char (Manual, Lossy) ❌ |
| **Example** | `char a = 'A';` or `char a = '\u0041';` or `char a = 65;` |

---

## 12. 🎯 Key Takeaways

✅ **Char stores single Unicode characters (16-bit)**
✅ **4 ways to declare: ASCII numeric, direct char, Unicode escape, escape sequences**
✅ **Widening (char→int) = Automatic + Lossless** 🟢
✅ **Narrowing (int→char) = Manual casting + Lossy** 🔴
✅ **Always use (Type) for narrowing conversions**
✅ **Data loss can occur in narrowing - use carefully!**

---

## 13. Practice Questions 💭

1. What is the ASCII value of 'Z'?
2. How do you declare the Bengali character 'অ'?
3. Is `int x = 'A';` valid? Why?
4. Is `char c = 65;` lossless or lossy?
5. What will `(char) 70000` produce?
6. Write code to check if a character is a vowel.

---


> 💡 **Remember:** Type casting is like converting containers - widening is adding to a bigger box (safe), narrowing is forcing into a smaller box (risky). Always be careful with narrowing conversions! ⚠️
