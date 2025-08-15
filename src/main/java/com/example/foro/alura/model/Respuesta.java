package com.example.foro.alura.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private Long topico;

    @Column(nullable = false)
    private Long autor;

    @Column(nullable = false)
    private Boolean solucion = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Long getTopico() { return topico; }
    public void setTopico(Long topico) { this.topico = topico; }
    public Long getAutor() { return autor; }
    public void setAutor(Long autor) { this.autor = autor; }
    public Boolean getSolucion() { return solucion; }
    public void setSolucion(Boolean solucion) { this.solucion = solucion; }
}

