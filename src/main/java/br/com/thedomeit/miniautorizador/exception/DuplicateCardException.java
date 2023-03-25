package br.com.thedomeit.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class DuplicateCardException extends RuntimeException {
	private final String cardNumber;
    private final String password;

    public DuplicateCardException (String cardNumber, String password) {
        super();
        this.cardNumber = cardNumber;
        this.password = password;
    }

}

