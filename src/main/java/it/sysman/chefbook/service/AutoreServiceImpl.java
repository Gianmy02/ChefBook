package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.User;
import it.sysman.chefbook.exception.AutoreNotFoundException;
import it.sysman.chefbook.repository.AutoreRepository;
import it.sysman.chefbook.repository.UserRepository;
import it.sysman.chefbook.utils.AutoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class AutoreServiceImpl implements AutoreService{

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private AutoreMapper autoreMapper;

    @Autowired
    private UserRepository userRepository;

    public void addAutore(@RequestBody AutoreDto dto) {
        Autore a = autoreMapper.autoreDtoToAutore(dto);
        User user = autoreMapper.autoreDtoToUser(dto);
        autoreRepository.save(a);
        userRepository.save(user);
    }

    public boolean removeAutore(@PathVariable int id) {
        return invokePostControl("deleteById",id, null);
    }

    public boolean editAutore(@RequestBody AutoreDto dto) {
        Autore a = autoreMapper.autoreDtoToAutore(dto);
        return invokePostControl("save",a.getId(), a);
    }


    public AutoreDto getAutoreByName(@RequestParam String nome) {
        Autore a = autoreRepository.findByNome(nome);
        if(a == null)
            throw new AutoreNotFoundException("Autore non trovato");
        return autoreMapper.autoreToAutoreDto(a);
    }


    public AutoreDto getAutoreByEmail(String email) {
        Autore a = autoreRepository.findByEmail(email);
        if(a == null){
            throw new AutoreNotFoundException("Autore non trovato");
        }
        return autoreMapper.autoreToAutoreDto(a);
    }


    public List<AutoreDto> getAllAutori() {
        return autoreMapper.autoriToAutoriDto(autoreRepository.findAll());
    }

    private boolean invokePostControl(String method, int id, Autore autore) {
        if (!autoreRepository.existsById(id))
            return false;
        else {
            if (method.contains("delete")) {
                try {
                    Method[] methods = this.autoreRepository.getClass()
                            .getDeclaredMethods();

                    for (Method m : methods) {
                        if (m.getName().contains(method)) {
                            m.invoke(autoreRepository, id);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.contains("save")) {
                try {
                    Method[] methods = this.autoreRepository.getClass()
                            .getDeclaredMethods();

                    for (Method m : methods) {
                        if (m.getName().contains(method)) {
                            m.invoke(autoreRepository, autore);  //fiduciosi che sia la prima funzione (save) che si trova
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
}
