package com.alihairapi.api.controller;

<<<<<<< HEAD
import com.alihairapi.api.dto.AgendamentoDTO;
import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.service.AgendamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

=======
import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

>>>>>>> 76c29d1ce845987a379eb167688c497540a810a2
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

<<<<<<< HEAD
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
=======
    private final AgendamentoService service;

    @Autowired
    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> getAllAgendamentos() {
        List<Agendamento> agendamentos = service.getAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable Long id) {
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        return agendamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Agendamento>> getAgendamentoByCliente(@PathVariable Long id) {
        // Aqui assume-se que a validação e busca do cliente são feitas dentro do serviço
        Optional<Cliente> cliente = Optional.of(new Cliente()); // Alterar conforme a lógica real para buscar o cliente
        List<Agendamento> agendamentos = service.getAgendamentoByCliente(cliente);
        return ResponseEntity.ok(agendamentos);
    }

    @PostMapping
    public ResponseEntity<Agendamento> createAgendamento(@RequestBody Agendamento agendamento) {
        try {
            Agendamento savedAgendamento = service.salvar(agendamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAgendamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if (agendamento.isPresent()) {
            service.excluir(agendamento.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> updateAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        Optional<Agendamento> existingAgendamento = service.getAgendamentoById(id);
        if (existingAgendamento.isPresent()) {
            agendamento.setId(id);
            Agendamento updatedAgendamento = service.salvar(agendamento);
            return ResponseEntity.ok(updatedAgendamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
>>>>>>> 76c29d1ce845987a379eb167688c497540a810a2
}
