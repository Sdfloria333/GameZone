# System Analysis & Design - GameZone Unicesar

## About System Persons

### 1. What attributes are common to all persons interacting with the store, and which are specific to each role? How is this reflected in a class hierarchy?
* **Common Attributes:** Every person in the system shares basic identifying attributes, specifically: ID / Identification Number (`id`), Full Name (`name`), and Contact Phone (`phone`).
* **Specific Attributes:**
    * **Customer (`Customer`):** Unique attributes include Email Address (`email`) and Purchase History (`purchaseHistory`).
    * **Seller / Employee (`Seller`):** Unique attributes include Employee Code (`employeeCode`) and Work Shift (`workShift`).
* **Class Hierarchy Reflection:** This distinction is reflected using Object-Oriented Inheritance. Common attributes are defined in a base class named `Person`, while specific attributes are encapsulated within derived classes (`Customer` and `Seller`) extending `Person`.

### 2. Should a class representing a "generic person" exist without specifying a role? Why or why not? What implication does this decision have on instantiating it?
* **Decision:** Yes, a general base class `Person` must exist to promote code reusability and polymorphism. However, in terms of business rules, every real-world entity interacting with the store is either a Customer or a Seller; a "generic person" with no role does not exist.
* **Implication:** The `Person` class must be declared as an **`abstract` class**. Declaring it abstract prevents direct instantiation (`new Person()` is prohibited), guaranteeing that only concrete derived entities (`Customer` and `Seller`) can be instantiated.

---

## About System Products

### 3. What characteristics are shared by all products sold by the store, regardless of type? What characteristics are specific to each product type?
* **Common Characteristics:** All products share core inventory attributes: Product Identifier (`id`), Title (`title`), Price (`price`), and Available Stock Quantity (`stockQuantity`).
* **Specific Characteristics:**
    * **Video Games (`Videogame`):** Specific attributes include Target Platform (`platform`), Genre (`genre`), and Recommended Age Rating (`ageRating`).
    * **Consoles (`Console`):** Specific attributes include Brand (`brand`), Specific Model (`model`), and Hardware Generation (`generation`).

### 4. How should the custom description behavior be declared in the base class to guarantee that all subclasses implement it properly? What OOP mechanism allows this?
* **Declaration:** The base class `Product` must declare an abstract method `public abstract String getDescription()`.
* **OOP Mechanism:** This is achieved through **Polymorphism and Method Overriding**. Declaring the method as `abstract` forces each concrete subclass (`Videogame` and `Console`) to provide its own `@Override` implementation integrating its specialized attributes.

---

## About Sales and Entity Relationships

### 5. What type of relationships exist between the Sale class and other system entities? Are these inheritance, association, composition, or another type?
* **Relationship with Customer and Seller:** **Aggregation / Association**. A sale links one existing `Customer` and one existing `Seller`. If the sale is deleted, the customer and seller entities continue to exist independently in system records.
* **Relationship with Products / Sale Items:** **Aggregation / Association**. A sale contains one or more `Product` objects (or `SaleItem` instances). The products exist in the store inventory independently of whether a sale record is created or removed.

### 6. Should the Sale class be responsible for calculating its own total, or should this recede into another class?
* **Decision:** The `Sale` class **must be responsible** for calculating its own total.
* **Justification:** Following the Information Expert Principle, `Sale` holds direct internal access to the list of purchased products and their respective unit prices/quantities. Encapsulating `calculateTotal()` inside `Sale` maintains strong cohesion.

---

## About Business Constraints

### 7. How is it guaranteed in the design that a sale cannot be registered without at least one product? Where should this rule be validated?
* **Guarantee:** In the `Sale` class constructor or via explicit setter logic, the product collection must be checked.
* **Validation Point:** This business constraint must be validated inside the **Service Layer (`SaleService`)** prior to processing the transaction and passing the sale to the persistence layer.

### 8. How is the automatic inventory update reflected in the design when a sale is registered? Which classes are involved?
* **Design & Workflow:** When `SaleService.registerSale(...)` is executed, it iterates through the products in the sale, calls `ProductService.updateStock(...)` to deduct sold quantities, and then persists both updated inventory and the new sale record.
* **Involved Classes:** `SaleService`, `ProductService`, `persistence`, `SaleRepository`, and domain models `Sale` and `Product`.

---

## About Layered Architecture

### 9. What type of classes belong to each of the four layers? What criterion determines where a class belongs?
1. **Model (`com.gamezone.model`):** Pure domain entities holding data, state, and domain business rules (`Person`, `Customer`, `Seller`, `Product`, `Videogame`, `Console`, `Sale`).
2. **Persistence (`com.gamezone.persistence`):** Data Access Objects responsible exclusively for reading from and writing to storage files (`PersonRepository`, `persistence`, `SaleRepository`).
3. **Services (`com.gamezone.service`):** Business logic orchestrators validating rules, coordinating transactions between models and repositories (`PersonService`, `ProductService`, `SaleService`).
4. **User Interface (`com.gamezone.ui`):** Presentation components handling user input, console menus, and output formatting (`ConsoleUI`).
* **Criterion:** Single Responsibility Principle (SRP) and Separation of Concerns.

### 10. Why shouldn't file storage logic exist inside domain classes? What problems occur when mixing responsibilities?
* **Why:** Domain classes should represent business concepts, not storage mechanisms.
* **Problems caused by mixing:** High coupling, poor maintainability, inability to write clean unit tests, and violation of the Single Responsibility Principle.

### 11. What layer dependencies are allowed and prohibited? Justify the permitted directions.
* **Allowed Dependencies:**
    * `ui` → `service`
    * `service` → `persistence`
    * `service` → `model`
    * `persistence` → `model`
* **Prohibited Dependencies:**
    * `model` → any other layer
    * `ui` → `persistence` directly
    * Circular dependencies between any layers
* **Justification:** Downward-only dependencies ensure that UI changes do not affect business logic, and business logic remains independent of storage implementations.