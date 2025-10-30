package com.example.demo.service;

import com.example.demo.entity.Configuracoes;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ConfiguracoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracoesService {

    @Autowired
    private ConfiguracoesRepository configuracoesRepository;

    public Long incluirConfiguracoes(Configuracoes config) {
        if (config.getUsuario() == null) {
            return null;
        }
        return configuracoesRepository.save(config).getIdConfiguracao();
    }

    public Boolean excluirConfiguracoes(Long idConfig) {
        try {
            configuracoesRepository.deleteById(idConfig);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao excluir configuração ID: " + idConfig + " Erro: " + ex.getLocalizedMessage());
            return false;
        }
    }

    public Optional<Configuracoes> consultarConfiguracoes(Long idConfig) {
        return configuracoesRepository.findById(idConfig);
    }

    public Optional<Configuracoes> consultarConfiguracoesPorUsuario(Usuario usuario) {
        return configuracoesRepository.findByUsuario(usuario);
    }

    public List<Configuracoes> listarConfiguracoes() {
        return configuracoesRepository.findAll();
    }

    public Boolean atualizarConfiguracoes(Configuracoes config) {
        Optional<Configuracoes> optionalConfig = configuracoesRepository.findById(config.getIdConfiguracao());
        if (optionalConfig.isPresent()) {
            Configuracoes c = optionalConfig.get();
            c.setUsuario(config.getUsuario());
            c.setUnidade(config.getUnidade());
            c.setTema(config.getTema());
            c.setAlertaEmail(config.isAlertaEmail());
            c.setAlertaPush(config.isAlertaPush());
            c.setOfflineHistorico(config.isOfflineHistorico());
            c.setOfflineGrafico(config.isOfflineGrafico());
            configuracoesRepository.save(c);
            return true;
        }
        return false;
    }
}
