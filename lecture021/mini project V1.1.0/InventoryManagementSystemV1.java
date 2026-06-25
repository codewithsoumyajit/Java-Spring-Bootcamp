package com.soumyajit.mini_project;

import java.util.Scanner;

public class InventoryManagementSystemV1 {

    static ProductV1[] products = new ProductV1[5];
    static boolean productsAdded = false;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n===== WELCOME TO INVENTORY MANAGEMENT SYSTEM =====\n");

        while (true) {

            System.out.println("""
                    
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
                    """);

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {

                case 1 -> {
                    if (productsAdded) {
                        System.out.println("Products already added.");
                    } else {
                        addProduct();
                        productsAdded = true;
                        System.out.println("\n===== ALL PRODUCTS ADDED SUCCESSFULLY =====");
                    }
                }

                case 2 -> {
                    System.out.println("\n===== ALL PRODUCTS =====");
                    displayAllProducts();
                }

                case 3 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = input.nextInt();

                    System.out.println("\n===== SEARCH RESULT =====");
                    search(productId);
                }

                case 4 -> {
                    input.nextLine();

                    System.out.print("Enter Product Name: ");
                    String productName = input.nextLine();

                    System.out.println("\n===== SEARCH RESULT =====");
                    search(productName);
                }

                case 5 -> {
                    System.out.print("Enter Product ID: ");
                    int productId = input.nextInt();

                    System.out.print("Enter New Quantity: ");
                    int quantity = input.nextInt();

                    updateProductQuantity(productId, quantity);
                }

                case 6 -> {
                    System.out.println("\n===== TOTAL INVENTORY VALUE =====");
                    totalInventoryValue();
                }

                case 7 -> {
                    System.out.println("\n===== MOST EXPENSIVE PRODUCT =====");
                    mostExpensiveProduct();
                }

                case 8 -> {
                    System.out.println("\n===== CHEAPEST PRODUCT =====");
                    cheapestProduct();
                }

                case 9 -> {
                    System.out.println("\n===== OUT OF STOCK PRODUCTS =====");
                    outOfStockProduct();
                }

                case 10 -> {
                    System.out.println("Thank you for using Inventory Management System.");
                    return;
                }

                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    static void addProduct() {

        for (int i = 0; i < products.length; i++) {

            ProductV1 p = new ProductV1();

            System.out.println("\nEnter Details For Product " + (i + 1));

            System.out.print("Enter Product ID: ");
            p.productId = input.nextInt();
            input.nextLine();

            System.out.print("Enter Product Name: ");
            p.productName = input.nextLine();

            do {
                System.out.print("Enter Product Price: ");
                p.price = input.nextDouble();

                if (p.price < 0) {
                    System.out.println("Price cannot be negative.");
                }

            } while (p.price < 0);

            do {
                System.out.print("Enter Product Quantity: ");
                p.quantity = input.nextInt();

                if (p.quantity < 0) {
                    System.out.println("Quantity cannot be negative.");
                }

            } while (p.quantity < 0);

            products[i] = p;
        }
    }

    static void displayAllProducts() {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        for (ProductV1 p : products) {

            if (p != null) {
                p.display();
                System.out.println("==============================");
            }
        }
    }

    // Method Overloading

    static void search(int productId) {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        for (ProductV1 p : products) {

            if (p.productId == productId) {
                p.display();
                return;
            }
        }

        System.out.println("Product Not Found");
    }

    static void search(String productName) {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        for (ProductV1 p : products) {

            if (p.productName.equalsIgnoreCase(productName)) {
                p.display();
                return;
            }
        }

        System.out.println("Product Not Found");
    }

    static void updateProductQuantity(int productId, int quantity) {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        for (ProductV1 p : products) {

            if (p.productId == productId) {

                p.quantity = quantity;

                System.out.println("Quantity Updated Successfully.");
                p.display();
                return;
            }
        }

        System.out.println("Product Not Found");
    }

    static void totalInventoryValue() {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        double total = 0;

        for (ProductV1 p : products) {
            total += p.calculateValue();
        }

        System.out.println("Total Inventory Value = " + total);
    }

    static void mostExpensiveProduct() {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        ProductV1 maxProduct = products[0];

        for (int i = 1; i < products.length; i++) {

            if (products[i].price > maxProduct.price) {
                maxProduct = products[i];
            }
        }

        maxProduct.display();
    }

    static void cheapestProduct() {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        ProductV1 cheapestProduct = products[0];

        for (int i = 1; i < products.length; i++) {

            if (products[i].price < cheapestProduct.price) {
                cheapestProduct = products[i];
            }
        }

        cheapestProduct.display();
    }

    static void outOfStockProduct() {

        if (!productsAdded) {
            System.out.println("No products available.");
            return;
        }

        boolean found = false;

        for (ProductV1 p : products) {

            if (p.quantity == 0) {
                p.display();
                System.out.println("==============================");
                found = true;
            }
        }

        if (!found) {
            System.out.println("All Products Are In Stock.");
        }
    }
}

class ProductV1 {

    int productId;
    String productName;
    double price;
    int quantity;

    void display() {

        System.out.println("Product ID      : " + productId);
        System.out.println("Product Name    : " + productName);
        System.out.println("Product Price   : " + price);
        System.out.println("Quantity        : " + quantity);
        System.out.println("Inventory Value : " + calculateValue());
    }

    double calculateValue() {
        return price * quantity;
    }
}