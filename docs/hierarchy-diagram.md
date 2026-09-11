# Hierarchy Diagram

```mermaid
classDiagram
    class Person {
        <<abstract>>
    }
    class Customer {
        +Concrete
    }
    class Seller {
        +Concrete
    }

    class Product {
        <<abstract>>
    }
    class Videogame {
        +Concrete
    }
    class Console {
        +Concrete
    }

    Person <|-- Customer
    Person <|-- Seller

    Product <|-- Videogame
    Product <|-- Console