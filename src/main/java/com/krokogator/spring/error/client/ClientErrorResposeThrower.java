package com.krokogator.spring.error.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ClientErrorResposeThrower {

    @Autowired
    ClientErrorException clientErrorException;

    /**
     * Throws 400 bad request response
     * @param message
     * @throws ClientErrorException
     */
    public void throwBadRequest(String message) throws ClientErrorException {
        clientErrorException.setStatus(HttpStatus.BAD_REQUEST);
        clientErrorException.setMessage(message);
        throw clientErrorException;
    }

    /**
     * Throws 404 not found response
     * @param message
     * @throws ClientErrorException
     */
    public void throwNotFound(String message) throws ClientErrorException {
        clientErrorException.setStatus(HttpStatus.NOT_FOUND);
        clientErrorException.setMessage(message);
        throw clientErrorException;
    }
}
