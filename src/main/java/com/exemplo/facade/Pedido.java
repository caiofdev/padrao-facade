package com.exemplo.facade;

public record Pedido(
        String codigoProduto,
        int quantidade,
        double precoUnitario,
        String cep,
        String numeroCartao,
        String email) {
}
