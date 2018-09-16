package com.krokogator.spring.error.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * Body of the exception REST response
 */
@Component
public class ClientErrorResponseBody {
    private HttpStatus status;
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
