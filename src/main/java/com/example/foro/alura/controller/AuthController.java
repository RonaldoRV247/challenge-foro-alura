package com.example.foro.alura.controller;

import com.example.foro.alura.dto.AuthRequest;
import com.example.foro.alura.security.TokenService;
import com.example.foro.alura.model.Usuario;
import com.example.foro.alura.repository.UsuarioRepository;
import com.example.foro.alura.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreoElectronico(), request.getContrasena())
        );
        Usuario usuario = usuarioRepository.findByCorreoElectronico(request.getCorreoElectronico()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        String token = tokenService.generarToken(request.getCorreoElectronico());
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico());
        return ResponseEntity.ok().body(new java.util.HashMap<>() {{
            put("token", token);
            put("usuario", usuarioResponse);
        }});
    }
}
