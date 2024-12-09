package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.AutoreDto;

import java.util.List;

public interface AutoreService {
    void addAutore(AutoreDto dto);
    boolean editAutore(AutoreDto dto);
    boolean removeAutore(int id);
    AutoreDto getAutoreByName(String nome);
    AutoreDto getAutoreByEmail(String email);
    List<AutoreDto> getAllAutori();
}
