package br.com.thedomeit.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidCardException extends RuntimeException {

    public InvalidCardException() {
        super("Cartão inválido.");
    }
}
