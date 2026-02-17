package uk.co.mayden.shopping.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {
	@ExceptionHandler(value=Exception.class)
	public ModelAndView handle(HttpServletRequest request, Exception exception) throws Exception {
		// Delegate framework exceptions
		if(exception.getClass().isAnnotationPresent(ResponseStatus.class)) {
			throw exception;
		}

		// Otherwise redirect to error page
		final var error = new ModelAndView();
		error.addObject(exception);
		error.addObject("url", request.getRequestURL());
		error.setViewName("error");
		return error;
	}
}
