package com.exemplo.facade;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagamentoTest {

    private static final String CARTAO_VALIDO = "1234567812345678";

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento(500.0);
    }

    @Test
    void construtorDeveLancarExcecaoQuandoLimiteForNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Pagamento(-1.0));
    }

    @Test
    void processarDeveRecusarQuandoCartaoForNulo() {
        assertFalse(pagamento.processar(null, 100.0));
    }

    @Test
    void processarDeveRecusarQuandoCartaoNaoTiverDezesseisDigitos() {
        assertFalse(pagamento.processar("1234", 100.0));
    }

    @Test
    void processarDeveRecusarQuandoValorNaoForPositivo() {
        assertFalse(pagamento.processar(CARTAO_VALIDO, 0.0));
    }

    @Test
    void processarDeveRecusarQuandoValorUltrapassarLimite() {
        assertFalse(pagamento.processar(CARTAO_VALIDO, 500.01));
    }

    @Test
    void processarDeveAprovarQuandoValorEstiverDentroDoLimite() {
        assertTrue(pagamento.processar(CARTAO_VALIDO, 500.0));
    }
}
