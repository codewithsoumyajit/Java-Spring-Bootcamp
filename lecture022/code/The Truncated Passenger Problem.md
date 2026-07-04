# 🏛️. Theater Seating Manager

<div align="center">
  <img src="https://img.shields.io/badge/Difficulty-Medium-orange?style=for-the-badge" alt="Medium">
  <img src="https://img.shields.io/badge/Topic-Arrays%20%26%20Strings-blue?style=for-the-badge" alt="Arrays & Strings">
  <img src="https://img.shields.io/badge/Time%20Limit-30--40%20Mins-red?style=for-the-badge" alt="Time Limit">
</div>

---

### 📖 Problem Description

You are tasked with building the core backend logic for a theater's seat allocation engine. The theater consists of a single row of seats numbered sequentially from `0` to `size - 1`. Initially, all seats are empty and marked with the status string `"Available"`.

Implement the `RowManager` class to handle bookings, batch processing, and formatted seat map rendering without using advanced OOP features (like inheritance or interfaces).

---

### ⚙️ Class Specification

Implement the following methods in the `RowManager` class:

#### 1. `RowManager(int size)`
*   Initializes the seating row with `size` number of elements.
*   Sets the initial state of every seat to the default string `"Available"`.

#### 2. `boolean bookSeat(int seatNumber, String passengerName)`
*   Attempts to assign the given `passengerName` to `seatNumber`.
*   **Returns `false`** if `seatNumber` is out of bounds or if the seat is already occupied (i.e., its current state is not `"Available"`).
*   **Returns `true`** if the seat is successfully updated with the `passengerName`.

#### 3. `int processBatchRequests(int[] preferredSeats, String[] names)`
*   Processes a list of real-time booking requests simultaneously. The $i^{th}$ person in the `names` array wants to book the seat specified at `preferredSeats[i]`.
*   Requests **must** be processed in strict sequential order from index `0` to `n - 1`.
*   **Returns** the total number of successfully completed bookings from this batch.

#### 4. `void displayRowChart()`
*   Prints a single-line visual layout of the row to standard output.
*   **Format Layout:** `[Seat 0: Name] [Seat 1: Name] ... `
*   ⚠️ **UI Constraint:** To prevent text overflow, if any name (or the status string) is **strictly greater than 5 characters long**, it must be truncated to its first 4 characters followed by a period `.` (e.g., `"Jonathan"` $\rightarrow$ `"Jona."`, `"Available"` $\rightarrow$ `"Avail."`). Otherwise, print the name as is.

---

### 📥 Example 1

#### **Execution Sequence**
```java
RowManager row = new RowManager(6);
row.bookSeat(2, "Alice");
int successfulBatch = row.processBatchRequests(
    new int[]{0, 2, 5, 12}, 
    new String[]{"Bob", "Charlie", "Jonathan", "David"}
);
row.displayRowChart();

```

#### **Output Console**

```text
// System.out.println(successfulBatch) prints: 2

[Seat 0: Bob] [Seat 1: Avail.] [Seat 2: Alice] [Seat 3: Avail.] [Seat 4: Avail.] [Seat 5: Jona.] 

```

#### **Step-by-Step Explanation**

1. `RowManager(6)` initializes 6 empty seats: `["Available", ..., "Available"]`.
2. `bookSeat(2, "Alice")` successfully books seat 2. Returns `true`.
3. `processBatchRequests` loops through the batch of 4 requests:
* **Index 0:** "Bob" wants seat 0 $\rightarrow$ Available $\rightarrow$ **Success** ✅
* **Index 1:** "Charlie" wants seat 2 $\rightarrow$ Taken by "Alice" $\rightarrow$ **Failed** ❌
* **Index 2:** "Jonathan" wants seat 5 $\rightarrow$ Available $\rightarrow$ **Success** ✅
* **Index 3:** "David" wants seat 12 $\rightarrow$ Out of bounds $\rightarrow$ **Failed** ❌
* Total successful batch bookings = `2`.


4. `displayRowChart()` runs string constraints: `"Available"` becomes `"Avail."` and `"Jonathan"` becomes `"Jona."`.

---

### 🛑 Constraints

* `1 <= size <= 100`
* `preferredSeats.length == names.length`
* `1 <= names.length <= 100`
* `0 <= preferredSeats[i] <= 1000`
* `names[i]` consists only of printable English characters.
* 🚫 **Constraint:** You are **not** allowed to import external collections (`ArrayList`, `HashMap`, etc.) or use advanced OOP architecture. Solve using primitive arrays, loops, and core `String` methods.

---

### 💻 Starter Code

```java
public class Main {
    public static void main(String[] args) {
        // Test your logic here!
        RowManager row = new RowManager(6);
        row.bookSeat(2, "Alice");
        
        int batchCount = row.processBatchRequests(
            new int[]{0, 2, 5, 12}, 
            new String[]{"Bob", "Charlie", "Jonathan", "David"}
        );
        
        System.out.println("Successful batch bookings: " + batchCount);
        System.out.println("\n--- Seating Chart ---");
        row.displayRowChart();
    }
}

class RowManager {
    // 1. Instance Variables
    private String[] seats;
    private int totalSeats;

    // 2. Constructor
    public RowManager(int size) {
        // TODO: Initialize array and fill with "Available"
    }
    
    // 3. Book Single Seat
    public boolean bookSeat(int seatNumber, String passengerName) {
        // TODO: Implement boundary checks and allocation logic
        return false;
    }
    
    // 4. Batch Processing
    public int processBatchRequests(int[] preferredSeats, String[] names) {
        // TODO: Loop through requests sequentially and return total success count
        return 0;
    }
    
    // 5. Render Seating Grid
    public void displayRowChart() {
        // TODO: Print formatted row chart applying the 5-character string rule
    }
}

```

---

💡 **Tip:** Set a timer for **35 minutes**, keep an eye out for off-by-one errors on the array boundaries, and remember to use `String.substring()` or similar logic for truncation! Good luck!