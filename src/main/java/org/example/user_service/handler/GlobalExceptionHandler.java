package org.example.user_service.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.handler.exception.DataValidationException;
import org.example.user_service.handler.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest rq) {
    log.error("Validation error: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest rq) {
    log.error("Validation error: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest rq) {
    BindingResult result = e.getBindingResult();
    List<String> errorMessages = result.getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();
    log.error("Validation error: {}", e.getMessage(), e);
    return responseBuild(errorMessages.toString(), rq.getRequestURI());
  }

  @ExceptionHandler(DataValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleDataValidationException(DataValidationException e, HttpServletRequest rq) {
    log.error("Validation error: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest rq) {
    log.error("IllegalArgumentException: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest rq) {
    log.error("RuntimeException: {}", e.getMessage(), e);
    return responseBuild("Server error. Everything will work soon, please try again later.", rq.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild("Server error. Everything will work soon, please try again later.", rq.getRequestURI());
  }

  private ErrorResponse responseBuild(String message, String requestURI) {
    return ErrorResponse.builder()
            .message(message)
            .url(requestURI)
            .build();
  }
}
