package org.api.repository;

import org.api.entities.LikeEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeEntityRepository extends BaseRepository<LikeEntity, String> {

    @Query("SELECT l FROM LikeEntity l WHERE l.postEntity.id = ?1 AND l.userEntityLike.id = ?2")
    public Optional<LikeEntity> findOneByPostEntityIdAndUserEntityLikeId(String idPost, String idUser);

    @Query("select count(l) from LikeEntity  l")
    public long countAllLike();

    @Query("select count(l) from LikeEntity l where month(l.createDate) =?1 and year(l.createDate)=?2")
    public long countLikeByMonth(int month, int year);

    @Query("select count(l) from LikeEntity l where day(l.createDate)=?1 and month(l.createDate) =?2 and year(l.createDate)=?3")
    public long countLikeToday(int day, int month, int year);
}
