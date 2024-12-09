package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.exception.RicettaNotFoundException;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class RicettaServiceImpl implements RicettaService{

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private RicettaMapper ricettaMapper;


    public void addRicetta(@RequestBody RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        ricettaRepository.save(r);
    }


    public boolean removeRicetta(@PathVariable int id){
        return invokePostControl("deleteById",id, null);
    }


    public boolean editRicetta(@RequestBody RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        return invokePostControl("save", r.getId(), r);
    }


    public RicettaDto getRicettaByName(@RequestParam String nome){
        Ricetta r = ricettaRepository.findByNome(nome);
        if(r == null)
            throw new RicettaNotFoundException("Ricetta not found");
        return ricettaMapper.ricettaToRicettaDto(r);
    }


    public List<RicettaDto> getAllRicette(){
        return ricettaMapper.ricetteToRicetteDto(ricettaRepository.findAll());
    }

    //programmazione riflessiva
    private boolean invokePostControl(String method, int id, Ricetta ricetta){
        if(method.contains("delete") && ricettaRepository.existsById(id)) {
            try {
                Method[] methods = this.ricettaRepository.getClass()
                        .getDeclaredMethods();

                for (Method m: methods){
                    if(m.getName().contains(method)) {
                        m.invoke(ricettaRepository, id);
                    }
                }
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if(method.contains("save")) {
            try {
                Method[] methods = this.ricettaRepository.getClass()
                        .getDeclaredMethods();

                for (Method m: methods){
                    if(m.getName().contains(method)) {
                        m.invoke(ricettaRepository, ricetta);  //fiduciosi che sia la prima funzione (save) che si trova
                        break;
                    }
                }
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
