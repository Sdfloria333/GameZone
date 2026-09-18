# Return Module - Analysis

## 1. Relationship between Return and Sale

The relationship between `Return` and `Sale` is an **association**, not inheritance, aggregation, or composition. `Return` holds a reference to the original `Sale` through the `originalSale` attribute, but `Return` is not a type of `Sale` (no inheritance), and it does not own the sale's lifecycle: the sale existed before the return and continues to exist independently of it. A simple association captures this "knows about" relationship without implying ownership.

## 2. Representing partial returns

Since a return may include only some of the products from the original sale, the `Return` class stores this in its `returnedProducts` attribute, a `List<Product>`. This list contains only the specific products the customer chose to return — a subset of the full list of products that were part of the original `Sale`. The original sale itself is untouched; the subset relationship is captured entirely within the `Return` instance.

## 3. Location of the 30-day validation rule

The date calculation itself lives in `Sale`, through the `canBeReturned()` method, which uses `ChronoUnit.DAYS.between(...)` to compare the sale date with the current date. However, the decision to reject the operation based on that result is made in `ReturnService`, inside `registerReturn()`. This separation is intentional: `Sale` (model layer) exposes a pure fact about itself, while `ReturnService` (service layer) is responsible for business rules and for deciding what to do when a rule is violated, throwing an `IllegalArgumentException` when the return window has expired.

## 4. Reusing restoreStock instead of duplicating logic

Instead of reimplementing inventory update logic inside the returns module, `ReturnService.registerReturn()` reuses the existing `ProductService.restoreStock(productId, quantity)` method. This method is invoked from `ReturnService` after a return is validated and persisted. Reusing existing methods avoids duplicating the same stock-update logic in two places, which would risk inconsistent behavior if the rules ever changed and only one copy were updated.

## 5. Location of the monthly balance report

`generateMonthlyBalance(month, year)` is located in `ReturnService`, because it needs to consolidate information from two different sources: sales data (through `SaleService.listSales()`) and the return module's own data (`returns`). This placement is coherent with the layered architecture because service classes are responsible for orchestrating and combining data from multiple sources; neither the model layer nor the persistence layer is meant to hold this kind of cross-module business logic.