package com.alihairapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    public String cep;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
    private String telefone;
    private Double preco;
    private String Servicos;
    private String cnpj;


}
