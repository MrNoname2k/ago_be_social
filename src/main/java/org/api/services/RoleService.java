package org.api.services;

import org.api.entities.UserRole;

public interface RoleService {

    boolean persist(UserRole role) throws Exception;

    UserRole getByName(String name);
}
