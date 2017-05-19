package uk.doh.oht.rina.registration.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by peterwhitehead on 28/04/2017.
 */

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Thrown from the controller(s) when an incoming request has binding
     * and / or validation errors. When the validation is invoked in the
     * service for mapping database entities onto view objects, any
     * validation errors cause {@code ValidationException} instances to be
     * thrown, which generate 500 responses for the client.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void handleValidationFailure(Exception e) {
        log.error("{}", e);
        // Nothing to do
    }
}
