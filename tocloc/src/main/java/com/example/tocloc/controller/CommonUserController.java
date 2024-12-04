package com.example.tocloc.controller;

import com.example.tocloc.service.CommonUserService;
import com.example.tocloc.model.CommonUser;
import com.example.tocloc.dto.CommonUserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/common-users")
public class CommonUserController {
    @Autowired
    private CommonUserService commonUserService;

    @GetMapping
    public List<CommonUser> getAllCommonUsers() {
        return commonUserService.findAllCommonUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonUser> getCommonUserById(@PathVariable int id) {
        return commonUserService.findCommonUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CommonUser> createCommonUser(@RequestBody CommonUser commonUser) {
        try {
            CommonUser createdUser = commonUserService.createCommonUser(commonUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // CPF já registrado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonUser> updateCommonUser(@PathVariable int id, @RequestBody CommonUser userDetails) {
        try {
            CommonUser updatedUser = commonUserService.updateCommonUser(id, userDetails.getName(), userDetails.getEmail());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody CommonUserLoginRequest loginRequest) {
        System.out.println("CPF recebido: " + loginRequest.getCpf());
        System.out.println("Senha recebida: " + loginRequest.getPassword());

        Optional<CommonUser> user = commonUserService.authenticate(loginRequest);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro de autenticação. Verifique suas credenciais.");
        }
    }


}
