package com.rhtinterprise.RHTcatalog.resources.exceptions;

import com.rhtinterprise.RHTcatalog.exceptions.DataBaseException;
import com.rhtinterprise.RHTcatalog.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> tratarErrosGenericos(final Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> tratarErrosGenericosRunTime(final Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);

		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		err.setError("Run Time Exception");
		err.setMessage(ex.getMessage());
		err.setPath(ExceptionUtils.getStackTrace(ex));
		return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
		
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> entityNotFound(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
		
	}

		@Override
		public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpStatus statuss = HttpStatus.UNPROCESSABLE_ENTITY;

		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(statuss.value());
		err.setError("Validation Exception");
		err.setMessage(ex.getMessage());
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		});
		err.setPath(request.getContextPath());
		return ResponseEntity.status(status).body(err);

	}
}
