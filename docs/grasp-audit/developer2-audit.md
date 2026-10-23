# GRASP Audit — Developer 2

**Requirement 7 – GRASP Audit and Correction · GameZone Unicesar**

| Item | Value |
|---|---|
| Role | Developer 2 |
| Audited branch | `develop` |
| Audited commit | `397b8b4` — *refactor: throw PersistenceException from SaleRepository on I/O errors* |
| Patterns evaluated | The nine GRASP patterns |

Verdicts used in this report:

- **violation** — the code breaks the pattern in one or more points (each point is recorded as `V-XX`).
- **correct** — the pattern is well applied; the class and method where it is applied are given.
- **not applicable** — the audited code has no point where the pattern applies; the reason is given.

When a fragment breaks more than one pattern it is recorded **once**, under the pattern that guides its correction, and the other patterns are listed as *also related*.

Line numbers refer to the files exactly as they are in the audited commit.

---

## 1. Scope

The scope follows the responsibility table in section 4 of the requirement for Developer 2.

| Module (requirement) | Class | Methods audited |
|---|---|---|
| Persons (Taller 1) | `model.persons.Person` | constructor, getters/setters, `getRoleDescription` (abstract) |
| Persons (Taller 1) | `model.persons.Customer`, `Seller` | constructors, getters/setters, `getRoleDescription` |
| Persons (Taller 1) | `persistence.PersonRepository` | `saveCustomers`, `loadCustomers`, `saveSellers`, `loadSellers`, `saveToFile`, `loadFromFile` |
| Persons (Taller 1) | `service.PersonService` | constructor, `preloadSellers`, `registerCustomer`, `listCustomers`, `listSellers`, `findCustomerByIdentification`, `findSellerByIdentification` |
| Accessories (Requirement 1) | `persistence.AccessoryRepository` | `saveAll`, `loadAll`, `saveToFile`, `loadFromFile` |
| Accessories (Requirement 1) | `service.AccessoryService` | both constructors, `registerController`, `registerCable`, `registerMemory`, `addAccessory`, `listAccessories`, `listAccessoriesByType`, `findById`, `findByConsole`, `hasEnoughStock`, `updateStock`, `restoreStock` |
| Promotions (Requirement 2) | `persistence.PromotionRepository` | `saveAll`, `loadAll`, `createDefaultPromotions` |
| Promotions (Requirement 2) | `service.PromotionService` | constructor, `registerPercentageDiscount`, `registerCategoryDiscount`, `registerBulkPurchaseDiscount`, `listAllPromotions`, `listActivePromotions`, `findById`, `findBestPromotionFor` |
| Returns (Requirement 3) | `persistence.ReturnRepository` | constructor, `ReturnRecord`, `saveAll`, `loadAll` |
| Returns (Requirement 3) | `service.ReturnService` | constructor, `registerReturn`, `saleContainsProduct`, `viewAllReturns`, `viewReturnsByCustomer`, `viewReturnsBySale`, monthly balance: `calculateMonthlySales`, `calculateMonthlyReturns`, `generateMonthlyBalance` |
| Warranties (Requirement 4) | `persistence.WarrantyRepository` | `WarrantyRecord`, `saveAll`, `loadAll` |
| Warranties (Requirement 4) | `service.WarrantyService` | constructor, `resolveRecords`, `assignBasicWarranty`, `assignExtendedWarranty`, `findWarrantyByProduct`, `cancelWarranties`, `listAllWarranties`, `listActiveWarranties`, `listWarrantiesExpiringSoon` |
| Exceptions and validators (Requirement 6) | the services and repositories above | how they throw, wrap or swallow errors and how they call `PersonValidator`, `ProductValidator` and `SaleValidator` |

**Excluded on purpose** (audited by their owners): the model hierarchies of accessories, promotions, returns and warranties and the `exceptions`/`validation` packages themselves (Developer 1); `SaleService`, `SaleRepository`, `ConsoleUI` and `Main` (Technical Leader).

