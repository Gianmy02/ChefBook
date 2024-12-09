package it.sysman.chefbook.exception;

public class AutoreNotFoundException extends RuntimeException{
    public AutoreNotFoundException() {
        super();
    }

    public AutoreNotFoundException(String message) {
        super(message);
    }
}
