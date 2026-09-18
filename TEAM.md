# TEAM.md

## Integrantes del Equipo

| Nombre Completo                | Código   | Rol             | Módulo Asignado                | Rama Git                 |
|:-------------------------------|:---------|:----------------|:-------------------------------|:-------------------------|
| Sebastián David Florián Cadena | [Código] | Líder Técnico   | Módulo de Ventas e Integración | `feature/sale-module`    |
| Juan Manuel Otero Rueda        | [Código] | Desarrollador 1 | Módulo de Productos            | `feature/product-module` |
| Oscar Sebastián Araujo Erazo   | [Código] | Desarrollador 2 | Módulo de Personas             | `feature/person-module`  |

---

## Distribución de Clases por Módulo

### 1. Módulo de Ventas e Integración

* **Responsable:** Sebastián David Florián Cadena (Líder Técnico)
* **Clases:**
    * `Sale.java` (Dominio)
    * `SaleRepository.java` (Persistencia)
    * `SaleService.java` (Servicio y reglas de negocio)
    * `ConsoleUI.java` (Interfaz de usuario / Menú de consola)
    * `Main.java` (Clase principal del sistema)

### 2. Módulo de Productos

* **Responsable:** Juan Manuel Otero Rueda (Desarrollador 1)
* **Clases:**
    * `Product.java` (Clase abstracta base)
    * `Videogame.java` (Clase derivada)
    * `Console.java` (Clase derivada)
    * `ProductRepository.java` (Persistencia)
    * `ProductService.java` (Servicio)

### 3. Módulo de Personas

* **Responsable:** Oscar Sebastián Araujo Erazo (Desarrollador 2)
* **Clases:**
    * `Person.java` (Clase abstracta base)
    * `Customer.java` (Clase derivada)
    * `Seller.java` (Clase derivada)
    * `PersonRepository.java` (Persistencia)
    * `PersonService.java` (Servicio)

---

## Compromiso de Actividades

### Sebastián David Florián Cadena (Líder Técnico)

- [ ] Crear el repositorio en GitHub e inicializar la estructura del proyecto Maven (`pom.xml`)
- [ ] Configurar las ramas `main` y `develop` con protección
- [ ] Crear la documentación del equipo en `TEAM.md`
- [ ] Implementar la clase de dominio `Sale.java` y su método de cálculo de total
- [ ] Implementar la clase de persistencia `SaleRepository.java`
- [ ] Implementar la clase `SaleService.java` (validación de stock, actualización de inventario)
- [ ] Construir la interfaz de usuario `ConsoleUI.java` (menú principal y submenús)
- [ ] Implementar `Main.java` con carga inicial e inyección de dependencias
- [ ] Revisar y aprobar Pull Requests en GitHub para integración en `develop`
- [ ] Elaborar el `README.md` final con instrucciones de ejecución

### Juan Manuel Otero Rueda (Desarrollador 1)

- [ ] Crear la rama `feature/product-module`
- [ ] Implementar la clase abstracta base `Product.java` y definir método abstracto de descripción
- [ ] Implementar la clase derivada `Videogame.java`
- [ ] Implementar la clase derivada `Console.java`
- [ ] Implementar la clase de persistencia `ProductRepository.java` (archivos)
- [ ] Implementar la clase de servicio `ProductService.java` (registro, listar, stock)
- [ ] Documentar el módulo con JavaDoc en inglés
- [ ] Solicitar Pull Request hacia `develop`

### Oscar Sebastián Araujo Erazo (Desarrollador 2)

- [ ] Crear la rama `feature/person-module`
- [ ] Implementar la clase abstracta base `Person.java`
- [ ] Implementar la clase derivada `Customer.java`
- [ ] Implementar la clase derivada `Seller.java`
- [ ] Implementar la clase de persistencia `PersonRepository.java` (archivos)
- [ ] Implementar la clase de servicio `PersonService.java` (registro y listado)
- [ ] Documentar el módulo con JavaDoc en inglés
- [ ] Solicitar Pull Request hacia `develop`

---

## Ramas Git

* **`main`**: Versión estable de producción.
* **`develop`**: Rama de integración del equipo.
* **`feature/sale-module`**: Módulo de Ventas e Interfaz (Sebastián Florián)
* **`feature/product-module`**: Módulo de Productos (Juan Otero)
* **`feature/person-module`**: Módulo de Personas (Oscar Araujo)