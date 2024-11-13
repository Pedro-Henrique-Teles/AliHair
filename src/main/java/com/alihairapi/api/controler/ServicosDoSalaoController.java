package com.alihairapi.api.controler;

import com.alihairapi.api.dto.ServicosDoSalaoDTO;
import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.ServicosDoSalao;
import com.alihairapi.service.ServicosDoSalaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ServicosDoSalao")
@RequiredArgsConstructor
@CrossOrigin
public class ServicosDoSalaoController {

    private final ServicosDoSalaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ServicosDoSalao> servicosDoSalao = service.getServicosDoSalao();
        return ResponseEntity.ok(servicosDoSalao.stream().map(ServicosDoSalaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ServicosDoSalao> servicosDoSalao = service.getServicosDoSalaoId(id);
        if (!servicosDoSalao.isPresent()) {
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servicosDoSalao.map(ServicosDoSalaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ServicosDoSalaoDTO dto) {
        try {
            ServicosDoSalao servicosDoSalao = converter(dto);
            servicosDoSalao = service.salvar(servicosDoSalao);
            return new ResponseEntity(servicosDoSalao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ServicosDoSalaoDTO dto) {
        if (!service.getServicosDoSalaoId(id).isPresent()) {
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ServicosDoSalao servicosDoSalao = converter(dto);
            servicosDoSalao.setId(id);
            service.salvar(servicosDoSalao);
            return ResponseEntity.ok(servicosDoSalao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ServicosDoSalao> servicosDoSalao = service.getServicosDoSalaoId(id);
        if (!servicosDoSalao.isPresent()) {
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(servicosDoSalao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ServicosDoSalao converter(ServicosDoSalaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ServicosDoSalao servicosDoSalao = modelMapper.map(dto, ServicosDoSalao.class);
        return servicosDoSalao;
    }
}
