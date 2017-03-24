package com.opipo.relac.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.opipo.relac.exception.NotFoundElement;

@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerController {
	
	private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		logger.error("ERROR:",e);
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage() + ExceptionUtils.getStackTrace(e));
	}

	@ExceptionHandler
	void handleNotFoundElementException(NotFoundElement e, HttpServletResponse response) throws IOException {
		logger.error("ERROR:",e);
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage() + ExceptionUtils.getStackTrace(e));
	}
}
