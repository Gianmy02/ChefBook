package it.sysman.chefbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RicettaDto {
    private int id;
    private String nome;
    private String descrizione;
    private boolean privacy;
    private Integer autoreId;
}
