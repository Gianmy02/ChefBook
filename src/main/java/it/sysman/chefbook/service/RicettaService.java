package it.sysman.chefbook.service;


import it.sysman.chefbook.dto.RicettaDto;


import java.util.List;

public interface RicettaService {
    void addRicetta(RicettaDto r);
    boolean editRicetta(RicettaDto r);
    boolean removeRicetta(int id);
    RicettaDto getRicettaByName(String nome);
    List<RicettaDto> getAllRicette();
}
