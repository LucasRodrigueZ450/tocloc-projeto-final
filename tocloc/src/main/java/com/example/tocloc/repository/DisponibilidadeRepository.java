package com.example.tocloc.repository;

import com.example.tocloc.model.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Integer> {

    // Encontrar disponibilidade por Local Esportivo e status (disponível ou não)
    List<Disponibilidade> findByLocalEsportivoIdAndDisponibilidade(int localEsportivoId, boolean disponibilidade);
}
