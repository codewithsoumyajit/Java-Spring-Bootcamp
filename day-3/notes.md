# The fundamental "skeleton" of a Java program! Here is a structured breakdown of what makes up a Java program and why the `main` method is so critical.

---

## 1. The Class: The Blueprint
In Java, everything revolves around **Classes**. Think of a class as a blueprint for an object (like a student, a car, or a laptop).
* **Syntax:** `class Test { ... }`
* **Purpose:** It acts as a container for all your code. You cannot write "free-floating" code in Java; it must live inside a class.
* **Properties & Behaviors:** Inside a class, you define what an object *has* (variables like `rollNumber`) and what it *does* (methods).



---

## 2. The Main Method: The Entry Point
The `main` method is the most important part of your initial journey. It is the **starting point** where the Java Virtual Machine (JVM) begins executing your program.

### The "Signature": `public static void main(String[] args)`
Each word in this line has a specific job:

* **`public` (Access Modifier):** This makes the method accessible from anywhere. The JVM needs to find it outside the class to start the program.
* **`static`:** This allows the JVM to call the method without creating an "instance" (object) of the class. It saves memory and time during startup.
* **`void` (Return Type):** This means the method does not return any value back to the caller. It just performs its task and finishes.
* **`main`:** This is the reserved name of the method. The JVM specifically looks for this name to start execution.
* **`String[] args`:** This is an **array of strings** used for "Command Line Arguments."

---

## 3. Command Line Arguments (`args`)
As shown in your example, `args` allows you to pass information into your program at the moment you run it.

> **Example:** If you run `java Test Apple Mango`, the `args` array will store:
> * `args[0]` = "Apple"
> * `args[1]` = "Mango"

This makes your program dynamic, allowing it to behave differently based on the input you provide in the terminal.

---

## 4. Printing to the Console
To see output, Java uses a specific "pathway":
`System.out.println("Hello World");`

* **`System`:** A built-in class that provides access to the system (like your screen).
* **`out`:** An object within that class representing the standard output stream.
* **`println`:** The method that actually prints the text and moves to a new line.



---

## 5. The Role of the Semicolon `;`
Java is a "terminated" language. Every statement must end with a **semicolon (`;`)**.
* It acts like a full stop at the end of a sentence.
* It tells the compiler, "This instruction is finished; move to the next one."

---

### Summary Table

| Component | Meaning |
| :--- | :--- |
| **`class`** | The container/blueprint for the code. |
| **`public`** | Accessible by the JVM. |
| **`static`** | Run without creating an object. |
| **`void`** | No return value. |
| **`main`** | The specific entry point name. |
| **`String[] args`** | Inputs from the command line. |
| **`{ }`** | Defines the "scope" or boundaries of a class or method. |

Don't worry if concepts like "Objects" or "Static" feel a bit blurry right now. In Java, these ideas click into place once you start building more complex programs!