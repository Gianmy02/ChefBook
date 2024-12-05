package it.sysman.chefbook.rest;


import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autori")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    @PostMapping
    public void addAutore(@RequestBody AutoreDto dto){
        autoreService.addAutore(dto);
    }
    @DeleteMapping("{id}")
    public void removeAutore(@PathVariable int id){
        autoreService.removeAutore(id);
    }
    @PutMapping
    public void editAutore(@RequestBody AutoreDto dto){
        autoreService.editAutore(dto);
    }
    @GetMapping
    public AutoreDto getAutoreByName(@RequestParam String nome){
        return autoreService.getAutoreByName(nome);
    }

    @GetMapping("all")
    public List<AutoreDto> getAllAutori(){
        return autoreService.getAllAutori();
    }
}
