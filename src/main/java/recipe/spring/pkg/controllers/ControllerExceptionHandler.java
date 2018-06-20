package recipe.spring.pkg.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handlerNumberFormatException(Exception exception) {
		log.error("Handling NumberFormatException");
		ModelAndView mAndView = new ModelAndView();
		mAndView.setViewName("400error");
		mAndView.addObject("exception", exception);
		return mAndView;
	}
}
