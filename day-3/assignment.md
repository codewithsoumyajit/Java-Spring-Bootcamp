# ☕ Java Architecture: Warm-Up Challenges
*Test your knowledge of the JDK, JRE, JVM, and the execution flow.*

---

## 🟢 Level 1: The Basics (Definitions)

### **The "Includes" Game**
1.  **Hierarchy:** Does the JRE contain the JDK, or does the JDK contain the JRE?
2.  **The Executor:** Which one of these (**JDK, JRE, JVM**) is the only one that actually runs the code?

### **True or False**
* **A:** A user who only wants to run a Java game on their laptop needs to install the full JDK.
* **B:** The JVM is exactly the same software for Windows, Mac, and Linux.

### **The File Extension Swap**
* What is the file extension of the code **you write**?
* What is the file extension of the code the **JVM understands**?

---

## 🟡 Level 2: The Process (Execution Flow)

### **Who am I?**
> *"I am the tool you call when you type `javac` in the terminal. I look for semi-colon errors and produce a special middle-man file."*

### **The Transformation**
Trace the journey: Start with `MyProgram.java`. What is the name of the tool that touches it first, and what file does it create?

### **Platform Independence**
Why is **Bytecode** called "platform-independent," while the **JVM** is called "platform-dependent"? How do they work together to make Java "Run Anywhere"?

---

## 🔴 Level 3: Deep Dive (JIT & Interpretation)

### **The Two-Pass Mystery**
Before executing code line-by-line, what does the JVM do in its **"First Pass"** (Parsing/Verification phase)?

### **The JIT Efficiency Test**
Imagine a loop that runs 10,000 times. How does the **JIT (Just-In-Time) compiler** handle this differently than a standard **Interpreter**?

### **The Command Line Catch**
When running a program using the `java` command (e.g., `java MyFile`), why do we get an error if we accidentally type `java MyFile.class`?

---

## 🏆 Bonus "Brain Teaser"
If you write a Java program on a Windows computer, compile it to a `.class` file, and then move that `.class` file to an Android phone or a Linux server, **do you need to compile it again?** Why or why not?