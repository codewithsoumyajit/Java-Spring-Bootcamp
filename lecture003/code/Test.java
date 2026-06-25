/*
 * ===========================================================================
 * STEPS TO RUN THIS JAVA PROGRAM IN DETAIL:
 * ===========================================================================
 * * STEP 1: SAVE THE FILE
 * ---------------------
 * Save this code in a file named exactly "Test.java". 
 * Note: The filename must match the class name (Test) and is case-sensitive.
 * * STEP 2: OPEN COMMAND PROMPT / TERMINAL
 * --------------------------------------
 * Open your CMD (Windows) or Terminal (Mac/Linux) and navigate to the 
 * folder where you saved "Test.java".
 * * STEP 3: COMPILE THE PROGRAM
 * ---------------------------
 * Type the following command and press Enter:
 * javac Test.java
 * * If there are no errors, a file named "Test.class" (Bytecode) will be created.
 * * STEP 4: RUN THE PROGRAM WITH ARGUMENTS
 * --------------------------------------
 * Since this code specifically asks for args[0] and args[1], you MUST provide 
 * at least two arguments, otherwise the program will throw an 
 * "ArrayIndexOutOfBoundsException".
 * * Type the following command (replace "Apple" and "Mango" with your choices):
 * java Test Apple Mango
 * * STEP 5: OBSERVE THE OUTPUT
 * --------------------------
 * Your console should display:
 * Test
 * Apple
 * Mango
 * The second fruit name is : Mango
 * ===========================================================================
 */

public class Test {
    public static void main(String[] args) {
        // Prints the string "Test"
        System.out.println("Test");

        // Prints the first argument provided in the command line (e.g., Apple)
        System.out.println(args[0]);

        // Prints the second argument provided in the command line (e.g., Mango)
        System.out.println(args[1]);

        // Concatenates a string with the second argument
        System.out.println("The second fruit name is : " + args[1]);
    }
}