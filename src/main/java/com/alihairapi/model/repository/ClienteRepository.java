package com.alihairapi.model.repository;

import com.alihairapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);

}
