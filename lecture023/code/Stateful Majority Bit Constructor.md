# Problem: Stateful Majority Bit Constructor

**Difficulty:** Medium
**Tags:** Bit Manipulation, Static State, Procedural Design

## Description

You are tasked with implementing a procedural analyzer that processes a stream of integers. Your goal is to dynamically construct and return a "majority bit" number based on all the integers processed since the last reset.

The **majority bit number** is an integer formed by examining each bit position (from 0 to 31). If a bit is set to `1` in **strictly more than half** of all the numbers ingested so far, that bit is set to `1` in the result. Otherwise, it is set to `0`.

You must implement this using **only** `static` methods and `static` primitive variables or primitive arrays to maintain state across function calls. 

Implement the following three `static` methods:
*   `public static void ingest(int num)`: Processes a new integer from the stream.
*   `public static int getMajorityNumber()`: Constructs and returns the current majority bit number. If no numbers have been ingested, return `0`.
*   `public static void clearState()`: Resets all static trackers and counters to their default state.

## Example 1

```text
Input:
["ingest", "getMajorityNumber", "ingest", "getMajorityNumber", "ingest", "getMajorityNumber", "clearState"]
[[3], [], [5], [], [3], [], []]

Output:
[null, 3, null, 1, null, 3, null]

Explanation:
1. ingest(3): Stream is [3] (Binary: 011). 
   - getMajorityNumber(): Bits 0 and 1 are set in 1 out of 1 numbers. Result = 3 (011).
2. ingest(5): Stream is [3, 5] (Binary: 011, 101). Total ingested = 2. Majority threshold > 1.
   - getMajorityNumber(): 
     - Bit 0 is set in both (count = 2). 2 > 1, so bit 0 is 1.
     - Bit 1 is set in one (count = 1). 1 is not > 1, so bit 1 is 0.
     - Bit 2 is set in one (count = 1). 1 is not > 1, so bit 2 is 0.
     Result = 1 (001).
3. ingest(3): Stream is [3, 5, 3] (Binary: 011, 101, 011). Total ingested = 3. Majority threshold > 1.
   - getMajorityNumber():
     - Bit 0 count = 3. 3 > 1, so bit 0 is 1.
     - Bit 1 count = 2. 2 > 1, so bit 1 is 1.
     - Bit 2 count = 1. 1 is not > 1, so bit 2 is 0.
     Result = 3 (011).
4. clearState(): Stream is cleared.
```

## Constraints

*   `-10^9 <= num <= 10^9`
*   At most `10^5` calls will be made in total to `ingest` and `getMajorityNumber`.
*   **Do not** use the `final` keyword anywhere in your solution.
*   **Do not** instantiate any classes or use Java Collections (e.g., no `ArrayList`, `HashMap`, or object instances). Stick to primitives and procedural logic.

---

## Starter Code

```java
class Solution {
    
    // Declare your static state variables here
    
    public static void ingest(int num) {
        // Implementation
    }
    
    public static int getMajorityNumber() {
        // Implementation
        return 0;
    }
    
    public static void clearState() {
        // Implementation
    }
}
```