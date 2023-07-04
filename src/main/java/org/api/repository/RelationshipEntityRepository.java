package org.api.repository;

import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RelationshipEntityRepository extends BaseRepository<RelationshipEntity, String> {

    public List<RelationshipEntity> findAllByUserEntityOneIdAndStatus(String id, String status);

    @Query("select r from RelationshipEntity r WHERE (r.userEntityOne.id = ?1 and r.status = ?2) or (r.userEntityTow.id = ?1 and r.status = ?2)")
    public List<RelationshipEntity> findAllByUserEntityOneIdOrUserEntityTowIdAndStatus(String idOne, String status);

}
