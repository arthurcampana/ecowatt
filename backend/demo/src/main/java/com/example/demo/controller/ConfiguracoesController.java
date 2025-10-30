package com.example.demo.controller;

import com.example.demo.entity.Configuracoes;
import com.example.demo.entity.Usuario;
import com.example.demo.service.ConfiguracoesService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

    @Autowired
    private ConfiguracoesService configuracoesService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Long> incluirConfiguracoes(@RequestBody Configuracoes config) {
        Long id = configuracoesService.incluirConfiguracoes(config);
        if (id != null) return new ResponseEntity<>(id, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConfiguracoes(@PathVariable Long id) {
        if (configuracoesService.excluirConfiguracoes(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Configuracoes> consultarConfiguracoes(@PathVariable Long id) {
        Optional<Configuracoes> config = configuracoesService.consultarConfiguracoes(id);
        return config.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Configuracoes> consultarConfiguracoesPorUsuario(@PathVariable Long idUsuario) {
        Optional<Usuario> usuario = usuarioService.consultarUsuario(idUsuario);
        if (usuario.isPresent()) {
            Optional<Configuracoes> config = configuracoesService.consultarConfiguracoesPorUsuario(usuario.get());
            return config.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Void> atualizarConfiguracoes(@RequestBody Configuracoes config) {
        if (configuracoesService.atualizarConfiguracoes(config)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Configuracoes>> listarConfiguracoes() {
        List<Configuracoes> configs = configuracoesService.listarConfiguracoes();
        return new ResponseEntity<>(configs, HttpStatus.OK);
    }
}
