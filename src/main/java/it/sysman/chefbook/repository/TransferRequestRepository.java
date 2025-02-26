package it.sysman.chefbook.repository;

import it.sysman.chefbook.entity.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequest, Integer> {
    public TransferRequest findByToken(String token);
    public TransferRequest findByRicettaId(int id);
}
