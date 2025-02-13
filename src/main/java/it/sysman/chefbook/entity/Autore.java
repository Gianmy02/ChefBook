package it.sysman.chefbook.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String email;
    private String nome;
    private String cognome;
    private String sesso;
    @OneToMany(mappedBy = "autore", orphanRemoval = true)
    private List<Ricetta> ricette;
}
