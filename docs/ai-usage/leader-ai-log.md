# AI Usage Log - GameZone Unicesar

This document records the prompt engineering, architectural assistance, code generation, and verification process associated with Artificial Intelligence tools used during the development of the Sales Module and Tech Lead integration tasks.

---

## 1. Tool Declaration

* **Primary AI Tool:** Gemini (Google DeepMind)
* **Scope of Usage:** Architecture design verification, persistence strategy formulation (JSON with Gson), domain model creation, service layer logic validation, and console UI menu implementation.

---

## 2. Prompts and Interaction Log

### Entry 1: Architecture and Multi-Layer Modeling
* **Prompt Intent:** Define the multi-layer structure (Model, Persistence, Service, UI) for the Sales module and verify integration requirements with existing modules.
* **AI Output:** Structural guidance separating `Sale` and `SaleDetail` entities from file storage and business validation logic.
* **Applied Code:** Created `com.gamezone.model.Sale` and `com.gamezone.model.SaleDetail`.

### Entry 2: Persistence Layer with JSON (Gson)
* **Prompt Intent:** Implement repository logic storing sales in JSON format using Google's Gson library.
* **AI Output:** Implementation of `SaleRepository.java` using `GsonBuilder`, `TypeToken`, and `FileReader`/`FileWriter` streams.
* **Applied Code:** Created `com.gamezone.persistence.SaleRepository`.

### Entry 3: Business Logic and Stock Integration
* **Prompt Intent:** Build `SaleService` enforcing business constraints (minimum one item, inventory validation via `ProductService`, and stock deduction).
* **AI Output:** Exception-driven validation logic connecting the Sales module to product and person dependencies.
* **Applied Code:** Created `com.gamezone.service.SaleService`.

### Entry 4: Interface Wiring and Console Execution
* **Prompt Intent:** Create the 10-option interactive menu in `ConsoleUI` and initialize dependency injection in `Main`.
* **AI Output:** Console menu loop reading terminal input via `Scanner` and wiring option triggers directly to the service layer.
* **Applied Code:** Updated `com.gamezone.ui.ConsoleUI` and `com.gamezone.Main`.

---

## 3. Human Code Verification and Adaptation

All AI-generated code was reviewed, validated, and modified manually to meet specific project constraints:

1. **Dependency Management:** Configured Maven dependencies (`pom.xml`) manually to include Google Gson `2.10.1`.
2. **Method Wiring:** Adjusted method invocations inside `ConsoleUI.java` to ensure parameters matched the actual signatures of `ProductService` and `PersonService`.
3. **Error Handling:** Enhanced exception catching (`try-catch` blocks for `NumberFormatException`) to prevent terminal crashes during menu interaction.