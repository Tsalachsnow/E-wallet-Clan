package com.example.walletapi.services.exception;


import com.example.walletapi.services.exception.types.ResourceNotFound;
import com.example.walletapi.services.exception.types.TransactionNotAllowed;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {


	@ExceptionHandler(value = ResourceNotFound.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFound e, WebRequest request) {
		final HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
		return getErrorMessageResponseEntity(e, request, status);
	}

	@ExceptionHandler(value = TransactionNotAllowed.class)
	public ResponseEntity<ErrorMessage> handleTransactionNotAllowedException(TransactionNotAllowed e, WebRequest request) {
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		return getErrorMessageResponseEntity(e, request, status);
	}


	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorMessage> handleAnyException(Exception e, WebRequest request) {
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return getErrorMessageResponseEntity(e, request, status);
	}


	private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception e, WebRequest request, HttpStatus status) {
		final String timeNow = ZonedDateTime.now(ZoneId.of("Africa/Lagos")).toString();
		ErrorMessage errorMessage = new ErrorMessage(
			e.getMessage(), e, status, timeNow, request.getDescription(false));
		return new ResponseEntity<>(errorMessage, status);
	}

}