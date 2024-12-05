package it.sysman.chefbook.dto;

import lombok.Data;

@Data
public class RicettaDto {
    private String nome;
    private String descrizione;
    private boolean privacy;
    private Integer autoreId;
}
