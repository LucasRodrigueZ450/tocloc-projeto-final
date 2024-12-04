package com.example.tocloc.controller;

import com.example.tocloc.model.Disponibilidade;
import com.example.tocloc.service.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    // Obter todas as disponibilidades
    @GetMapping
    public List<Disponibilidade> getAllDisponibilidades() {
        return disponibilidadeService.findAll();
    }

    // Obter disponibilidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidade> getDisponibilidadeById(@PathVariable int id) {
        return disponibilidadeService.findById(id)
                .map(disponibilidade -> new ResponseEntity<>(disponibilidade, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter disponibilidades por Local Esportivo e status (disponível ou não)
    @GetMapping("/local/{localEsportivoId}/disponibilidade/{disponibilidade}")
    public List<Disponibilidade> getDisponibilidadesByLocalAndStatus(
            @PathVariable int localEsportivoId, @PathVariable boolean disponibilidade) {
        return disponibilidadeService.findByLocalEsportivoAndDisponibilidade(localEsportivoId, disponibilidade);
    }

    // Criar nova disponibilidade
    @PostMapping
    public ResponseEntity<Disponibilidade> createDisponibilidade(@RequestBody Disponibilidade disponibilidade) {
        Disponibilidade novaDisponibilidade = disponibilidadeService.create(disponibilidade);
        return new ResponseEntity<>(novaDisponibilidade, HttpStatus.CREATED);
    }

    // Atualizar disponibilidade existente
    @PutMapping("/{id}")
    public ResponseEntity<Disponibilidade> updateDisponibilidade(
            @PathVariable int id, @RequestBody Disponibilidade disponibilidadeDetails) {
        try {
            Disponibilidade updatedDisponibilidade = disponibilidadeService.update(id, disponibilidadeDetails);
            return new ResponseEntity<>(updatedDisponibilidade, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Disponibilidade não encontrada
        }
    }

    // Deletar disponibilidade
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilidade(@PathVariable int id) {
        try {
            disponibilidadeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Disponibilidade não encontrada
        }
    }

    @PutMapping("/disponibilidade-reservada/{id}")
    public ResponseEntity<Disponibilidade> marcarDisponibilidadeReservada(
            @PathVariable int id) {
        try {
            Disponibilidade updatedDisponibilidade = disponibilidadeService.marcarComoReservada(id);
            return new ResponseEntity<>(updatedDisponibilidade, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Disponibilidade não encontrada
        }
    }
}