**Note for the consolidation:** the commit history shows that part of `AccessoryRepository`, `AccessoryService`, `PromotionRepository` and `PromotionService` was committed by the Technical Leader, and part of `ReturnRepository`, `ReturnService`, `WarrantyRepository` and `WarrantyService` by Developer 1. This report follows the responsibility table of the requirement; the final assignment is for the Technical Leader to record (section 4, last rule).

When the evidence of a violation in my code is visible in a class owned by another member, the report says so in the field *Other member's code*, and the item is listed again in the last section so the Technical Leader can unify it.


---

## 2. Verdict summary

| # | Pattern | Verdict | Violations |
|---|---|---|---|
| 1 | Information Expert | **violation** | V-06, V-07 |
| 2 | Creator | **correct** | — |
| 3 | Controller | **not applicable** | — |
| 4 | Low Coupling | **violation** | V-01, V-05 |
| 5 | High Cohesion | **violation** | V-04; also related: V-07 |
| 6 | Polymorphism | **violation** | V-02, V-03, V-08; also related: V-06 |
| 7 | Pure Fabrication | **violation** | V-09 |
| 8 | Indirection | **correct** | — |
| 9 | Protected Variations | **correct** | — |


---

## 3. Information Expert

**Verdict: violation**

### Persons (Taller 1)

Correct: `PersonService.findCustomerByIdentification` and `findSellerByIdentification` (lines 54-66) search the lists the service itself holds, and `registerCustomer` (lines 35-44) gathers the existing people it owns (lines 38-39) and hands them to `PersonValidator.validateUniquePerson`, which never touches persistence. `Customer.getRoleDescription` (lines 47-50) and `Seller.getRoleDescription` (lines 69-72) use their own attributes.

### Accessories (Requirement 1)

Correct: `AccessoryService` holds the accessory list and its repository, so it is the expert for lookups (`findById`, lines 68-75; `findByConsole`, lines 77-81) and for accessory stock (`updateStock`, lines 89-96; `restoreStock`, lines 108-115), which other classes delegate to it.

### Promotions (Requirement 2)

Correct: `PromotionService.findBestPromotionFor` (lines 94-114) does not compute any discount itself; it asks each promotion, the expert (`promo.calculateDiscount(sale)`, line 104), and only compares the amounts. `listActivePromotions` (lines 72-81) asks each promotion `isActive`.

### Returns (Requirement 3)

Correct in the queries over the service's own list: `viewReturnsByCustomer` (lines 144-152), `viewReturnsBySale` (lines 160-168) and `calculateMonthlyReturns` (lines 197-205); and the refund is computed by `Return` itself. **Violations: `ReturnService` decides which products have warranties → V-06; and it totals the sales, which belong to `SaleService` → V-07.**

### Warranties (Requirement 4)

Correct: `WarrantyService` holds the warranties, so it answers `findWarrantyByProduct` (lines 119-127), `cancelWarranties` (lines 139-158) and `listWarrantiesExpiringSoon` (lines 193-205); validity is delegated to each warranty (`w.isActive(today)`, line 179).

### Exceptions and validators in services and repositories (Requirement 6)

Correct: `PersonService.registerCustomer` gives `PersonValidator` the data it needs (lines 36 and 40), and `PersonRepository` wraps file errors with the path it knows (lines 54 and 67).

#### V-06

| Field | Content |
|---|---|
| **ID** | V-06 |
| **Pattern** | Information Expert (also related: Polymorphism) |
| **Module** | Returns (Requirement 3), integration with Warranties (Requirement 4) |
| **Evidence** | `ReturnService.registerReturn`, lines 96-101: `for (Product product : returnedProducts) { if (product instanceof Console) { warrantyRefund += warrantyService.cancelWarranties(product.getId(), saleId); } }`. |
| **Explanation** | `ReturnService` decides, by the type of the product, which products have warranties. The class that knows which warranties exist is `WarrantyService` (it holds them): `cancelWarranties` (lines 139-158) already searches by product and sale, returns `0.0` and saves nothing when there is none. The rule "only consoles get warranties" is duplicated from `SaleService.registerSale` (line 98). |
| **Consequence** | If warranties are ever offered for other products, returns would not cancel them and the refund would be wrong, and nobody would notice because the rule lives in two places. |
| **Proposed solution** | Apply **Information Expert**: call `warrantyService.cancelWarranties(product.getId(), saleId)` for every returned product and let the expert answer; remove the `Console` check and import. Today only consoles have warranties, so refunds, receipts and `warranties.json` stay identical. |
| **Other member's code** | No. |

