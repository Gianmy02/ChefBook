package it.sysman.chefbook.rest;


import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @PostMapping
    public void addRicetta(@RequestBody RicettaDto r){
        ricettaService.addRicetta(r);
    }

    @DeleteMapping("{id}")
    public void removeRicetta(@PathVariable int id){
        ricettaService.removeRicetta(id);
    }

    @PutMapping
    public void editRicetta(@RequestBody RicettaDto r){
        ricettaService.editRicetta(r);
    }

    @GetMapping
    public RicettaDto getRicettaByName(@RequestParam String nome){
        return ricettaService.getRicettaByName(nome);
    }

    @GetMapping("all")
    public List<RicettaDto> getAllRicette(){
        return ricettaService.getAllRicette();
    }
}
