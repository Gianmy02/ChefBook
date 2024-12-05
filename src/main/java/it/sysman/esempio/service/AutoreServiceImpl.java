package it.sysman.esempio.service;

import it.sysman.esempio.dto.AutoreDto;
import it.sysman.esempio.entity.Autore;
import it.sysman.esempio.repository.AutoreRepository;
import it.sysman.esempio.utils.AutoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class AutoreServiceImpl implements AutoreService{

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private AutoreMapper autoreMapper;

    @PostMapping
    public void addAutore(@RequestBody AutoreDto dto) {
        Autore a = autoreMapper.autoreDtoToAutore(dto);
        autoreRepository.save(a);
    }

    @DeleteMapping("{id}")
    public void removeAutore(@PathVariable int id) {
        autoreRepository.deleteById(id);
    }

    @PutMapping
    public void editAutore(@RequestBody AutoreDto dto) {
        Autore a = autoreMapper.autoreDtoToAutore(dto);
        autoreRepository.save(a);
    }

    @GetMapping
    public AutoreDto getAutoreByName(@RequestParam String nome) {
        return autoreMapper.autoreToAutoreDto(autoreRepository.findByNome(nome));
    }

    @GetMapping("all")
    public List<AutoreDto> getAllAutori() {
        return autoreMapper.autoriToAutoriDto(autoreRepository.findAll());
    }
}
