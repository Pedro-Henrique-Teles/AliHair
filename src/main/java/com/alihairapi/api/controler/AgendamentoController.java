package com.alihairapi.api.controller;

import com.alihairapi.api.dto.AgendamentoDTO;
import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.service.AgendamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            Agendamento agendamento = modelMapper.map(agendamentoDTO, Agendamento.class);
            Agendamento agendamentoSalvo = agendamentoService.salvar(agendamento);
            AgendamentoDTO agendamentoSalvoDTO = modelMapper.map(agendamentoSalvo, AgendamentoDTO.class);
            return ResponseEntity.ok(agendamentoSalvoDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime a stack trace para ajudar na depuração
            return ResponseEntity.status(500).body(null);
        }
    }

    // Outros endpoints como buscar, atualizar, deletar podem ser adicionados aqui
}
