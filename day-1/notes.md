## 1. What are you actually installing?
When someone says "Install Java," they usually mean installing the **JDK (Java Development Kit)**. It is not a single application but a collection of tools that allow you to:
* **Write** Java code.
* **Compile** code (using `javac`).
* **Run** applications (using the JVM).

### Key Components:
* **JDK (Java Development Kit):** The full package (Tools + Runtime).
* **JRE (Java Runtime Environment):** Allows you to run Java apps (included in the JDK).
* **JVM (Java Virtual Machine):** The engine that actually executes the Java bytecode.

---

## 2. Choosing a Distribution: Why OpenJDK?
There are many versions of the JDK provided by different companies (Oracle, Amazon, Microsoft).
* **The History:** Java was created by Sun Microsystems (1995) and later open-sourced as **OpenJDK** (2006).
* **The Difference:** Most distributions are based on the OpenJDK reference. The main differences lie in **licensing** and **support**.
* **Oracle JDK:** Now requires a paid license for commercial use (though it remains free for personal use).
* **The Choice:** This guide uses **Adoptium (Temurin)** because it provides pre-built, high-quality, free OpenJDK binaries that are widely used in the industry today.

---

## 3. Step-by-Step Installation

### Step A: Download
1.  Go to [oracle.com](https://www.oracle.com/in/java/technologies/downloads/#jdk25-windows).
2.  The website usually auto-detects your OS (Windows 64-bit).
3.  Download the latest **LTS (Long Term Support)** version (e.g., JDK 25).

### Step B: Install
1.  Run the `.msi` or `.exe` installer.
2.  **Important:** During setup, ensure you select the option **"Set JAVA_HOME variable"** and **"Add to PATH."** This saves you from manual configuration later.
3.  Click **Next** and **Finish**.

### Step C: Verify
1.  Open the **Command Prompt (CMD)**.
2.  Type the following command:
    `java -version`
3.  If successful, you will see details like `openjdk version "21.x.x"` and `OpenJDK Runtime Environment`.

---

## 4. Troubleshooting: "Java is not recognized"
If you get an error, your **Environment Variables** are likely not set. Here is how to fix it manually:

1.  **Find the Path:** Go to your installation folder (usually `C:\Program Files\Eclipse Foundation\jdk-21.x.x\bin`) and copy the address.
2.  **Open Settings:** Search for **"Edit the system environment variables"** in your Windows search bar.
3.  **Environment Variables:** Click the button at the bottom.
4.  **Edit Path:**
    * Under "System Variables," find **Path**, select it, and click **Edit**.
    * Click **New** and paste the path to the `bin` folder you copied earlier.
5.  **Set JAVA_HOME (Optional but recommended):**
    * Click **New** under System Variables.
    * Variable Name: `JAVA_HOME`
    * Variable Value: The path to the JDK folder (the one *above* the bin folder).
6.  **Restart CMD:** Close and reopen the Command Prompt for changes to take effect.

---

## 5. Summary Table

| Term | Meaning |
| :--- | :--- |
| **OpenJDK** | The open-source reference implementation of Java. |
| **LTS** | Long Term Support (stable versions supported for years). |
| **Binaries** | The ready-to-use executable files for your OS. |
| **Path** | A system variable telling Windows where to find Java tools. |

> **Note:** Java releases a new version every 6 months, but sticking to an **LTS** version ensures stability and security updates for a long period (usually 7–8 years).