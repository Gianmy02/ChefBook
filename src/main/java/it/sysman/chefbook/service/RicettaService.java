package it.sysman.chefbook.service;


import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.dto.TransferRequestDto;


import java.util.List;

public interface RicettaService {
    void addRicetta(RicettaDto r);
    boolean editRicetta(RicettaDto r);
    boolean removeRicetta(int id);
    RicettaDto getRicettaByName(String nome);
    List<RicettaDto> getAllRicette();
    void transferRicetta(TransferRequestDto dto);
    void acceptRicetta(String token);
    void declineRicetta(String token);
    void revokeTransferRicetta(String token);
}
