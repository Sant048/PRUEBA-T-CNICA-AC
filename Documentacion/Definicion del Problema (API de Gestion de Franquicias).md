# Prueba Técnica Backend - Problema

## Enunciado

Se requiere construir una API para manejar una lista de franquicias.

Una franquicia se compone de:
- Un nombre
- Una lista de sucursales

Una sucursal se compone de:
- Un nombre
- Un listado de productos ofertados en la sucursal

Un producto se compone de:
- Un nombre
- Una cantidad de stock
## Criterios de Aceptación

1. El proyecto debe ser desarrollado en Spring Boot
2. Exponer endpoint para agregar una nueva franquicia
3. Exponer endpoint para agregar una nueva sucursal a la franquicia
4. Exponer endpoint para agregar un nuevo producto a la sucursal
5. Exponer endpoint para eliminar un producto de una sucursal
6. Exponer endpoint para modificar el stock de un producto
7. Exponer endpoint que permita mostrar cuál es el producto con mayor stock por sucursal para una franquicia puntual.
    - Debe retornar un listado de productos indicando a qué sucursal pertenece    
8. Utilizar un sistema de persistencia de datos (MySQL, MongoDB, Redis, DynamoDB, etc.) en algún proveedor de nube
## Puntos Extra

- Empaquetar la aplicación con Docker
- Usar programación funcional o reactiva
- Exponer endpoint para actualizar:
    - Nombre de la franquicia
    - Nombre de la sucursal
    - Nombre del producto
    
- Aprovisionar la persistencia como infraestructura como código (Terraform, CloudFormation, etc.)
- Desplegar la solución en la nube
## Entrega

- Repositorio público (GitHub, GitLab, Bitbucket, etc.)
- Documentación que permita desplegar la aplicación en un entorno local (README.md)