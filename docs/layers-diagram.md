# Layer Diagram

```mermaid
flowchart TD
    subgraph UI_Layer ["Capa de Interfaz de Usuario (com.gamezone.ui)"]
        ConsoleUI[ConsoleUI]
    end

    subgraph Service_Layer ["Capa de Servicios (com.gamezone.service)"]
        PersonService[PersonService]
        ProductService[ProductService]
        SaleService[SaleService]
        AccessoryService[AccessoryService]
        PromotionService[PromotionService]
        ReturnService[ReturnService]
        WarrantyService[WarrantyService]
    end

    subgraph Persistence_Layer ["Capa de Persistencia (com.gamezone.persistence)"]
        PersonRepository[PersonRepository]
        ProductRepository[ProductRepository]
        SaleRepository[SaleRepository]
        AccessoryRepository[AccessoryRepository]
        PromotionRepository[PromotionRepository]
        ReturnRepository[ReturnRepository]
        WarrantyRepository[WarrantyRepository]
    end

    subgraph Model_Layer ["Capa de Modelo (com.gamezone.model)"]
        Person[Person / Customer / Seller]
        Product[Product / Videogame / Console]
        Sale[Sale / SaleDetail]
        Accessory[Accessory / Controller / Cable / Memory]
        Promotion[Promotion / PercentageDiscount / CategoryDiscount / BulkPurchaseDiscount]
        Return[Return]
        Warranty[Warranty / BasicWarranty / ExtendedWarranty]
    end

    ConsoleUI --> PersonService
    ConsoleUI --> ProductService
    ConsoleUI --> SaleService
    ConsoleUI --> AccessoryService
    ConsoleUI --> PromotionService
    ConsoleUI --> ReturnService
    ConsoleUI -.->|pendiente| WarrantyService

    SaleService --> PersonService
    SaleService --> ProductService
    SaleService --> AccessoryService
    SaleService --> PromotionService
    SaleService -.->|pendiente| WarrantyService

    ReturnService --> SaleService
    ReturnService --> ProductService

    PersonService --> PersonRepository
    ProductService --> ProductRepository
    SaleService --> SaleRepository
    AccessoryService --> AccessoryRepository
    PromotionService --> PromotionRepository
    ReturnService --> ReturnRepository
    WarrantyService --> WarrantyRepository

    ReturnRepository --> SaleService
    ReturnRepository --> ProductService
    WarrantyRepository --> SaleService
    WarrantyRepository --> ProductService

    PersonService --> Person
    ProductService --> Product
    SaleService --> Sale
    AccessoryService --> Accessory
    PromotionService --> Promotion
    ReturnService --> Return
    WarrantyService --> Warranty

    PersonRepository --> Person
    ProductRepository --> Product
    SaleRepository --> Sale
    AccessoryRepository --> Accessory
    PromotionRepository --> Promotion
    ReturnRepository --> Return
    WarrantyRepository --> Warranty
```

Las dos líneas punteadas marcadas "pendiente" muestran las conexiones que **todavía no existen**: ni `ConsoleUI` ni `SaleService` invocan a `WarrantyService` todavía. El resto de la capa de garantías (`WarrantyService → WarrantyRepository → Warranty`) sí está completamente implementado y funcional.