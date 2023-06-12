package org.api.repository;

import org.api.entities.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface NotificationEntityRepository extends BaseRepository<NotificationEntity, String> {

    @Query("select n FROM NotificationEntity n where n.postEntity.userEntityPost.id = ?1")
    public Page<NotificationEntity> findAllByPostEntityUserEntityPostId(String idUser, Pageable pageable);

}
