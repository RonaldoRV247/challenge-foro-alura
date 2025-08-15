package com.example.foro.alura.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "correoElectronico", nullable = false, length = 150, unique = true)
    private String correoElectronico;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @ManyToMany
    @JoinTable(
        name = "Usuario_Perfil",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfiles;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public Set<Perfil> getPerfiles() { return perfiles; }
    public void setPerfiles(Set<Perfil> perfiles) { this.perfiles = perfiles; }
}
