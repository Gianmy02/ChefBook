package it.sysman.chefbook.repository;

import it.sysman.chefbook.entity.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, Integer> {
    Autore findByNome(String nome);
}
