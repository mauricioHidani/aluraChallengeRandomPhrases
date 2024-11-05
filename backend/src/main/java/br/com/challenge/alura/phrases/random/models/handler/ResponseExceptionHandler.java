package br.com.challenge.alura.phrases.random.models.handler;

import java.time.Instant;

public record ResponseExceptionHandler(
	Instant timestamp,
	Integer codeStatus,
	String status,
	String message,
	String path
) {

}
