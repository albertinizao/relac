package com.opipo.relac.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.opipo.relac.exception.NotFoundElement;

@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		System.out.println(e);
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage() + ExceptionUtils.getStackTrace(e));
	}

	@ExceptionHandler
	void handleNotFoundElementException(NotFoundElement e, HttpServletResponse response) throws IOException {
		System.out.println(e);
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage() + ExceptionUtils.getStackTrace(e));
	}
}
