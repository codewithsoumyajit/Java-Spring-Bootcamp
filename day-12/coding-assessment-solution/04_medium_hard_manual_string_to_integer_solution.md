# Solution 4: Manual String to Integer Conversion

## Approach
Convert the string digit by digit.

For each character:
- Check whether it is a digit.
- Multiply the current result by 10.
- Add the digit value.

Also:
- Handle an optional `+` or `-`.
- Check overflow before updating the result.
- After conversion, demonstrate explicit casting.

## Java Code
```java
import java.util.Scanner;

public class ManualStringToInteger {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        int sign = 1;
        int index = 0;

        if (text.charAt(0) == '+') {
            index = 1;
        } else if (text.charAt(0) == '-') {
            sign = -1;
            index = 1;
        }

        long result = 0;
        boolean valid = true;

        for (int i = index; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch < '0' || ch > '9') {
                valid = false;
                break;
            }

            int digit = ch - '0';

            if (result > (Long.MAX_VALUE - digit) / 10) {
                valid = false;
                break;
            }

            result = result * 10 + digit;
        }

        if (!valid) {
            System.out.println("Invalid input or overflow occurred.");
        } else {
            result = result * sign;

            if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                System.out.println("Value out of int range.");
            } else {
                int value = (int) result;

                System.out.println("Integer Value: " + value);
                System.out.println("Square: " + (value * value));
                System.out.println("As byte: " + (byte) value);
                System.out.println("As short: " + (short) value);
                System.out.println("As long: " + (long) value);
            }
        }

        sc.close();
    }
}
```

## Sample Input
```text
-123
```

## Sample Output
```text
Integer Value: -123
Square: 15129
As byte: -123
As short: -123
As long: -123
```
