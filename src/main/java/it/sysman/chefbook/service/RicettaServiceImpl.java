package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class RicettaServiceImpl implements RicettaService{

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private RicettaMapper ricettaMapper;

    @PostMapping
    public void addRicetta(@RequestBody RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        ricettaRepository.save(r);
    }

    @DeleteMapping("{id}")
    public void removeRicetta(@PathVariable int id){
        ricettaRepository.deleteById(id);
    }

    @PutMapping
    public void editRicetta(@RequestBody RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        ricettaRepository.save(r);
    }

    @GetMapping
    public RicettaDto getRicettaByName(@RequestParam String nome){
       return ricettaMapper.ricettaToRicettaDto(ricettaRepository.findByNome(nome));
    }

    @GetMapping("all")
    public List<RicettaDto> getAllRicette(){
        return ricettaMapper.ricetteToRicetteDto(ricettaRepository.findAll());

    }
}
