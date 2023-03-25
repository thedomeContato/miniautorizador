package br.com.thedomeit.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NonexistentCardBalanceException extends RuntimeException {

	public NonexistentCardBalanceException() {
		super();
	}

}
