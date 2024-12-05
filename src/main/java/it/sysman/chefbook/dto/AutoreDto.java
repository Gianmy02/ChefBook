package it.sysman.chefbook.dto;

import it.sysman.chefbook.entity.Ricetta;
import lombok.Data;

import java.util.List;

@Data
public class AutoreDto {
    private String nome;
    private String cognome;
    private String sesso;
    private List<Ricetta> ricette;
}
