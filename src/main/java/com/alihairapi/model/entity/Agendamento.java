package com.alihairapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Salao salao;

    @ManyToOne
    private ServicosDoSalao servicos;

    @Temporal(TemporalType.DATE)
    private LocalDate diaAgendamento;

    @Temporal(TemporalType.TIME)
    private LocalTime horarioAgendamento;
    
}
