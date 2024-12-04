package it.sysman.esempio.rest;

import it.sysman.esempio.entity.Ricetta;
import it.sysman.esempio.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @PostMapping
    public void addRicetta(@RequestBody Ricetta r){
        ricettaService.addRicetta(r);
    }

    @DeleteMapping("{id}")
    public void removeRicetta(@PathVariable int id){
        ricettaService.removeRicetta(id);
    }

    @PutMapping
    public void editRicetta(@RequestBody Ricetta r){
        ricettaService.editRicetta(r);
    }

    @GetMapping
    public Ricetta getRicettaByName(@RequestParam String nome){
        return ricettaService.getRicettaByName(nome);
    }

    @GetMapping("all")
    public List<Ricetta> getAllRicette(){
        return ricettaService.getAllRicette();

    }
}
