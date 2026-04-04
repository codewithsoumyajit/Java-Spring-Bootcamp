Understanding the Java architecture is essential for any developer. To break it down, think of it as a hierarchy where one component lives inside another to provide a complete environment for building and running applications.

## 1. The Big Three: JDK, JRE, and JVM

To understand how Java works, we look at these three nested components:

* **JDK (Java Development Kit):** This is the full software package you install to **develop** Java applications. It contains everything: the JRE, the JVM, and development tools like the compiler (`javac`) and debugger.
* **JRE (Java Runtime Environment):** This is a subset of the JDK. It provides the minimum requirements to **run** an existing Java program. It includes the JVM and the standard class libraries (pre-written code) that Java programs need.
* **JVM (Java Virtual Machine):** The heart of the system. It is the "engine" that actually executes the code. It is platform-dependent (different versions for Windows, Mac, and Linux), which allows the code it runs to be platform-independent.



---

## 2. Key Execution Components

* **javac (The Compiler):** This tool converts your human-readable code (the `.java` file) into a format the machine understands. In the terminal, you run it as `javac FileName.java`.
* **Bytecode:** This is the result of the compilation. It is saved in a `.class` file. Bytecode is not machine code (0s and 1s); it is an intermediate language that is **Platform Independent**. You can compile code on Windows and run the resulting Bytecode on a Mac.
* **JIT (Just-In-Time Compiler):** Found inside the JVM, the JIT's job is to boost performance. While the JVM usually interprets bytecode line-by-line, the JIT identifies "hot spots" (code that runs repeatedly, like a loop). It compiles these spots directly into machine code so they run much faster the next time.

---

## 3. The Complete Execution Process

The journey from writing code to seeing "Hello World" follows these specific steps:

### Step 1: Writing Code
You create a source file with a `.java` extension (e.g., `Test.java`). This is the code you write in a text editor or IDE.

### Step 2: Compilation
You use the **javac** compiler. 
> **Command:** `javac Test.java`
The compiler checks for syntax errors. If none are found, it generates a `Test.class` file containing **Bytecode**.



### Step 3: Execution (The Two-Pass Process)
You use the **java** command to trigger the JVM.
> **Command:** `java Test` (Note: you don't add the .class extension here).

The JVM performs a two-pass process:
1.  **Parsing/Grouping:** It reads through the bytecode to understand the structure and group instructions.
2.  **Interpretation:** It begins executing the instructions line-by-line.

### Step 4: Machine Code Generation
The JVM (assisted by the **JIT Compiler**) converts the Bytecode into **Machine Code** (native code) specifically for your operating system (Windows, Linux, etc.). 

### Step 5: Final Output
The hardware executes the machine code, and your program's output (e.g., "Hello World") appears on the screen.

---

## Summary Table

| Component | Full Form | Purpose |
| :--- | :--- | :--- |
| **JDK** | Java Development Kit | For **developing** and running Java apps. |
| **JRE** | Java Runtime Environment | For **running** Java apps only. |
| **JVM** | Java Virtual Machine | **Executes** the bytecode. |
| **javac** | Java Compiler | Converts `.java` to `.class` (Bytecode). |
| **Bytecode** | — | Intermediate, platform-independent code. |
| **JIT** | Just-In-Time Compiler | Optimizes performance by compiling frequent code blocks. |