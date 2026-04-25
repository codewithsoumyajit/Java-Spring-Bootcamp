# 📚 Bitwise Operators (Deep Dive) ⚙️🔥

---

## 🧠 1. What are Bitwise Operators?

Bitwise operators work **directly on binary representation (bits)** of numbers.

👉 Instead of decimal (5, 4, etc.), CPU actually works like:

```
5 → 00000101
4 → 00000100
```

---

## ⚙️ 2. Types of Bitwise Operators

| Operator | Name                     | Description                          |
| -------- | ------------------------ | ------------------------------------ |
| `&`      | AND                      | 1 only if both bits are 1            |
| `\|`     | OR                       | 1 if at least one bit is 1           |
| `^`      | XOR (Exclusive OR)       | 1 if bits are different              |
| `~`      | NOT (Bitwise Complement) | Flips all bits                       |
| `<<`     | Left Shift               | Shifts bits left (adds 0 on right)   |
| `>>`     | Right Shift              | Shifts bits right (keeps sign bit)   |
| `>>>`    | Unsigned Right Shift     | Shifts right (fills 0, ignores sign) |


---

# 🔷 3. Bitwise AND (`&`)

### 📌 Rule:

```
1 & 1 = 1  
Else → 0
```

---

### 💻 Example:

```java
int result = 5 & 4;
```

---

### 🧩 Step-by-Step

```
5 = 00000101
4 = 00000100
--------------
&   00000100
```

👉 Result:

```
00000100 → 4
```

---

# 🔷 4. Bitwise OR (`|`)

### 📌 Rule:

```
If any bit is 1 → result is 1
```

---

### 🧩 Example

```
5 = 00000101
4 = 00000100
--------------
|   00000101
```

👉 Result:

```
5
```

---

# 🔷 5. Bitwise XOR (`^`)

### 📌 Rule:

```
Same → 0  
Different → 1
```

---

### 🧩 Example

```
5 = 00000101
4 = 00000100
--------------
^   00000001
```

👉 Result:

```
1
```

---

# 🔥 6. Bitwise NOT (`~`) — MOST IMPORTANT

This is where students get confused 👇

---

## 📌 Rule:

```
~ → flips all bits (0 → 1, 1 → 0)
```

---

## 💻 Example:

```java
~5 = ?
```

---

## 🧩 Step 1: Convert to Binary (32-bit)

```
5 = 00000000 00000000 00000000 00000101
```

---

## 🧩 Step 2: Apply NOT (~)

```
~5 = 11111111 11111111 11111111 11111010
```

---

## ❓ Why is this -6?

Because Java uses **2’s Complement** for negative numbers.

---

## 🧩 Step 3: Convert back to Decimal

We got:

```
11111111 11111111 11111111 11111010
```

### 👉 Find magnitude:

1. Take 2’s complement again:

```
Invert → 00000000 00000000 00000000 00000101
Add 1 → 00000000 00000000 00000000 00000110
```

👉 Decimal = `6`

---

### 👉 Final Answer:

```
~5 = -6
```

---

## 🧠 Shortcut Trick

```
~n = -(n + 1)
```

👉 Example:

```
~5 = -(5 + 1) = -6
```

---

# 🔷 7. Left Shift (`<<`)

### 📌 Rule:

```
Shift bits to left  
Fill right with 0
```

---

### 💻 Example:

```java
5 << 1
```

---

### 🧩 Step-by-Step

```
5 = 00000101

Shift left by 1 →
      00001010
```

👉 Result:

```
10
```

---

## 🧠 Shortcut

```
n << k = n × (2^k)
```

👉 Example:

```
5 << 1 = 5 × 2 = 10
5 << 2 = 5 × 4 = 20
```

---

# 🔷 8. Right Shift (`>>`)

### 📌 Rule:

```
Shift bits to right  
Fill left with sign bit
```

---

### 💻 Example:

```java
5 >> 1
```

---

### 🧩 Step-by-Step

```
5 = 00000101

Shift →
    00000010
```

👉 Result:

```
2
```

---

## 🧠 Shortcut

```
n >> k = n / (2^k)
```

👉 Example:

```
5 >> 1 = 2
```

---

## ⚠️ For Negative Numbers

Sign bit is preserved 👇

```
-5 → starts with 1 (negative)
>> keeps filling 1 on left
```

---

# 🔷 9. Unsigned Right Shift (`>>>`)

### 📌 Rule:

```
Shift right  
Fill left with 0 (no sign)
```

---

### 🔥 Difference from `>>`

| Operator | Behavior     |
| -------- | ------------ |
| `>>`     | Keeps sign   |
| `>>>`    | Ignores sign |

---

### 🧩 Example (conceptual)

```
-5 >>> 1 → becomes large positive number
```

👉 Because sign bit is removed

---

# 🚀 Final Summary

* Bitwise works on **binary level**
* `~5 = -6` because of **2’s complement**
* Left shift = multiply by 2
* Right shift = divide by 2
* `>>>` removes sign (important for advanced cases)

---

## 🧠 Quick Practice

1. Find:

```
~7 = ?
```

2. Output:

```
6 << 1
```

3. Output:

```
8 >> 2
```

---

