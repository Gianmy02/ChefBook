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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "\"value\"")
    private String value;
    @OneToMany(mappedBy = "role", orphanRemoval = true)
    List<User> users;
}
