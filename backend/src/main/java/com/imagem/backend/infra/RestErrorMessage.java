package com.imagem.backend.infra;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(HttpStatus httpStatus, String message) {
    public RestErrorMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
