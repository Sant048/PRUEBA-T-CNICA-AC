package com.Prueba.Accenture.shared.handler;

import com.Prueba.Accenture.shared.exception.DomainException;
import com.Prueba.Accenture.shared.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handleNotFound(NotFoundException ex) {
        return Mono.just(ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<String> handleDomain(DomainException ex) {
        return Mono.just(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<String> handleGeneral(Exception ex) {
        return Mono.just("Internal server error");
    }
}