package com.example.tocloc.service;

import com.example.tocloc.model.Empresa;
import com.example.tocloc.model.Host;
import com.example.tocloc.dto.HostLoginRequest;
import com.example.tocloc.repository.HostRepository;
import com.example.tocloc.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public Host createHost(Host host) throws IllegalArgumentException {
        Optional<Host> existingHost = hostRepository.findByCpf(host.getCpf());
        if (existingHost.isPresent()) {
            throw new IllegalArgumentException("CPF already registered");
        }

        // Se existirem empresas associadas ao host, validá-las
        if (host.getEmpresas() != null) {
            for (Empresa empresa : host.getEmpresas()) {
                Optional<Empresa> existingEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());
                if (existingEmpresa.isPresent()) {
                    throw new IllegalArgumentException("CNPJ already registered");
                }
                empresa.setHost(host); // Associando a empresa ao host
            }
        }

        return hostRepository.save(host);
    }

    public Optional<Host> findHostById(int id) {
        return hostRepository.findById(id);
    }

    public List<Host> findAllHosts() {
        return hostRepository.findAll();
    }

  //  public boolean authenticate(HostLoginRequest loginRequest) {
   //     Optional<Host> host = hostRepository.findByCpf(loginRequest.getCpf());
    //    return host.isPresent() && host.get().getPassword().equals(loginRequest.getPassword());
  //  }
    public Optional<Host> authenticate(HostLoginRequest loginRequest) {
        Optional<Host> host = hostRepository.findByCpf(loginRequest.getCpf());
        if (host.isPresent() && host.get().getPassword().equals(loginRequest.getPassword())) {
            return host; // Retorna o objeto completo do Host em caso de sucesso
        }
        return Optional.empty(); // Retorna vazio caso a autenticação falhe
    }

    public Host updateHost(int id, String newName, String newEmail, List<Empresa> newEmpresas) {
        return hostRepository.findById(id)
                .map(host -> {
                    host.setName(newName);
                    host.setEmail(newEmail);

                    if (newEmpresas != null) {
                        for (Empresa empresa : newEmpresas) {
                            Optional<Empresa> existingEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());
                            if (existingEmpresa.isPresent() && existingEmpresa.get().getId() != empresa.getId()) {
                                throw new IllegalArgumentException("CNPJ already registered");
                            }
                            empresa.setHost(host);
                        }
                        host.setEmpresas(newEmpresas);
                    }

                    return hostRepository.save(host);
                }).orElseThrow(() -> new RuntimeException("Host not found!"));
    }


    public Host addEmpresaToHost(int hostId, Empresa newEmpresa) {
        return hostRepository.findById(hostId)
                .map(host -> {
                    // Verificar se o CNPJ já está registrado
                    Optional<Empresa> existingEmpresa = empresaRepository.findByCnpj(newEmpresa.getCnpj());
                    if (existingEmpresa.isPresent()) {
                        throw new IllegalArgumentException("CNPJ already registered");
                    }

                    // Garantir que o status da nova empresa esteja definido
                    if (newEmpresa.getStatusEmpresa() == null) {
                        newEmpresa.setStatusEmpresa(true); // Define como "Ativa" por padrão
                    }

                    // Associar a empresa ao host
                    newEmpresa.setHost(host);
                    host.getEmpresas().add(newEmpresa);

                    // Salvar o host atualizado
                    return hostRepository.save(host);
                }).orElseThrow(() -> new RuntimeException("Host not found"));
    }


    public void recoverPassword(int id, String newPassword) {
        hostRepository.findById(id)
                .ifPresentOrElse(host -> {
                    host.setPassword(newPassword);
                    hostRepository.save(host);
                }, () -> { throw new RuntimeException("Host not found!"); });
    }
}
