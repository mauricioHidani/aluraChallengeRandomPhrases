package br.com.challenge.alura.phrases.random.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalSeverErrorException extends RuntimeException {
	public InternalSeverErrorException(String message) {
		super(message);
	}
}
