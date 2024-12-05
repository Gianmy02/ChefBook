package it.sysman.chefbook.repository;

import it.sysman.chefbook.entity.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Integer> {
    Ricetta findByNome(String nome);
}
