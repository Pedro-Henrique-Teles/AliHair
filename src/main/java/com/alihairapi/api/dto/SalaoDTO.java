package com.alihairapi.api.dto;

import com.alihairapi.model.entity.Salao;
import org.modelmapper.ModelMapper;

public class SalaoDTO {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String avaliacao;
    private Double preco;
    private String Servicos;
    private String cnpj;

    public static SalaoDTO create(Salao salao){
        ModelMapper modelMapper = new ModelMapper();
        SalaoDTO dto = modelMapper.map(salao, SalaoDTO.class);
        return dto;
    }
}
