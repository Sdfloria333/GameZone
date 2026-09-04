### File 4: `docs/layers-diagram.md`

```markdown
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
    end

    subgraph Persistence_Layer ["Capa de Persistencia (com.gamezone.persistence)"]
        PersonRepository[PersonRepository]
        ProductRepository[ProductRepository]
        SaleRepository[SaleRepository]
    end

    subgraph Model_Layer ["Capa de Modelo (com.gamezone.model)"]
        Person[Person / Customer / Seller]
        Product[Product / Videogame / Console]
        Sale[Sale]
    end

    ConsoleUI --> PersonService
    ConsoleUI --> ProductService
    ConsoleUI --> SaleService

    SaleService --> PersonService
    SaleService --> ProductService

    PersonService --> PersonRepository
    ProductService --> ProductRepository
    SaleService --> SaleRepository

    PersonService --> Person
    ProductService --> Product
    SaleService --> Sale

    PersonRepository --> Person
    ProductRepository --> Product
    SaleRepository --> Sale