package com.alihairapi.model.entity;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Temporal(TemporalType.DATE)
    private String diaAgendamento;

    @Temporal(TemporalType.TIME)
    private String horarioAgendamento;

    private String nomeCliente; // Adiciona este campo }

}
