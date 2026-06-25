/** 
 * DataTypes.java
 * Demonstrates Java primitive data types: their declaration, usage, and range limits
 */

public class DataTypes {

    public static void main(String[] args) {
        
        // ===== WHOLE NUMBERS (INTEGRAL TYPES) =====
        
        // BYTE: Smallest integer type (-128 to 127)
        // Use when: memory is critical and values are in range
        byte age = -128;
        System.out.println(age);                    // Print the value
        System.out.println(Byte.MAX_VALUE);         // Print maximum value byte can hold
        System.out.println(Byte.MIN_VALUE);         // Print minimum value byte can hold
        
        // SHORT: Medium integer type (-32,768 to 32,767)
        // Use when: need larger range than byte but smaller than int
        short num = 20000;
        System.out.println(Short.MAX_VALUE);        // Maximum short value
        System.out.println(Short.MIN_VALUE);        // Minimum short value
        
        // INT: Default integer type (-2,147,483,648 to 2,147,483,647)
        // Use when: working with regular whole numbers
        System.out.println(Integer.MAX_VALUE);      // Maximum int value
        System.out.println(Integer.MIN_VALUE);      // Minimum int value
        
        // LONG: Largest integer type (for very large numbers)
        // Use when: numbers exceed int range
        // Note: Must end with 'l' or 'L' to specify it's a long literal
        System.out.println(Long.MAX_VALUE);         // Maximum long value
        System.out.println(Long.MIN_VALUE);         // Minimum long value
        long l = 2147483648l;                       // Number too large for int, needs 'l' suffix
        System.out.println(l);                      // Print the long value
        
        
        // ===== DECIMAL NUMBERS (FLOATING-POINT TYPES) =====
        
        // FLOAT: Single precision decimal type
        // Use when: decimal numbers with limited precision
        // Note: Must end with 'f' or 'F' to specify it's a float literal
        float num1 = 10.1234667654523654255f;       // Declare as float with 'f' suffix
        System.out.println(num1);                   // Print float value
        System.out.println(Float.MAX_VALUE);        // Maximum float value
        System.out.println(Float.MIN_VALUE);        // Minimum float value
        
        // DOUBLE: Double precision decimal type (default for decimals)
        // Use when: more precision needed than float offers
        // Note: No suffix needed - decimals are double by default
        double num2 = 10.1234667654523654255;       // Automatically treated as double
        System.out.println(num2);                   // Print double value (more precise than float)
        // System.out.println(Double.MAX_VALUE);           // Commented out - uncomment to see
        // System.out.println(Double.MIN_VALUE);           // Commented out - uncomment to see
        
        
        // ===== CHARACTER TYPE =====
        // char: Stores a single character (A, B, c, 1, @, etc.)
        // Note: Character not initialized in this example
        
        
        // ===== BOOLEAN TYPE =====
        // boolean: Can only hold true or false values
        // Use when: working with conditions and decisions
        boolean b = true;                           // Boolean variable set to true
        boolean b2 = false;                         // Boolean variable set to false
    }
}
