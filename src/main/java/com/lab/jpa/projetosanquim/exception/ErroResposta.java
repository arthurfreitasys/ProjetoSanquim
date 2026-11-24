package com.lab.jpa.projetosanquim.exception;

import java.time.Instant;
import java.util.List;

public record ErroResposta(
        Instant timestamp,
        int status,
        String mensagem,
        String caminho,
        List<ErroCampo> erros
) {

    public record ErroCampo(String campo, String mensagem) {
    }
}