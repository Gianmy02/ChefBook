package it.sysman.chefbook.service;

import it.sysman.chefbook.entity.Role;
import it.sysman.chefbook.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByValue(String value){
        return roleRepository.findByValue(value);
    }
}
