package com.example.demo.controller;

import com.example.demo.entity.Meta;
import com.example.demo.entity.Usuario;
import com.example.demo.service.MetaService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Long> incluirMeta(@RequestBody Meta meta) {
        Long id = metaService.incluirMeta(meta);
        if (id != null) return new ResponseEntity<>(id, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMeta(@PathVariable Long id) {
        if (metaService.excluirMeta(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> consultarMeta(@PathVariable Long id) {
        Optional<Meta> meta = metaService.consultarMeta(id);
        return meta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{idUsuario}/mes/{anoMes}")
    public ResponseEntity<Meta> consultarMetaPorUsuarioEMes(@PathVariable Long idUsuario, @PathVariable String anoMes) {
        Optional<Usuario> usuario = usuarioService.consultarUsuario(idUsuario);
        if (usuario.isPresent()) {
            YearMonth mes = YearMonth.parse(anoMes); // formato "2025-10"
            Optional<Meta> meta = metaService.buscarMetaPorUsuarioEMes(usuario.get(), mes);
            return meta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Void> atualizarMeta(@RequestBody Meta meta) {
        if (metaService.atualizarMeta(meta)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Meta>> listarMetas() {
        List<Meta> metas = metaService.listarMetas();
        return new ResponseEntity<>(metas, HttpStatus.OK);
    }
}
