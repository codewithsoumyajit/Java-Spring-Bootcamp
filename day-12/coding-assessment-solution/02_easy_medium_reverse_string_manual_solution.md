# Solution 2: Reverse a String Without Built-in Methods

## Approach
Read the string and print characters from the last index to the first index using a loop.

To check palindrome:
- Compare the first character with the last character
- Move inward from both ends
- If all pairs match, the string is a palindrome

## Java Code
```java
import java.util.Scanner;

public class ReverseStringManual {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        String reversed = "";

        for (int i = text.length() - 1; i >= 0; i--) {
            reversed = reversed + text.charAt(i);
        }

        System.out.println(reversed);

        boolean palindrome = true;
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                palindrome = false;
                break;
            }
            left++;
            right--;
        }

        if (palindrome) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not Palindrome");
        }

        sc.close();
    }
}
```

## Sample Input
```text
Programming
```

## Sample Output
```text
gnimmargorP
Not Palindrome
```
