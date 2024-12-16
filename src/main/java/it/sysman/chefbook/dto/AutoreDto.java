package it.sysman.chefbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoreDto {
    private int id;
    private String nome;
    private String cognome;
    private String sesso;
    private String email;
    private String password;
}
