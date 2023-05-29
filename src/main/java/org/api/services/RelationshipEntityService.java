package org.api.services;

import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.utils.ApiValidateException;

import java.util.List;

public interface RelationshipEntityService {
    public List<RelationshipEntity> getRelationshipsByOwnerPost(String ownerId) throws ApiValidateException;
}
