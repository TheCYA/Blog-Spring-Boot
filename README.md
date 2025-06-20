# 🛠️ Spring Boot API - Autenticación JWT y Gestión de Posts

Este proyecto es una API REST construida con **Spring Boot**, que incluye autenticación con **JWT**, control de acceso por **roles (USER, AUTHOR, ADMIN)**, manejo de posts, y seguridad basada en `Spring Security`.

---

## 🚀 Características

- Registro y login de usuarios con JWT
- Protección de rutas según roles (`@PreAuthorize`)
- CRUD de posts
- Relación entre usuarios y posts
- Validación de datos con DTOs
- Envío y validación de tokens JWT
- Soporte para actualización parcial con `PATCH`
- Control de errores personalizado

---

## 📦 Tecnologías

- Java 21
- Spring Boot 3.5
- Spring Security 6.5
- JWT (JSON Web Token)
- Maven
- JPA + Hibernate
- H2/MySQL/PostgreSQL (configurable)

---

## ⚙️ Configuración

1. Crea un archivo `application.properties` en `src/main/resources/`
Agrega las propiedades marcadas en el archivo `application-example.properties` con las que sean según tu caso.


## Ejecución
- mvn spring-boot:run
