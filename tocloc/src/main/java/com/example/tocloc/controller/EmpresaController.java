package com.example.tocloc.controller;

import com.example.tocloc.service.EmpresaService;
import com.example.tocloc.model.Empresa;
import com.example.tocloc.model.Host;
import com.example.tocloc.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable int id) {
        return empresaService.findEmpresaById(id)
                .map(empresa -> new ResponseEntity<>(empresa, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.findAllEmpresas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresaDetails) {
        try {
            Empresa updatedEmpresa = empresaService.updateEmpresa(id, empresaDetails);
            return new ResponseEntity<>(updatedEmpresa, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Empresa> toggleStatus(@PathVariable int id) {
        try {
            Empresa empresaAtualizada = empresaService.toggleStatus(id);
            return new ResponseEntity<>(empresaAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}