#### V-07

| Field | Content |
|---|---|
| **ID** | V-07 |
| **Pattern** | Information Expert (also related: High Cohesion) |
| **Module** | Returns (Requirement 3) — monthly balance |
| **Evidence** | `ReturnService.calculateMonthlySales`, lines 178-187, walks `saleService.listSales()` and adds `sale.getTotal()` for the given month. `ConsoleUI` asks the returns service for the sales total (line 684). |
| **Explanation** | The information needed to total the sales of a month (the list of sales) belongs to `SaleService`, which is the expert. `ReturnService` should only total its own returns (`calculateMonthlyReturns`, lines 197-205, is correct) and combine both figures. |
| **Consequence** | Any change in what counts as a month's sales (for example excluding cancelled sales) would have to be made in the returns module, and `ReturnService` grows with responsibilities that are not about returns. |
| **Proposed solution** | Apply **Information Expert**: add `calculateMonthlySales(int month, int year)` to `SaleService` with the same loop; `ReturnService.calculateMonthlySales` delegates to it (keeping its signature, so `ConsoleUI` does not change). The figures are summed in the same order, so the output is identical. |
| **Other member's code** | Yes. Technical Leader: new method in `SaleService`. |


---

## 4. Creator

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonService` aggregates the sellers and customers, so it creates them — `preloadSellers` (lines 29-31) and `registerCustomer` (line 41, with the initializing data received). That it also creates its own repository is a coupling problem, recorded as V-01.

### Accessories (Requirement 1)

Correct: `AccessoryService` aggregates accessories and creates the preloaded ones (lines 21-23); `AccessoryRepository.loadFromFile` (lines 51-61) creates the concrete accessories because it holds the initializing data read from the file.

### Promotions (Requirement 2)

Correct in `PromotionService`: it records the promotions and creates them in `registerPercentageDiscount` (line 32), `registerCategoryDiscount` (line 56) and `registerBulkPurchaseDiscount` (line 64). The default promotions created inside the repository are recorded under High Cohesion (V-04).

### Returns (Requirement 3)

Correct: `ReturnService` records the returns and creates them (line 104); `ReturnRepository` creates each `ReturnRecord` (lines 76-90) and rebuilds each `Return` from the stored data it holds (lines 145-147).

### Warranties (Requirement 4)

Correct: `WarrantyService` records the warranties and creates them in `assignBasicWarranty` (line 89), `assignExtendedWarranty` (line 105) and `resolveRecords` (lines 69-73); `WarrantyRepository.saveAll` creates the `WarrantyRecord`s it writes (lines 81-86).

### Exceptions and validators in services and repositories (Requirement 6)

Correct: `PersonRepository` creates the `PersistenceException` it throws, because it holds the path and the original cause (lines 54 and 67); `PersonService` delegates the creation of validation exceptions to `PersonValidator`.


---

## 5. Controller

**Verdict: not applicable**

### Persons (Taller 1)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Accessories (Requirement 1)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Promotions (Requirement 2)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Returns (Requirement 3)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Warranties (Requirement 4)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Exceptions and validators in services and repositories (Requirement 6)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.


---

## 6. Low Coupling

**Verdict: violation**

### Persons (Taller 1)

The person model depends only on its own hierarchy, and `PersonRepository` only on the model, Gson and `PersistenceException`. **Violation in the `PersonService` constructor → V-01.**

### Accessories (Requirement 1)

Correct: `AccessoryService` receives its repository by constructor (lines 15-17), and `AccessoryRepository` depends only on the accessory model and Gson.

### Promotions (Requirement 2)

Correct: `PromotionService` receives its repository by constructor (lines 23-26) and works with the abstract `Promotion`; it never depends on a concrete promotion in its selection logic.

### Returns (Requirement 3)

`ReturnService` depends on the services it really needs (sales, products, accessories, warranties) through its constructor (lines 36-45). **Violation: `ReturnRepository` depends on three services → V-05.**

### Warranties (Requirement 4)

Correct: `WarrantyRepository` has no dependency on services (its records keep identifiers only, lines 31-70). `WarrantyService` depends on `SaleRepository` instead of `SaleService` (lines 28 and 58) on purpose: `SaleService` already depends on `WarrantyService`, and this choice removed the circular dependency corrected in Requirement 4. It is an accepted, documented trade-off.

### Exceptions and validators in services and repositories (Requirement 6)

Correct: services call the validators as static utilities, and validators never depend on repositories or services.

#### V-01

| Field | Content |
|---|---|
| **ID** | V-01 |
| **Pattern** | Low Coupling (also related: Protected Variations) |
| **Module** | Persons (Taller 1) |
| **Evidence** | `PersonService` constructor, lines 18-26: `this.repository = new PersonRepository();` (line 19). The repository is created inside the service. In contrast, `AccessoryService(AccessoryRepository repository)` (lines 15-17), `PromotionService(PromotionRepository repository)` (lines 23-26) and `WarrantyService(WarrantyRepository, ...)` (lines 40-46) receive theirs, and `Main` already wires dependencies by constructor. |
| **Explanation** | `PersonService` is tied to one concrete repository and to the way it is built; the dependency is hidden from whoever assembles the system and cannot be replaced or shared. |
| **Consequence** | Any change in how people are stored (another location or format, or a test double for the no-regression checks) requires editing the service itself. |
| **Proposed solution** | Apply **Low Coupling** through constructor injection: add `PersonService(PersonRepository repository)` and keep the current no-argument constructor delegating to it with `new PersonRepository()`, so `Main` keeps compiling and behaving the same (the Technical Leader may later switch `Main` to the injecting constructor). |
| **Other member's code** | No (optional, non-blocking change in `Main` by the Technical Leader). |

#### V-05

| Field | Content |
|---|---|
| **ID** | V-05 |
| **Pattern** | Low Coupling (also related: Indirection) |
| **Module** | Returns (Requirement 3) |
| **Evidence** | `ReturnRepository` imports `SaleService`, `ProductService` and `AccessoryService` (lines 6-8), keeps them as fields (lines 32-34), receives them in the constructor (lines 44-49) and calls them in `loadAll`: `saleService.findSaleById` (line 129), `accessoryService.findById` (line 136), `productService.findProductById` (line 138). |
| **Explanation** | A persistence class depends on the service layer, against the dependency direction required by the architecture (`ui` → `service` → `persistence` → `model`). The same problem was already corrected for `WarrantyRepository` in Requirement 4 (commit `4ae27da`), but not here. |
| **Consequence** | The repository cannot be built without the whole service graph, `Main` must create it after `SaleService`, and any change in a service constructor or lookup method reaches the persistence layer. It also opens the door to circular dependencies like the one already suffered with warranties. |
| **Proposed solution** | Apply **Low Coupling** with the same design used for warranties: `ReturnRepository` gets a no-argument constructor, `ReturnRecord` becomes a public static class with getters, and `loadAll` returns the records (identifiers only). `ReturnService` resolves sales and products from the records, keeping the current order (accessory first, then product) and the same skipping rules. `returns.json` does not change. |
| **Other member's code** | Yes. Technical Leader: `Main`, line 36, must build `new ReturnRepository()` instead of passing the three services. |


---

## 7. High Cohesion

**Verdict: violation**

### Persons (Taller 1)

Correct: `PersonRepository` only reads and writes the two person files; `PersonService` keeps the person rules and delegates data validation to `PersonValidator`.

### Accessories (Requirement 1)

Correct: `AccessoryService` is limited to registration, queries and stock of accessories; `AccessoryRepository` only to the accessories file.

### Promotions (Requirement 2)

`PromotionService` is focused on registering, listing and selecting promotions. **Violation: `PromotionRepository` also defines and creates the store's default promotions → V-04.**

### Returns (Requirement 3)

Registering and querying returns is cohesive, but `ReturnService` also totals the month's sales; it is recorded once under Information Expert as **V-07**.

### Warranties (Requirement 4)

Correct: `WarrantyService` only assigns, resolves, cancels and queries warranties; `WarrantyRepository` only handles the warranties file.

### Exceptions and validators in services and repositories (Requirement 6)

Correct: validation lives in the validators and not inline in `PersonService` (lines 36 and 40).

#### V-04

| Field | Content |
|---|---|
| **ID** | V-04 |
| **Pattern** | High Cohesion (also related: Creator) |
| **Module** | Promotions (Requirement 2) |
| **Evidence** | `PromotionRepository.loadAll`, lines 59-65: when the file does not exist or is empty it calls `createDefaultPromotions()` and `saveAll(...)`. `createDefaultPromotions`, lines 108-131, defines the four business promotions (ids, names, percentages, dates, target categories). The other modules seed in the service: `PersonService.preloadSellers` (lines 28-33) and the `AccessoryService` constructor (lines 20-25). |
| **Explanation** | A repository's responsibility is to read and write data. `PromotionRepository` also decides the store's initial commercial offer, and its read operation writes to disk as a side effect, so the class mixes persistence with business rules. |
| **Consequence** | Changing the preloaded promotions (a business decision) means editing the persistence layer, and anyone calling `loadAll` may unexpectedly create the file. |
| **Proposed solution** | Apply **High Cohesion**: move `createDefaultPromotions` to `PromotionService`. The repository exposes a query such as `hasStoredData()` (file exists and is not empty — the same condition as today) and `loadAll` only reads. The `PromotionService` constructor creates and saves the defaults when `hasStoredData()` is false. Same file content and same dates (still computed from `LocalDate.now()`). |
| **Other member's code** | No. |


---

## 8. Polymorphism

**Verdict: violation**

### Persons (Taller 1)

Correct: `getRoleDescription()` is abstract in `Person` (line 86) and redefined by `Customer` and `Seller`; no class of the system inspects the type of a person. (The operation is currently not called by `ConsoleUI`, which prints people with getters.)

### Accessories (Requirement 1)

`AccessoryService` works with the abstract `Accessory` in every method except one. **Violation in `listAccessoriesByType` → V-02.**

### Promotions (Requirement 2)

Correct in `PromotionService.findBestPromotionFor`, which calls `calculateDiscount` on the abstract type (line 104). The `switch` on the JSON `type` field in `PromotionRepository.loadAll` (lines 79-98) is accepted: it builds objects from external data, where no object exists yet to receive a polymorphic call. **Violation in `PromotionRepository.saveAll` → V-03.**

### Returns (Requirement 3)

`ReturnService.registerReturn` checks `instanceof Console` to decide whether to cancel warranties (lines 96-101); recorded once under Information Expert as **V-06**.

### Warranties (Requirement 4)

Correct in `WarrantyService`, which uses `getAdditionalCost()` (line 148) and `isActive()` (line 179) on the abstract `Warranty`; `resolveRecords` (lines 69-73) chooses the class from the stored type, which is accepted for the same reason as `PromotionRepository.loadAll`. **Violation in `WarrantyRepository.saveAll` → V-08.**

### Exceptions and validators in services and repositories (Requirement 6)

Not applicable: there is no variant behaviour by type in the way my services and repositories throw or handle errors.

#### V-02

| Field | Content |
|---|---|
| **ID** | V-02 |
| **Pattern** | Polymorphism (also related: Protected Variations) |
| **Module** | Accessories (Requirement 1) |
| **Evidence** | `AccessoryService.listAccessoriesByType`, lines 62-66: `.filter(a -> a.getClass().getSimpleName().equalsIgnoreCase(type))` (line 64). |
| **Explanation** | The accessory type is resolved by asking for the Java class name instead of asking the object polymorphically. The business category becomes coupled to an implementation detail (the class name). |
| **Consequence** | Renaming or adding an accessory class silently changes the results of the filter. The method is not called by any class today, so it is a latent problem, but it is part of the public API of the service. |
| **Proposed solution** | Apply **Polymorphism**: once the product hierarchy exposes a category operation (proposed by Developer 1, `Product.getCategory()`), filter with `a.getCategory().equalsIgnoreCase(type)`. The results for `controller`, `cable` and `memory` are the same. |
| **Other member's code** | Yes. Developer 1 must first add `getCategory()` to the product/accessory hierarchy; then I change this method. |

#### V-03

| Field | Content |
|---|---|
| **ID** | V-03 |
| **Pattern** | Polymorphism (also related: Protected Variations) |
| **Module** | Promotions (Requirement 2) |
| **Evidence** | `PromotionRepository.saveAll`, lines 38-44: `if (promo instanceof PercentageDiscount) jsonObject.addProperty("type", "PERCENTAGE"); else if (promo instanceof CategoryDiscount) ... "CATEGORY"; else if (promo instanceof BulkPurchaseDiscount) ... "BULK";`. |
| **Explanation** | The type code varies with the subclass, so it should be answered by the hierarchy. The repository discovers it from outside with a chain of `instanceof`. |
| **Consequence** | A fourth promotion type forces editing the repository; if the branch is forgotten, the promotion is saved without `type` and on loading it falls into the field-guessing `default` block (lines 89-97), which can rebuild it as the wrong class. |
| **Proposed solution** | Apply **Polymorphism**: once `Promotion` declares `getTypeCode()` (Developer 1), write `jsonObject.addProperty("type", promo.getTypeCode())`. The JSON written is identical, so `promotions.json` does not change. |
| **Other member's code** | Yes. Developer 1 adds `getTypeCode()` to `Promotion` and its subclasses first; then I change `saveAll`. |

#### V-08

| Field | Content |
|---|---|
| **ID** | V-08 |
| **Pattern** | Polymorphism (also related: Protected Variations) |
| **Module** | Warranties (Requirement 4) |
| **Evidence** | `WarrantyRepository.saveAll`, line 83: `(w instanceof BasicWarranty) ? "BASIC" : "EXTENDED"`. |
| **Explanation** | The type code varies with the subclass but is decided from outside the hierarchy, and the ternary assumes that every warranty that is not basic is extended. |
| **Consequence** | A third warranty type would be silently saved as `"EXTENDED"` and reloaded by `WarrantyService.resolveRecords` (lines 69-73) as an `ExtendedWarranty`, with the wrong duration and cost. |
| **Proposed solution** | Apply **Polymorphism**: once `Warranty` declares `getTypeCode()` (Developer 1), write `w.getTypeCode()`. `warranties.json` keeps the same content. |
| **Other member's code** | Yes. Developer 1 adds `getTypeCode()` to the warranty hierarchy first; then I change `saveAll`. |


---

## 9. Pure Fabrication

**Verdict: violation**

### Persons (Taller 1)

Correct: `PersonRepository` is a fabricated class that takes JSON persistence away from `Person` (models never touch files).

### Accessories (Requirement 1)

Correct: `AccessoryRepository` fabricates the persistence of accessories.

### Promotions (Requirement 2)

Correct: `PromotionRepository` fabricates the persistence of promotions (its extra responsibility is recorded as V-04).

### Returns (Requirement 3)

Correct: `ReturnRepository.ReturnRecord` (lines 55-63) is a fabricated, id-only representation that keeps object references out of the JSON file.

### Warranties (Requirement 4)

Correct: `WarrantyRepository.WarrantyRecord` (lines 31-70) is a fabricated, id-only record, which is what allowed `WarrantyRepository` to drop its service dependencies.

### Exceptions and validators in services and repositories (Requirement 6)

`PersonService` uses the fabricated `PersonValidator` correctly. **Violation: the file-access code is copied in every repository instead of living in one fabricated class → V-09.**

#### V-09

| Field | Content |
|---|---|
| **ID** | V-09 |
| **Pattern** | Pure Fabrication (also related: Protected Variations) |
| **Module** | Exceptions and validators in services and repositories (Requirement 6) — all my repositories |
| **Evidence** | Each of my five repositories repeats the same file-access code: a hard-coded `"src/main/data/..."` path, its own `Gson` instance, `new File(path)`, `getParentFile().mkdirs()`, `FileWriter`/`FileReader` and the error handling. `PersonRepository` lines 21-23 and 45-69; `AccessoryRepository` lines 18-19 and 29-66; `PromotionRepository` lines 16-25, 27-54 and 56-105; `ReturnRepository` lines 29-30, 93-103 and 114-151; `WarrantyRepository` lines 24-25, 89-98 and 106-118. |
| **Explanation** | Reading and writing JSON files is a technical responsibility that does not belong to any domain concept; it should live once in a fabricated class. Instead it is copied in every repository. |
| **Consequence** | Changing the data folder, the encoding or the Gson configuration means editing every repository. The copies have already drifted: after Requirement 6, `PersonRepository` throws `PersistenceException` while the other four still swallow the error (see section 12.3). |
| **Proposed solution** | Apply **Pure Fabrication**: create `persistence.JsonFileStorage` with the shared operations (write a list to a path, read a path with a `Type`, check whether the file has data), using the same Gson configuration. Each repository delegates to it and keeps exactly its current error behaviour (each one still decides whether to throw or to return `false`/an empty list), so files and messages stay the same. |
| **Other member's code** | No. (`ProductRepository` — Developer 1 — and `SaleRepository` — Technical Leader — have the same copy and could reuse the class, optionally.) |


---

## 10. Indirection

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonService` reaches the files only through `PersonRepository`, and `SaleService` reaches the people only through `PersonService`.

