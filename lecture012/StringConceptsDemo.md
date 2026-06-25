# 📘 Java Notes — String Creation, Immutability, `StringBuilder`, `hashCode()`, and `final`

---

# ✅ Refactored Code with Proper Comments

```java id="7u9r9m"
package com.soumyajit.javabootcamp;

/*
    Demonstrates:
    ------------------------
    ✔ Creating String using char[]
    ✔ Creating String using byte[]
    ✔ StringBuilder to String conversion
    ✔ String Immutability
    ✔ hashCode() behavior
    ✔ concat() method
    ✔ toLowerCase() method
    ✔ final keyword
*/

public class StringArrays {

    public static void main(String[] args) {

        /*
         * -----------------------------------------
         * Creating String using Character Array
         * -----------------------------------------
         */

        char[] arr = {'H', 'e', 'l', 'l', 'o'};

        // Convert char array to String
        String s = new String(arr);

        System.out.println(s);


        /*
         * -----------------------------------------
         * Creating String using Byte Array
         * -----------------------------------------
         */

        byte[] arr1 = {65, 66, 67};

        // ASCII values:
        // 65 -> A
        // 66 -> B
        // 67 -> C

        String s1 = new String(arr1);

        System.out.println(s1);


        /*
         * -----------------------------------------
         * StringBuilder to String Conversion
         * -----------------------------------------
         */

        StringBuilder sb = new StringBuilder("Java");

        // Convert StringBuilder object to String
        String sb1 = sb.toString();

        System.out.println(sb1);


        /*
         * -----------------------------------------
         * String Immutability Example
         * -----------------------------------------
         */

        String name = "RAM";

        // Original hashCode
        System.out.println(name.hashCode());

        // concat() creates a NEW String object
        System.out.println(name.concat("AYA"));

        // Original string remains unchanged
        System.out.println(name.hashCode());

        // Reassigning reference variable
        name = "RAMAN";

        // New hashCode
        System.out.println(name.hashCode());


        /*
         * -----------------------------------------
         * Primitive Variable Example
         * -----------------------------------------
         */

        int i = 0;

        i = 10;

        System.out.println(i);


        /*
         * -----------------------------------------
         * Another String Immutability Example
         * -----------------------------------------
         */

        String p = "Java";

        // Creates a new String object
        String concat = p.concat(" Programming");

        // Original string unchanged
        System.out.println(p);

        // New String object
        System.out.println(concat);


        /*
         * -----------------------------------------
         * String toLowerCase()
         * -----------------------------------------
         */

        name = name.toLowerCase();

        System.out.println(name);


        /*
         * -----------------------------------------
         * final Keyword Example
         * -----------------------------------------
         */

        final int a = 10;

        // ❌ ERROR
        // a = 20;

        // final variables cannot be reassigned
    }
}
```

---

# 📖 Detailed Notes

---

# 🧠 What is a String in Java?

A `String` is a sequence of characters.

Example:

```java id="jys67u"
String name = "Java";
```

Strings in Java are:

✅ Objects
✅ Immutable
✅ Stored in String Pool (if literal)

---

# 🏗️ Creating String Using `char[]`

```java id="z47ntv"
char[] arr = {'H','e','l','l','o'};

String s = new String(arr);
```

---

## 🔹 Explanation

A character array is converted into a String object.

### Output

```text id="yx1b1n"
Hello
```

---

# 📌 Memory Representation

```text id="o2gprj"
char[] arr  ---> ['H','e','l','l','o']

String s    ---> "Hello"
```

---

# 🏗️ Creating String Using `byte[]`

```java id="q4r0lg"
byte[] arr1 = {65,66,67};

String s1 = new String(arr1);
```

---

## 🔹 ASCII Values

| Byte | Character |
| ---- | --------- |
| 65   | A         |
| 66   | B         |
| 67   | C         |

---

## ✅ Output

```text id="p1crzw"
ABC
```

---

# 🧠 What is `StringBuilder`?

`StringBuilder` is a mutable sequence of characters.

```java id="48u7ff"
StringBuilder sb = new StringBuilder("Java");
```

---

