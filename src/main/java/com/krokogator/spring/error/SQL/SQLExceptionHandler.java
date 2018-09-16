package com.krokogator.spring.error.SQL;

import com.krokogator.spring.error.client.ClientErrorResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles all SQL exceptions
 */
@ControllerAdvice
public class SQLExceptionHandler {

    @Autowired
    ClientErrorResponseBody clientErrorResponseBody;

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ClientErrorResponseBody> processConstraintError(DataIntegrityViolationException ex) {
        clientErrorResponseBody.setStatus(HttpStatus.BAD_REQUEST);
        clientErrorResponseBody.setDescription(ex.getMessage());
        return new ResponseEntity<>(clientErrorResponseBody, HttpStatus.BAD_REQUEST);
    }
}