### Accessories (Requirement 1)

Correct: `ProductService`, `SaleService` and `ReturnService` change accessory stock only through `AccessoryService`.

### Promotions (Requirement 2)

Correct: `SaleService` does not know any concrete promotion; it obtains the best one through `PromotionService.findBestPromotionFor`.

### Returns (Requirement 3)

Correct: `ReturnService` cancels warranties through `WarrantyService` and restores stock through `ProductService`/`AccessoryService`. The missing indirection between `ReturnRepository` and the services is recorded as V-05.

### Warranties (Requirement 4)

Correct: `WarrantyRecord` acts as the intermediary that keeps `WarrantyRepository` independent from sales and products.

### Exceptions and validators in services and repositories (Requirement 6)

Correct: `PersonService` raises validation errors through `PersonValidator`, and `PersonRepository` hides `IOException`/`JsonParseException` behind `PersistenceException`.


---

## 11. Protected Variations

**Verdict: correct**

### Persons (Taller 1)

Correct: the abstract `Person` keeps clients independent of the concrete kinds of person, and unique-identification checking is encapsulated in `PersonValidator.validateUniquePerson`.

### Accessories (Requirement 1)

Correct: `AccessoryService` works only with the abstract `Accessory`, so a new kind of accessory does not change its stock logic. (How `AccessoryRepository` recognizes the subtype is discussed in section 12.3.)

