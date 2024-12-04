package com.example.tocloc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "CNPJ cannot be blank")
    @Column(unique = true)
    private String cnpj;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    @JsonBackReference
    private Host host;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LocalEsportivo> locaisEsportivos = new ArrayList<>();


    @Column(nullable = false)
    private Boolean statusEmpresa ;

// Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
    public List<LocalEsportivo> getLocaisEsportivos() {return locaisEsportivos;
    }

    public void setLocaisEsportivos(List<LocalEsportivo> locaisEsportivos) {this.locaisEsportivos = locaisEsportivos;
    }

    public Boolean getStatusEmpresa() {return statusEmpresa;}

    public void setStatusEmpresa(Boolean statusEmpresa) {this.statusEmpresa = statusEmpresa;}
}
