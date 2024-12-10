package it.sysman.chefbook.rest;

import it.sysman.chefbook.exception.AutoreNotFoundException;
import it.sysman.chefbook.exception.RicettaNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler({AutoreNotFoundException.class, RicettaNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