### Promotions (Requirement 2)

Correct: the selection of the best promotion depends only on the abstract `Promotion`; a new kind of promotion does not change `findBestPromotionFor` nor `SaleService`.

### Returns (Requirement 3)

Correct: the 30-day rule is asked to `Sale.canBeReturned()` (line 64) and the refund to `Return`, so `ReturnService` is protected from changes in those rules.

### Warranties (Requirement 4)

Correct: duration and cost are asked to each warranty, so `WarrantyService` is protected from changes in the concrete warranty rules.

### Exceptions and validators in services and repositories (Requirement 6)

Correct in the persons module: `PersonRepository` and `PersonService` use the `GameZoneException` hierarchy, so `ConsoleUI` handles their errors by category. The modules that still do not use it are listed in section 12.3, because aligning them would change what the user sees.


---

## 12. Items reported to the Technical Leader

### 12.1 Boundary findings (my code + another member's code)

| My ID | Other member | Their part of the correction | Order |
|---|---|---|---|
| V-02 | Developer 1 | Add `getCategory()` to the product/accessory hierarchy. | Developer 1 first, then me. |
| V-03 | Developer 1 | Add `getTypeCode()` to `Promotion` and its subclasses. | Developer 1 first, then me. |
| V-05 | Technical Leader | `Main` line 36: `new ReturnRepository()`. | Same commit sequence: my change in `ReturnRepository`/`ReturnService` and the Leader's line in `Main` must be pushed together so `develop` compiles. |
| V-07 | Technical Leader | Add `SaleService.calculateMonthlySales(int, int)`. | Leader first, then me. |
| V-08 | Developer 1 | Add `getTypeCode()` to `Warranty` and its subclasses. | Developer 1 first, then me. |

