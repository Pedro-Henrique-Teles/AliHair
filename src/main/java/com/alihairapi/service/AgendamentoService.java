package com.alihairapi.service;

import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.model.entity.Salao;
import com.alihairapi.model.repository.AgendamentoRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AgendamentoService {

    private AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public List<Agendamento> getAgendamentos() {
        return repository.findAll();
    }

    public Optional<Agendamento> getAgendamentoById(Long id) {
        return repository.findById(id);
    }

    public List<Agendamento> getAgendamentoByCliente(Optional<Cliente> cliente) {
        return repository.findByCliente(cliente);
    }

    @Transactional
    public Agendamento salvar(Agendamento agendamento) {
        validar(agendamento);
        return repository.save(agendamento);
    }

    @Transactional
    public void excluir(Agendamento agendamento) {
        Objects.requireNonNull(agendamento.getId());
        repository.delete(agendamento);
    }

    public void validar(Agendamento agendamento) {
        validarCliente(agendamento.getCliente());
        validarSalao(agendamento.getSalao());
        validarDiaAgendamento(agendamento.getDiaAgendamento());
        validarHorarioAgendamento(agendamento.getHorarioAgendamento());
    }

    public void validarCliente(Cliente cliente) {
        if (cliente == null ||
                cliente.getId() == null ||
                cliente.getId() == 0) {
            throw new RegraNegocioException("Cliente Inválido");
        }
    }

    public void validarSalao(Salao salao) {
        if (salao == null ||
                salao.getId() == null ||
                salao.getId() == 0) {
            throw new RegraNegocioException("Salão Inválido");
        }
    }

    public void validarDiaAgendamento(LocalDate diaAgendamento) {
        if (diaAgendamento == null) {
            throw new RegraNegocioException("O dia do agendamento não pode ser nulo.");
        }

        if (diaAgendamento.isBefore(LocalDate.now())) {
            throw new RegraNegocioException("O dia do agendamento não pode ser no passado.");
        }
    }

    public void validarHorarioAgendamento(LocalTime horarioAgendamento) {
        if (horarioAgendamento == null) {
            throw new RegraNegocioException("O horário de agendamento não pode ser nulo.");
        }

        if (horarioAgendamento.isBefore(LocalTime.of(8, 0)) || horarioAgendamento.isAfter(LocalTime.of(18, 0))) {
            throw new RegraNegocioException("O horário de agendamento deve estar entre 8:00 e 18:00.");
        }
    }

}
