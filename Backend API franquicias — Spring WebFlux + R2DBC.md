API reactiva desarrollada con **Spring Boot WebFlux** y acceso a base de datos MySQL mediante **R2DBC**.  
El proyecto está preparado para despliegue en **Render + Docker**.

---

## Tecnologías

|Tecnología|Detalle|
|---|---|
|Java|17|
|Spring Boot|WebFlux|
|Spring Data|R2DBC|
|Base de datos|MySQL (R2DBC Driver)|
|Build|Maven|
|Contenedor|Docker|
|Paradigma|Reactor (programación reactiva)|

---

## Arquitectura

El proyecto sigue un enfoque reactivo **non-blocking** de extremo a extremo:

```
Controller → Service → Repository (R2DBC) → Database
```

Todo el flujo utiliza `Mono` y `Flux` de Project Reactor, garantizando un pipeline completamente asíncrono.

---

## Configuración local

### 1. Clonar el repositorio

```bash
git clone https://github.com/Sant048/PRUEBA-T-CNICA-AC.git
cd PRUEBA-T-CNICA-AC
```

### 2. Variables de entorno

Crear un archivo `.env` en la raíz del proyecto:

```env
DB_PASSWORD=tu_password
SPRING_R2DBC_URL=r2dbc:mysql://host:port/database
SPRING_R2DBC_USERNAME=root
SPRING_R2DBC_PASSWORD=${DB_PASSWORD}
```

### 3. Configuración Spring

En `src/main/resources/application.properties`:

```properties
spring.r2dbc.url=${SPRING_R2DBC_URL}
spring.r2dbc.username=${SPRING_R2DBC_USERNAME}
spring.r2dbc.password=${SPRING_R2DBC_PASSWORD}
```

---

## Ejecutar con Docker

```bash
# Build de la imagen
docker build -t accenture-backend .

# Ejecutar el contenedor
docker run -p 8080:8080 --env-file .env accenture-backend
```

---

## Ejecutar en local (sin Docker)

```bash
./mvnw spring-boot:run
```

o con Maven instalado globalmente:

```bash
mvn spring-boot:run
```

---

## Deploy en Render

### Comandos

|Campo|Valor|
|---|---|
|Build Command|`mvn clean package -DskipTests`|
|Start Command|`java -jar target/*.jar`|
|Root Directory|`Accenture` _(si aplica)_|

### Variables de entorno

Agregar en la sección **Environment** del servicio:

|Variable|Descripción|
|---|---|
|`SPRING_R2DBC_URL`|URL de conexión R2DBC a la base de datos|
|`SPRING_R2DBC_USERNAME`|Usuario de la base de datos|
|`SPRING_R2DBC_PASSWORD`|Contraseña de la base de datos|

## Estructura del proyecto

La solución se implementa como un **monolito modular** basado en principios de Clean Architecture. Se despliega como una sola aplicación organizada internamente en módulos por dominio, con separación de responsabilidades para mantener bajo acoplamiento y alta cohesión.

### Módulos de alto nivel

```
src/main/java/com/accenture/
│
├── franchise/
├── branch/
├── product/
├── report/
└── shared/
```

|Módulo|Responsabilidad|
|---|---|
|`franchise`|Creación y actualización de franquicias|
|`branch`|Gestión de sucursales asociadas a una franquicia|
|`product`|Creación, eliminación, stock y nombre de productos|
|`report`|Consultas complejas (solo lectura)|
|`shared`|Excepciones, configuración y utilidades transversales|

### Estructura interna por módulo

Cada módulo sigue la separación de capas de Clean Architecture:

```
module/
├── domain/          # Entidades y reglas de negocio puras
├── application/     # Casos de uso y orquestación de lógica
├── infrastructure/  # Persistencia e integraciones externas
└── interfaces/      # Controladores REST y DTOs
```

### Flujo de la aplicación

```
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

### Estructura completa del repositorio

```
PRUEBA-T-CNICA-AC/
├── Accenture/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/accenture/
│   │       │       ├── franchise/
│   │       │       │   ├── domain/
│   │       │       │   ├── application/
│   │       │       │   ├── infrastructure/
│   │       │       │   └── interfaces/
│   │       │       ├── branch/
│   │       │       │   ├── domain/
│   │       │       │   ├── application/
│   │       │       │   ├── infrastructure/
│   │       │       │   └── interfaces/
│   │       │       ├── product/
│   │       │       │   ├── domain/
│   │       │       │   ├── application/
│   │       │       │   ├── infrastructure/
│   │       │       │   └── interfaces/
│   │       │       ├── report/
│   │       │       │   ├── application/
│   │       │       │   └── interfaces/
│   │       │       └── shared/
│   │       └── resources/
│   │           └── application.properties
│   ├── Dockerfile
│   └── pom.xml
└── README.md
```

### Reglas de arquitectura

- Las dependencias se dirigen siempre hacia el dominio
- No existe acceso directo entre módulos a nivel de base de datos
- La comunicación entre módulos se realiza mediante servicios o identificadores
- El dominio no depende de frameworks externos
## Problemas comunes

**Build falla en Render**

- Verificar que `pom.xml` no tenga errores XML
- Verificar que la estructura del repositorio sea correcta
- No duplicar dependencias en `pom.xml`

**Error `pom.xml not found`**

- Ajustar el campo Root Directory en Render para apuntar a la carpeta correcta
- O mover el proyecto a la raíz del repositorio

**Error de conexión a la base de datos**

- Verificar que la URL R2DBC tenga el formato correcto: `r2dbc:mysql://host:port/dbname`
- Verificar usuario y contraseña
- Revisar reglas de firewall / lista blanca de IPs de la base de datos