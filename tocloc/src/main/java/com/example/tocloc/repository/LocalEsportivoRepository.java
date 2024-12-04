package com.example.tocloc.repository;

import com.example.tocloc.model.LocalEsportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalEsportivoRepository extends JpaRepository<LocalEsportivo, Integer> {

    List<LocalEsportivo> findByEmpresaId(int empresaId);
}
