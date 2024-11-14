package com.alihairapi.api.controler;

import com.alihairapi.api.dto.LoginDTO;
import com.alihairapi.model.entity.Cliente;
import com.alihairapi.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/clientes")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody LoginDTO loginDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha());
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
