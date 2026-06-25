/*
* JAVA MINI PROJECT ASSIGNMENT

PROJECT TITLE: INVENTORY MANAGEMENT SYSTEM

Objective:
Build a console-based Inventory Management System using Java.

Topics Covered:
- Primitive Data Types
- Strings
- Arrays
- Methods
- Method Overloading
- Classes and Objects
- Conditional Statements
- Loops

Note:
Do NOT use Constructors, Collections Framework, Inheritance, or Database.

==================================================

Create a class named Product.

Attributes:
- int productId
- String productName
- double price
- int quantity

Methods:
- void addProduct()
- void displayProduct()
- double calculateValue()

Inventory Value Formula:
Inventory Value = price × quantity

==================================================

FUNCTIONAL REQUIREMENTS

Create an array capable of storing details of at least 10 products.

Implement the following menu:

1. Add Product
2. Search Product by ID
3. Search Product by Name
4. Display All Products
5. Update Product Quantity
6. Calculate Total Inventory Value
7. Find Most Expensive Product
8. Find Cheapest Product
9. Find Out Of Stock Products
10. Exit

==================================================

FEATURE 1: ADD PRODUCT

Accept:
- Product ID
- Product Name
- Price
- Quantity

Store the product inside the array.

Validation:
- Price cannot be negative.
- Quantity cannot be negative.

==================================================

FEATURE 2: SEARCH PRODUCT BY ID

Input:
Enter Product ID

Display complete product information if found.

Otherwise print:
Product Not Found

==================================================

FEATURE 3: SEARCH PRODUCT BY NAME

Input:
Enter Product Name

Search and display matching product details.

==================================================

FEATURE 4: DISPLAY ALL PRODUCTS

Display:
- Product ID
- Product Name
- Price
- Quantity
- Inventory Value

for every stored product.

==================================================

FEATURE 5: UPDATE QUANTITY

Input:
- Product ID
- New Quantity

Update the quantity of that product.

==================================================

FEATURE 6: CALCULATE TOTAL INVENTORY VALUE

Formula:
Total Inventory Value = Sum of (Price × Quantity) of all products

Display the final inventory value.

==================================================

FEATURE 7: FIND MOST EXPENSIVE PRODUCT

Display:
- Product Name
- Price

of the product having maximum price.

==================================================

FEATURE 8: FIND CHEAPEST PRODUCT

Display:
- Product Name
- Price

of the product having minimum price.

==================================================

FEATURE 9: FIND OUT OF STOCK PRODUCTS

Display all products where:
Quantity = 0

==================================================

METHOD OVERLOADING REQUIREMENT

Implement any one of the following:

search(int productId)
search(String productName)

OR

display()
display(int productId)
display(String productName)

==================================================

BONUS CHALLENGES

1. Count total products in inventory.

2. Count products having quantity less than 5.

3. Find average product price.

4. Display products whose price is greater than 1000.

5. Find product having highest inventory value.
   Inventory Value = Price × Quantity

6. Sort products by price in ascending order.

7. Sort products by quantity in descending order.

==================================================

EXPECTED LEARNING OUTCOMES

After completing this project, you should be comfortable with:

- Classes and Objects
- Arrays of Objects
- Methods
- Method Overloading
- String Handling
- Searching Algorithms
- Basic Sorting
- Menu Driven Programs
- Real-world Problem Solving

Difficulty Level:
Intermediate Beginner (Perfect before learning Constructors and advanced OOP concepts)
* */


package com.soumyajit.mini_project;

public class InventoryManagementSystem {
    static Product[] products = new Product[5];

    public static void main(String[] args) {

        addProduct();

        System.out.println(" ===== ALL PRODUCTS ADDED SUCCESSFULLY ===== ");

        System.out.println(" ===== ALL PRODUCTS ===== ");
        displayAllProducts();

        System.out.println(" ===== PRODUCT SEARCH BY ID ===== ");
        search(104);

        System.out.println(" ===== PRODUCT SEARCH BY NAME ===== ");
        search("Oppo");

        System.out.println(" ===== UPDATING PRODUCT QUANTITY ===== ");
        updateProductQuantity(104, 6);

        System.out.println(" ===== TOTAL INVENTORY VALUE ===== ");
        totalInventoryValue();

        System.out.println(" ===== MOST EXPENSIVE PRODUCT ===== ");
        mostExpensiveProduct();

        System.out.println(" ===== CHEAPEST PRODUCT ===== ");
        cheapestProduct();

        System.out.println(" ===== OUT OF STOCK PRODUCTS ===== ");
        outOfStockProduct();

    }

    static void addProduct() {
        Product p1 = new Product();
        p1.productId = 101;
        p1.productName = "Samsung";
        p1.price = 1_00_000;
        p1.quantity = 2;

        Product p2 = new Product();
        p2.productId = 102;
        p2.productName = "Apple";
        p2.price = 1_50_000;
        p2.quantity = 3;


        Product p3 = new Product();
        p3.productId = 103;
        p3.productName = "Google Pixel";
        p3.price = 1_20_000;
        p3.quantity = 5;

        Product p4 = new Product();
        p4.productId = 104;
        p4.productName = "Vivo";
        p4.price = 1_25_000;
        p4.quantity = 12;

        Product p5 = new Product();
        p5.productId = 105;
        p5.productName = "Oppo";
        p5.price = 1_10_000;
        p5.quantity = 6;

        products[0] = p1;
        products[1] = p2;
        products[2] = p3;
        products[3] = p4;
        products[4] = p5;

    }

    static void displayAllProducts() {
        for (Product p : products) {
            p.display();
            System.out.println(" ============================== ");
        }
    }

    // Method Overloading

    static void search(String productName) {
        for (Product p : products) {
            if (p.productName.equalsIgnoreCase(productName)) {
                p.display();
                return;
            }
        }
        System.out.println("Products not found");
    }

    static void search(int productId) {
        for (Product p : products) {
            if (p.productId == productId) {
                p.display();
                return;
            }
        }
        System.out.println("Product not found");
    }

    static void updateProductQuantity(int productId, int quantity) {
        for (Product p : products) {
            if (p.productId == productId) {
                p.quantity = quantity;
                System.out.println("Quantity updated");
                p.display();
                return;
            }
        }
        System.out.println("Product Not found");
    }

    static void totalInventoryValue() {
        double total = 0;
        for (Product p : products) {
            total += p.calculateValue();
        }
        System.out.println("Total Inventory Value: " + total);
    }

    static void mostExpensiveProduct() {
//        Product maxValue = products[0];
//
//        for (int i = 1; i < products.length; i++) {
//            if(products[i].price > maxValue.price) {
//                maxValue = products[i];
//            }
//        }
//        maxValue.display();

        double maxValue = Double.MIN_VALUE;
        Product maxProduct = null;

        for (Product p : products) {
            if (p.price > maxValue) {
                maxValue = p.price;
                maxProduct = p;
            }
        }

        if (maxProduct != null) {
            maxProduct.display();
        }
    }

    static void cheapestProduct() {
        Product cheapestProduct = products[0];

        for (int i = 1; i < products.length; i++) {
            if(cheapestProduct.price > products[i].price) {
                cheapestProduct = products[i];
            }
        }

        cheapestProduct.display();
    }

    static void outOfStockProduct() {
        for (Product p : products) {
            if(p.quantity <= 0){
                p.display();
            }
        }
    }

}

class Product {
    int productId;
    String productName;
    double price;
    int quantity;

    void display() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Inventory Value: " + calculateValue());
    }

    double calculateValue() {
        return price * quantity;
    }
}
