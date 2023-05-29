package org.api.repository;

import org.api.entities.LikeEntity;

import java.util.Optional;

public interface LikeEntityRepository extends BaseRepository<LikeEntity, String> {

    public Optional<LikeEntity> findOneByPostEntityIdAndUserEntityLikeId(String idPost, String idUser);

}
