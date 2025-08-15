package com.example.foro.alura.dto;

public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String correoElectronico;

    public UsuarioResponse(Long id, String nombre, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreoElectronico() { return correoElectronico; }
}

