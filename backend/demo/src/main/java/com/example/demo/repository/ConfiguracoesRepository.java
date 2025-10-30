package com.example.demo.repository;

import com.example.demo.entity.Configuracoes;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfiguracoesRepository extends JpaRepository<Configuracoes, Long> {
    Optional<Configuracoes> findByUsuario(Usuario usuario);
}
