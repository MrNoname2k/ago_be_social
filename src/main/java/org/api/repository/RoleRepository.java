package org.api.repository;

import org.api.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByAuthority(String authority);
    UserRole getByAuthority(String authority);
}
