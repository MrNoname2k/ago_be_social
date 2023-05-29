package org.api.repository;

import org.api.entities.AbumEntity;

import java.util.Optional;

public interface AbumEntityRepository extends BaseRepository<AbumEntity, String> {

    public Boolean existsByTypeAbum(String tpeAbum);

    public Optional<AbumEntity> findOneByTypeAbum(String tpeAbum);

}
