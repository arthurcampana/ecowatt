package com.example.demo.service;

import com.example.demo.entity.Consumo;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoService {

    @Autowired
    private ConsumoRepository consumoRepository;

    public Long incluirConsumo(Consumo consumo) {
        if (consumo.getUsuario() == null || consumo.getData() == null || consumo.getValorKWh() == null) {
            return null;
        }
        return consumoRepository.save(consumo).getIdConsumo();
    }

    public Boolean excluirConsumo(Long idConsumo) {
        try {
            consumoRepository.deleteById(idConsumo);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao excluir consumo ID: " + idConsumo + " Erro: " + ex.getLocalizedMessage());
            return false;
        }
    }

    public Optional<Consumo> consultarConsumo(Long idConsumo) {
        return consumoRepository.findById(idConsumo);
    }

    public List<Consumo> listarConsumos() {
        return consumoRepository.findAll();
    }

    public List<Consumo> listarConsumosPorUsuario(Usuario usuario) {
        return consumoRepository.findByUsuario(usuario);
    }

    public List<Consumo> listarConsumosPorUsuarioEntreDatas(Usuario usuario, LocalDate inicio, LocalDate fim) {
        return consumoRepository.findByUsuarioAndDataBetween(usuario, inicio, fim);
    }

    public Boolean atualizarConsumo(Consumo consumo) {
        Optional<Consumo> optionalConsumo = consumoRepository.findById(consumo.getIdConsumo());
        if (optionalConsumo.isPresent()) {
            Consumo c = optionalConsumo.get();
            c.setUsuario(consumo.getUsuario());
            c.setData(consumo.getData());
            c.setValorKWh(consumo.getValorKWh());
            consumoRepository.save(c);
            return true;
        }
        return false;
    }
}
