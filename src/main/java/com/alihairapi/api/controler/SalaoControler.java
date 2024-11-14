package com.alihairapi.api.controler;

import com.alihairapi.api.dto.SalaoDTO;
import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.model.entity.Salao;
import com.alihairapi.service.SalaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/salao")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SalaoControler {

    private final SalaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Salao> saloes = service.getSalao();
        return ResponseEntity.ok(saloes.stream().map(SalaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Salao> salao = service.getSalaobyId(id);
        if (!salao.isPresent()) {
            return new ResponseEntity("Salão não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(salao.map(SalaoDTO::create));
    }

    @PostMapping
      public ResponseEntity post(@RequestBody SalaoDTO dto){
        try {
            Salao salao = converter(dto);
            salao = service.salvar(salao);
            return new ResponseEntity(salao, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody SalaoDTO dto) {
        if (!service.getSalaobyId(id).isPresent()) {
            return new ResponseEntity("Salão Não Encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Salao salao = converter(dto);
            salao.setId(id);
            service.salvar(salao);
            return ResponseEntity.ok(salao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        Optional<Salao> salao = service.getSalaobyId(id);
        if (!salao.isPresent()){
            return new ResponseEntity("Salão não encontrado", HttpStatus.NOT_FOUND);
        }try {
            service.excluir(salao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Salao converter(SalaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Salao salao = modelMapper.map(dto, Salao.class);
        return salao;
    }
}
