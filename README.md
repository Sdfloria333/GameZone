# GameZone Unicesar

Information system for managing a video game, console, and accessory store: inventory, customers, sellers, sales, promotions, returns, and warranties.

Academic project for the **Programming III** course (Universidad Popular del Cesar), developed in Java with a layered architecture and persistence using JSON files.

## Tech Stack

- Java 21+ (Maven, `pom.xml`)
- [Gson](https://github.com/google/gson) for JSON persistence
- Console Interface (`ConsoleUI` / `ConsoleMenu`)

## How to Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.gamezone.Main"