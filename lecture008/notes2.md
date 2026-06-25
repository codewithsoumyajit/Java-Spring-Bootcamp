# 📚 Print Statements & Output System (Deep Dive) 🖨️🔥

---

# 🧠 1. What is `System.out.println()` really?

When you write:

```java
System.out.println("Hello");
```

👉 It’s not just “printing” — it’s a **chain of objects and streams** working together.

---

## 🔗 Breakdown

```java
System.out.println("Hello");
```

### Step-by-step:

1. **`System`**

   * A built-in class from `java.lang`
   * Provides access to system-level resources

2. **`out`**

   * A **static object**
   * Type: `PrintStream`
   * Connected to **console output (stdout)**

3. **`println()`**

   * Method of `PrintStream`
   * Prints data + moves cursor to next line

---

## 🧩 Visual Flow

```
Your Code
   ↓
System.out (PrintStream object)
   ↓
JVM Output Stream
   ↓
Console (Terminal)
```

---

# 🔍 2. What is `PrintStream`?

`System.out` is actually:

```java
public static final PrintStream out;
```

---

### 🧠 Meaning:

* `static` → belongs to class
* `final` → cannot be reassigned
* `PrintStream` → class used to print data

---

### 💡 Key Feature

`PrintStream`:

* Converts data → text
* Sends it to output stream
* Handles multiple data types automatically

---

# 🔷 3. `print()` vs `println()` vs `printf()`

---

## 🟢 `print()`

```java
System.out.print("Hello");
System.out.print("World");
```

👉 Output:

```
HelloWorld
```

(No new line)

---

## 🔵 `println()`

```java
System.out.println("Hello");
System.out.println("World");
```

👉 Output:

```
Hello
World
```

---

## 🟣 `printf()` (Formatted Output)

```java
System.out.printf("Sum is %d", 10);
```

👉 Output:

```
Sum is 10
```

---

# 🔥 4. String Concatenation (VERY IMPORTANT)

---

## ❌ Common Mistake

```java
int a = 1, b = 2;
String c = "Sum";

System.out.println("Sum: " + c);
```

👉 Output:

```
Sum: Sum
```

---

## ❓ Why?

Because:

```
"Sum: " + c → String + String → String
```

No calculation happens.

---

## ✅ Correct Way

```java
System.out.println(c + ": " + (a + b));
```

👉 Output:

```
Sum: 3
```

---

## ⚠️ Golden Rule

```java
"anything" + number → becomes String
```

So always use:

```java
(a + b)
```

---

# 🧠 5. `printf()` Deep Understanding

---

## 💻 Example

```java
System.out.printf("%s of %d and %d is %d", c, a, b, a + b);
```

---

## 🧩 How it works internally

* `%s`, `%d` → placeholders
* Values are inserted **in order**

---

## 📊 Format Specifiers

| Specifier | Meaning   |
| --------- | --------- |
| `%s`      | String    |
| `%d`      | Integer   |
| `%f`      | Floating  |
| `%c`      | Character |
| `%n`      | New line  |

---

## 🔥 Example

```java
double num = 12.3456;
System.out.printf("%.2f", num);
```

👉 Output:

```
12.35
```

---

# 🌍 6. What is `System.err`?

You’ve seen `System.out`, but there’s also 👇

---

## 🔴 `System.err`

```java
System.err.println("Error occurred");
```

👉 Used for:

* Error messages
* Logs

---

## 🧩 Difference

| Stream       | Purpose       |
| ------------ | ------------- |
| `System.out` | Normal output |
| `System.err` | Error output  |

---

# 🔥 7. What is `System.in`?

---

## 🔵 Input Stream

```java
System.in
```

👉 Used to take input (keyboard)

---

## 💻 Example

```java
Scanner sc = new Scanner(System.in);
int x = sc.nextInt();
```

---

## 🧩 Flow

```
Keyboard → System.in → JVM → Your Program
```

---

# 🚀 8. What is `System.getenv()`? (ADVANCED)

Now the real deep part 👇

---

## 🔍 Definition

```java
System.getenv()
```

👉 Returns **environment variables**

---

## 💻 Example

```java
String path = System.getenv("PATH");
System.out.println(path);
```

---

## 🧠 What are Environment Variables?

* OS-level variables
* Used to store:

  * Paths
  * Configurations
  * Secrets (API keys)

---

## 📊 Examples

| Variable    | Meaning           |
| ----------- | ----------------- |
| `PATH`      | Executable paths  |
| `JAVA_HOME` | Java installation |
| `USER`      | Current user      |

---

## ⚠️ Important

* Read-only in Java
* Cannot modify using `System.getenv()`

---

# 🔥 9. `System.getProperty()` vs `getenv()`

---

## 🧩 Difference

| Method          | Source         |
| --------------- | -------------- |
| `getenv()`      | OS Environment |
| `getProperty()` | JVM Properties |

---

### 💻 Example

```java
System.getProperty("java.version");
System.getProperty("user.dir");
```

---

# 🧠 10. Internal Working Summary

When you print:

```java
System.out.println("Hello");
```

👉 Internally:

```
String → PrintStream → Output Stream → Console
```

---

# 🚀 Final Summary

* `System.out` → standard output stream
* `println()` → prints + newline
* `printf()` → formatted printing (important in real apps)
* `System.err` → error printing
* `System.in` → input stream
* `System.getenv()` → OS environment variables

---

# 🧠 Quick Practice

1. What will be output?

```java
System.out.println("Sum: " + 2 + 3);
```

---

2. Fix this:

```java
System.out.println("Total: " + a + b);
```

---

3. Print using `printf`:

```
Product of 2 and 3 is 6
```

---
