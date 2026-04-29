package com.fmi.springcourse.marketplace.exception;

import com.fmi.springcourse.marketplace.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodValidation(MethodArgumentNotValidException ex) {
		var fieldError = ex.getBindingResult().getFieldError();
		
		if (fieldError != null) {
			return ResponseEntity.badRequest()
				.body(new ExceptionResponse(fieldError.getDefaultMessage()));
		}
		
		var globalError = ex.getBindingResult().getGlobalError();
		
		if (globalError != null) {
			return ResponseEntity.badRequest()
				.body(new ExceptionResponse(globalError.getDefaultMessage()));
		}
		
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse("Validation failed"));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(ex.getMessage()));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(IllegalArgumentException ex) {
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(ex.getMessage()));
	}
}
