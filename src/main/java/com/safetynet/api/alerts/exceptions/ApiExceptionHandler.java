package com.safetynet.api.alerts.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class ApiExceptionHandler {
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public String handleNotFoundEntity(EntityNotFoundException ex){
		String message = ex.getMessage();
		return message;
		
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorMessage("400", message));
    }
	
	
	@ExceptionHandler(EntityNotFoundExceptionWithReturn.class)
	public ResponseEntity<Map<String, Object>> handleNotFoundEntity(EntityNotFoundExceptionWithReturn ex){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", ex.getMessage());
		body.put("element", ex.getElement());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		
	}
	
	
	
	
}
