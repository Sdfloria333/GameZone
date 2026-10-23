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

_Partial summary: 1 of 6 modules audited so far._

| # | Pattern | Verdict | Violations |
|---|---|---|---|
| 1 | Information Expert | **correct** | — |
| 2 | Creator | **correct** | — |
| 3 | Controller | **not applicable** | — |
| 4 | Low Coupling | **violation** | V-01 |
| 5 | High Cohesion | **correct** | — |
| 6 | Polymorphism | **correct** | — |
| 7 | Pure Fabrication | **correct** | — |
| 8 | Indirection | **correct** | — |
| 9 | Protected Variations | **correct** | — |


---

## 3. Information Expert

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonService.findCustomerByIdentification` and `findSellerByIdentification` (lines 54-66) search the lists the service itself holds, and `registerCustomer` (lines 35-44) gathers the existing people it owns (lines 38-39) and hands them to `PersonValidator.validateUniquePerson`, which never touches persistence. `Customer.getRoleDescription` (lines 47-50) and `Seller.getRoleDescription` (lines 69-72) use their own attributes.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 4. Creator

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonService` aggregates the sellers and customers, so it creates them — `preloadSellers` (lines 29-31) and `registerCustomer` (line 41, with the initializing data received). That it also creates its own repository is a coupling problem, recorded as V-01.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 5. Controller

**Verdict: not applicable**

### Persons (Taller 1)

Not applicable: these services and repositories do not receive system events. The events of the console menu are received and coordinated by `ConsoleUI` (Technical Leader), which delegates to my services.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 6. Low Coupling

**Verdict: violation**

### Persons (Taller 1)

The person model depends only on its own hierarchy, and `PersonRepository` only on the model, Gson and `PersistenceException`. **Violation in the `PersonService` constructor → V-01.**

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._

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


---

## 7. High Cohesion

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonRepository` only reads and writes the two person files; `PersonService` keeps the person rules and delegates data validation to `PersonValidator`.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 8. Polymorphism

**Verdict: correct**

### Persons (Taller 1)

Correct: `getRoleDescription()` is abstract in `Person` (line 86) and redefined by `Customer` and `Seller`; no class of the system inspects the type of a person. (The operation is currently not called by `ConsoleUI`, which prints people with getters.)

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 9. Pure Fabrication

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonRepository` is a fabricated class that takes JSON persistence away from `Person` (models never touch files).

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 10. Indirection

**Verdict: correct**

### Persons (Taller 1)

Correct: `PersonService` reaches the files only through `PersonRepository`, and `SaleService` reaches the people only through `PersonService`.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._


---

## 11. Protected Variations

**Verdict: correct**

### Persons (Taller 1)

Correct: the abstract `Person` keeps clients independent of the concrete kinds of person, and unique-identification checking is encapsulated in `PersonValidator.validateUniquePerson`.

### Accessories (Requirement 1)

_Pending — this module has not been audited yet._

### Promotions (Requirement 2)

_Pending — this module has not been audited yet._

### Returns (Requirement 3)

_Pending — this module has not been audited yet._

### Warranties (Requirement 4)

_Pending — this module has not been audited yet._

### Exceptions and validators in services and repositories (Requirement 6)

_Pending — this module has not been audited yet._
