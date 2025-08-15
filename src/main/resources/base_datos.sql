CREATE DATABASE ForoHub;
USE ForoHub;

-- Tabla Perfil
CREATE TABLE Perfil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla Usuario
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
);

-- Tabla intermedia para relación muchos a muchos: Usuario - Perfil
CREATE TABLE Usuario_Perfil (
    usuario_id INT NOT NULL,
    perfil_id INT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id) ON DELETE CASCADE
);

-- Tabla Curso
CREATE TABLE Curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100)
);

-- Tabla Tópico
CREATE TABLE Topico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    autor INT NOT NULL,
    curso INT NOT NULL,
    FOREIGN KEY (autor) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (curso) REFERENCES Curso(id) ON DELETE CASCADE
);

-- Tabla Respuesta
CREATE TABLE Respuesta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    topico INT NOT NULL,
    fechaCreacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    autor INT NOT NULL,
    solucion BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (topico) REFERENCES Topico(id) ON DELETE CASCADE,
    FOREIGN KEY (autor) REFERENCES Usuario(id) ON DELETE CASCADE
);
