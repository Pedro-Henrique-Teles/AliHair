package com.alihairapi.api.controler;

import com.alihairapi.api.dto.AgendamentoDTO;
import com.alihairapi.model.entity.Agendamento;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.service.AgendamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/agendamento")
public class AgendamentoController {

    private final AgendamentoService service;
    private final ModelMapper modelMapper;

    @Autowired
    public AgendamentoController(AgendamentoService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            // Validação de exemplo
            if (agendamentoDTO.getNomeCliente() == null || agendamentoDTO.getNomeCliente().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            Agendamento agendamento = modelMapper.map(agendamentoDTO, Agendamento.class);
            Agendamento agendamentoSalvo = service.salvar(agendamento);
            AgendamentoDTO agendamentoSalvoDTO = modelMapper.map(agendamentoSalvo, AgendamentoDTO.class);
            return ResponseEntity.ok(agendamentoSalvoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
}
