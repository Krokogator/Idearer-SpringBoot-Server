package com.krokogator.spring.error.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Provides custom message for bad requests
 */
@Component
public class ClientErrorException extends Exception {
    private HttpStatus status;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
