package it.sysman.esempio.service;


import it.sysman.esempio.entity.Ricetta;

import java.util.List;

public interface RicettaService {
    void addRicetta(Ricetta r);
    void editRicetta(Ricetta r);
    void removeRicetta(int id);
    Ricetta getRicettaByName(String nome);
    List<Ricetta> getAllRicette();
}
