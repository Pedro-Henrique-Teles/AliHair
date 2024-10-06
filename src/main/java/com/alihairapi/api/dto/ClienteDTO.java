package com.alihairapi.api.dto;

import com.alihairapi.model.entity.Cliente;
import org.modelmapper.ModelMapper;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;


    public static ClienteDTO create(Cliente cliente){
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        return dto;
    }
}
