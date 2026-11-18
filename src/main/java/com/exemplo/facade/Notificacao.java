package com.exemplo.facade;

import java.util.ArrayList;
import java.util.List;

public class Notificacao {

    private final List<String> enviadas = new ArrayList<>();

    public void enviar(String email, String mensagem) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        enviadas.add(email + ": " + mensagem);
    }

    public List<String> getEnviadas() {
        return List.copyOf(enviadas);
    }
}
