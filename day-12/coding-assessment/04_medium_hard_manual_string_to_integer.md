# 4. Manual String to Integer Conversion

**Difficulty:** Medium-Hard  
**Topics:** `String`, primitive data types, type casting, arithmetic, loops

## Problem Statement
Write a Java program that reads a numeric string and converts it into an integer manually.

After conversion, the program must:

1. Print the converted integer.
2. Print the square of the number.
3. Print the result after explicitly casting it to `byte`, `short`, and `long`.

## Rules
- Do **not** use `Integer.parseInt()`.
- Do **not** use any built-in `String` method for conversion.
- Handle only optional leading `+` or `-` signs.
- Use loops and primitive variables only.

## Input
A numeric string.

## Output
Print:
- converted integer
- square of the number
- values after casting to `byte`, `short`, and `long`

## Example
**Input**
```text
-123
```

**Output**
```text
Integer Value: -123
Square: 15129
As byte: -123
As short: -123
As long: -123
```

## Extra Requirement
If the converted value goes out of `int` range, print an appropriate message instead of crashing.
