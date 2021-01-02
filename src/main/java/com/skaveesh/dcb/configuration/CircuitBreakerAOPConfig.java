package com.skaveesh.dcb.configuration;

import java.util.function.Function;
import java.util.function.Supplier;

import com.skaveesh.dcb.exception.CircuitBreakerDownstreamCallException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.skaveesh.dcb.functional.SupplierUtil.rethrowSupplier;

@Aspect
@Component
public class CircuitBreakerAOPConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerAOPConfig.class);

  private final CircuitBreaker circuitBreaker;

  public CircuitBreakerAOPConfig(CircuitBreakerRegistry circuitBreakerRegistry){
    this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("myAPOConfiguredMyCircuitBreaker");
  }

  @Around("@annotation(com.skaveesh.dcb.annotation.EnableCircuitBreakerScan)")
  public Object around(ProceedingJoinPoint proceedingJoinPoint) {

    Object returnValue = execute(rethrowSupplier(proceedingJoinPoint::proceed), this::fallback);

    LOGGER.info("Around after class - {}, method - {}, returns - {}", proceedingJoinPoint.getSignature().getDeclaringType().getName(), proceedingJoinPoint.getSignature().getName(), returnValue);

    return returnValue;
  }

  private <T> T execute(Supplier<T> supplier, Function<Throwable, T> fallback) {
    return Decorators.ofSupplier(supplier)
        .withCircuitBreaker(circuitBreaker)
        .withFallback(fallback)
        .get();
  }

  private ResponseEntity<String> fallback(Throwable ex) {
      throw new CircuitBreakerDownstreamCallException(ex.getMessage());
  }

}
