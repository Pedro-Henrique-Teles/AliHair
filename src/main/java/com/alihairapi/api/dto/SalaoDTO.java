package com.alihairapi.api.dto;

import com.alihairapi.model.entity.Salao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaoDTO {

    private Long id;
    private String nome;
    public String cep;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
    private String telefone;
    private Double preco;
    private String cnpj;
    private String email;

    public static SalaoDTO create(Salao salao){
        ModelMapper modelMapper = new ModelMapper();
        SalaoDTO dto = modelMapper.map(salao, SalaoDTO.class);
        return dto;
    }
}
