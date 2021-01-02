package com.skaveesh.dcb.handler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import com.skaveesh.dcb.exception.CircuitBreakerDownstreamCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Component
public class ApplicationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationResponseEntityExceptionHandler.class);

  @ExceptionHandler(CircuitBreakerDownstreamCallException.class)
  public ResponseEntity<Map<String, Object>> handleException(final Exception exception, final HttpServletRequest request) {
    LOGGER.error("Downstream call Exception: {} ", exception.getMessage(), exception);
    Map<String, Object> responseBody = new HashMap<>();
    HttpStatus httpStatus = HttpStatus.GATEWAY_TIMEOUT;
    responseBody.put(exception.getMessage(), httpStatus.value());
    return new ResponseEntity<>(responseBody, httpStatus);
  }

}
