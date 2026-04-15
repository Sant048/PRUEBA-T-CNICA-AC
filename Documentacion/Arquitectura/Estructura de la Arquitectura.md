# Arquitectura - API de Franquicias

## Enfoque General

La solución se implementa como un **monolito modular** basado en principios de **Clean Architecture**.

- Se despliega como una sola aplicación (una API)
- Se organiza internamente en módulos por dominio
- Se separan responsabilidades para mantener bajo acoplamiento y alta cohesión
## Estructura de Alto Nivel

```text
API (Spring Boot)
│
├── franchise
├── branch
├── product
├── report
└── shared
```

## Descripción de Módulos

### franchise

Responsable de la gestión de franquicias.

Incluye:
- Creación de franquicias
- Actualización de nombre

No incluye:
- Lógica de sucursales o productos
### branch

Responsable de la gestión de sucursales.

Incluye:
- Creación de sucursales asociadas a una franquicia
- Actualización de nombre
- Consulta de sucursales por franquicia

No incluye:
- Lógica de productos
- Creación de franquicias
### product

Responsable de la gestión de productos.

Incluye:
- Creación de productos
- Eliminación de productos
- Actualización de stock
- Actualización de nombre

No incluye:
- Lógica de franquicias
- Creación de sucursales
### report

Responsable de consultas complejas.

Incluye:
- Obtención del producto con mayor stock por sucursal en una franquicia

No incluye:
- Modificación de datos

### shared

Contiene componentes reutilizables.

Incluye:

- Manejo de excepciones
- Configuración general
- Clases utilitarias

No incluye:
- Lógica de negocio específica
## Estructura Interna por Módulo

Cada módulo sigue la siguiente separación:

```text
module/
├── domain
├── application
├── infrastructure
└── interfaces
```

### domain

- Entidades del negocio
- Reglas de negocio puras
### application

- Casos de uso
- Orquestación de lógica
### infrastructure

- Implementaciones de persistencia
- Integraciones externas
### interfaces

- Controladores REST
- Entrada/salida de datos (DTOs)
## Flujo de la Aplicación

```text
HTTP Request
   ↓
Controller (interfaces)
   ↓
Service / Use Case (application)
   ↓
Domain
   ↓
Repository (infrastructure)
   ↓
Database
```

## Reglas de Arquitectura

- Separación de responsabilidades por módulo
- Dependencias dirigidas hacia el dominio
- No acceso directo entre módulos a nivel de base de datos
- Comunicación entre módulos mediante servicios o identificadores
- El dominio no depende de frameworks externos

## Justificación

Este enfoque permite:

- Escalabilidad del sistema
- Facilidad de mantenimiento
- Claridad en la organización del código
- Bajo acoplamiento entre componentes
- Adaptabilidad a futuros cambios