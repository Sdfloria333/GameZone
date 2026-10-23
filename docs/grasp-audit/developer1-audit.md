# GRASP Audit — Developer 1 (Requerimiento 7, Fase 1)

**Author:** Juan Manuel Otero Rueda (Developer 1)
**Branch:** `feature/grasp-audit-dev1`
**Scope:** Product, Accessory, Promotion, Return, Warranty, Exceptions/Validation

## 1. Audit Scope

Classes and methods reviewed, grouped by module. Classes marked *(evidence only)* belong to another team member and were read only to trace a cross-boundary finding — they were not audited on their own.

| Module | Requirement | Classes / methods reviewed |
|---|---|---|
| Product | Taller 2 (own module) | `Product`, `Console`, `Videogame`, `ProductRepository`, `ProductService` (all methods); `ConsoleUI.registerConsole()`/`registerVideogame()` *(evidence only)* |
| Accessory | Taller 2 | `Accessory`, `Controller`, `Cable`, `Memory`; `ConsoleUI.registerAccessory()` *(evidence only)*; `AccessoryService` seed data *(evidence only)* |
| Promotion | Taller 2 | `Promotion`, `PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`, `PromotionService`, `PromotionRepository` |
| Return | Requerimiento 3 (own implementation) | `Return`, `ReturnService` (all methods), `ReturnRepository`; `Sale.canBeReturned()` |
| Warranty | Requerimiento 4 (own implementation) | `Warranty`, `BasicWarranty`, `ExtendedWarranty`, `WarrantyService` (all methods), `WarrantyRepository` |
| Exceptions / Validation | Requerimiento 6 (own implementation) | `GameZoneException` and its 4 subclasses; `ProductValidator`, `PersonValidator`, `SaleValidator` |

