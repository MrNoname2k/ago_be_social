package org.api.repository;

import org.api.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends BaseRepository<UserEntity, String> {

    public Optional<UserEntity> findOneById(String id);

    public Optional<UserEntity> findOneByMail(String mail);

    public Boolean existsByMail(String mail);
    
}
