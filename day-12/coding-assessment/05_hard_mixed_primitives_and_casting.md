# 5. Mixed Primitive Types and Casting Challenge

**Difficulty:** Hard  
**Topics:** primitive data types, implicit casting, explicit casting, `String`, loops, arithmetic

## Problem Statement
Write a Java program for a small billing system.

The program should read:

- quantity of items as `int`
- price per item as `double`
- discount percentage as `float`
- tax percentage as `byte`
- delivery charge as `short`

Then it should calculate:

1. total price before discount
2. discount amount
3. price after discount
4. tax amount
5. final payable amount

Also print:
- the final amount rounded down to `int`
- the final amount rounded to the nearest `long`
- the final amount cast to `float`

## Rules
- Use the given primitive data types properly.
- Show at least one example of implicit casting and one example of explicit casting in your solution.
- Do not use any unnecessary helper methods.
- If you store any numeric input as a string first, convert it manually.

## Input
Five values in this order:
1. quantity
2. price per item
3. discount percentage
4. tax percentage
5. delivery charge

## Output
Print each step of the calculation clearly.

## Example
**Input**
```text
3
199.50
10.0
5
40
```

**Output**
```text
Total Price Before Discount: 598.5
Discount Amount: 59.85
Price After Discount: 538.65
Tax Amount: 26.9325
Final Payable Amount: 565.5825
As int: 565
As long: 566
As float: 565.5825
```

## Extra Requirement
Use manual arithmetic carefully so that the final answer demonstrates correct casting behavior and precision handling.
