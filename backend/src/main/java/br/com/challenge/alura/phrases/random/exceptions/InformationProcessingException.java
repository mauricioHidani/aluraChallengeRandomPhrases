package br.com.challenge.alura.phrases.random.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InformationProcessingException extends RuntimeException {
	public InformationProcessingException(String message) {
		super(message);
	}
}
