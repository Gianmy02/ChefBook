package it.sysman.esempio.service;


import it.sysman.esempio.dto.RicettaDto;


import java.util.List;

public interface RicettaService {
    void addRicetta(RicettaDto r);
    void editRicetta(RicettaDto r);
    void removeRicetta(int id);
    RicettaDto getRicettaByName(String nome);
    List<RicettaDto> getAllRicette();
}
