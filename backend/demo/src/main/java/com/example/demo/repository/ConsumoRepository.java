package com.example.demo.repository;

import com.example.demo.entity.Consumo;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    List<Consumo> findByUsuario(Usuario usuario);
    List<Consumo> findByUsuarioAndDataBetween(Usuario usuario, LocalDate inicio, LocalDate fim);
}
