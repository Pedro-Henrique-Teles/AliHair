package com.alihairapi.service;

import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Salao;
import com.alihairapi.model.entity.ServicosDoSalao;
import com.alihairapi.model.repository.SalaoRepository;
import com.alihairapi.model.repository.ServicosDoSalaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicosDoSalaoService {
    private ServicosDoSalaoRepository repository;

    public ServicosDoSalaoService(ServicosDoSalaoRepository repository){
        this.repository = repository;
    }

    public List<ServicosDoSalao> getServicosDoSalao(){
        return repository.findAll();
    }

    public Optional<ServicosDoSalao> getServicosDoSalaoId(Long id){
        return repository.findById(id);
    }

    @Transactional
    public ServicosDoSalao salvar(ServicosDoSalao servicosDoSalao){
        validar(servicosDoSalao);
        return repository.save(servicosDoSalao);
    }

    @Transactional
    public void excluir(ServicosDoSalao servicosDoSalao){
        Objects.requireNonNull(servicosDoSalao.getId());
        repository.delete(servicosDoSalao);
    }

    public void validar(ServicosDoSalao servicosDoSalao){
        validarServico(servicosDoSalao.getServico());
        validarPreco(servicosDoSalao.getPreco());
    }

    public void validarServico(String servico){
        if (servico == null || servico.trim().isEmpty()){
            throw new RegraNegocioException("Servico Inválido");
        }
    }

    public void validarPreco(BigDecimal preco){
        if (preco == null){
            throw new RegraNegocioException("O Preço é obrigatorio");
        }
        BigDecimal valorMaximo = new BigDecimal(10000.0);
        if (preco.compareTo(BigDecimal.ZERO) < 0 || preco.compareTo(valorMaximo) > 0){
            throw new RegraNegocioException("O preço deve estar entre 0 e " + valorMaximo);
        }
        if (preco.scale()>2){
            throw new RegraNegocioException("O preço deve conter no máximo duas casas decimais");
        }
    }
}