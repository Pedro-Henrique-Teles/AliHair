package com.alihairapi.service;

import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.model.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public List<Cliente> getCliente(){
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente){
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente){

    }
}
