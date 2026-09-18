# Promotion Module Analysis

This document provides the technical analysis and design decisions for integrating the Promotions and Discounts Module into the GameZone Unicesar system.

---

## Question 1: Class Hierarchy Design and Polymorphism

**Question:** The three promotions have different calculation rules but share common attributes and behaviors. How is this situation reflected in the design of the class hierarchy? What object-oriented programming mechanism allows each type of promotion to calculate its discount differently without the rest of the system needing to know the concrete types?

**Answer:**
* **Hierarchy Design:** This is reflected by creating an abstract base class `Promotion` that encapsulates shared attributes (`id`, `name`, `startDate`, `endDate`) and common behaviors (such as `isActive()`). The three distinct promotion types (`PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`) inherit from `Promotion` as concrete subclasses.
* **OOP Mechanism:** The mechanism is **Polymorphism** combined with **Method Overriding**. The base class declares the abstract method `calculateDiscount(Sale sale)`, which each concrete subclass implements with its specific logic. Higher-level services (such as `PromotionService` or `SaleService`) interact strictly with the abstract `Promotion` type, invoking `calculateDiscount()` dynamically at runtime without needing `instanceof` checks or knowledge of concrete subclasses.

---

## Question 2: Abstract Method Declaration in Base Class

**Question:** The base class `Promotion` cannot implement the discount calculation method because each type has a different logic. How is this method declared in the base class and what does this declaration guarantee regarding the subclasses?

**Answer:**
* **Method Declaration:** The method is declared in `Promotion` using the `abstract` modifier without a method body:
  ```java
  public abstract double calculateDiscount(Sale sale);