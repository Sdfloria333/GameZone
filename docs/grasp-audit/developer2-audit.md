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

_Partial summary: 0 of 6 modules audited so far._

| # | Pattern | Verdict | Violations |
|---|---|---|---|
| 1 | Information Expert | **pending** | — |
| 2 | Creator | **pending** | — |
| 3 | Controller | **pending** | — |
| 4 | Low Coupling | **pending** | — |
| 5 | High Cohesion | **pending** | — |
| 6 | Polymorphism | **pending** | — |
| 7 | Pure Fabrication | **pending** | — |
| 8 | Indirection | **pending** | — |
| 9 | Protected Variations | **pending** | — |


---

## 3. Information Expert

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

## 7. High Cohesion

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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

**Verdict: pending**

### Persons (Taller 1)

_Pending — this module has not been audited yet._

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
