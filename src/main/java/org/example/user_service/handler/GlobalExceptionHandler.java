package org.example.user_service.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(ConstraintViolationException e, HttpServletRequest rq) {
    log.error("validation error: {}", e.getMessage(), e);
    System.out.println(e.getMessage());
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception e, HttpServletRequest rq) {
    log.error("Exception: {}", e.getMessage(), e);
    return responseBuild(e.getMessage(), rq.getRequestURI());
  }

  private ErrorResponse responseBuild(String message, String requestURI) {
    return ErrorResponse.builder()
            .message(message)
            .url(requestURI)
            .build();
  }
}
