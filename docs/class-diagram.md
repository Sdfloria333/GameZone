### File 3: `docs/class-diagram.md`

```markdown
# Class Diagram

```mermaid
classDiagram
    namespace model {
        class Person {
            <<abstract>>
            -String id
            -String name
            -String phone
            +getId() String
            +getName() String
            +getPhone() String
        }

        class Customer {
            -String email
            -List~String~ purchaseHistory
            +getEmail() String
            +getPurchaseHistory() List~String~
        }

        class Seller {
            -String employeeCode
            -String workShift
            +getEmployeeCode() String
            +getWorkShift() String
        }

        class Product {
            <<abstract>>
            -String id
            -String title
            -double price
            -int stockQuantity
            +getId() String
            +getTitle() String
            +getPrice() double
            +getStockQuantity() int
            +setStockQuantity(int quantity) void
            +getDescription()* String
        }

        class Videogame {
            -String platform
            -String genre
            -String ageRating
            +getDescription() String
        }

        class Console {
            -String brand
            -String model
            -String generation
            +getDescription() String
        }

        class Sale {
            -String id
            -LocalDate date
            -double total
            +calculateTotal() double
            +getId() String
            +getDate() LocalDate
            +getTotal() double
        }
    }

    namespace persistence {
        class PersonRepository {
            -String filePath
            +saveAll(List~Person~ persons) void
            +findAll() List~Person~
        }

        class ProductRepository {
            -String filePath
            +saveAll(List~Product~ products) void
            +findAll() List~Product~
        }

        class SaleRepository {
            -String filePath
            +saveAll(List~Sale~ sales) void
            +findAll() List~Sale~
        }
    }

    namespace service {
        class PersonService {
            +registerCustomer(Customer customer) void
            +listCustomers() List~Customer~
            +listSellers() List~Seller~
            +findPersonById(String id) Person
        }

        class ProductService {
            +registerProduct(Product product) void
            +listProducts() List~Product~
            +updateStock(String productId, int quantity) void
            +findProductById(String id) Product
        }

        class SaleService {
            +registerSale(Sale sale) void
            +listSales() List~Sale~
            +getSalesByCustomer(String customerId) List~Sale~
            +getSalesBySeller(String sellerId) List~Sale~
        }
    }

    namespace ui {
        class ConsoleUI {
            +start() void
            -showMainMenu() void
        }
    }

    Person <|-- Customer
    Person <|-- Seller
    Product <|-- Videogame
    Product <|-- Console

    Sale "1" --> "1" Customer : customer
    Sale "1" --> "1" Seller : seller
    Sale "*" --> "1..*" Product : products

    PersonRepository ..> Person
    ProductRepository ..> Product
    SaleRepository ..> Sale

    PersonService --> PersonRepository
    ProductService --> ProductRepository
    SaleService --> SaleRepository
    SaleService --> PersonService
    SaleService --> ProductService

    ConsoleUI --> PersonService
    ConsoleUI --> ProductService
    ConsoleUI --> SaleService