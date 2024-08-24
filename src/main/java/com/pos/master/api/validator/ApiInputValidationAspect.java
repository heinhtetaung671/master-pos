package com.pos.master.api.validator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.pos.master.model.exception.ValidationException;

@Aspect
@Component
public class ApiInputValidationAspect {

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController) ")
	void apiMethods() {}
	
	@Before(value = "apiMethods() && args(.., result)", argNames = "result")
	public void validate(BindingResult result) {
		if( result.hasErrors() ) {
			throw new ValidationException(result.getFieldErrors().stream().map(fe -> fe.getDefaultMessage()).toList());
		}
	}
	
}
