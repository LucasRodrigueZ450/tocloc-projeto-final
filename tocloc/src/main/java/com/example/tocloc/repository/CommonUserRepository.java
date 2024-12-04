package com.example.tocloc.repository;

import com.example.tocloc.model.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonUserRepository extends JpaRepository<CommonUser, Integer> {
    Optional<CommonUser> findByCpf(String cpf);
}
