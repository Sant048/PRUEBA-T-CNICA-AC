## Enfoque General

Se adopta una arquitectura de tipo **monolito modular** utilizando **Spring Boot**, con una organización interna basada en principios de **Clean Architecture**.

Este enfoque permite cumplir con los requisitos de la prueba manteniendo una estructura clara, escalable y mantenible sin introducir complejidad innecesaria.
## Decisión 1: Separación por Módulos

**Decisión:** División por dominios (`franchise`, `branch`, `product`, `report`)

**Justificación:**

- Permite alta cohesión dentro de cada módulo
- Reduce el acoplamiento entre funcionalidades
- Mejora la mantenibilidad del código
- Facilita la evolución futura del sistema
## Decisión 2: Uso de Clean Architecture

**Decisión:** Separación en capas (`domain`, `application`, `infrastructure`, `interfaces`)

**Justificación:**

- Aísla la lógica de negocio del framework
- Facilita pruebas y cambios tecnológicos
- Mantiene reglas claras de dependencia
- Evita mezclar lógica de negocio con detalles técnicos

## Decisión 3: Modelo Relacional

**Decisión:** Uso de base de datos relacional (MySQL)

**Justificación:**
- El dominio tiene relaciones claras uno a muchos
- Se requiere integridad referencial
- Permite consultas agregadas eficientes
- Compatible de forma natural con JPA
## Decisión 4: Normalización de Datos

**Decisión:** Modelo en tercera forma normal (3FN)

**Justificación:**
- Evita redundancia de datos
- Mantiene consistencia
- Simplifica mantenimiento
- Facilita integridad referencial
## Decisión 5: Uso de Claves Foráneas

**Decisión:** Implementar relaciones mediante claves foráneas

**Justificación:**
- Garantiza consistencia de datos
- Previene registros huérfanos
- Refuerza la integridad del modelo
## Decisión 6: Manejo de Reportes

**Decisión:** Crear un módulo específico de consultas (`report`)

**Justificación:**
- Separa lógica de lectura de lógica de escritura
- Evita sobrecargar servicios de dominio
- Permite implementar consultas complejas de forma clara
- Se alinea con principios de CQRS
## Decisión 7: Uso de DTOs

**Decisión:** Utilizar objetos de transferencia de datos para respuestas

**Justificación:**
- Evita exponer entidades directamente
- Permite controlar la estructura de salida
- Facilita cambios sin afectar el dominio
## Decisión 8: Índices y Restricciones

**Decisión:** Agregar índices y restricciones de unicidad

**Justificación:**
- Mejora el rendimiento de consultas
- Evita duplicidad de datos lógicos
- Refuerza reglas de negocio a nivel de base de datos
## Decisión 9: Simplicidad sobre Sobreingeniería

**Decisión:** Implementar únicamente lo necesario para cumplir y optimizar la prueba

**Justificación:**

- Evita complejidad innecesaria
- Mejora la claridad del código
- Facilita la evaluación del sistema
- Permite enfocarse en calidad en lugar de cantidad

## Conclusión

La arquitectura propuesta busca un balance entre simplicidad y buenas prácticas, asegurando que el sistema sea:

- Correcto desde el punto de vista técnico
- Fácil de mantener
- Escalable a futuro
- Comprensible para evaluadores