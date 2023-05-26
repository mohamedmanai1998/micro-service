package tn.esprit.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tn.esprit.exceptions.RestException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { RestException.class })
	protected ResponseEntity<Object> handleConflict(RestException ex, WebRequest request) {
		return ResponseEntity.status(ex.getStatus()).body(ex);
	}
}
