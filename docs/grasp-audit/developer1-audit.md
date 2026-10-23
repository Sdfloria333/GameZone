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