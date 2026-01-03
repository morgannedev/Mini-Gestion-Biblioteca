📚 Proyecto: BibliotecaApp (Java Backend)  

Mini proyecto de biblioteca desarrollado en Java, con conexión a base de datos MariaDB/MySQL.  

✅ Funcionalidades:  
- Registro y login de usuarios  
- Préstamos y devoluciones de libros  
- Gestión de estados (DISPONIBLE / PRESTADO)  

💻 Tecnologías: 
- Java 21  
- JDBC  
- MariaDB / MySQL  
- Patrones DAO y Service (MVC simplificado)  
- Enum para estados de libros (`DISPONIBLE`, `PRESTADO`)  
- Consola interactiva para el usuario  

🔗 Repositorio: github.com/tuUsuario/BibliotecaApp  

Una forma práctica de demostrar cómo implemento lógica de negocio, manejo de bases de datos y patrones de diseño.

## Configuración de la Base de Datos

1. Instalar MariaDB o MySQL y crear la base de datos:

Crear las tablas necesarias:
```sql
CREATE DATABASE myapp;
USE myapp;

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50),
    fecha_nacimiento DATE NOT NULL,
    rol VARCHAR(20) DEFAULT 'USER'
);

CREATE TABLE Libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    isbn VARCHAR(20),
    autor VARCHAR(100),
    fecha_publicacion DATE,
    estado VARCHAR(20) DEFAULT 'DISPONIBLE'
);

CREATE TABLE Prestamo (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro)
);
```
Crear un usuario de base de datos con permisos
```sql
CREATE USER 'backendMyApp'@'localhost' IDENTIFIED BY 'TU_PASSWORD';
GRANT ALL PRIVILEGES ON myapp.* TO 'backendMyApp'@'localhost';
FLUSH PRIVILEGES;
```
Configurar DBConnection.java con tus credenciales:
```java
private static final String URL="jdbc:mysql://localhost:3306/myapp";
private static final String USER="backendMyApp";
private static final String PASSWORD="TU_PASSWORD";
```
