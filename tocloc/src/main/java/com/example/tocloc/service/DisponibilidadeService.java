package com.example.tocloc.service;

import com.example.tocloc.model.Disponibilidade;
import com.example.tocloc.repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadeService {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public Disponibilidade create(Disponibilidade disponibilidade) {
        return disponibilidadeRepository.save(disponibilidade);
    }

    public List<Disponibilidade> findAll() {
        return disponibilidadeRepository.findAll();
    }

    public Optional<Disponibilidade> findById(int id) {
        return disponibilidadeRepository.findById(id);
    }

    public List<Disponibilidade> findByLocalEsportivoAndDisponibilidade(int localEsportivoId, boolean disponibilidade) {
        return disponibilidadeRepository.findByLocalEsportivoIdAndDisponibilidade(localEsportivoId, disponibilidade);
    }

    public Disponibilidade update(int id, Disponibilidade disponibilidadeDetails) {
        return disponibilidadeRepository.findById(id).map(disponibilidade -> {
            disponibilidade.setDataHora(disponibilidadeDetails.getDataHora());
            disponibilidade.setDisponibilidade(disponibilidadeDetails.isDisponibilidade());
            disponibilidade.setLocalEsportivo(disponibilidadeDetails.getLocalEsportivo());
            return disponibilidadeRepository.save(disponibilidade);
        }).orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada!"));
    }

    public void delete(int id) {
        disponibilidadeRepository.deleteById(id);
    }

    public Disponibilidade marcarComoReservada(int id) {
        return disponibilidadeRepository.findById(id).map(disponibilidade -> {
            disponibilidade.setDisponibilidade(false); // Marca como reservada (indisponível)
            return disponibilidadeRepository.save(disponibilidade);
        }).orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada!"));
    }
}
