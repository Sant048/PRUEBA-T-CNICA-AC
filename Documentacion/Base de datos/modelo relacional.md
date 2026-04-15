![[Pasted image 20260414194743.png]]

# Modelo de Datos - API de Franquicias

## Descripción General

El modelo de datos representa una estructura jerárquica compuesta por franquicias, sucursales y productos.

```text
Franchise
 └── Branch
       └── Product
```

Cada nivel depende del anterior y mantiene relaciones uno a muchos.

## Entidades

### Franchise

Representa una franquicia dentro del sistema.

**Campos:**

- `id` (bigint, PK, auto incremental): Identificador único
- `name` (varchar, not null): Nombre de la franquicia
### Branch

Representa una sucursal perteneciente a una franquicia.

**Campos:**
- `id` (bigint, PK, auto incremental): Identificador único
- `name` (varchar, not null): Nombre de la sucursal
- `franchise_id` (bigint, FK, not null): Referencia a la franquicia

**Restricciones:**
- Una sucursal pertenece obligatoriamente a una franquicia
- No se permiten nombres duplicados de sucursales dentro de la misma franquicia
### Product

Representa un producto disponible en una sucursal.

**Campos:**

- `id` (bigint, PK, auto incremental): Identificador único
- `name` (varchar, not null): Nombre del producto
- `stock` (int, not null, default 0): Cantidad disponible
- `branch_id` (bigint, FK, not null): Referencia a la sucursal

**Restricciones:**

- Un producto pertenece obligatoriamente a una sucursal
- No se permiten nombres duplicados de productos dentro de la misma sucursal
- El stock no puede ser nulo
## Relaciones

- Una franquicia puede tener múltiples sucursales
- Una sucursal puede tener múltiples productos

```text
Franchise (1) ──── (N) Branch
Branch    (1) ──── (N) Product
```

## Integridad Referencial

- `branch.franchise_id` referencia a `franchise.id`
- `product.branch_id` referencia a `branch.id`

Estas relaciones garantizan consistencia en los datos y evitan registros huérfanos.
## Índices

Se definen índices para optimizar consultas y garantizar unicidad lógica:

- Índice sobre `branch.franchise_id` para búsquedas por franquicia
- Índice sobre `product.branch_id` para búsquedas por sucursal
- Índice único compuesto `(name, franchise_id)` en sucursal
- Índice único compuesto `(name, branch_id)` en producto
## Consideraciones de Diseño

- El modelo sigue principios de normalización (hasta 3FN)
- Se utilizan claves primarias simples para compatibilidad con JPA
- Las relaciones se manejan mediante claves foráneas
- Se evita el uso de estructuras anidadas o datos redundantes
- Se prioriza simplicidad y claridad sobre complejidad innecesaria
## Soporte para Requerimientos

Este modelo permite:
- Crear franquicias, sucursales y productos
- Asociar correctamente cada entidad
- Gestionar stock de productos
- Consultar información agregada (producto con mayor stock por sucursal)