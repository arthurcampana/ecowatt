package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "metas")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMeta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private YearMonth mes; // Mês da meta (ex: 2025-10)

    @Column(nullable = false)
    private Double valorMeta; // Meta em kWh

    public Long getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(Long idMeta) {
        this.idMeta = idMeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public YearMonth getMes() {
        return mes;
    }

    public void setMes(YearMonth mes) {
        this.mes = mes;
    }

    public Double getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(Double valorMeta) {
        this.valorMeta = valorMeta;
    }
}
