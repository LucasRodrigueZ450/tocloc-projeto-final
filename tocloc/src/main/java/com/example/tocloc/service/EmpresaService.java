package com.example.tocloc.service;

import com.example.tocloc.model.Empresa;
import com.example.tocloc.model.Host;
import com.example.tocloc.repository.EmpresaRepository;
import com.example.tocloc.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Optional<Empresa> findEmpresaById(int id) {
        return empresaRepository.findById(id);
    }

    public List<Empresa> findAllEmpresas() {
        return empresaRepository.findAll();
    }



    public Empresa updateEmpresa(int id, Empresa empresaDetails) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresa.setCnpj(empresaDetails.getCnpj());
                    empresa.setCompanyName(empresaDetails.getCompanyName());
                    return empresaRepository.save(empresa);
                }).orElseThrow(() -> new RuntimeException("Empresa not found!"));
    }


    public Empresa toggleStatus(int id) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(id);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.setStatusEmpresa(!empresa.getStatusEmpresa()); // Alterna o status da empresa
            return empresaRepository.save(empresa); // Salva a empresa com o novo status
        } else {
            throw new RuntimeException("Empresa não encontrada"); // Exceção se a empresa não for encontrada
        }
    }
}

