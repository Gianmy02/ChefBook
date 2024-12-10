package it.sysman.chefbook.rest;


import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addRicetta(@RequestBody RicettaDto r){
        ricettaService.addRicetta(r);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeRicetta(@PathVariable int id){
        return ricettaService.removeRicetta(id) ?
                ResponseEntity.status(HttpStatus.OK).body(null) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping
    public ResponseEntity<Void> editRicetta(@RequestBody RicettaDto dto){
        return ricettaService.editRicetta(dto) ?
                ResponseEntity.status(HttpStatus.OK).body(null) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("nome")
    public RicettaDto getRicettaByName(@RequestParam String value){
        return ricettaService.getRicettaByName(value);
    }

    @GetMapping("all")
    public List<RicettaDto> getAllRicette(){
        return ricettaService.getAllRicette();
    }
}
