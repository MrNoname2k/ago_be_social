package org.api.repository;

import org.api.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRoleEntity, String> {
    UserRoleEntity findByAuthority(String authority);
    UserRoleEntity getByAuthority(String authority);
}
