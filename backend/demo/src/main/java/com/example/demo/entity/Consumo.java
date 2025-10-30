package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consumos")
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idConsumo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate data; // Dia do registro

    @Column(nullable = false)
    private Double valorKWh; // Consumo do dia em kWh

    public Long getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(Long idConsumo) {
        this.idConsumo = idConsumo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValorKWh() {
        return valorKWh;
    }

    public void setValorKWh(Double valorKWh) {
        this.valorKWh = valorKWh;
    }
}
