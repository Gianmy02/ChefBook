package it.sysman.esempio.rest;

import it.sysman.esempio.entity.Ricetta;
import it.sysman.esempio.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ricette")
public class RicettaController {

    @Autowired
    private RicettaRepository ricettaRepository;

    @PostMapping
    public void addRicetta(@RequestBody Ricetta r){
        ricettaRepository.save(r);
    }

    @DeleteMapping("{id}")
    public void removeRicetta(@PathVariable int id){
        ricettaRepository.deleteById(id);
    }

    @PutMapping
    public void editRicetta(@RequestBody Ricetta r){
        ricettaRepository.save(r);
    }

    @GetMapping
    public Ricetta getRicettaByName(@RequestParam String nome){
        return ricettaRepository.findByNome(nome);
    }

    @GetMapping("all")
    public List<Ricetta> getAllRicette(){
        return ricettaRepository.findAll();

    }
}
