package com.skaveesh.dcb.exception;

public class CircuitBreakerDownstreamCallException extends RuntimeException {

    public CircuitBreakerDownstreamCallException(String s) {
        super(s);
    }

}
