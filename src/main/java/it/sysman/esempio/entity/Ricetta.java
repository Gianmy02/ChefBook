package it.sysman.esempio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ricetta {
    @Id
    @GeneratedValue
    private int id;

    private String nome;
    private String descrizione;
    private boolean privacy;
    @ManyToOne
    @JoinColumn(name = "autore")
    private Autore autore;

}
