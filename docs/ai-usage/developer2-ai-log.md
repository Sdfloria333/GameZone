# AI Usage Log — Developer 2 (Person Module)

## Tool Used
Claude
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

## 4. Adjustment A6 — Monthly Balance Report

### Assistance received from the AI
- Theoretical explanation of how breaking down `generateMonthlyBalance` into smaller methods improves cohesion and facilitates the reuse of individual calculations.
- Conceptual guidance on structuring business logic and presenting data within the user interface layer.
- Guidance on Git version control: procedures for renaming a branch according to the required convention (`fix/monthly-balance-report`) and deleting the previous remote branch.

### Technical decisions made by the developer
- Extraction of the methods `calculateMonthlySales(int month, int year)` and `calculateMonthlyReturns(int month, int year)` within `ReturnService`.
- Retention of the original signature for `generateMonthlyBalance`, ensuring it returns the calculated difference between the two amounts.
- Definition of total sales criteria based on the final amount of each transaction (including discounts and extended warranties).
- Update to `ConsoleUI.viewMonthlyBalance` to clearly display total sales, total returns, and the net balance.

### Verification performed
- Generation of a monthly balance report for a period containing both sales and return transactions, confirming that the value of sales minus returns matches the net balance exactly.
- Successful project compilation with no errors.

## 5. Adjustment A7 — Warranty Cancellation for Console Returns

### Assistance received from the AI
- Conceptual explanation of business rules regarding returns: why a returned console should not retain an active warranty, and why only the cost of an extended warranty is eligible for a refund.
- Guidance on resolving merge conflicts within `Main.java`, including instructions on how to properly handle conflict markers before committing changes.

### Technical decisions made by the developer
- Implementation of `WarrantyService.cancelWarranties(productId, saleId)` to remove warranties associated with a product within a specific sale, returning the refundable cost (zero for basic warranties and the corresponding additional cost for extended warranties).
- Configuration of `ReturnService.registerReturn` to trigger the cancellation for each returned console and propagate the result to the `Return` entity.
- Incorporation of preliminary product and reason validations prior to warranty cancellation, ensuring that a failure during the return process does not improperly alter the status of the warranties.
- Update to the `Return` entity to include the warranty refund in the total amount to be returned, reflecting this on the receipt and ensuring its persistence via `ReturnRepository`.
- Injection of `WarrantyService` through the `ReturnService` constructor, with an adjustment to the instantiation order in `Main` to ensure the warranty module is initialized before the returns module.

## 6. Requirement 6 — Exceptions and Validation in Services and Repositories

### Assistance received from the AI
- Review of the exception classes and validators delivered by Developer 1 to align the service changes with their exact signatures.
- Draft of the service and repository refactoring. The AI prepared the changes as ready-made patch files (one per commit), and the developer applied them to the repository with `git am`, reviewed the result and pushed the branches.
- The commits therefore carry the AI co-author trailer added by the tool, so that the teacher can take it into account when they see it.
- Explanation of why a failed write should throw instead of returning `false`, and of the risk of treating an unreadable file as an empty list.

### Technical decisions made by the developer
- `ProductRepository`, `PersonRepository` and `SaleRepository` throw `PersistenceException` with the file path and the operation, preserving the original cause.
- Services call the validators first and pass them any data they need, so validators never access persistence.
- `findProductById` and the other query methods keep returning `null`; only operations that cannot continue throw.
- `registerSale` keeps its existing pipeline and now throws `InvalidDataException`, `BusinessRuleException` or `ResourceNotFoundException` instead of returning `false`.
- Differences with the previous behaviour (price 0, required email and phone, duplicate identifications) are documented in `docs/exception-analysis.md`.

## Requirement 7

### Phase 1
- No use of AI