package br.com.challenge.alura.phrases.random.handlers;

import br.com.challenge.alura.phrases.random.exceptions.InformationProcessingException;
import br.com.challenge.alura.phrases.random.exceptions.InternalSeverErrorException;
import br.com.challenge.alura.phrases.random.exceptions.NotFoundException;
import br.com.challenge.alura.phrases.random.models.handler.ResponseExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class RestResponseExceptionHandler {

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<ResponseExceptionHandler> notFoundException(
			NotFoundException ex, HttpServletRequest request
	) {
		final var status = HttpStatus.NOT_FOUND;
		final var response = buildResponseExceptionHandler(ex, request, status);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(value = { InternalSeverErrorException.class })
	protected ResponseEntity<ResponseExceptionHandler> internalSeverErrorException(InternalSeverErrorException ex,
																				   HttpServletRequest request) {
		final var status = HttpStatus.INTERNAL_SERVER_ERROR;
		final var response = buildResponseExceptionHandler(ex, request, status);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(value = { InformationProcessingException.class })
	protected ResponseEntity<ResponseExceptionHandler> informationProcessingException(
			IllegalArgumentException ex, HttpServletRequest request
	) {
		final var status = HttpStatus.UNPROCESSABLE_ENTITY;
		final var response = buildResponseExceptionHandler(ex, request, status);
		return ResponseEntity.status(status).body(response);
	}

	private ResponseExceptionHandler buildResponseExceptionHandler(
			RuntimeException ex, HttpServletRequest request, HttpStatus status
	) {
		return new ResponseExceptionHandler(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI()
		);
	}
}
