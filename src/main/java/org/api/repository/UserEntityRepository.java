package org.api.repository;

import org.api.entities.UserEntity;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends BaseRepository<UserEntity, String> {

    public Optional<UserEntity> findOneById(String id);

    public Optional<UserEntity> findOneByMail(String mail);

    public Boolean existsByMail(String mail);

    @Query("SELECT u FROM UserEntity u WHERE u.mail = ?1 AND u.id <> ?2")
    public Optional<UserEntity> existsByMailAndId(String mail, String id);
    
}
