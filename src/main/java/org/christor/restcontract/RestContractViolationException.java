package org.christor.restcontract;

public class RestContractViolationException extends Exception {

    public RestContractViolationException() {
    }

    public RestContractViolationException(String message) {
        super(message);
    }

    public RestContractViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestContractViolationException(Throwable cause) {
        super(cause);
    }

    public RestContractViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
