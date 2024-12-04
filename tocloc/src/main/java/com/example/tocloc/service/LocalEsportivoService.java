package com.example.tocloc.service;

import com.example.tocloc.model.Empresa;
import com.example.tocloc.model.LocalEsportivo;
import com.example.tocloc.repository.EmpresaRepository;
import com.example.tocloc.repository.LocalEsportivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocalEsportivoService {

    @Autowired
    private LocalEsportivoRepository localEsportivoRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    public LocalEsportivo create(LocalEsportivo localEsportivo) {
        return localEsportivoRepository.save(localEsportivo);
    }

    public List<LocalEsportivo> findAll() {
        return localEsportivoRepository.findAll();
    }

    public Optional<LocalEsportivo> findById(int id) {
        return localEsportivoRepository.findById(id);
    }

    public List<LocalEsportivo> findByEmpresaId(int empresaId) {
        return localEsportivoRepository.findByEmpresaId(empresaId);
    }

    public LocalEsportivo update(int id, LocalEsportivo updatedLocalEsportivo) {
        return localEsportivoRepository.findById(id).map(local -> {
            local.setNome(updatedLocalEsportivo.getNome());
            local.setEndereco(updatedLocalEsportivo.getEndereco());
            local.setCapacidade(updatedLocalEsportivo.getCapacidade());
            local.setTipoEsporte(updatedLocalEsportivo.getTipoEsporte());
            local.setEmpresa(updatedLocalEsportivo.getEmpresa());
            return localEsportivoRepository.save(local);
        }).orElseThrow(() -> new RuntimeException("Local Esportivo não encontrado!"));
    }

    public void delete(int id) {
        localEsportivoRepository.deleteById(id);
    }


    public List<LocalEsportivo> getLocaisAtivos() {
        List<LocalEsportivo> locaisAtivos = new ArrayList<>();

        // Buscando todos os locais esportivos
        List<LocalEsportivo> locais = localEsportivoRepository.findAll();

        for (LocalEsportivo local : locais) {
            // Buscando a empresa associada ao local
            Empresa empresa = empresaRepository.findById(local.getEmpresa().getId()).orElse(null);
            if (empresa != null && empresa.getStatusEmpresa()) { // Verifica se a empresa está ativa
                locaisAtivos.add(local); // Se a empresa está ativa, adiciona o local
            }
        }

        return locaisAtivos;
    }
}
