package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracoes")
public class Configuracoes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idConfiguracao;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private String unidade; // kWh ou MWh
    private String tema; // claro, escuro, alto contraste
    private boolean alertaEmail;
    private boolean alertaPush;
    private boolean offlineHistorico;
    private boolean offlineGrafico;

    // Getters e Setters
    public Long getIdConfiguracao() {
        return idConfiguracao;
    }

    public void setIdConfiguracao(Long idConfiguracao) {
        this.idConfiguracao = idConfiguracao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public boolean isAlertaEmail() {
        return alertaEmail;
    }

    public void setAlertaEmail(boolean alertaEmail) {
        this.alertaEmail = alertaEmail;
    }

    public boolean isAlertaPush() {
        return alertaPush;
    }

    public void setAlertaPush(boolean alertaPush) {
        this.alertaPush = alertaPush;
    }

    public boolean isOfflineHistorico() {
        return offlineHistorico;
    }

    public void setOfflineHistorico(boolean offlineHistorico) {
        this.offlineHistorico = offlineHistorico;
    }

    public boolean isOfflineGrafico() {
        return offlineGrafico;
    }

    public void setOfflineGrafico(boolean offlineGrafico) {
        this.offlineGrafico = offlineGrafico;
    }
}
