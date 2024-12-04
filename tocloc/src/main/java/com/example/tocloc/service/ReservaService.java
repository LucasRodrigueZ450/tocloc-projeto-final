package com.example.tocloc.service;

import com.example.tocloc.model.Reserva;
import com.example.tocloc.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Criar uma nova reserva
    public Reserva criarReserva(Reserva reserva) {
        reserva.setStatusAtiva(true); // Define a reserva como ativa inicialmente
        return reservaRepository.save(reserva);
    }

    // Cancelar uma reserva
    public Reserva cancelarReserva(int id) {
        Reserva reserva = buscarReservaPorId(id);
        reserva.setStatusAtiva(false); // Define a reserva como inativa
        return reservaRepository.save(reserva);
    }

    // Listar todas as reservas
    public List<Reserva> listarTodasReservas() {
        return reservaRepository.findAll();
    }

    // Buscar reserva por ID
    public Reserva buscarReservaPorId(int id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
    }

    // Atualizar uma reserva
    public Reserva atualizarReserva(int id, Reserva novaReserva) {
        Reserva reservaExistente = buscarReservaPorId(id);
        reservaExistente.setDataHora(novaReserva.getDataHora());
        reservaExistente.setLocalEsportivo(novaReserva.getLocalEsportivo());
        reservaExistente.setUsuarioComum(novaReserva.getUsuarioComum());
        return reservaRepository.save(reservaExistente);
    }

    // Realizar check-in
    public Reserva realizarCheckIn(int id) {
        Reserva reserva = buscarReservaPorId(id);
        if (!reserva.isStatusAtiva()) {
            throw new RuntimeException("Não é possível realizar check-in em uma reserva cancelada.");
        }
        if (reserva.isCheckInRealizado()) {
            throw new RuntimeException("Check-in já foi realizado para esta reserva.");
        }

        reserva.setCheckInRealizado(true);
        reserva.setStatusAtiva(false);
        reserva.setDataCheckIn(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }
}
