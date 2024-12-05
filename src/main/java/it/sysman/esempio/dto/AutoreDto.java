package it.sysman.esempio.dto;

import it.sysman.esempio.entity.Ricetta;
import lombok.Data;

import java.util.List;

@Data
public class AutoreDto {
    private String nome;
    private String cognome;
    private String sesso;
    private List<Ricetta> ricette;
}
