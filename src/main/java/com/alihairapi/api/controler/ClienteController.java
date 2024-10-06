package com.alihairapi.api.controler;

import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.api.dto.ClienteDTO;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/clientes")
@RequiredArgsConstructor
@CrossOrigin
public class ClienteController {

    private final ClienteService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Cliente> clientes = service.getCliente();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ClienteDTO dto){
        try {
            Cliente cliente = converter(dto);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()){
            return new ResponseEntity("Cliente Não Encontrado", HttpStatus.NOT_FOUND);
        }try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delet(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





    public Cliente converter(ClienteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        return cliente;
    }
}
