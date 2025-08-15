package com.example.foro.alura.controller;

import com.example.foro.alura.dto.TopicoRequest;
import com.example.foro.alura.dto.TopicoResponse;
import com.example.foro.alura.model.Topico;
import com.example.foro.alura.model.Usuario;
import com.example.foro.alura.model.Curso;
import com.example.foro.alura.repository.TopicoRepository;
import com.example.foro.alura.repository.UsuarioRepository;
import com.example.foro.alura.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoRequest request) {
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tópico duplicado");
        }
        Optional<Usuario> autor = usuarioRepository.findById(request.getAutor());
        Optional<Curso> curso = cursoRepository.findById(request.getCurso());
        if (autor.isEmpty() || curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor o curso no existe");
        }
        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor.get());
        topico.setCurso(curso.get());
        Topico guardado = topicoRepository.save(topico);
        TopicoResponse response = new TopicoResponse(
            guardado.getId(), guardado.getTitulo(), guardado.getMensaje(), guardado.getFechaCreacion(),
            guardado.getStatus(), guardado.getAutor().getNombre(), guardado.getCurso().getNombre()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<TopicoResponse> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable,
            @RequestParam(required = false) String nombreCurso,
            @RequestParam(required = false) Integer anio
    ) {
        Page<Topico> page = (nombreCurso != null || anio != null)
                ? topicoRepository.findByCursoAndAnio(nombreCurso, anio, pageable)
                : topicoRepository.findAll(pageable);
        return page.map(t -> new TopicoResponse(
                t.getId(), t.getTitulo(), t.getMensaje(), t.getFechaCreacion(),
                t.getStatus(), t.getAutor().getNombre(), t.getCurso().getNombre()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }
        Topico t = topico.get();
        return ResponseEntity.ok(new TopicoResponse(
                t.getId(), t.getTitulo(), t.getMensaje(), t.getFechaCreacion(),
                t.getStatus(), t.getAutor().getNombre(), t.getCurso().getNombre()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoRequest request) {
        Optional<Topico> topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tópico duplicado");
        }
        Optional<Usuario> autor = usuarioRepository.findById(request.getAutor());
        Optional<Curso> curso = cursoRepository.findById(request.getCurso());
        if (autor.isEmpty() || curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor o curso no existe");
        }
        Topico topico = topicoOpt.get();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor.get());
        topico.setCurso(curso.get());
        Topico actualizado = topicoRepository.save(topico);
        return ResponseEntity.ok(new TopicoResponse(
                actualizado.getId(), actualizado.getTitulo(), actualizado.getMensaje(), actualizado.getFechaCreacion(),
                actualizado.getStatus(), actualizado.getAutor().getNombre(), actualizado.getCurso().getNombre()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
