# 🚀 Java Mini Project Assignment

# 📦 Inventory Management System

## 🎯 Objective

Build a **Console-Based Inventory Management System** using Java.

This project is designed to strengthen your understanding of:

* Primitive Data Types
* Strings
* Arrays
* Methods
* Method Overloading
* Classes and Objects
* Conditional Statements
* Loops

> **Important:**
> ❌ Do NOT use Constructors
> ❌ Do NOT use Collections Framework (`ArrayList`, `HashMap`, etc.)
> ❌ Do NOT use Inheritance
> ❌ Do NOT use Database

---

# 🏗️ Class Design

Create a class named **Product**.

## Attributes

```java
int productId;
String productName;
double price;
int quantity;
```

## Methods

```java
void addProduct();
void displayProduct();
double calculateValue();
```

### Inventory Value Formula

```text
Inventory Value = Price × Quantity
```

---

# 📋 Functional Requirements

Create an array capable of storing details of **at least 10 products**.

Implement the following menu:

```text
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
```

---

# ➕ Feature 1: Add Product

Accept the following details:

* Product ID
* Product Name
* Price
* Quantity

Store the product inside the array.

## Validation Rules

```text
Price cannot be negative.
Quantity cannot be negative.
```

---

# 🔍 Feature 2: Search Product by ID

### Input

```text
Enter Product ID
```

### Output

Display complete product information if found.

Otherwise print:

```text
Product Not Found
```

---

# 🔎 Feature 3: Search Product by Name

### Input

```text
Enter Product Name
```

### Output

Display matching product details.

---

# 📄 Feature 4: Display All Products

Display the following information for every stored product:

* Product ID
* Product Name
* Price
* Quantity
* Inventory Value

### Example

```text
ID: 101
Name: Laptop
Price: 50000
Quantity: 5
Inventory Value: 250000
```

---

# ✏️ Feature 5: Update Product Quantity

### Input

```text
Enter Product ID
Enter New Quantity
```

### Output

Update the quantity of the specified product.

---

# 💰 Feature 6: Calculate Total Inventory Value

### Formula

```text
Total Inventory Value =
Sum of (Price × Quantity) of all products
```

### Output

Display the final inventory value.

---

# 💎 Feature 7: Find Most Expensive Product

Display:

```text
Product Name
Price
```

for the product having the highest price.

---

# 💵 Feature 8: Find Cheapest Product

Display:

```text
Product Name
Price
```

for the product having the lowest price.

---

# 📉 Feature 9: Find Out Of Stock Products

Display all products where:

```text
Quantity = 0
```

---

# 🔄 Method Overloading Requirement

Implement **any one** of the following:

### Option 1

```java
search(int productId);
search(String productName);
```

### OR

### Option 2

```java
display();
display(int productId);
display(String productName);
```

---

# ⭐ Bonus Challenges

## 1. Count Total Products

Display the total number of products currently stored.

---

## 2. Low Stock Products

Display products having:

```text
Quantity < 5
```

---

## 3. Average Product Price

Calculate and display:

```text
Average Product Price
```

---

## 4. Expensive Products

Display all products where:

```text
Price > 1000
```

---

## 5. Highest Inventory Value Product

Find the product with the highest:

```text
Price × Quantity
```

---

## 6. Sort Products by Price

Sort products in:

```text
Ascending Order
```

---

## 7. Sort Products by Quantity

Sort products in:

```text
Descending Order
```

---

# 🎓 Expected Learning Outcomes

After completing this project, you should be comfortable with:

✅ Classes and Objects
✅ Arrays of Objects
✅ Methods
✅ Method Overloading
✅ String Handling
✅ Searching Algorithms
✅ Basic Sorting Techniques
✅ Menu Driven Programs
✅ Real-World Problem Solving

---

# 📊 Difficulty Level

**Intermediate Beginner**

✨ Perfect Project Before Learning:

* Constructors
* Advanced OOP Concepts
* Collections Framework
* Inheritance
* Polymorphism

---

# 🏁 Submission Guidelines

* Use meaningful variable names.
* Follow proper indentation.
* Add comments wherever necessary.
* Ensure all menu options work correctly.
* Handle invalid inputs gracefully.
* Demonstrate Method Overloading.

### Submission Files

```text
Product.java
InventoryManagementSystem.java
```

**Best of Luck! 🚀**
