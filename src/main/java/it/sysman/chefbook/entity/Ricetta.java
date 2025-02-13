package it.sysman.chefbook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ricetta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String descrizione;
    private boolean privacy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autore")
    private Autore autore;

}
