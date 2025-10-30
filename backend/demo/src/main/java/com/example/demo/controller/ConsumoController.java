package com.example.demo.controller;

import com.example.demo.entity.Consumo;
import com.example.demo.entity.Usuario;
import com.example.demo.service.ConsumoService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/consumo")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Long> incluirConsumo(@RequestBody Consumo consumo) {
        Long id = consumoService.incluirConsumo(consumo);
        if (id != null) return new ResponseEntity<>(id, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConsumo(@PathVariable Long id) {
        if (consumoService.excluirConsumo(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumo> consultarConsumo(@PathVariable Long id) {
        Optional<Consumo> consumo = consumoService.consultarConsumo(id);
        return consumo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Consumo>> listarConsumosPorUsuario(@PathVariable Long idUsuario) {
        Optional<Usuario> usuario = usuarioService.consultarUsuario(idUsuario);
        if (usuario.isPresent()) {
            List<Consumo> consumos = consumoService.listarConsumosPorUsuario(usuario.get());
            return new ResponseEntity<>(consumos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Void> atualizarConsumo(@RequestBody Consumo consumo) {
        if (consumoService.atualizarConsumo(consumo)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
