package com.lab.jpa.projetosanquim.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarArgumentoNaoValidado(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ErroResposta.ErroCampo> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapearErroCampo)
                .toList();

        return criarResposta(HttpStatus.BAD_REQUEST, "Erro de validação", request, erros);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResposta> tratarRegraNegocio(
            RegraNegocioException exception,
            HttpServletRequest request
    ) {
        return criarResposta(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception,
            HttpServletRequest request
    ) {
        return criarResposta(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResposta> tratarCredenciaisInvalidas(HttpServletRequest request) {
        return criarResposta(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResposta> tratarAcessoNegado(HttpServletRequest request) {
        return criarResposta(HttpStatus.FORBIDDEN, "Acesso negado", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroInterno(HttpServletRequest request) {
        return criarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", request);
    }

    private ErroResposta.ErroCampo mapearErroCampo(FieldError erro) {
        String mensagem = erro.getDefaultMessage() == null ? "Valor inválido" : erro.getDefaultMessage();
        return new ErroResposta.ErroCampo(erro.getField(), mensagem);
    }

    private ResponseEntity<ErroResposta> criarResposta(HttpStatus status, String mensagem, HttpServletRequest request) {
        return criarResposta(status, mensagem, request, List.of());
    }

    private ResponseEntity<ErroResposta> criarResposta(
            HttpStatus status,
            String mensagem,
            HttpServletRequest request,
            List<ErroResposta.ErroCampo> erros
    ) {
        ErroResposta resposta = new ErroResposta(
                Instant.now(),
                status.value(),
                mensagem,
                request.getRequestURI(),
                erros
        );
        return ResponseEntity.status(status).body(resposta);
    }
}