# 🔹 Mutable Means?

The object can be modified without creating a new object.

---

# 🔄 Converting `StringBuilder` to String

```java id="0s63yf"
String sb1 = sb.toString();
```

---

## ✅ Output

```text id="2j2imn"
Java
```

---

# 🧠 String Immutability

```java id="94r81c"
String name = "RAM";
```

Strings in Java are **immutable**.

Meaning:

❌ Cannot be changed after creation.

---

# 🔥 Example

```java id="6l9x6m"
name.concat("AYA");
```

This does NOT modify original string.

Instead:

✅ Creates NEW String object.

---

# 📌 Important

```java id="nrzws2"
System.out.println(name.concat("AYA"));
```

Output:

```text id="t1e4g8"
RAMAYA
```

But original remains:

```java id="r9n0g8"
System.out.println(name);
```

Output:

```text id="d8zjtb"
RAM
```

---

# 🧠 Understanding `hashCode()`

```java id="spw5cf"
System.out.println(name.hashCode());
```

`hashCode()` generates an integer representation of object data.

---

# 🔹 Why hashCode Changes?

```java id="aqo7r0"
name = "RAMAN";
```

Now `name` points to a NEW object.

Therefore:

✅ Different hashCode.

---

# 📌 Memory Visualization

Before:

```text id="v8fskw"
name ───► "RAM"
```

After:

```text id="f7m7dz"
name ───► "RAMAN"
```

Old object still exists until garbage collected.

---

# 🧠 Primitive Variable Example

```java id="w3zj6j"
int i = 0;
i = 10;
```

Primitive variables store actual values directly.

---

## ✅ Output

```text id="9b1ldg"
10
```

---

# 📌 Difference Between Primitive and String

| Primitive             | String           |
| --------------------- | ---------------- |
| Stores value directly | Stores reference |
| Mutable assignment    | Immutable object |
| Faster                | Object overhead  |

---

# 🧠 Another Immutability Example

```java id="g0qod7"
String p = "Java";

String concat = p.concat(" Programming");
```

---

## 🔹 Output

```text id="u8u2xg"
Java
Java Programming
```

---

# 📌 Why?

Because:

```java id="k2l30x"
concat()
```

creates NEW object.

Original remains unchanged.

---

# 🧠 `toLowerCase()` Method

```java id="f9tgr4"
name = name.toLowerCase();
```

Converts uppercase to lowercase.

---

## ✅ Output

```text id="3ck1ya"
raman
```

---

# ⚠ Important

`toLowerCase()` also creates a NEW String object.

---

# 🧠 final Keyword

```java id="5akq95"
final int a = 10;
```

---

# 🔹 Meaning of `final`

Once assigned:

❌ Cannot be reassigned.

---

# ❌ Invalid

```java id="cq2frh"
a = 20;
```

Compiler Error.

---

# 📌 final Variable Rules

| Type      | Behavior                |
| --------- | ----------------------- |
| Primitive | Value cannot change     |
| Reference | Reference cannot change |

---

# 🚀 Complete Output

```text id="q3czem"
Hello
ABC
Java
81009
RAMAYA
81009
77848939
10
Java
Java Programming
raman
```

---

# 🔥 Important Interview Questions

---

# ❓ Why are Strings immutable in Java?

For:

* Security
* Thread safety
* String pool optimization
* Performance

---

# ❓ Difference between String and StringBuilder?

| String                  | StringBuilder   |
| ----------------------- | --------------- |
| Immutable               | Mutable         |
| Slower for modification | Faster          |
| Thread-safe             | Not thread-safe |

---

# ❓ Does `concat()` modify original string?

❌ No.

It creates a new String object.

---

# ❓ What does `final` mean?

Value/reference cannot be reassigned after initialization.

---

# ❓ Why use `StringBuilder`?

Efficient string modifications.

---

# 🏁 Conclusion

This program demonstrates:

✅ String creation from arrays
✅ StringBuilder usage
✅ String immutability
✅ hashCode behavior
✅ concat() method
✅ toLowerCase() method
✅ Primitive variables
✅ final keyword
✅ Memory/reference concepts
