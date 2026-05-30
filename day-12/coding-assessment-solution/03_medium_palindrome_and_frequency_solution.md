# Solution 3: Palindrome Check and Character Frequency

## Approach
This problem has two parts.

### Palindrome check
- Ignore spaces.
- Compare characters from both ends.
- Make comparison case-insensitive manually without using case-conversion methods.

### Frequency count
- Keep a `visited` array to avoid counting the same character twice.
- Traverse the string and count each character occurrence manually.

## Java Code
```java
import java.util.Scanner;

public class PalindromeAndFrequency {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        // Palindrome check ignoring spaces
        boolean palindrome = true;
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            while (left < right && text.charAt(left) == ' ') {
                left++;
            }
            while (left < right && text.charAt(right) == ' ') {
                right--;
            }

            char a = text.charAt(left);
            char b = text.charAt(right);

            if (a >= 'A' && a <= 'Z') {
                a = (char) (a + 32);
            }
            if (b >= 'A' && b <= 'Z') {
                b = (char) (b + 32);
            }

            if (a != b) {
                palindrome = false;
                break;
            }

            left++;
            right--;
        }

        if (palindrome) {
            System.out.println("Palindrome: Yes");
        } else {
            System.out.println("Palindrome: No");
        }

        // Frequency count
        boolean[] visited = new boolean[text.length()];
        System.out.println("Character Frequencies:");

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);

            if (current == ' ') {
                continue;
            }

            if (visited[i]) {
                continue;
            }

            int count = 0;
            for (int j = 0; j < text.length(); j++) {
                if (text.charAt(j) == current) {
                    count++;
                    visited[j] = true;
                }
            }

            System.out.println(current + " -> " + count);
        }

        sc.close();
    }
}
```

## Sample Input
```text
nurses run
```

## Sample Output
```text
Palindrome: Yes
Character Frequencies:
n -> 2
u -> 2
r -> 2
s -> 2
e -> 1
```
