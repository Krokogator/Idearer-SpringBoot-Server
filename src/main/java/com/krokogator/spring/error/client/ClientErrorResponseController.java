package com.krokogator.spring.error.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientErrorResponseController {

    @Autowired
    ClientErrorResponseBody clientErrorResponseBody;

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ClientErrorResponseBody> throwResponseException(ClientErrorException exception) {
        clientErrorResponseBody.setStatus(exception.getStatus());
        clientErrorResponseBody.setDescription(exception.getMessage());

        return new ResponseEntity<>(clientErrorResponseBody, exception.getStatus());
    }
}
