1. Adjustment A1: Category Discount for Accessories
Component: CategoryDiscount, PromotionService, ConsoleMenu

Root Cause: Category promotions were hardcoded to validate only VIDEOGAME and CONSOLE product categories, strictly excluding ACCESSORY and its sub-types (CONTROLLER, CABLE, MEMORY).

Applied Solution:

Updated CategoryDiscount logic and category validation in PromotionService.registerCategoryDiscount to recognize ACCESSORY categories.

Added UI support in ConsoleMenu to allow users to create and manage category discounts applied to accessories.

2. Adjustment A2: Warranty Circular Dependency Fix
Component: WarrantyRepository, WarrantyService, SaleService

Root Cause: WarrantyRepository directly depended on SaleService to reconstruct full Sale objects during data deserialization, creating a circular dependency loop: SaleService -> WarrantyService -> WarrantyRepository -> SaleService.

Applied Solution:

Refactored WarrantyRepository to store scalar identifiers (saleId, productId) instead of complex domain objects.

Delegated reference resolution to WarrantyService, utilizing SaleRepository and ProductService after repository initialization.

3. Adjustment A3: Unified Sale Registration Flow
Component: SaleService.registerSale, Sale.java, ConsoleMenu

Root Cause: Independent implementations caused sequence mismatches when processing sales (e.g., applying discounts after warranty calculation, or deducting inventory before validating customer/seller presence).

Applied Solution:

Enforced a strict, standardized execution pipeline in SaleService.registerSale:

Input parameter and sale ID uniqueness validation.

Customer and seller existence validation via PersonService.

Unified stock availability check across both products and accessories.

Sale instance creation and subtotal computation.

Automatic selection and application of the best active promotion on the subtotal.

Automatic basic warranty assignment for consoles and optional extended warranty processing.

Final total calculation: Total = Subtotal - Discount + Extended Warranty Costs.

Deferred stock reduction in respective services only after successful validation.

Atomically persisting sales and generated warranties.

Updated Sale.generateReceipt() to display itemized subtotals, applied promotion names, warranty costs, and final totals.

4. Adjustment A4: Return Accessory Stock Restoration
Component: ReturnService, AccessoryService

Root Cause: ReturnService.registerReturn only invoked ProductService.restoreStock, leaving accessory stock unrestored when customers returned accessory items.

Applied Solution:

Injected AccessoryService into ReturnService.

Implemented stock restoration logic in AccessoryService.

Updated ReturnService to inspect item types and delegate stock restoration to the appropriate service (ProductService or AccessoryService).

5. Adjustment A5: Discounted Refund Calculation
Component: Return.calculateRefundAmount, ReturnService

Root Cause: Refund logic calculated return values using original unit list prices, resulting in over-refunding when items were purchased under an active promotional discount.

Applied Solution:

Updated refund calculation logic to apply proportional discount deductions based on the effective discount percentage applied to the original sale (Refund = Item Price * (1 - Discount / Subtotal)).

6. Adjustment A6: Monthly Balance Report
Component: ReturnService, ConsoleMenu

Root Cause: generateMonthlyBalance calculated and printed only the net balance value without explicitly breaking down total monthly revenue vs total monthly refunds.

Applied Solution:

Added helper calculation methods calculateMonthlySales and calculateMonthlyReturns in ReturnService.

Updated generateMonthlyBalance and ConsoleMenu to clearly display Total Sales, Total Returns/Refunds, and Net Balance for the requested month.

7. Adjustment A7: Warranty Cancellation on Returned Consoles
Component: WarrantyService, ReturnService

Root Cause: Returned consoles maintained active warranties in persistence, allowing invalid post-return warranty claims. Additionally, paid extended warranty costs were not refunded.

Applied Solution:

Added cancelWarranties(saleId, productId) in WarrantyService to revoke active basic and extended warranties upon item return.

Integrated warranty cancellation inside ReturnService.registerReturn and added refunded extended warranty costs to the total return amount.