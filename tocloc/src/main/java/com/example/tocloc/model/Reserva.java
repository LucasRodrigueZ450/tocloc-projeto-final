package com.example.tocloc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dataHora;

    private boolean statusAtiva; // true = ativa, false = cancelada

    private boolean checkInRealizado; // true = check-in concluído, false = pendente

    private LocalDateTime dataCheckIn;

    @ManyToOne
    @JoinColumn(name = "usuario_comum_id", nullable = false)
    @JsonBackReference
    private CommonUser usuarioComum;

    @ManyToOne
    @JoinColumn(name = "local_esportivo_id", nullable = false)
    private LocalEsportivo localEsportivo;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isStatusAtiva() {
        return statusAtiva;
    }

    public void setStatusAtiva(boolean statusAtiva) {
        this.statusAtiva = statusAtiva;
    }

    public boolean isCheckInRealizado() {
        return checkInRealizado;
    }

    public void setCheckInRealizado(boolean checkInRealizado) {
        this.checkInRealizado = checkInRealizado;
    }

    public LocalDateTime getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDateTime dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public CommonUser getUsuarioComum() {
        return usuarioComum;
    }

    public void setUsuarioComum(CommonUser usuarioComum) {
        this.usuarioComum = usuarioComum;
    }

    public LocalEsportivo getLocalEsportivo() {
        return localEsportivo;
    }

    public void setLocalEsportivo(LocalEsportivo localEsportivo) {
        this.localEsportivo = localEsportivo;
    }

    // Construtor padrão
    public Reserva() {
    }

    // Construtor completo
    public Reserva(LocalDateTime dataHora, boolean statusAtiva, CommonUser usuarioComum, LocalEsportivo localEsportivo) {
        this.dataHora = dataHora;
        this.statusAtiva = statusAtiva;
        this.usuarioComum = usuarioComum;
        this.localEsportivo = localEsportivo;
        this.checkInRealizado = false; // Inicialmente, check-in não realizado
    }
}
