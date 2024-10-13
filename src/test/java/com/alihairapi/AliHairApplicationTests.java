package com.alihairapi;

import com.alihairapi.exception.RegraNegocioException;
import com.alihairapi.service.SalaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AliHairApplicationTests {

    @Autowired
    private SalaoService cepService;

    @Test
    void deveValidarCepValido() {
        // Exemplo de CEP válido
        String cepValido = "01001-000";

        // Não deve lançar exceção
        cepService.validarCep(cepValido);
    }

    @Test
    void deveLancarExcecaoParaCepInvalido() {
        // Exemplo de CEP inexistente
        String cepInvalido = "00000-000";

        // Deve lançar RegraNegocioException para um CEP inexistente
        assertThrows(RegraNegocioException.class, () -> cepService.validarCep(cepInvalido));
    }

    @Test
    void deveLancarExcecaoParaFormatoInvalido() {
        // Exemplo de CEP com formato incorreto
        String cepFormatoInvalido = "1234-567";

        // Deve lançar RegraNegocioException para formato inválido
        assertThrows(RegraNegocioException.class, () -> cepService.validarCep(cepFormatoInvalido));
    }

    @Test
    void deveLancarExcecaoParaCepNuloOuVazio() {
        // Testando CEP nulo
        assertThrows(RegraNegocioException.class, () -> cepService.validarCep(null));

        // Testando CEP vazio
        assertThrows(RegraNegocioException.class, () -> cepService.validarCep(""));
    }
}
