package uk.doh.oht.rina.registration.exceptions;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
public class RinaRegistrationException extends RuntimeException {
    public RinaRegistrationException() {
        super();
    }

    public RinaRegistrationException(String message) {
        super(message);
    }
}
