package com.example.foro.alura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TopicoRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String mensaje;
    @NotNull
    private Long autor;
    @NotNull
    private Long curso;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Long getAutor() { return autor; }
    public void setAutor(Long autor) { this.autor = autor; }
    public Long getCurso() { return curso; }
    public void setCurso(Long curso) { this.curso = curso; }
}
