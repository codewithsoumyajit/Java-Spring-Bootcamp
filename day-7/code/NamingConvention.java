package com.soumyajit.javabootcamp;

public class NamingConvention {
    public static void main(String[] args) {

        // 🔹 Java is CASE-SENSITIVE
        // 'name' and 'Name' are treated as two DIFFERENT variables
        String name = "Soumyajit";
        String Name = "Different Variable";

        // 🔹 A variable name can include:
        //    - alphabets (a-z, A-Z)
        //    - numbers (0-9)
        //    - special characters: $ and _
        
        // ✅ camelCase (recommended style)
        String myFullName = "Soumyajit";

        // ✅ underscore (_) is allowed
        String full_name = "Soumyajit Nandi";

        // ✅ dollar sign ($) is allowed
        String $salary = "50000";

        // ✅ numbers are allowed BUT not at the beginning
        String user1 = "User One";

        // ❌ INVALID: variable name cannot start with a number
        // String 1user = "Invalid"; // Compile-time error

        // 🔹 Printing values to verify variables
        System.out.println(name);
        System.out.println(Name);
        System.out.println(myFullName);
        System.out.println(full_name);
        System.out.println($salary);
        System.out.println(user1);
    }
}