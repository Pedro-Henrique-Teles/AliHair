package com.alihairapi.model.repository;

import com.alihairapi.model.entity.Salao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaoRepository extends JpaRepository<Salao, Long> {
    boolean existsByCnpj(String cnpj);
    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);
}
