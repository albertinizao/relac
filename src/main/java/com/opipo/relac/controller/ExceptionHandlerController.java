package com.opipo.relac.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.opipo.relac.exception.NotFoundElement;

@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
	}
	@ExceptionHandler
	void handleNotFoundElementException(NotFoundElement e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(),e.getMessage());
	}
}
