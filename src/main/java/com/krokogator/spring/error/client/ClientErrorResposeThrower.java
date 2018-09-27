package com.krokogator.spring.error.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ClientErrorResposeThrower {


    /**
     * Throws 400 bad request response
     * @param message
     * @throws ClientErrorException
     */
    public void throwBadRequest(String message) throws ClientErrorException {
        throw new ClientErrorException(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Throws 404 not found response
     * @param message
     * @throws ClientErrorException
     */
    public void throwNotFound(String message) throws ClientErrorException {
        throw new ClientErrorException(HttpStatus.NOT_FOUND, message);
    }
}
