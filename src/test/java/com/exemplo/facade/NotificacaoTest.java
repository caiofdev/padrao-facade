package com.exemplo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificacaoTest {

    private Notificacao notificacao;

    @BeforeEach
    void setUp() {
        notificacao = new Notificacao();
    }

    @Test
    void enviarDeveLancarExcecaoQuandoEmailForNulo() {
        assertThrows(IllegalArgumentException.class, () -> notificacao.enviar(null, "mensagem"));
    }

    @Test
    void enviarDeveLancarExcecaoQuandoEmailNaoContiverArroba() {
        assertThrows(IllegalArgumentException.class, () -> notificacao.enviar("cliente.com", "mensagem"));
    }

    @Test
    void enviarDeveRegistrarMensagemQuandoEmailForValido() {
        notificacao.enviar("cliente@email.com", "Olá");

        assertEquals(List.of("cliente@email.com: Olá"), notificacao.getEnviadas());
    }

    @Test
    void getEnviadasDeveRetornarVazioQuandoNadaForEnviado() {
        assertTrue(notificacao.getEnviadas().isEmpty());
    }

    @Test
    void getEnviadasDeveRetornarListaImutavel() {
        List<String> enviadas = notificacao.getEnviadas();

        assertThrows(UnsupportedOperationException.class, () -> enviadas.add("x"));
    }
}
