package org.api.repository;

import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RelationshipEntityRepository extends BaseRepository<RelationshipEntity, String> {

    @Query("SELECT r FROM RelationshipEntity r WHERE r.userEntityOne.id = ?1 and r.status = 'friend'")
    public List<RelationshipEntity> getRelationshipEntityByUserEntityOne(String ownerId);
}
