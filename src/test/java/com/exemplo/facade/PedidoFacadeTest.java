package com.exemplo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoFacadeTest {

    private static final String CARTAO_VALIDO = "1234567812345678";
    private static final String EMAIL = "cliente@email.com";

    private Estoque estoque;
    private Pagamento pagamento;
    private Frete frete;
    private Notificacao notificacao;
    private PedidoFacade facade;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
        estoque.adicionar("P1", 5);
        pagamento = new Pagamento(500.0);
        frete = new Frete();
        notificacao = new Notificacao();
        facade = new PedidoFacade(estoque, pagamento, frete, notificacao);
    }

    @Test
    void construtorDeveLancarExcecaoQuandoEstoqueForNulo() {
        assertThrows(NullPointerException.class, () -> new PedidoFacade(null, pagamento, frete, notificacao));
    }

    @Test
    void construtorDeveLancarExcecaoQuandoPagamentoForNulo() {
        assertThrows(NullPointerException.class, () -> new PedidoFacade(estoque, null, frete, notificacao));
    }

    @Test
    void construtorDeveLancarExcecaoQuandoFreteForNulo() {
        assertThrows(NullPointerException.class, () -> new PedidoFacade(estoque, pagamento, null, notificacao));
    }

    @Test
    void construtorDeveLancarExcecaoQuandoNotificacaoForNula() {
        assertThrows(NullPointerException.class, () -> new PedidoFacade(estoque, pagamento, frete, null));
    }

    @Test
    void realizarPedidoDeveRetornarEstoqueIndisponivelQuandoNaoHouverQuantidade() {
        Pedido pedido = new Pedido("P1", 6, 10.0, "12345678", CARTAO_VALIDO, EMAIL);

        ResultadoPedido resultado = facade.realizarPedido(pedido);

        assertEquals(StatusPedido.ESTOQUE_INDISPONIVEL, resultado.status());
        assertEquals(0.0, resultado.valorTotal());
        assertEquals(5, estoque.consultar("P1"));
        assertTrue(notificacao.getEnviadas().isEmpty());
    }

    @Test
    void realizarPedidoDeveRetornarPagamentoRecusadoQuandoPagamentoFalhar() {
        Pedido pedido = new Pedido("P1", 2, 10.0, "12345678", "123", EMAIL);

        ResultadoPedido resultado = facade.realizarPedido(pedido);

        assertEquals(StatusPedido.PAGAMENTO_RECUSADO, resultado.status());
        assertEquals(0.0, resultado.valorTotal());
        assertEquals(5, estoque.consultar("P1"));
        assertTrue(notificacao.getEnviadas().isEmpty());
    }

    @Test
    void realizarPedidoDeveAprovarReservarEstoqueENotificarCliente() {
        Pedido pedido = new Pedido("P1", 2, 10.0, "12345678", CARTAO_VALIDO, EMAIL);

        ResultadoPedido resultado = facade.realizarPedido(pedido);

        assertEquals(StatusPedido.APROVADO, resultado.status());
        assertEquals(45.0, resultado.valorTotal());
        assertEquals(3, estoque.consultar("P1"));
        assertEquals(1, notificacao.getEnviadas().size());
    }
}
