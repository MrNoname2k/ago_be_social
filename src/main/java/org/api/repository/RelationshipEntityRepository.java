package org.api.repository;

import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RelationshipEntityRepository extends BaseRepository<RelationshipEntity, String> {

//    public List<RelationshipEntity> findAllByUserEntityOneIdAndStatus(String id, String status);
//
//    public List<RelationshipEntity> findAllByUserEntityOneIdOrIdUserEntityTowAndStatus(String idOne, String idTow, String status);

    public RelationshipEntity findOneByStatus(String status);

}
