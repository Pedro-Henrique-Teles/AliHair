package com.alihairapi.api.dto;

import com.alihairapi.model.entity.ServicosDoSalao;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class ServicosDoSalaoDTO {
    private Long id;
    private Long idSalao;
    private String Servico;
    private BigDecimal preco;

    public static ServicosDoSalaoDTO create(ServicosDoSalao servicosDoSalao){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(servicosDoSalao, ServicosDoSalaoDTO.class);
    }

}
