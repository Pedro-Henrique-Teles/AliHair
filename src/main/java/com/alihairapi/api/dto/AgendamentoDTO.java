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

    public static AgendamentoDTO create(Agendamento agendamento){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendamento, AgendamentoDTO.class);
    }


}
