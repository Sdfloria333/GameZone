# AI Usage Log — Developer 2 (Person Module)

## Tool used

## 1. Person Module and Base Architecture

### Assistance received from the AI
- Conceptual explanations regarding class hierarchy design and the use of abstraction.
- Theoretical analysis of the distribution of common versus specific attributes in object-oriented programming.
- Guidance on the importance of polymorphism through abstract methods.
- Instruction on tool usage: Git commands, branching workflows (feature branch/develop), and JDK configuration in IntelliJ.
- Explanation of how Java persistence components (try-with-resources, BufferedReader, and FileWriter) function.

### Technical decisions made by the developer
- Defining `Person` as an abstract class with an abstract method, `getRoleDescription()`.
- Separating persistence into two distinct files (`customers.txt` and `sellers.txt`) to simplify parsing.
- Identifying and assigning common attributes (name, ID, phone number) and specific attributes (email for customers; employee code and shift for sellers).
- Placing data preloading and validation logic within `PersonService` to maintain architectural layer separation.

## 2. Adjustment A2 — Circular Dependency Between `SaleService` and `WarrantyService`

### Assistance received from the AI
- Conceptual diagnosis of design issues related to circular dependencies ("code smells").
- Explanation of why constructor-based dependency injection is preferred over setter-based injection.
- Guidance on Git best practices: commands for excluding test data from the staging area, exiting the pager, and merging with the `develop` branch.
- Advice on how to reflect dependency modifications in the class diagram.

### Technical decisions made by the developer
- Removing service dependencies from within `WarrantyRepository`. Configured `WarrantyService` to receive `WarrantyRepository`, `SaleRepository`, and `ProductService`.
- Redesigned `SaleService` to receive `WarrantyService` via the constructor, with the field marked as `final` (removed `setWarrantyService`).
- Defined the instantiation order in `Main`, ensuring `WarrantyService` is initialized before `SaleService`.
- Excluded test JSON files (`consoles`, `customers`, `sales`, `warranties`) from the Pull Request commits.

### Verification performed
- Successful project compilation in IntelliJ with no warnings or errors.
- Correct registration of sales involving consoles, automatic warranty generation, and persistence to `warranties.json`.
- Clean traceability via two specific commits dedicated to `SaleService`.

## 3. Adjustment A4 — Stock Restoration Following Accessory Returns

### Assistance received from the AI
- Analysis of the logical flow to identify why stock variations were not being saved.
- Theoretical explanation regarding the behavioral differences between `ProductService` and `AccessoryService`.
- Guidance on structuring centralized request routing within services.
- Support in resolving development environment issues (outdated JDK paths).

### Technical decisions made by the developer
- Implemented quantity incrementing and data persistence using `repository.saveAll` within `AccessoryService.restoreStock`.
- Centralized routing logic in `ReturnService` by injecting `AccessoryService` to process accessories directly.
- Modified `ReturnRepository` to query `AccessoryService` first when loading returns.
- Maintained delegation to `ProductService.restoreStock` as a safeguard for direct calls.
- Preserved the existing business rule (a fixed restoration of 1 unit per return).
