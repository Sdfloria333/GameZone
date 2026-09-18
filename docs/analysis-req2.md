# Object-Oriented Design & Architecture Answers

## 1. Class Hierarchy and Discount Mechanism
* **Reflecting the situation in class hierarchy:** A base class (or superclass) named `Promotion` is defined to encapsulate shared attributes (such as `name`, `startDate`, and `endDate`) and common behaviors. The three specific promotion types are modeled as subclasses inheriting directly from `Promotion`.
* **OOP Mechanism:** The core mechanism is **Polymorphism** combined with method overriding. By declaring a common method signature (e.g., `calculateDiscount(Sale sale)`) in the base class, each subclass provides its own distinct implementation. The rest of the system interacts exclusively with the abstract `Promotion` type, triggering the calculation without needing to know the concrete class being executed.

---

## 2. Method Declaration in the Base Class
* **Declaration in the base class:** The method is declared as an **abstract method** using the `abstract` keyword (or defined within an interface/abstract class). Since it contains at least one abstract method, `Promotion` itself must be declared as an abstract class.
* **Guarantee for subclasses:** It enforces a compile-time contract requiring **every concrete subclass to implement the discount calculation logic**. If a subclass fails to override the abstract method, the code will not compile unless that subclass is also marked abstract.

---

## 3. Location of the Best Promotion Selection Logic
* **Logic Location:** It is placed in the Application/Domain layer within a dedicated service class, specifically **`PromotionService`** (or `PromotionManager`).
* **Layered Architecture Coherence:** It honors the separation of concerns principle. Service classes coordinate business rules across multiple entities or collections of domain objects.
* **Why NOT in `Sale` or the Console Menu:**
  * **Not in `Sale`:** The `Sale` entity represents a single transaction and should remain highly cohesive around its own state (line items, total, customer). It should not take on the responsibility of querying, iterating, or comparing global promotion catalogs.
  * **Not in Console Menu:** The console belongs to the **Presentation Layer**. Placing calculation or selection rules there couples business logic to the user interface, preventing code reuse across other delivery mechanisms (such as a REST API or GUI).

---

## 4. Modifications to `Sale` and `generateReceipt`
* **Required Modifications:**
  1. In `Sale`: Add an attribute to hold the applied promotion (e.g., `appliedPromotion` of type `Promotion`) or the discounted amount (`discountAmount`), along with corresponding getters and setters.
  2. In `generateReceipt`: Update the receipt formatting logic to output a line item displaying the applied promotion name and the deducted amount from the grand total.
* **Impact on existing system:** **It does not break existing behavior**, provided the **Open/Closed Principle** is followed. If a promotion is optional or null (when no promotion applies), the receipt generator safely skips the discount line or renders `0.0`, keeping previous total calculation flows intact.

---

## 5. Promotion Date Validity Location
* **Answer:** Date validation is distributed across **both classes**, splitting responsibilities cleanly:

| Class | Responsibility | Justification |
| :--- | :--- | :--- |
| **`Promotion`** | Single-instance validation (`isActive(LocalDate currentDate)`) | Each promotion instance holds its own start and end dates. Applying the **Information Expert** pattern, the entity autonomously answers whether it is active for a given date. |
| **`PromotionService`** | Orchestration and filtering | The service retrieves the current date, iterates over registered promotions, and delegates to each promotion's `isActive(...)` method to filter active candidates before selecting the highest discount. |