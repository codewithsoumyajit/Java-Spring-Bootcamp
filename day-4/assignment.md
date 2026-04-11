# Warm-up Questions

## Phase 1: The Program Skeleton

1. **The Entry Point:** If you change the name of the `main` method to `start`, will the program compile? Will it run?
2. **Access Modifiers:** Why must the main method be `public`? What would happen if the JVM tried to run a `private` main method?
3. **The Blueprint:** Can you run a Java file that contains code but is **not** wrapped inside a `class` block?
4. **Static Logic:** Why is the keyword `static` used for the main method? (Hint: Think about whether we need to create a "Test" object before the program even starts).

## Phase 2: Command Line & Syntax

5. **The Args Array:** In the code you provided, if you run the command `java Test Apple`, the program will crash. Why?
   > *Refresher:* Look at your `args[1]` calls.
6. **Punctuation:** What is the specific error the compiler usually throws if you forget a semicolon (`;`) at the end of a `System.out.println` statement?
7. **Naming Convention:** If your file is named `Hello.java`, what must the class name inside that file be?

## Phase 3: The Environment (JDK/JRE/JVM)

8. **The Toolbox:** Which component is responsible for converting your `.java` file into a `.class` file: the JDK, JRE, or JVM?
9. **Platform Independence:** We say Java is "Write Once, Run Anywhere." Which component (JVM, JRE, or JDK) is responsible for actually executing the bytecode on different operating systems?
10. **Path Variables:** Why do we add the `/bin` folder to the System Path environment variable instead of the main JDK folder?

## Challenge Question

Look at the following signature:
`public static int main(String[] args)`

**Will the JVM accept this as the starting point of the program?** (Pay close attention to the return type).