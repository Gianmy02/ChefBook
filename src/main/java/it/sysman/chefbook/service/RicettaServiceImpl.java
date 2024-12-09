package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.RicettaDto;
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
        if(ricettaRepository.existsById(id)){
            return invokePostControl("deleteById",id);
        }else
            return false;

    }


    public void editRicetta(@RequestBody RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        ricettaRepository.save(r);
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

    private boolean invokePostControl(String method, int id){
        if(!ricettaRepository.existsById(id))
            return false;
        else {
            if(method.contains("delete")) {
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
            } else if(method.contains("edit")) {
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
            }
            return false;
        }
    }

}