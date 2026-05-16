# 🌟 Student Username Generator 🌟

## 📌 Problem Statement

Create a Java program that generates a student's username using their personal details.

Your program should:

✅ Take the following inputs from the user:

- 👤 First Name (`String`)
- 👤 Last Name (`String`)
- 🎂 Age (`int`)
- 📊 Marks (`double`)

---

## 🎯 Username Generation Rules

The username should contain:

- First **3 characters** of the first name
- First **2 characters** of the last name
- Student's age

---

## 📢 Result Criteria

Display:

- 🆔 Generated Username
- 📝 Student Result

### ✔ Pass Condition
- If marks are **greater than or equal to 40** → `"Passed"`

### ✘ Fail Condition
- If marks are **less than 40** → `"Failed"`

---

# ⚠ Important Restrictions

❌ Do NOT use:

- `substring()`
- `toLowerCase()`

✅ Instead, use:

- `charAt()`
- loops
- conditional statements
- ASCII value conversion

---

# 🧪 Example Input

```text
First Name: Soumyajit
Last Name: Nandi
Age: 22
Marks: 78.5
```

# ✅ Example Output

```text
Username: souna22
Result: Passed
```

---

# 🛠 Concepts Covered

| Concept | Description |
|---|---|
| `String` | Handling text input |
| Primitive Data Types | `int`, `double` |
| Loops | Iterating through characters |
| `charAt()` | Accessing characters manually |
| ASCII Conversion | Converting uppercase to lowercase |
| `if-else` | Decision making |

---

# ⭐ Bonus Challenges

Try adding these extra features:

- 🔁 Reverse the username
- 🔤 Count total characters in full name
- 🅰 Check whether the first name starts with a vowel
- 🔒 Prevent username creation if name length is too short

---

# 🚀 Goal

Build the complete program **without using built-in string shortcut methods** and strengthen your understanding of:

> Strings + Primitive Data Types + Character Manipulation