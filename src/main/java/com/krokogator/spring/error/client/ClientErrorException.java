package com.krokogator.spring.error.client;

import org.springframework.http.HttpStatus;

/**
 * Provides custom message for bad requests
 */

public class ClientErrorException extends Exception {
    private HttpStatus status;
    private String message;

    public ClientErrorException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

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
