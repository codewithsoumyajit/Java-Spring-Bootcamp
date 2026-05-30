# Solution 5: Mixed Primitive Types and Casting Challenge

## Approach
Read all values using the required primitive types and calculate the bill step by step.

### Calculation order
1. Total price before discount = quantity × price
2. Discount amount = total × discount / 100
3. Price after discount = total - discount
4. Tax amount = after discount × tax / 100
5. Final payable amount = after discount + tax + delivery charge

### Casting
- Use implicit casting where smaller values become larger values automatically.
- Use explicit casting when converting the final amount into `int`, `long`, or `float`.

## Java Code
```java
import java.util.Scanner;

public class BillingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int quantity = sc.nextInt();
        double pricePerItem = sc.nextDouble();
        float discountPercent = sc.nextFloat();
        byte taxPercent = sc.nextByte();
        short deliveryCharge = sc.nextShort();

        double totalPrice = quantity * pricePerItem;
        double discountAmount = totalPrice * discountPercent / 100.0;
        double priceAfterDiscount = totalPrice - discountAmount;
        double taxAmount = priceAfterDiscount * taxPercent / 100.0;
        double finalPayableAmount = priceAfterDiscount + taxAmount + deliveryCharge;

        System.out.println("Total Price Before Discount: " + totalPrice);
        System.out.println("Discount Amount: " + discountAmount);
        System.out.println("Price After Discount: " + priceAfterDiscount);
        System.out.println("Tax Amount: " + taxAmount);
        System.out.println("Final Payable Amount: " + finalPayableAmount);

        int asInt = (int) finalPayableAmount;
        long asLong = Math.round(finalPayableAmount);
        float asFloat = (float) finalPayableAmount;

        System.out.println("As int: " + asInt);
        System.out.println("As long: " + asLong);
        System.out.println("As float: " + asFloat);

        sc.close();
    }
}
```

## Sample Input
```text
3
199.50
10.0
5
40
```

## Sample Output
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
