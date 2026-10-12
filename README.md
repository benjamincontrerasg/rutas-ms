# Microservicio: `rutas-ms`

Microservicio de rutas del caso **RutaLimpia** (servicio de recolección de reciclaje a domicilio), desarrollado con **Spring Boot**, **Spring Data JPA** y **PostgreSQL**.

En el sistema RutaLimpia, cada **ruta** representa la hoja de ruta asignada a un camión, y sus **paradas** son los retiros programados que debe efectuar el conductor. El conductor consulta las paradas asignadas a su camión y registra si cada retiro fue realizado o falló.

Los datos de camiones y solicitudes provienen de otros microservicios con sus propias bases de datos:
- `camionId`: hace referencia al camión en el microservicio de **Flota**.
- `solicitudId`: hace referencia a la solicitud de retiro en el microservicio de **Solicitudes**.

Desarrollado en el contexto de la **Evaluación Parcial 2** de la asignatura **Java: Diseño y Construcción de Soluciones Nativas en Nube (JVY0101)**.

---

## 1. Requisitos del Sistema

Para compilar y ejecutar este microservicio se requiere:

- **Java JDK 17** (o superior compatible).
- **Apache Maven 3.8+** (o utilizar el wrapper `./mvnw` / `mvnw.cmd` incluido).
- **PostgreSQL 14+** en ejecución local o accesible en red.

---

## 2. Base de Datos

El microservicio utiliza una base de datos PostgreSQL llamada `rutas_db`.

Para crearla, ejecutar el siguiente comando en PostgreSQL (psql o pgAdmin):

```sql
CREATE DATABASE rutas_db;
```

> **Nota:** Hibernate (`spring.jpa.hibernate.ddl-auto=update`) creará automáticamente las tablas `rutas` y `paradas` junto con sus relaciones y claves foráneas al iniciar el servicio.

---

## 3. Configuración

El archivo de configuración principal se encuentra en `src/main/resources/application.properties`. Asegúrate de configurar las credenciales de tu servidor PostgreSQL:

```properties
# Nombre de la aplicación y puerto del microservicio
spring.application.name=rutas-ms
server.port=8082

# Conexión a la base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/rutas_db
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD_AQUI
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate: crea y actualiza las tablas según las entidades, y muestra el SQL en consola
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 4. Clonar el Repositorio

Para clonar el proyecto en tu máquina local:

```bash
git clone https://github.com/benjamincontrerasg/rutas-ms.git
cd rutas-ms
```

---

## 5. Compilación y Ejecución

Puedes utilizar tu instalación global de `mvn` o el Maven Wrapper (`./mvnw` en Linux/macOS o `mvnw.cmd` en Windows).

### Comandos de ciclo de vida Maven

- **Limpiar artefactos generados:**
  ```bash
  mvn clean
  ```
- **Compilar e instalar dependencias:**
  ```bash
  mvn install
  ```
- **Empaquetar el archivo JAR:**
  ```bash
  mvn clean package
  ```

### Ejecución de la aplicación

El microservicio corre en el puerto **`8082`**. Puedes iniciarlo mediante cualquiera de las siguientes alternativas:

1. **Vía Maven Plugin:**
   ```bash
   mvn spring-boot:run
   ```
2. **Vía archivo JAR ejecutable:**
   ```bash
   java -jar target/rutas-ms-0.0.1-SNAPSHOT.jar
   ```

---

## 6. Endpoints de la API REST

**Puerto del servicio:** `8082`  
**URL base:** `http://localhost:8082`

### Endpoints de Rutas (`/rutas`)

