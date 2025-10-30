package com.example.demo.service;

import com.example.demo.entity.Meta;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    public Long incluirMeta(Meta meta) {
        if (meta.getUsuario() == null || meta.getMes() == null || meta.getValorMeta() == null) {
            return null;
        }
        return metaRepository.save(meta).getIdMeta();
    }

    public Boolean excluirMeta(Long idMeta) {
        try {
            metaRepository.deleteById(idMeta);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao excluir meta ID: " + idMeta + " Erro: " + ex.getLocalizedMessage());
            return false;
        }
    }

    public Optional<Meta> consultarMeta(Long idMeta) {
        return metaRepository.findById(idMeta);
    }

    public List<Meta> listarMetas() {
        return metaRepository.findAll();
    }

    public Optional<Meta> buscarMetaPorUsuarioEMes(Usuario usuario, YearMonth mes) {
        return metaRepository.findByUsuarioAndMes(usuario, mes);
    }

    public Boolean atualizarMeta(Meta meta) {
        Optional<Meta> optionalMeta = metaRepository.findById(meta.getIdMeta());
        if (optionalMeta.isPresent()) {
            Meta m = optionalMeta.get();
            m.setUsuario(meta.getUsuario());
            m.setMes(meta.getMes());
            m.setValorMeta(meta.getValorMeta());
            metaRepository.save(m);
            return true;
        }
        return false;
    }
}
