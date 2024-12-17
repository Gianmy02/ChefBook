package it.sysman.chefbook.repository;

import it.sysman.chefbook.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByValue(String value);
}
