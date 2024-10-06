package com.alihairapi.model.repository;

import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.model.entity.Salao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByCliente(Cliente cliente);
    List<Agendamento> findBySalao(Salao salao);
}
