package com.alihairapi.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Salao;
import com.alihairapi.model.repository.SalaoRepository;
import com.alihairapi.model.repository.ServicosDoSalaoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@Service
public class SalaoService {
    private SalaoRepository repository;
    private final ServicosDoSalaoRepository servicosDoSalaoRepository;

    public SalaoService(SalaoRepository repository,
                        ServicosDoSalaoRepository servicosDoSalaoRepository) {
        this.repository = repository;
        this.servicosDoSalaoRepository = servicosDoSalaoRepository;
    }

    public List<Salao> getSalao() {
        return repository.findAll();
    }

    public Optional<Salao> getSalaobyId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Salao salvar(Salao salao) {
        validar(salao);
        return repository.save(salao);
    }

    @Transactional
    public void excluir(Salao salao) {
        Objects.requireNonNull(salao.getId());
        repository.delete(salao);
    }

    public void validar(Salao salao) {
        validarNome(salao.getNome());
        validarEmail(salao.getEmail());
        validarCep(salao.getCep());
        validarTelefone(salao.getTelefone());
        validarCnpj(salao.getCnpj());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (nome.length() < 10) {
            throw new RegraNegocioException("Nome muito curto");
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

    public void validarCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new RegraNegocioException("CEP não pode ser nulo ou vazio");
        }
        if (!cep.matches("^\\d{5}-\\d{3}$")) {
            throw new RegraNegocioException("Formato de CEP inválido. Use o formato 00000-000");
        }

        // Consulta o ViaCEP
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RegraNegocioException("Erro ao consultar o serviço ViaCEP");
            }

            // Lê a resposta
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Verifica se o CEP é inexistente (o ViaCEP retorna um campo {"erro": true} quando o CEP não é encontrado)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.toString());
            if (jsonResponse.has("erro") && jsonResponse.get("erro").asBoolean()) {
                throw new RegraNegocioException("CEP inexistente");
            }
        } catch (IOException e) {
            throw new RegraNegocioException("Erro ao validar o CEP: " + e.getMessage());
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
            throw new RegraNegocioException("Este telefone já foi registrado");
        }
    }

    public void validarCnpj(String cnpj){
        //Limpando " . " e " / " do cnpj
        cnpj = cnpj.replaceAll("[^0-9]", "");
        CNPJValidator cnpjValidator = new CNPJValidator();
        try {
            cnpjValidator.assertValid(cnpj);
        }catch (InvalidStateException e){
            throw new RegraNegocioException("CNPJ inválido");
        }
        if (repository.existsByCnpj(cnpj)){
            throw new RegraNegocioException("Este CNPJ já foi registrado");
        }
    }

}
