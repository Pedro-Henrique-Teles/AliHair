package com.alihairapi.api.dto;

import com.alihairapi.model.entity.Agendamento;
import org.modelmapper.ModelMapper;

public class AgendamentoDTO {

    private Long id;
    private Long idCliente;
    private Long idSalao;
    private String diaAgendamento;
    private String horarioAgendamento;

    public static AgendamentoDTO create(Agendamento agendamento){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendamento, AgendamentoDTO.class);
    }


}
