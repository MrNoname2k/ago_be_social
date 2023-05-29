package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.repository.RelationshipEntityRepository;
import org.api.services.RelationshipEntityService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })

public class RelationshipEntityServiceImpl implements RelationshipEntityService {

    @Autowired
    private RelationshipEntityRepository relationshipEntityRepository;

    @Override
    public List<RelationshipEntity> getRelationshipsByOwnerPost(String ownerId) throws ApiValidateException {
        List<RelationshipEntity> listFriends = relationshipEntityRepository.getRelationshipEntityByUserEntityOne(ownerId);
        return listFriends;
    }
}
