package com.example.tocloc.service;

import com.example.tocloc.model.CommonUser;
import com.example.tocloc.dto.CommonUserLoginRequest;
import com.example.tocloc.model.Reserva;
import com.example.tocloc.repository.CommonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommonUserService {

    @Autowired
    private CommonUserRepository commonUserRepository;

    public CommonUser createCommonUser(CommonUser commonUser) throws IllegalArgumentException {
        Optional<CommonUser> existingUser = commonUserRepository.findByCpf(commonUser.getCpf());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("CPF already registered");
        }
        return commonUserRepository.save(commonUser);
    }

    public Optional<CommonUser> findCommonUserById(int id) {
        return commonUserRepository.findById(id);
    }

    public List<CommonUser> findAllCommonUsers() {
        return commonUserRepository.findAll();
    }

    public Optional<CommonUser> authenticate(CommonUserLoginRequest loginRequest) {
        Optional<CommonUser> user = commonUserRepository.findByCpf(loginRequest.getCpf());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return user; // Retorna o objeto completo do usuário
        }
        return Optional.empty(); // Retorna vazio caso a autenticação falhe
    }


    public CommonUser updateCommonUser(int id, String newName, String newEmail) {
        return commonUserRepository.findById(id)
                .map(user -> {
                    user.setName(newName);
                    user.setEmail(newEmail);
                    return commonUserRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public void recoverPassword(int id, String newPassword) {
        commonUserRepository.findById(id)
                .ifPresentOrElse(user -> {
                    user.setPassword(newPassword);
                    commonUserRepository.save(user);
                }, () -> { throw new RuntimeException("User not found!"); });
    }



}
