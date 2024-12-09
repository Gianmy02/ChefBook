package it.sysman.chefbook.rest;


import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autori")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addAutore(@RequestBody AutoreDto dto){
        autoreService.addAutore(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAutore(@PathVariable int id){
            return autoreService.removeAutore(id) ?
                    ResponseEntity.status(HttpStatus.OK).body(null) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping
    public ResponseEntity<Void> editAutore(@RequestBody AutoreDto dto){
        autoreService.editAutore(dto);
        return null;
    }

    @GetMapping("nome")
    public AutoreDto getAutoreByName(@RequestParam String value){
        return autoreService.getAutoreByName(value);
    }

    @GetMapping("email")
    public AutoreDto getAutoreByEmail(@RequestParam String value){
        return autoreService.getAutoreByEmail(value);
    }

    @GetMapping("all")
    public List<AutoreDto> getAllAutori(){
        return autoreService.getAllAutori();
    }
}