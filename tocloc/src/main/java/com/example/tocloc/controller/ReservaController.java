package com.example.tocloc.controller;

import com.example.tocloc.model.Reserva;
import com.example.tocloc.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Criar uma nova reserva
    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva) {
        Reserva novaReserva = reservaService.criarReserva(reserva);
        return ResponseEntity.ok(novaReserva);
    }

    // Cancelar uma reserva
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable int id) {
        try {
            Reserva reservaCancelada = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(reservaCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorna erro 400 em caso de falha
        }
    }

    // Listar todas as reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodasReservas() {
        List<Reserva> reservas = reservaService.listarTodasReservas();
        return ResponseEntity.ok(reservas);
    }

    // Buscar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable int id) {
        Reserva reserva = reservaService.buscarReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    // Atualizar dados de uma reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable int id, @RequestBody Reserva novaReserva) {
        Reserva reservaAtualizada = reservaService.atualizarReserva(id, novaReserva);
        return ResponseEntity.ok(reservaAtualizada);
    }

    // Realizar check-in
    @PutMapping("/{id}/check-in")
    public ResponseEntity<Reserva> realizarCheckIn(@PathVariable int id) {
        try {
            Reserva reservaComCheckIn = reservaService.realizarCheckIn(id);
            return ResponseEntity.ok(reservaComCheckIn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorna erro 400 em caso de falha
        }
    }
}
