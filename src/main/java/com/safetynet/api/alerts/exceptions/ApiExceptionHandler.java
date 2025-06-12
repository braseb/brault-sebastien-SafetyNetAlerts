package com.safetynet.api.alerts.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;

import jakarta.servlet.http.HttpServletRequest;



@RestControllerAdvice
public class ApiExceptionHandler {
	
	private final Logger log = LogManager.getLogger();
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFoundEntity(EntityNotFoundException ex, HttpServletRequest request){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", ex.getMessage());
		body.put("uri", request.getRequestURI());
		log.error("NOT FOUND -> {}", request.getRequestURI(), ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)//, ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));
        log.error("ARGUMENT NOT VALID -> {} : {}", request.getRequestURI(), message, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorMessage("400", message));
    }
	
	
	@ExceptionHandler(EntityNotFoundExceptionWithReturn.class)
	public ResponseEntity<Map<String, Object>> handleNotFoundEntity(EntityNotFoundExceptionWithReturn ex, HttpServletRequest request){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", ex.getMessage());
		body.put("element", ex.getElement());
		log.error("NOT FOUND -> {} : {}", request.getRequestURI(), ex.getElement(), ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		
	}
	
	@ExceptionHandler(EntityAlreadyExistException.class)
	public ResponseEntity<Map<String, Object>> handleAlreadyExist(EntityAlreadyExistException ex, HttpServletRequest request){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", ex.getMessage());
		body.put("uri", request.getRequestURI());
		body.put("element", ex.getElement());
		log.error("ALREADY EXIST -> {} : {}", request.getRequestURI(), ex.getElement(), ex);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
		
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", ex.getMessage());
		String url = ex.getRequestURL();
		body.put("url", url);
		body.put("timestamp", LocalDateTime.now());
		log.error("NO HANDLER FOUND -> {}", request.getRequestURI(), ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        errorDetails.put("error", "Method Not Allowed");
        errorDetails.put("message", ex.getMessage());
        List<String> supportedMethods = ex.getSupportedHttpMethods()
                .stream()
                .map(HttpMethod::name)
                .toList();
        
        errorDetails.put("supportedMethods", String.join(", ", supportedMethods));
        log.error("METHOD NOT SUPPORTED -> {} :  supported : {}", request.getRequestURI(), String.join(", ", supportedMethods), ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }
	
	
	
	
}
