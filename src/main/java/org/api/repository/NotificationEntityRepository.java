package org.api.repository;

import org.api.entities.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationEntityRepository extends BaseRepository<NotificationEntity, String> {

    public Page<NotificationEntity> findAllByPostEntityUserEntityPostId(String idUser, Pageable pageable);

}
