package com.exemplo.facade;

import java.util.Objects;

public class PedidoFacade {

    private final Estoque estoque;
    private final Pagamento pagamento;
    private final Frete frete;
    private final Notificacao notificacao;

    public PedidoFacade(Estoque estoque, Pagamento pagamento, Frete frete, Notificacao notificacao) {
        this.estoque = Objects.requireNonNull(estoque);
        this.pagamento = Objects.requireNonNull(pagamento);
        this.frete = Objects.requireNonNull(frete);
        this.notificacao = Objects.requireNonNull(notificacao);
    }

    public ResultadoPedido realizarPedido(Pedido pedido) {
        if (!estoque.possuiDisponivel(pedido.codigoProduto(), pedido.quantidade())) {
            return new ResultadoPedido(StatusPedido.ESTOQUE_INDISPONIVEL, 0.0);
        }
        double subtotal = pedido.precoUnitario() * pedido.quantidade();
        double total = subtotal + frete.calcular(pedido.cep(), subtotal);
        if (!pagamento.processar(pedido.numeroCartao(), total)) {
            return new ResultadoPedido(StatusPedido.PAGAMENTO_RECUSADO, 0.0);
        }
        estoque.reservar(pedido.codigoProduto(), pedido.quantidade());
        notificacao.enviar(pedido.email(), "Pedido aprovado no valor de " + total);
        return new ResultadoPedido(StatusPedido.APROVADO, total);
    }
}
