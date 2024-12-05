package it.sysman.esempio.service;

import it.sysman.esempio.dto.AutoreDto;

import java.util.List;

public interface AutoreService {
    void addAutore(AutoreDto dto);
    void editAutore(AutoreDto dto);
    void removeAutore(int id);
    AutoreDto getAutoreByName(String nome);
    List<AutoreDto> getAllAutori();
}
