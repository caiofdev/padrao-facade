package com.exemplo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstoqueTest {

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
    }

    @Test
    void adicionarDeveLancarExcecaoQuandoCodigoForNulo() {
        assertThrows(IllegalArgumentException.class, () -> estoque.adicionar(null, 1));
    }

    @Test
    void adicionarDeveLancarExcecaoQuandoCodigoForEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> estoque.adicionar("  ", 1));
    }

    @Test
    void adicionarDeveLancarExcecaoQuandoQuantidadeNaoForPositiva() {
        assertThrows(IllegalArgumentException.class, () -> estoque.adicionar("P1", 0));
    }

    @Test
    void adicionarDeveSomarQuantidadeAoProdutoExistente() {
        estoque.adicionar("P1", 3);
        estoque.adicionar("P1", 2);

        assertEquals(5, estoque.consultar("P1"));
    }

    @Test
    void consultarDeveRetornarZeroQuandoProdutoNaoExistir() {
        assertEquals(0, estoque.consultar("inexistente"));
    }

    @Test
    void possuiDisponivelDeveRetornarFalsoQuandoQuantidadeNaoForPositiva() {
        estoque.adicionar("P1", 5);

        assertFalse(estoque.possuiDisponivel("P1", 0));
    }

    @Test
    void possuiDisponivelDeveRetornarFalsoQuandoEstoqueForInsuficiente() {
        estoque.adicionar("P1", 2);

        assertFalse(estoque.possuiDisponivel("P1", 3));
    }

    @Test
    void possuiDisponivelDeveRetornarVerdadeiroQuandoEstoqueForSuficiente() {
        estoque.adicionar("P1", 3);

        assertTrue(estoque.possuiDisponivel("P1", 3));
    }

    @Test
    void reservarDeveLancarExcecaoQuandoEstoqueForInsuficiente() {
        estoque.adicionar("P1", 1);

        assertThrows(IllegalStateException.class, () -> estoque.reservar("P1", 2));
    }

    @Test
    void reservarDeveDescontarQuantidadeDoEstoque() {
        estoque.adicionar("P1", 5);

        estoque.reservar("P1", 2);

        assertEquals(3, estoque.consultar("P1"));
    }
}
