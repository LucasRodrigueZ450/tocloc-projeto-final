package com.example.tocloc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class LocalEsportivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "The name cannot be left blank")
    private String nome;

    @NotBlank(message = "The address cannot be blank")
    private String endereco;

    @NotNull(message = "Capacity cannot be zero")
    private Integer capacidade;

    @NotBlank(message = "The type of sport cannot be blank")
    private String tipoEsporte;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference
    private Empresa empresa;

    // Relacionamento com Disponibilidade
    @OneToMany(mappedBy = "localEsportivo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Disponibilidade> disponibilidades;

    // Relacionamento com Reserva
    @OneToMany(mappedBy = "localEsportivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> reservas;

    // Getters e Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public String getTipoEsporte() { return tipoEsporte; }
    public void setTipoEsporte(String tipoEsporte) { this.tipoEsporte = tipoEsporte; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public List<Disponibilidade> getDisponibilidades() { return disponibilidades; }
    public void setDisponibilidades(List<Disponibilidade> disponibilidades) { this.disponibilidades = disponibilidades; }

    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
}
