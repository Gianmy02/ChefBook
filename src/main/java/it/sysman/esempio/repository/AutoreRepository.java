package it.sysman.esempio.repository;

import it.sysman.esempio.entity.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, Integer> {
    Autore findByNome(String nome);
}
