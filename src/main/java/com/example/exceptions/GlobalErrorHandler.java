package com.example.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.model.ErrorModel;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ModelNotFoundException.class)
	public ResponseEntity<ErrorModel> handleModelNotFound(ModelNotFoundException ex, WebRequest request) {
		ErrorModel errorModel = new ErrorModel(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
	}

}
