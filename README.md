# ForoHub API

API RESTful para la gestión de un foro educativo, desarrollada con Spring Boot, Spring Security, JWT y Swagger.

## Características
- Autenticación con JWT
- Endpoints protegidos
- CRUD de usuarios, tópicos y respuestas
- Asignación automática de perfil al usuario
- Contraseñas cifradas
- Documentación automática con Swagger

## Instalación y ejecución
1. Clona el repositorio.
2. Configura la base de datos en `src/main/resources/application.properties`.
3. Ejecuta las migraciones SQL de `base_datos.sql`.
4. Instala dependencias y ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Accede a la API en `http://localhost:8080`.

## Documentación Swagger
- Accede a la documentación interactiva en:
  - `http://localhost:8080/swagger-ui.html` (SpringFox Swagger 2)
  - `http://localhost:8080/swagger-ui/index.html` (SpringFox Swagger 3)

## Autenticación
1. Realiza un POST a `/login` con:
   ```json
   {
     "correoElectronico": "usuario@example.com",
     "contrasena": "password123"
   }
   ```
2. Copia el token JWT de la respuesta.
3. En cada solicitud protegida, agrega el header:
   ```
   Authorization: Bearer <tu_token>
   ```

## Ejemplos de uso

### Usuarios
- **Crear usuario:**
  ```json
  {
    "nombre": "Juan Perez",
    "correoElectronico": "juan@example.com",
    "contrasena": "password123"
  }
  ```
- **Listar usuarios:** `GET /usuario`
- **Actualizar usuario:** `PUT /usuario/{id}`
- **Eliminar usuario:** `DELETE /usuario/{id}`

### Tópicos
- **Crear tópico:**
  ```json
  {
    "titulo": "Nuevo Tópico",
    "mensaje": "Mensaje del tópico",
    "autor": 1,
    "curso": 1
  }
  ```
- **Listar tópicos:** `GET /topicos`
- **Detalle tópico:** `GET /topicos/{id}`
- **Actualizar tópico:** `PUT /topicos/{id}`
- **Eliminar tópico:** `DELETE /topicos/{id}`

### Respuestas
- **Crear respuesta:**
  ```json
  {
    "mensaje": "Esta es una respuesta.",
    "topico": 1,
    "autor": 1,
    "solucion": false
  }
  ```
- **Listar respuestas:** `GET /respuestas`
- **Detalle respuesta:** `GET /respuestas/{id}`
- **Actualizar respuesta:** `PUT /respuestas/{id}`
- **Eliminar respuesta:** `DELETE /respuestas/{id}`

## Notas
- El perfil default para usuarios es "USER". Asegúrate de tenerlo en la tabla Perfil.
- Las contraseñas se almacenan cifradas.
- Todos los endpoints protegidos requieren el token JWT en el header Authorization.

## Autor
- Desarrollado por Ronaldo Rivera para Alura Latam.

