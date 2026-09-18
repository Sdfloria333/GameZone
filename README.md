# GameZone Unicesar

Sistema de información para la gestión de una tienda de videojuegos, consolas y
accesorios: inventario, clientes, vendedores, ventas, promociones, devoluciones
y garantías.

Proyecto académico de la asignatura **Programación III** (Universidad Popular
del Cesar), desarrollado en Java con arquitectura en capas y persistencia en
archivos JSON.

## Tecnologías

- Java 21+ (Maven, `pom.xml`)
- [Gson](https://github.com/google/gson) para persistencia JSON
- Interfaz de consola (`ConsoleUI`)

## Cómo ejecutar

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.gamezone.Main"
```

O ejecuta `Main.java` directamente desde tu IDE.

## Arquitectura

El proyecto sigue una arquitectura en cuatro capas, con dependencias en un
único sentido: `ui → service → persistence → model`.