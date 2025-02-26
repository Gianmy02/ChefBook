package it.sysman.chefbook.exception;

public class ForbiddenActionException extends RuntimeException{
    public ForbiddenActionException() {
        super();
    }

    public ForbiddenActionException(String message) {
        super(message);
    }
}
