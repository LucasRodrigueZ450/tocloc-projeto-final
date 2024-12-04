package com.example.tocloc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Host extends User {

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Empresa> empresas = new ArrayList<>();

    // Getters e Setters

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    @Override
    public String getRole() {
        return "Host";
    }
}
