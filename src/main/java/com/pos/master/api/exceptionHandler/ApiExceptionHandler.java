package com.pos.master.api.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pos.master.api.output.ErrorResponse;
import com.pos.master.model.exception.ValidationException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	ErrorResponse handle(ValidationException ex) {
		return new ErrorResponse(ex.getErrorMessages());
	}
	
}
