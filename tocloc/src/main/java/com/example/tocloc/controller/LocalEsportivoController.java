package com.example.tocloc.controller;

import com.example.tocloc.model.LocalEsportivo;
import com.example.tocloc.service.LocalEsportivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locais-esportivos")
public class LocalEsportivoController {

    @Autowired
    private LocalEsportivoService localEsportivoService;

    @GetMapping
    public List<LocalEsportivo> getAllLocaisEsportivos() {
        return localEsportivoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalEsportivo> getLocalEsportivoById(@PathVariable int id) {
        return localEsportivoService.findById(id)
                .map(local -> new ResponseEntity<>(local, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<LocalEsportivo>> getLocaisEsportivosByEmpresaId(@PathVariable int empresaId) {
        List<LocalEsportivo> locais = localEsportivoService.findByEmpresaId(empresaId);
        if (locais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locais, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocalEsportivo> createLocalEsportivo(@RequestBody LocalEsportivo localEsportivo) {
        try {
            LocalEsportivo novoLocal = localEsportivoService.create(localEsportivo);
            return new ResponseEntity<>(novoLocal, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Alguma regra de negócio foi violada
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalEsportivo> updateLocalEsportivo(
            @PathVariable int id, @RequestBody LocalEsportivo localEsportivoDetails) {
        try {
            LocalEsportivo updatedLocal = localEsportivoService.update(id, localEsportivoDetails);
            return new ResponseEntity<>(updatedLocal, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Local não encontrado
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalEsportivo(@PathVariable int id) {
        try {
            localEsportivoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Local não encontrado
        }
    }


    @GetMapping("/locais-empresas-ativo")
    public ResponseEntity<List<LocalEsportivo>> getLocaisAtivos() {
        List<LocalEsportivo> locaisAtivos = localEsportivoService.getLocaisAtivos();
        return ResponseEntity.ok(locaisAtivos);
    }
}
