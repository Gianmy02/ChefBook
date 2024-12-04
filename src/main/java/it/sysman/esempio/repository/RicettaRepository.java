package it.sysman.esempio.repository;

import it.sysman.esempio.entity.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Integer> {
    Ricetta findByNome(String nome);
}
