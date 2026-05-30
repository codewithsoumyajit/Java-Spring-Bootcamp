# Solution 1: Character Statistics in a String

## Approach
Traverse the string character by character and classify each character manually.

- If the character is a letter, count it as an alphabet.
- If it is one of `a, e, i, o, u` or uppercase versions, count it as a vowel.
- If it is a letter but not a vowel, count it as a consonant.
- If it is a digit, count it as a digit.
- If it is a space, count it as a space.
- Otherwise, count it as a special character.

## Java Code
```java
import java.util.Scanner;

public class CharacterStatistics {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        int alphabets = 0;
        int vowels = 0;
        int consonants = 0;
        int digits = 0;
        int spaces = 0;
        int special = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            boolean isLetter = false;
            boolean isVowel = false;

            if (ch >= 'A' && ch <= 'Z') {
                isLetter = true;
                if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
                    isVowel = true;
                }
            } else if (ch >= 'a' && ch <= 'z') {
                isLetter = true;
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    isVowel = true;
                }
            }

            if (isLetter) {
                alphabets++;
                if (isVowel) {
                    vowels++;
                } else {
                    consonants++;
                }
            } else if (ch >= '0' && ch <= '9') {
                digits++;
            } else if (ch == ' ') {
                spaces++;
            } else {
                special++;
            }
        }

        System.out.println("Alphabets: " + alphabets);
        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        System.out.println("Digits: " + digits);
        System.out.println("Spaces: " + spaces);
        System.out.println("Special Characters: " + special);

        sc.close();
    }
}
```

## Sample Input
```text
Hello World 123!
```

## Sample Output
```text
Alphabets: 10
Vowels: 3
Consonants: 7
Digits: 3
Spaces: 2
Special Characters: 1
```
