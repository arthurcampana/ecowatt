package com.example.demo.repository;

import com.example.demo.entity.Meta;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.Optional;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    Optional<Meta> findByUsuarioAndMes(Usuario usuario, YearMonth mes);
}
