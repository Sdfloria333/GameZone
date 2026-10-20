# Warranty Module - Class Diagram

```mermaid
classDiagram
    namespace model_warranties {
        class Warranty {
            <<abstract>>
            -String warrantyId
            -Product product
            -Sale sale
            -LocalDate startDate
            -LocalDate endDate
            +Warranty(String, Product, Sale, LocalDate)
            +getWarrantyId() String
            +getProduct() Product
            +getSale() Sale
            +getStartDate() LocalDate
            +getEndDate() LocalDate
            +getDurationInMonths()* int
            +getWarrantyType()* String
            +getAdditionalCost()* double
            +isActive(LocalDate) boolean
            +generateWarrantyCertificate() String
        }
        class BasicWarranty {
            +BasicWarranty(String, Product, Sale, LocalDate)
            +getDurationInMonths() int
            +getWarrantyType() String
            +getAdditionalCost() double
        }
        class ExtendedWarranty {
            +ExtendedWarranty(String, Product, Sale, LocalDate)
            +getDurationInMonths() int
            +getWarrantyType() String
            +getAdditionalCost() double
        }
    }

    namespace model_products {
        class Product {
            <<abstract>>
            -String id
            -String title
            -double price
            -int stockQuantity
        }
    }

    namespace model_sales {
        class Sale {
            -String id
            -LocalDate date
            -String customerId
            -String sellerId
            -List~SaleDetail~ details
            -double total
            +getSaleId() String
            +getDate() LocalDate
        }
    }

    namespace persistence {
        class WarrantyRepository {
            -String WARRANTIES_FILE
            -Gson gson
            +WarrantyRepository()
            +saveAll(List~Warranty~) boolean
            +loadAll() List~Warranty~
        }
        class SaleRepository {
            +loadSales() List~Sale~
        }
    }

    namespace service {
         class WarrantyService {
            -WarrantyRepository repository
            -SaleRepository saleRepository
            -ProductService productService
            -List~Warranty~ warranties
            +WarrantyService(WarrantyRepository, SaleRepository, ProductService)
            +assignBasicWarranty(Product, Sale, LocalDate) BasicWarranty
            +assignExtendedWarranty(Product, Sale, LocalDate) ExtendedWarranty
            +findWarrantyByProduct(String, String) Warranty
            +listAllWarranties() List~Warranty~
            +listActiveWarranties() List~Warranty~
            +listWarrantiesExpiringSoon(int) List~Warranty~
        }
        
        class SaleService {
            -WarrantyService warrantyService
            +SaleService(ProductService, AccessoryService, PersonService, PromotionService, WarrantyService)
            +findSaleById(String) Sale
        }
        class ProductService {
            +findProductById(String) Product
        }
    }

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty
    Warranty "*" --> "1" Product : product
    Warranty "*" --> "1" Sale : sale
    WarrantyRepository ..> Warranty
    WarrantyService --> SaleRepository
    WarrantyService --> ProductService
    SaleService --> WarrantyService
    WarrantyService --> WarrantyRepository
```

Este diagrama refleja **exactamente lo que existe hoy**: el modelo, la persistencia y el servicio de garantías están completos, pero no hay ninguna flecha hacia `ConsoleUI` ni una versión modificada de `SaleService.registerSale` — porque esa integración todavía no se ha hecho.