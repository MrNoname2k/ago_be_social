package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantColumns;
import org.api.constants.ConstantJsonFileValidate;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.entities.CommentEntity;
import org.api.entities.PostEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.repository.CommentEntityRepository;
import org.api.services.AuthenticationService;
import org.api.services.CommentEntityService;
import org.api.services.PostEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.ValidateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class CommentEntityServiceImpl implements CommentEntityService {

    @Autowired
    private CommentEntityRepository commentEntityRepository;

    @Autowired
    private PostEntityService postEntityService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResultBean createComment(String json) throws ApiValidateException, Exception {
        UserEntity userEntity = authenticationService.authentication();
        CommentEntity entity = new CommentEntity();
        entity.setUserEntityComment(userEntity);
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_LIKE_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        CommentEntity entityOld = commentEntityRepository.save(entity);
        return new ResultBean(entityOld, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    private void convertJsonToEntity(JsonObject json, CommentEntity entity) throws ApiValidateException, Exception {
        if (DataUtil.hasMember(json, ConstantColumns.CONTENT)) {
            entity.setContent(DataUtil.getJsonString(json, ConstantColumns.CONTENT));
        }
        if (DataUtil.hasMember(json, ConstantColumns.ID_COMMENT)) {
            entity.setIdComment(DataUtil.getJsonString(json, ConstantColumns.ID_COMMENT));
        }
        if (DataUtil.hasMember(json, ConstantColumns.ID_POST)) {
            PostEntity postEntity = postEntityService.findOneById(DataUtil.getJsonString(json, ConstantColumns.ID_POST));
            entity.setPostEntity(postEntity);
        }
    }

}
