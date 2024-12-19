package it.sysman.chefbook.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
public class AccountController {
    @GetMapping("signin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getToken(){
    }
}
