package com.exemplo.facade;

import java.util.HashMap;
import java.util.Map;

public class Estoque {

    private final Map<String, Integer> quantidades = new HashMap<>();

    public void adicionar(String codigo, int quantidade) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do produto é obrigatório");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        quantidades.merge(codigo, quantidade, Integer::sum);
    }

    public int consultar(String codigo) {
        return quantidades.getOrDefault(codigo, 0);
    }

    public boolean possuiDisponivel(String codigo, int quantidade) {
        return quantidade > 0 && consultar(codigo) >= quantidade;
    }

    public void reservar(String codigo, int quantidade) {
        if (!possuiDisponivel(codigo, quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para o produto " + codigo);
        }
        quantidades.put(codigo, consultar(codigo) - quantidade);
    }
}
