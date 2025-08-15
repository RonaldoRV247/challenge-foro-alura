package com.example.foro.alura.controller;

import com.example.foro.alura.model.Respuesta;
import com.example.foro.alura.repository.RespuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @PostMapping
    public ResponseEntity<Respuesta> crearRespuesta(@RequestBody @Valid Respuesta respuesta) {
        Respuesta guardada = respuestaRepository.save(respuesta);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @GetMapping
    public List<Respuesta> listarRespuestas() {
        return respuestaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> obtenerRespuesta(@PathVariable Long id) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        return respuesta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid Respuesta respuesta) {
        Optional<Respuesta> respuestaOpt = respuestaRepository.findById(id);
        if (respuestaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Respuesta existente = respuestaOpt.get();
        existente.setMensaje(respuesta.getMensaje());
        existente.setSolucion(respuesta.getSolucion());
        Respuesta actualizada = respuestaRepository.save(existente);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        if (!respuestaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