| Método | Ruta | Descripción | Respuestas HTTP |
|---|---|---|---|
| `POST` | `/rutas` | Registra una nueva hoja de ruta asociada a un camión | `201 Created` con la ruta guardada<br>`400 Bad Request` si el nombre viene nulo/vacío o si `camionId` viene nulo |
| `GET` | `/rutas` | Lista todas las rutas registradas | `200 OK` con listado de rutas |
| `GET` | `/rutas/{id}` | Busca una ruta por su ID | `200 OK` con la ruta<br>`404 Not Found` si no existe |
| `PUT` | `/rutas/{id}` | Actualiza los datos de una ruta (nombre, sector, día, estado y camión) | `200 OK` con la ruta actualizada<br>`404 Not Found` si no existe |
| `DELETE` | `/rutas/{id}` | Elimina una ruta por su ID | `204 No Content` si se eliminó<br>`404 Not Found` si no existe |
| `GET` | `/rutas/sector/{sector}` | Consulta rutas por sector geográfico | `200 OK` con las rutas coincidentes |
| `GET` | `/rutas/estado/{estado}` | Consulta rutas por estado (`ACTIVA`/`INACTIVA`) | `200 OK` con las rutas coincidentes |
| `GET` | `/rutas/camion/{camionId}` | Consulta las hojas de ruta asignadas a un camión | `200 OK` con las rutas del camión |

### Endpoints de Paradas (`/paradas`)

| Método | Ruta | Descripción | Respuestas HTTP |
|---|---|---|---|
| `POST` | `/paradas/ruta/{rutaId}` | Crea una parada de retiro asociada a una ruta | `201 Created` con la parada creada<br>`400 Bad Request` si la dirección viene nula/vacía o si `solicitudId` viene nulo<br>`404 Not Found` si la ruta no existe |
| `GET` | `/paradas` | Lista todas las paradas | `200 OK` con listado de paradas |
| `GET` | `/paradas/{id}` | Busca una parada por su ID | `200 OK` con la parada<br>`404 Not Found` si no existe |
| `PUT` | `/paradas/{id}` | Actualiza datos de la parada (dirección, orden, observación) | `200 OK` con la parada actualizada<br>`404 Not Found` si no existe |
| `DELETE` | `/paradas/{id}` | Elimina una parada por su ID | `204 No Content` si se eliminó<br>`404 Not Found` si no existe |
| `GET` | `/paradas/ruta/{rutaId}` | Obtiene las paradas de una ruta ordenadas por posición | `200 OK` con la lista de paradas ordenada |
| `GET` | `/paradas/camion/{camionId}` | Consulta las paradas asignadas a un camión ordenadas por orden ascendente | `200 OK` con las paradas del camión |
| `PUT` | `/paradas/{id}/resultado` | Registra el resultado del retiro por parte del conductor (`REALIZADA` o `FALLIDA`) | `200 OK` con la parada actualizada<br>`400 Bad Request` si el estado no es `REALIZADA` o `FALLIDA`<br>`404 Not Found` si la parada no existe |

---

## 7. Ejemplos de JSON para Pruebas

### 7.1. Crear una Ruta

**Solicitud:** `POST /rutas`  
**Headers:** `Content-Type: application/json`

```json
{
  "nombre": "Ruta Norte 1",
  "sector": "Sector Norte",
  "diaRecoleccion": "LUNES",
  "camionId": 1
}
```

*Nota: Si el campo `estado` se omite o viene vacío, el servicio asignará `"ACTIVA"` de forma predeterminada.*

### 7.2. Crear una Parada

**Solicitud:** `POST /paradas/ruta/1`  
**Headers:** `Content-Type: application/json`

```json
{
  "direccion": "Av. Principal 123",
  "orden": 1,
  "observacion": "Frente a la plaza",
  "solicitudId": 1
}
```

*Nota: Si el campo `estado` se omite o viene vacío al crear la parada, se asignará `"PENDIENTE"` de forma predeterminada.*

### 7.3. Registrar Resultado de Retiro

**Solicitud:** `PUT /paradas/1/resultado`  
**Headers:** `Content-Type: application/json`

```json
{
  "estado": "REALIZADA",
  "observacion": "Retiro completado"
}
```

---

## 8. Arquitectura y Tecnologías

- **Capa Controller:** Endpoints REST que gestionan solicitudes HTTP y códigos de estado.
- **Capa Service:** Implementación de reglas y validaciones de negocio.
- **Capa Repository:** Interfaces Spring Data JPA con consultas por método.
- **Capa Model:** Entidades `@Entity` con mapeo relacional `@OneToMany` y `@ManyToOne`.
- **Inyección por constructor:** Componentes inmutables sin uso de `@Autowired` en campos.
- **Sin Lombok:** Getters y setters manuales para transparencia y control del código.
