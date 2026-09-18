# Accessory Module Analysis

This document provides the technical analysis and design decisions for integrating the Accessory Module into the GameZone Unicesar system.

---

## Question 1: Product Hierarchy Integration vs. Independent Hierarchy

**Question:** Should accessories be integrated into the existing product hierarchy (extending `Product`) or form an independent hierarchy? Justify your decision considering code reuse and model consistency.

**Answer:**
Accessories should **extend the existing `Product` class** (integrating into the product hierarchy).

* **Code Reuse:** Extending `Product` allows `Accessory` to automatically inherit essential attributes (`id`, `title`, `price`, `stockQuantity`) and core domain behaviors (such as stock validation and price access) without duplicating code.
* **Model Consistency & Polymorphism:** In GameZone, accessories are sellable inventory items just like videogames and consoles. By extending `Product`, accessories can be treated polymorphically across the system. This enables `Sale` and `SaleDetail` to process accessories alongside videogames and consoles seamlessly without requiring custom data structures.

---

## Question 2: Common and Specific Attributes in the Hierarchy

**Question:** Which attributes are common to all three types of accessories and which are specific to each type? How is this distinction reflected in the module's class hierarchy?

**Answer:**
* **Common Attributes:** Inherited from `Product` (`id`, `title`, `price`, `stockQuantity`) plus the accessory-specific common attribute: `compatibleConsoles` (a `List<String>` or `List<Console>` storing compatible console identifiers).
* **Type-Specific Attributes:**
  * `Controller`: `connectionType` (String, e.g., "Wireless" or "Wired").
  * `Cable`: `lengthMeters` (double) and `connectorType` (String, e.g., "HDMI", "USB").
  * `Memory`: `capacityGB` (int) and `memoryType` (String, e.g., "SD", "MicroSD").
* **Class Hierarchy Reflection:** The abstract base class `Accessory` (which extends `Product`) encapsulates the common attribute `compatibleConsoles` and declares the abstract/overridden `getDescription()` method. The three concrete subclasses (`Controller`, `Cable`, `Memory`) extend `Accessory` and define only their specific attributes, implementing `getDescription()` to include their distinct details.

---

## Question 3: Console Compatibility Representation and Persistence

**Question:** Compatibility between an accessory and a console is a relationship between two system entities. How is this relationship represented in design and persistence? Is compatibility an attribute of the accessory, the console, or both?

**Answer:**
* **Attribute Placement:** Compatibility is modeled primarily as an attribute of the **Accessory**. An accessory is designed to be used with specific consoles (e.g., a PS5 controller works with PS5 consoles), whereas a console is an independent hardware platform.
* **Object-Oriented Design:** Represented inside the `Accessory` class as a collection attribute, specifically `List<String> compatibleConsoleIds` (or `List<Console>`).
* **Persistence:** In the persistence layer (`accessories.csv` or `accessories.json`), the list of compatible console IDs is stored as a delimited string array or JSON array within each accessory's record. This allows `AccessoryService.findAccessoriesCompatibleWith(consoleId)` to filter and return compatible items efficiently.

---

## Question 4: Additive Modifications in SaleService

**Question:** What modifications are necessary in the sales service class (`SaleService`) so that sales can include accessories without breaking existing behavior with videogames and consoles?

**Answer:**
To support accessories without breaking existing logic, `SaleService` requires the following **additive modifications**:

1. **Dependency Injection:** Inject `AccessoryService` into `SaleService` alongside `ProductService`.
2. **Unified Stock Validation:** During `registerSale`, the stock check verifies both `ProductService` and `AccessoryService` using the item's ID before confirming the transaction.
3. **Delegated Stock Reduction:** When decreasing stock upon sale completion, `SaleService` checks whether the item is a core product or an accessory and delegates the stock update to `productService.reduceStock()` or `accessoryService.updateStock()` accordingly.
4. **Polymorphic Subtotal Calculation:** Since `SaleDetail` references items extending `Product`, price calculations remain unified without modifying the `Sale` class structure.

---

## Question 5: Architectural Layer Allocation

**Question:** In which layer of the system architecture should the new classes of the accessory module be located? Justify your decision based on the responsibilities of each layer.

**Answer:**
The new classes must be distributed strictly across the four architectural layers to respect separation of concerns:

1. **Model Layer (`com.gamezone.model.accessories`):** `Accessory`, `Controller`, `Cable`, and `Memory` reside here. They hold state, getters/setters, and domain rules (e.g., overriding `getDescription()`), with zero I/O or UI knowledge.
2. **Persistence Layer (`com.gamezone.persistence`):** `AccessoryRepository` belongs here to manage file reading/writing (`data/accessories.json` or `.csv`) and handle JSON serialization/deserialization.
3. **Service Layer (`com.gamezone.service`):** `AccessoryService` resides here to coordinate business operations (registering accessories, filtering by type, checking compatibility, and updating inventory).
4. **UI Layer (`com.gamezone.ui`):** `ConsoleUI` / `ConsoleMenu` extensions belong here to capture user input from the terminal and display output messages in Spanish.