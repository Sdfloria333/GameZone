# Integrated System Class Diagram - GameZone Unicesar

```mermaid
classDiagram
    %% UI Layer
    class ConsoleMenu {
        -SaleService saleService
        -ReturnService returnService
        -ProductService productService
        -AccessoryService accessoryService
        -PromotionService promotionService
        -WarrantyService warrantyService
        +start()
        -registerSale()
        -registerReturn()
        -generateMonthlyBalance()
    }

    %% Service Layer
    class SaleService {
        -SaleRepository repository
        -ProductService productService
        -AccessoryService accessoryService
        -PersonService personService
        -PromotionService promotionService
        -WarrantyService warrantyService
        +registerSale()
        +listSales()
        +findSaleById()
    }

    class ProductService {
        -ProductRepository repository
        +hasEnoughStock()
        +reduceStock()
        +restoreStock()
        +findProductById()
    }

    class AccessoryService {
        -AccessoryRepository repository
        +hasEnoughStock()
        +updateStock()
        +restoreStock()
        +findById()
    }

    class PromotionService {
        -PromotionRepository repository
        +registerCategoryDiscount()
        +findBestPromotionFor()
    }

    class WarrantyService {
        -WarrantyRepository repository
        -SaleRepository saleRepository
        -ProductService productService
        +assignBasicWarranty()
        +assignExtendedWarranty()
        +cancelWarranties()
        +saveWarranties()
    }

    class ReturnService {
        -ReturnRepository repository
        -SaleService saleService
        -ProductService productService
        -AccessoryService accessoryService
        -WarrantyService warrantyService
        +registerReturn()
        +calculateMonthlySales()
        +calculateMonthlyReturns()
        +generateMonthlyBalance()
    }

    %% Persistence Layer
    class SaleRepository {
        +loadSales()
        +saveSales()
    }
    class ProductRepository {
        +loadProducts()
        +saveProducts()
    }
    class AccessoryRepository {
        +loadAccessories()
        +saveAccessories()
    }
    class PromotionRepository {
        +loadPromotions()
        +savePromotions()
    }
    class WarrantyRepository {
        +loadWarranties()
        +saveWarranties()
    }
    class ReturnRepository {
        +loadReturns()
        +saveReturns()
    }

    %% Model Layer
    class Sale {
        -String saleId
        -LocalDate date
        -String customerId
        -String sellerId
        -List~SaleDetail~ details
        -double discountAmount
        -double warrantyCost
        -double total
        +getSubtotal()
        +generateReceipt()
    }

    class SaleDetail {
        -String productId
        -String category
        -int quantity
        -double unitPrice
    }

    class Product {
        #String id
        #String name
        #double price
        #int stock
    }

    class Console {
        -int warrantyMonths
    }

    class Accessory {
        -String type
    }

    class Return {
        -String returnId
        -String saleId
        -LocalDate returnDate
        -List~SaleDetail~ returnedItems
        -double refundAmount
        +calculateRefundAmount()
    }

    %% Relationships
    Product <|-- Console
    Product <|-- Accessory

    ConsoleMenu --> SaleService
    ConsoleMenu --> ReturnService
    ConsoleMenu --> PromotionService

    SaleService --> ProductService
    SaleService --> AccessoryService
    SaleService --> PromotionService
    SaleService --> WarrantyService
    SaleService --> SaleRepository

    ReturnService --> SaleService
    ReturnService --> ProductService
    ReturnService --> AccessoryService
    ReturnService --> WarrantyService
    ReturnService --> ReturnRepository

    WarrantyService --> SaleRepository
    WarrantyService --> ProductService
    WarrantyService --> WarrantyRepository

    Sale "1" *-- "1..*" SaleDetail
    Return "1" *-- "1..*" SaleDetail