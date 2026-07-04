# 🕹️ Java Beginner Project: Number Guessing Game

## Problem Statement

You are developing a simple **Number Guessing Game**.

The computer has already chosen a secret number. Your program should allow the player to guess the number within a limited number of attempts.

---

## Requirements

1. Store the secret number in an integer variable.
2. Allow the user to enter guesses using a loop.
3. The player gets **5 attempts** to guess the correct number.
4. After each guess:
   - If the guess is smaller than the secret number, print:
     ```
     Too Low!
     ```
   - If the guess is greater than the secret number, print:
     ```
     Too High!
     ```
   - If the guess is correct, print:
     ```
     Congratulations! You guessed the correct number.
     ```
     and terminate the game immediately.
5. If all attempts are exhausted without guessing correctly, print:
   ```
   Game Over!
   The correct number was <secretNumber>.
   ```

---

## Bonus Challenge

- Store all the user's guesses in an array.
- At the end of the game, print all the guesses entered by the player.

Example:

```
Your Guesses:
12 25 30 42 50
```

---

## Sample Output 1

```
Welcome to the Number Guessing Game!

Attempt 1: Enter your guess:
20
Too Low!

Attempt 2: Enter your guess:
35
Too High!

Attempt 3: Enter your guess:
30

Congratulations! You guessed the correct number.
```

---

## Sample Output 2

```
Welcome to the Number Guessing Game!

Attempt 1: Enter your guess:
10
Too Low!

Attempt 2: Enter your guess:
15
Too Low!

Attempt 3: Enter your guess:
22
Too Low!

Attempt 4: Enter your guess:
45
Too High!

Attempt 5: Enter your guess:
50
Too High!

Game Over!
The correct number was 30.

Your Guesses:
10 15 22 45 50
```

---

## Constraints

- Use `Scanner` for input.
- Use at least one loop.
- Use an array to store guesses.
- Do not use classes, objects, constructors, methods, or any advanced OOP concepts.
- Keep the program in a single `main()` method.

---

## Concepts You'll Practice

- Variables
- Input using `Scanner`
- `if-else`
- Loops (`for` or `while`)
- Arrays
- `break`
- Basic problem-solving

---

## Difficulty

⭐ Beginner

**Estimated Time:** 20–30 minutes