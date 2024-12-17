package it.sysman.chefbook.dto;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}")
    private String email;
    private String password;
}
