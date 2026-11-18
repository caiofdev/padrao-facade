package com.exemplo.facade;

public class Pagamento {

    private final double limite;

    public Pagamento(double limite) {
        if (limite < 0) {
            throw new IllegalArgumentException("O limite não pode ser negativo");
        }
        this.limite = limite;
    }

    public boolean processar(String numeroCartao, double valor) {
        if (numeroCartao == null || numeroCartao.length() != 16) {
            return false;
        }
        return valor > 0 && valor <= limite;
    }
}
