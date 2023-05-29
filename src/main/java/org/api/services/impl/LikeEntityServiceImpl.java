package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.entities.LikeEntity;
import org.api.entities.PostEntity;
import org.api.entities.UserEntity;
import org.api.constants.ConstantColumns;
import org.api.constants.ConstantJsonFileValidate;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.repository.LikeEntityRepository;
import org.api.services.AuthenticationService;
import org.api.services.LikeEntityService;
import org.api.services.PostEntityService;
import org.api.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class LikeEntityServiceImpl implements LikeEntityService {

    @Autowired
    private LikeEntityRepository likeEntityRepository;

    @Autowired
    private PostEntityService postEntityService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResultBean likeOrUnlike(String json) throws ApiValidateException, Exception {
        UserEntity userEntity = authenticationService.authentication();
        LikeEntity entityOld = null;
        LikeEntity entity = new LikeEntity();
        entity.setUserEntityLike(userEntity);
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_LIKE_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        Optional<LikeEntity> likeEntityOptional = likeEntityRepository.findOneByPostEntityIdAndUserEntityLikeId(entity.getPostEntity().getId(),entity.getUserEntityLike().getId());
        if(likeEntityOptional.isEmpty() && entity.getDelFlg() == 0){
            entity.setDelFlg(0);
            entityOld = likeEntityRepository.save(entity);
        }else if(!likeEntityOptional.isEmpty() && entity.getDelFlg() == 1){
                likeEntityOptional.get().setDelFlg(1);
                entityOld = likeEntityRepository.save(likeEntityOptional.get());
        }
        return new ResultBean(entityOld, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    private void convertJsonToEntity(JsonObject json, LikeEntity entity) throws ApiValidateException, Exception {
        if (DataUtil.hasMember(json, ConstantColumns.ID_POST)) {
            PostEntity postEntity = postEntityService.findOneById(DataUtil.getJsonString(json, ConstantColumns.ID_POST));
            entity.setPostEntity(postEntity);
        }
        if (DataUtil.hasMember(json, ConstantColumns.DEL_FLG)) {
            entity.setDelFlg(DataUtil.getJsonInteger(json, ConstantColumns.DEL_FLG));
        }
    }

}
