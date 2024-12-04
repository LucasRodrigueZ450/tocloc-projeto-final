package com.example.tocloc.controller;

import com.example.tocloc.model.Empresa;
import com.example.tocloc.service.HostService;
import com.example.tocloc.model.Host;
import com.example.tocloc.dto.HostLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hosts")
public class HostController {
    @Autowired
    private HostService hostService;

    @GetMapping
    public List<Host> getAllHosts() {
        return hostService.findAllHosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Host> getHostById(@PathVariable int id) {
        return hostService.findHostById(id)
                .map(host -> new ResponseEntity<>(host, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Host> createHost(@RequestBody Host host) {
        try {
            Host createdHost = hostService.createHost(host);
            return new ResponseEntity<>(createdHost, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // CPF já registrado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Host> updateHost(@PathVariable int id, @RequestBody Host hostDetails) {
        try {
            Host updatedHost = hostService.updateHost(
                    id,
                    hostDetails.getName(),
                    hostDetails.getEmail(),
                    hostDetails.getEmpresas()
            );
            return new ResponseEntity<>(updatedHost, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{hostId}/empresas")
    public ResponseEntity<Host> addEmpresaToHost(@PathVariable int hostId, @RequestBody Empresa empresa) {
        try {
            Host updatedHost = hostService.addEmpresaToHost(hostId, empresa);
            return new ResponseEntity<>(updatedHost, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // CNPJ já registrado
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Host não encontrado
        }
    }


   // @PostMapping("/authenticate")
   // public ResponseEntity<Void> authenticateHost(@RequestBody HostLoginRequest loginRequest) {
  //      boolean isAuthenticated = hostService.authenticate(loginRequest);
   //     return new ResponseEntity<>(isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
   // }
   @PostMapping("/authenticate")
   public ResponseEntity<?> authenticateHost(@RequestBody HostLoginRequest loginRequest) {
       Optional<Host> host = hostService.authenticate(loginRequest);
       if (host.isPresent()) {
           // Retorna os dados completos do Host em caso de sucesso
           return ResponseEntity.ok(host.get()); // Retorna o objeto do Host
       } else {
           // Caso de falha na autenticação
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos.");
       }
   }
}
