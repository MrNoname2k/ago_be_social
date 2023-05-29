package org.api.services;

import org.api.entities.AbumEntity;

import java.util.Optional;

public interface AbumEntityService {

    public AbumEntity createAbumDefault(String type);

    public Boolean existsByTypeAbum(String tpeAbum);

    public AbumEntity findOneById(String id);

    public Optional<AbumEntity> findOneByTypeAbum(String tpeAbum);


}
