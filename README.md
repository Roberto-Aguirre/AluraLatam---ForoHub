# ForoHub

API REST para gestion de topics, comentarios, cursos y usuarios con autenticacion JWT, autorizacion por rol y restriccion de ownership para usuarios `USER`.

## Requisitos

- Java 21+
- Maven Wrapper (`mvnw.cmd` ya incluido)
- MySQL (o Docker con el `docker-compose.yml` del proyecto)

## Variables de Entorno

Estas variables se leen desde `application.yaml`:

- `FOROHUB_DB_URL`: URL de conexion MySQL.
- `FOROHUB_DB_USER`: usuario de MySQL.
- `FOROHUB_DB_PASSWORD`: password de MySQL.
- `FOROHUB_JWT_SECRET`: secreto para firmar/verificar JWT.
- `FOROHUB_HASH_SECRET`: palabra secreta (pepper) aplicada al hash de password.
- `FOROHUB_BCRYPT_STRENGTH`: costo BCrypt, por defecto `10`.

Ejemplo en PowerShell:

```powershell
$env:FOROHUB_DB_URL = "jdbc:mysql://127.0.0.1:3309/forohub?createDatabaseIfNotExist=true"
$env:FOROHUB_DB_USER = "root"
$env:FOROHUB_DB_PASSWORD = "AluraLatamForoHub"
$env:FOROHUB_JWT_SECRET = "forohub-jwt-super-secreto"
$env:FOROHUB_HASH_SECRET = "forohub-pepper-secreto"
$env:FOROHUB_BCRYPT_STRENGTH = "10"
```

## Levantar la Aplicacion

```powershell
.\mvnw.cmd clean spring-boot:run
```

## Autenticacion

### Login

`POST /login`

Body:

```json
{
  "email": "admin@example.com",
  "password": "password"
}
```

Respuesta:

```json
{
  "token": "<jwt>"
}
```

Usa el token en header:

`Authorization: Bearer <jwt>`

## Reglas de Roles

- `ADMIN`: puede ejecutar todos los metodos.
- `USER`: puede `POST` y `PUT` solo sobre sus propios `topics` y `comentarios`.

## Swagger (Springdoc)

Con la app corriendo:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

`/login` y endpoints de Swagger son publicos.

## Coleccion Postman

La coleccion actualizada se encuentra en:

- `postman/ForoHub.postman_collection.json`

Incluye requests de login y variable `token` para autorizacion Bearer.
