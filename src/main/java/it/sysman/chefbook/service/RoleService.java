package it.sysman.chefbook.service;

import it.sysman.chefbook.entity.Role;

public interface RoleService {
    Role findByValue(String value);
}