**Explicitly out of scope** for this audit (another developer's responsibility; referenced only as evidence where a finding crosses the boundary): `ConsoleUI`/`ConsoleMenu`, `SaleService`, `Sale` (beyond `canBeReturned()`), `PersonService`, `AccessoryService`'s and `AccessoryRepository`'s own internal design.

## 2. Pattern-by-Pattern Verdicts

### Information Expert
**Correct across all audited modules.** Every class that computes something uses only its own data or data explicitly given to it: `Return.calculateRefundAmount()`, `Sale.canBeReturned()`, each `Warranty` subclass's `getAdditionalCost()`, each `Promotion` subclass's `calculateDiscount()`. The exceptions/validation classes are intentionally stateless utilities, so this pattern does not directly apply to them.

### Creator
**Mixed.** Correct in Promotion (`PromotionService` creates all three discount types) and in Warranty (`WarrantyService` creates both warranty types) and in Return/Exceptions (the service/validator that detects the need creates the object). **Violated** in Product and Accessory: `ConsoleUI` constructs `Console`/`Videogame`/`Controller`/`Cable`/`Memory` directly instead of delegating to the corresponding service (V-01, V-03).

### Controller
**Not applicable.** The actual entry-point controller is `ConsoleUI`/`ConsoleMenu`, which is outside this audit's responsibility.


### Low Coupling
**Correct.** Every class depends only on the collaborators it genuinely needs. `ReturnService` has the most collaborators (five), but each corresponds to one real step of the return workflow — no redundant dependency was found.

### High Cohesion
**Correct, with one violation.** `ReturnService` mixes return management with sales-reporting responsibilities that do not belong to it — `calculateMonthlySales()` does not even touch the class's own `returns` data (V-05). Every other service stays focused on a single responsibility.

### Polymorphism
**Violated in four places.** `ProductService.reduceStock()` branches on the product's concrete type instead of dispatching polymorphically (V-02). The same symptom repeats in `ReturnService.registerReturn()` (V-06), and, for a different reason (Gson cannot deserialize an abstract type without a discriminator), in `PromotionRepository` (V-04) and `WarrantyRepository`/`WarrantyService` (V-07). The exceptions/validation hierarchy has no such branching; catching each exception type individually is the normal language mechanism, not a violation.


### Pure Fabrication
**Correct, and the validation layer is the clearest example in the whole system.** All five repositories are textbook Pure Fabrication. `ProductValidator`/`PersonValidator`/`SaleValidator` were created specifically to hold a responsibility (validation) that does not belong to any single domain object.

### Indirection
**Correct where it appears.** `ReturnRepository.ReturnRecord` and `WarrantyRepository.WarrantyRecord` are lightweight DTOs that decouple the domain objects (which hold full `Sale`/`Product` references) from the JSON persistence format. The validators act as indirection between services and the model for validation logic.

### Protected Variations
**Violated wherever Polymorphism was violated (same root cause, not counted separately).** Adding a new concrete `Product`, `Accessory`, `Promotion`, or `Warranty` type currently requires editing existing conditional logic in `ProductService`, `ReturnService`, `PromotionRepository`, or `WarrantyRepository`/`WarrantyService` — exactly what this pattern is meant to prevent. The exceptions/validation hierarchy is correctly protected: a new error category or validation rule needs no change to existing classes.


## 3. Violation Table

| ID | Pattern(s) | Module | Evidence | Explanation | Consequence | Proposed solution | Another member's code? |
|---|---|---|---|---|---|---|---|
| V-01 | Creator | Product | `ConsoleUI.registerVideogame()` line 288, `registerConsole()` line 317 | The UI constructs `Videogame`/`Console` directly instead of delegating to `ProductService.registerVideogame()`/`registerConsole()`, which already exist and contain the validation logic. | Any future change to how a product is created (e.g. a new mandatory field) must be duplicated in the UI instead of living in one place. | `ConsoleUI` should collect the raw input and call the existing `ProductService` registration methods instead of calling `new` itself. | **Yes** — `ConsoleUI` (Líder Técnico). To be reported, not self-fixed. |
| V-02 | Polymorphism, Low Cohesion, Protected Variations | Product | `ProductService.reduceStock()` lines 102–108 | An `instanceof Console / Videogame / Accessory` chain decides which repository/service call to make, instead of the product hierarchy exposing a single polymorphic persistence hook. | Adding a new product type requires editing this method; it also currently calls `accessoryService.addAccessory()` for the `Accessory` branch instead of an update/save method — a known functional defect already flagged in the PR #42 review and worked around inside `SaleService`. | Replace the `instanceof` chain with a polymorphic save operation (e.g. each product type knows how to persist itself, or the method delegates to a small per-type strategy). | No — own module (Developer 1). **Out of correction scope for Fase 2**: fixing the polymorphism correctly would force a decision about the underlying Accessory bug, which would change observable behavior. To be documented and reported as a known defect instead of silently corrected. |
| V-03 | Creator | Accessory | `ConsoleUI.registerAccessory()` lines 553, 558, 563; `AccessoryService` seed data lines 21–23 *(correct Creator example, not part of the violation)* | The UI constructs `Controller`/`Cable`/`Memory` directly, bypassing `AccessoryService`, which already proves it knows how to be the Creator (it builds the same three types correctly for its own seed data). | Same consequence as V-01, applied to the accessory hierarchy. | `ConsoleUI.registerAccessory()` should delegate the construction to `AccessoryService`, the same way its own seed-data code already does internally. | **Yes** — `ConsoleUI` (Líder Técnico) and `AccessoryService` (Developer 2). To be reported, not self-fixed. |
| V-04 | Polymorphism, Protected Variations | Promotion (persistence) | `PromotionRepository.saveAll()` lines 38–44; `loadAll()` lines 79–98 | An `instanceof` chain writes a `"type"` discriminator, and a `switch`/fallback chain reads it back, to work around Gson's lack of native polymorphic deserialization. | A new `Promotion` subtype requires editing both blocks. | Register a dedicated `JsonSerializer`/`JsonDeserializer<Promotion>` via `GsonBuilder`, the same mechanism this file already uses for `LocalDate`, to encapsulate the type decision in one place. | **Not confirmed** — authorship of the Promotion module was not established during this audit; report to the team before acting on it in Fase 2. |
| V-05 | High Cohesion | Return | `ReturnService.calculateMonthlySales()`, `calculateMonthlyReturns()`, `generateMonthlyBalance()` | These three methods implement a sales/returns financial report, a responsibility distinct from managing returns. `calculateMonthlySales()` does not use the class's own `returns` field at all. | `ReturnService` grows for two unrelated reasons, making it harder to maintain either responsibility independently. | Extract the three methods into a dedicated `ReportingService` (or similar), which `ReturnService` and `SaleService` can both be queried by. | No — own code (Requerimiento 3). |
| V-06 | Polymorphism, Protected Variations | Return | `ReturnService.registerReturn()` line 98 (`instanceof Console`), lines 106–112 (`accessoryService.findById(...) != null` branch) | Two decisions are made by asking "what kind of product is this" instead of letting the product/service hierarchy answer polymorphically. | Adding a new product type that needs warranty cancellation or a different stock-restoration path requires editing this method again. | Expose a single `restoreStock(productId, quantity)` entry point that internally resolves which service owns the id, and let warranty cancellation be driven by a product capability rather than `instanceof Console`. | No — own code (Requerimiento 3). |
| V-07 | Polymorphism, Protected Variations | Warranty (persistence) | `WarrantyService.resolveRecords()` lines 69–73; `WarrantyRepository.saveAll()` line 83 | Same root cause as V-04 (Gson's lack of polymorphic deserialization), applied to the Warranty hierarchy: a string/`instanceof` check decides which concrete type to build or label. | A third warranty type requires editing both locations. | Same proposed solution as V-04: a dedicated `JsonSerializer`/`JsonDeserializer<Warranty>`. | No — own code (Requerimiento 4). |