### 12.2 Related code of other members

- `PromotionService.VALID_CATEGORIES` (my code, line 18) repeats the category names `VIDEOGAME`, `CONSOLE`, `ACCESSORY`. If Developer 1 adds `getCategory()` to the product hierarchy, this list should be reviewed in the same consolidated finding.
- `ProductRepository` (Developer 1) and `SaleRepository` (Technical Leader) contain the same file-access copy described in V-09.

### 12.3 Out-of-scope findings (functional defects / changes visible to the user)

| # | Finding | Evidence | Why it is not corrected now |
|---|---|---|---|
| 1 | **Duplicated warranty ids.** New ids are `"W" + (warranties.size() + 1)`, but `cancelWarranties` removes warranties from the list. After a return cancels one warranty, the next sale can receive an id that already exists. | `WarrantyService` lines 88, 104 and 139-158. | Fixing it changes the ids shown in the warranty listings and saved in `warranties.json`. |
| 2 | **Requirement 6 not applied in four modules.** The repositories swallow I/O errors and return `false` or an empty list, the services return `false` instead of typed exceptions, and returns use `IllegalArgumentException` outside the `GameZoneException` hierarchy. | `AccessoryRepository` 37-39, 63-65; `PromotionRepository` 51-53, 100-102; `ReturnRepository` 101-103, 149-151; `WarrantyRepository` 96-98, 115-117; `AccessoryService.addAccessory` 46-56; `PromotionService.register*` 28-66; `ReturnService.registerReturn` 58-93 (caught by `ConsoleUI` line 629). | Throwing the typed exceptions changes the messages printed by the menu (for example "Error al registrar accesorio." would become the generic business-rule message). |
| 3 | **Accessory id can repeat a console or videogame id.** `AccessoryService.addAccessory` checks duplicates only among accessories, and `ProductService.findProductById` then returns the console first. | `AccessoryService` line 51; `ProductService.findProductById` lines 71-88. | Rejecting those ids would change the result of registering an accessory. |
| 4 | **Accessory subtype recognized by the presence of a field.** Renaming `connectionType`, `connectorType` or `memoryType` would make accessories disappear on load. | `AccessoryRepository.loadFromFile` lines 54-60. | The robust fix (writing an explicit `type`) changes the content of `accessories.json`. |

### 12.4 Observations (not GRASP violations)

- Public methods with no caller in the system: `AccessoryService.registerController`, `registerCable`, `registerMemory` (lines 34-44), `hasEnoughStock` (lines 84-87) and `listAccessoriesByType` (lines 62-66); `Person.getRoleDescription` is implemented but `ConsoleUI` does not use it.
