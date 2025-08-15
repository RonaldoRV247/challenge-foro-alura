package com.example.foro.alura.controller;

import com.example.foro.alura.model.Perfil;
import com.example.foro.alura.model.Usuario;
import com.example.foro.alura.repository.PerfilRepository;
import com.example.foro.alura.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid Usuario usuario) {
        if (usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Perfil perfilDefault = perfilRepository.findByNombre("USUARIO").orElse(null);
        if (perfilDefault != null) {
            usuario.setPerfiles(new HashSet<>());
            usuario.getPerfiles().add(perfilDefault);
        }
        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario existente = usuarioOpt.get();
        existente.setNombre(usuario.getNombre());
        existente.setCorreoElectronico(usuario.getCorreoElectronico());
        existente.setContrasena(usuario.getContrasena());
        Usuario actualizado = usuarioRepository.save(existente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
