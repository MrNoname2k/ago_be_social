package org.api.services.impl;

import org.api.entities.UserRole;
import org.api.repository.RoleRepository;
import org.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean persist(UserRole role) throws Exception {
        return this.roleRepository.save(role) != null;
    }

    @Override
    public UserRole getByName(String name) {
        return this.roleRepository.findByAuthority(name);
    }
}
