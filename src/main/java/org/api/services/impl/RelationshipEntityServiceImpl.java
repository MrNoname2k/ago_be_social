package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantColumns;
import org.api.constants.ConstantJsonFileValidate;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.entities.RelationshipEntity;
import org.api.payload.ResultBean;
import org.api.repository.RelationshipEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.RelationshipEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.ValidateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class RelationshipEntityServiceImpl implements RelationshipEntityService {

    @Autowired
    private RelationshipEntityRepository relationshipEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public ResultBean findAllByUserEntityOneIdAndStatus(String id, String status) {
        List<RelationshipEntity> lists = relationshipEntityRepository.findAllByUserEntityOneIdAndStatus(id, status);
        return new ResultBean(lists, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean friendOrUnFriend(String json, String status) throws ApiValidateException, Exception {
        RelationshipEntity entity = new RelationshipEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_RELATIONSHIP_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity, status);
        RelationshipEntity entityOld = relationshipEntityRepository.save(entity);
        return new ResultBean(entityOld, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public List<RelationshipEntity> findAllByUserEntityOneIdOrUserEntityTowAndStatus(String idOne, String idTow, String status) {
        List<RelationshipEntity> lists = relationshipEntityRepository.findAllByUserEntityOneIdOrUserEntityTowIdAndStatus(idOne, idTow, status);
        if (lists.isEmpty())
            return null;
        return lists;
    }

    private void convertJsonToEntity(JsonObject json, RelationshipEntity entity, String status) throws ApiValidateException {
        entity.setStatus(status);
        if (DataUtil.hasMember(json, ConstantColumns.ID_USER_ONE)) {
            entity.setUserEntityOne(userEntityRepository.findOneById(DataUtil.getJsonString(json, ConstantColumns.ID_USER_ONE)).get());
        }
        if (DataUtil.hasMember(json, ConstantColumns.ID_USER_TOW)) {
            entity.setUserEntityTow(userEntityRepository.findOneById(DataUtil.getJsonString(json, ConstantColumns.ID_USER_TOW)).get());
        }
    }
}
