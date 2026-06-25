/*
  PROBLEM STATEMENT FILE LOCATION :
    * day-21/Problem-Statement.md
* */

package com.soumyajit.mini_project;

import java.util.Scanner;

public class InventoryManagementSystem {
    static Product[] products = new Product[5];

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("\n\nWelcome to the Inventory Management System\n\n");


        System.out.println("""
                *** Note: Please add the products before executing other menu's
                          Press -> "1" to Add the products
                """);

        while (true) {

            String message = """
                    1. Add Product
                    2. Display All Products
                    3. Search Product by ID
                    4. Search Product by Name
                    5. Update Product Quantity
                    6. Calculate Total Inventory Value
                    7. Find Most Expensive Product
                    8. Find Cheapest Product
                    9. Find Out Of Stock Products
                    10. Exit
                    """;

            System.out.println(message);
            System.out.print("Enter your choice: ");
            switch (input.nextInt()) {
                case 1 -> {
                    addProduct();
                    System.out.println(" ===== ALL PRODUCTS ADDED SUCCESSFULLY ===== ");
                }
                case 2 -> {
                    System.out.println(" ===== ALL PRODUCTS ===== ");
                    displayAllProducts();
                }
                case 3 -> {
                    System.out.println(" ===== PRODUCT SEARCH BY ID ===== ");
                    search(104);
                }
                case 4 -> {
                    System.out.println(" ===== PRODUCT SEARCH BY NAME ===== ");
                    search("Oppo");
                }
                case 5 -> {
                    System.out.println(" ===== UPDATING PRODUCT QUANTITY ===== ");
                    updateProductQuantity(104, 6);
                }
                case 6 -> {
                    System.out.println(" ===== TOTAL INVENTORY VALUE ===== ");
                    totalInventoryValue();
                }
                case 7 -> {
                    System.out.println(" ===== MOST EXPENSIVE PRODUCT ===== ");
                    mostExpensiveProduct();
                }
                case 8 -> {
                    System.out.println(" ===== CHEAPEST PRODUCT ===== ");
                    cheapestProduct();
                }
                case 9 -> {
                    System.out.println(" ===== OUT OF STOCK PRODUCTS ===== ");
                    outOfStockProduct();
                }
                case 10 -> {
                    return;
                }

                default -> System.out.println("Invalid choice");
            }
        }
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
            if (cheapestProduct.price > products[i].price) {
                cheapestProduct = products[i];
            }
        }

        cheapestProduct.display();
    }

    static void outOfStockProduct() {
        for (Product p : products) {
            if (p.quantity <= 0) {
                p.display();
                return;
            }
        }
        System.out.println("All Products are in stock");
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
