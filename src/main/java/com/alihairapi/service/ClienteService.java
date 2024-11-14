package com.alihairapi.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
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

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getCliente() {
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente) {
        validarNome(cliente.getNome());
        validarTelefone(cliente.getTelefone());
        validarEmail(cliente.getEmail());
        validarCpf(cliente.getCpf());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (nome.length() < 10) {
            throw new RegraNegocioException("Nome muito curto");
        }
    }

    public void validarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new RegraNegocioException("O telefone, não pode estar vazio");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new RegraNegocioException("O telefone não pode estar vazio");
        } else {
            String telefoneLimpo = telefone.replaceAll("([^\\d])", "");
            if (telefoneLimpo.length() != 11) {
                throw new RegraNegocioException("O telefone deve ter ao menos 11 digitos");
            }
        }
        if (repository.existsByTelefone(telefone)){
            throw new RegraNegocioException("Esse telefone já existe");
        }
    }

    public void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RegraNegocioException("E-mail inválido");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RegraNegocioException("Formato de e-mail inválido");
        }
        if (repository.existsByEmail(email)){
            throw new RegraNegocioException("Esse email já está registrado");
        }
    }

    private void validarCpf(String cpf) {

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new RegraNegocioException("CPF não pode estar vazio");
        }

        //Validação com (Stella Caelum) -> Stella Core
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (repository.existsByCpf(cpf)){
            throw new RegraNegocioException("Esse CPF já está registrado");
        }
    }
}