```java
package com.soumyajit.mini_project.TheTruncatedPassengerProblem;

import java.util.Arrays;

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
        seats = new String[size];
        Arrays.fill(seats, "Available");

    }

    // 3. Book Single Seat
    public boolean bookSeat(int seatNumber, String passengerName) {
        // TODO: Implement boundary checks and allocation logic
        if (seatNumber < 0 || seatNumber >= seats.length || !(seats[seatNumber].equals("Available"))) {
            return false;
        } else {
            seats[seatNumber] = passengerName;
            return true;
        }
    }

    // 4. Batch Processing
    public int processBatchRequests(int[] preferredSeats, String[] names) {
        // TODO: Loop through requests sequentially and return total success count
        for (int i = 0; i < preferredSeats.length; i++) {
            if (bookSeat(preferredSeats[i], names[i])) {
                totalSeats++;
            }
        }
        return totalSeats;
    }

    // 5. Render Seating Grid
    public void displayRowChart() {
        // TODO: Print formatted row chart applying the 5-character string rule
        int i = 0;
        for (String name : seats) {
            if (name.length() > 5 && !(name.equals("Available"))) {
                name = name.substring(0, 4);
                name += ".";
            } else if (name.equals("Available")) {
                name = name.substring(0, 5);
                name += ".";
            }
            System.out.printf("[Seat %d : %s] ", i, name);
            i++;
        }
    }
}
