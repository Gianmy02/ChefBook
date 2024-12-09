package it.sysman.chefbook.exception;


public class RicettaNotFoundException extends RuntimeException{
    public RicettaNotFoundException(String message) {
        super(message);
    }

    public RicettaNotFoundException(){
        super();
    }
}
