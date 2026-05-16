# 💻 Solution: Student Username Generator

```java
import java.util.Scanner;

public class StudentUsernameGenerator {

    // Method to manually convert uppercase letters to lowercase
    public static char toLower(char ch) {

        if (ch >= 'A' && ch <= 'Z') {
            return (char)(ch + 32);
        }

        return ch;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking input
        System.out.print("First Name: ");
        String firstName = sc.next();

        System.out.print("Last Name: ");
        String lastName = sc.next();

        System.out.print("Age: ");
        int age = sc.nextInt();

        System.out.print("Marks: ");
        double marks = sc.nextDouble();

        // Username creation
        String username = "";

        // First 3 letters of first name
        for (int i = 0; i < 3 && i < firstName.length(); i++) {

            username += toLower(firstName.charAt(i));
        }

        // First 2 letters of last name
        for (int i = 0; i < 2 && i < lastName.length(); i++) {

            username += toLower(lastName.charAt(i));
        }

        // Adding age
        username += age;

        // Checking result
        String result;

        if (marks >= 40) {
            result = "Passed";
        } else {
            result = "Failed";
        }

        // Output
        System.out.println("\n===== OUTPUT =====");

        System.out.println("Username: " + username);
        System.out.println("Result: " + result);

        sc.close();
    }
}
```

---

# 🔍 Explanation

## ✅ Step 1: Take User Input

The program accepts:

- First Name
- Last Name
- Age
- Marks

using the `Scanner` class.

---

## ✅ Step 2: Convert Characters to Lowercase

Since `toLowerCase()` is not allowed,
we manually convert uppercase letters using ASCII values.

```java
if (ch >= 'A' && ch <= 'Z') {
    return (char)(ch + 32);
}
```

---

## ✅ Step 3: Generate Username

We use:

- `charAt()` → to access characters
- loops → to extract required characters

### Username Format

```text
[first 3 letters of first name]
+
[first 2 letters of last name]
+
[age]
```

---

## ✅ Step 4: Check Pass/Fail

```java
if (marks >= 40)
```

- Marks ≥ 40 → Passed
- Otherwise → Failed

---

# 🧪 Sample Run

## Input

```text
First Name: Soumyajit
Last Name: Nandi
Age: 22
Marks: 78.5
```

## Output

```text
===== OUTPUT =====
Username: souna22
Result: Passed
```

---

# 🛠 Concepts Used

| Concept | Usage |
|---|---|
| `String` | Store names |
| `int` | Store age |
| `double` | Store marks |
| loops | Extract characters |
| `charAt()` | Access characters |
| ASCII values | Lowercase conversion |
| `if-else` | Result checking |

---

# 🚀 Learning Outcome

After solving this problem, you will understand:

- Manual string handling
- Character manipulation
- Primitive data types
- Loop-based logic
- Basic username generation systems