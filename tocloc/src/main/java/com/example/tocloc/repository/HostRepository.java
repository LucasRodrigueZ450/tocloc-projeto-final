package com.example.tocloc.repository;

import com.example.tocloc.model.Host;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Integer> {
    Optional<Host> findByCpf(String cpf);

    @EntityGraph(attributePaths = {"empresas"})
    Optional<Host> findById(Integer id);
}
