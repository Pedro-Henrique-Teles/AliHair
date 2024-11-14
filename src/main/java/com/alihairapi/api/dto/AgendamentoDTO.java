package com.alihairapi.api.dto;

import com.alihairapi.model.entity.Agendamento;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoDTO {

    private Long id;
    private Long idCliente;
    private Long idSalao;
    private LocalDate diaAgendamento;
    private LocalTime horarioAgendamento;
    private String nomeCliente;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(Long idSalao) {
        this.idSalao = idSalao;
    }

    public LocalDate getDiaAgendamento() {
        return diaAgendamento;
    }

    public void setDiaAgendamento(LocalDate diaAgendamento) {
        this.diaAgendamento = diaAgendamento;
    }

    public LocalTime getHorarioAgendamento() {
        return horarioAgendamento;
    }

    public void setHorarioAgendamento(LocalTime horarioAgendamento) {
        this.horarioAgendamento = horarioAgendamento;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public static AgendamentoDTO create(Agendamento agendamento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendamento, AgendamentoDTO.class);
    }
}
