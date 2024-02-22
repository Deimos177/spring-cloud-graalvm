package br.com.bruce.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import br.com.bruce.dto.ApiError;
import jakarta.ws.rs.BadRequestException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {

		return ResponseEntity.badRequest().body(new ApiError(ex.getStatusCode(), getValidationMessage(ex.toString())));
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResponseEntity<Object> handleBadRequest(BadRequestException ex) {

		return ResponseEntity.badRequest().body(new ApiError(HttpStatusCode.valueOf(400), ex.getMessage()));
	}

	@ExceptionHandler(InternalServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ResponseEntity<Object> handleInternalServerError(InternalServerError ex) {

		return ResponseEntity.internalServerError().body(new ApiError(ex.getStatusCode(), ex.getMessage()));
	}

	protected String getValidationMessage(String exceptionMessage) {

		String patternString = "default message \\[(.*?)\\]";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(exceptionMessage);
		StringBuilder extractedMessages = new StringBuilder();

		while (matcher.find()) {
			String message = matcher.group(1);
			extractedMessages.append(message).append(" ");
		}

		return extractedMessages.toString();
	}
}