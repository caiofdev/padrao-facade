package com.exemplo.facade;

public class Frete {

    public static final double VALOR_MINIMO_FRETE_GRATIS = 200.0;
    public static final double FRETE_REGIONAL = 10.0;
    public static final double FRETE_NACIONAL = 25.0;

    public double calcular(String cep, double valorPedido) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido");
        }
        if (valorPedido >= VALOR_MINIMO_FRETE_GRATIS) {
            return 0.0;
        }
        if (cep.startsWith("0")) {
            return FRETE_REGIONAL;
        }
        return FRETE_NACIONAL;
    }
}
