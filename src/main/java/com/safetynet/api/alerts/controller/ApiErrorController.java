package com.safetynet.api.alerts.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ApiErrorController implements ErrorController {

	    private final ErrorAttributes errorAttributes;

	    public ApiErrorController(ErrorAttributes errorAttributes) {
	        this.errorAttributes = errorAttributes;
	    }

	    @RequestMapping("/error")
	    public ResponseEntity<Object> handleError(HttpServletRequest request) {
	        ServletWebRequest webRequest = new ServletWebRequest(request);

	        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
	            webRequest,
	            ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,
	            							ErrorAttributeOptions.Include.STATUS,
	            							ErrorAttributeOptions.Include.PATH,
	            							ErrorAttributeOptions.Include.ERROR
	            							)
	        );
	        
	        System.out.println("ERROR ATTRIBUTES = " + attributes);
	        Map<String, Object> body = new HashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("status", attributes.get("status"));
	        body.put("error", attributes.get("error"));
	        body.put("message", attributes.get("message"));
	        body.put("path",  attributes.get("path"));
	        
	        Object statusObj = attributes.get("status");
	        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	        if (statusObj instanceof Integer) {
	            status = HttpStatus.valueOf((Integer) statusObj);
	        }
	        return new ResponseEntity<>(body, status);
	        
	    }
	
}
