package com.example.tocloc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Date and time cannot be null")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "local_esportivo_id", nullable = false)
    @JsonBackReference
    private LocalEsportivo localEsportivo;

    @NotNull(message = "Availability status cannot be null")
    private boolean disponibilidade; // true = disponível, false = não disponível

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

    public LocalEsportivo getLocalEsportivo() {
        return localEsportivo;
    }

    public void setLocalEsportivo(LocalEsportivo localEsportivo) {
        this.localEsportivo = localEsportivo;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
