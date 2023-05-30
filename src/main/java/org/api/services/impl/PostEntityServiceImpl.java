package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.entities.AbumEntity;
import org.api.entities.FileEntity;
import org.api.entities.PostEntity;
import org.api.entities.UserEntity;
import org.api.constants.*;
import org.api.repository.PostEntityRepository;
import org.api.services.*;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.ValidateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class PostEntityServiceImpl implements PostEntityService {

    public static final String ALIAS = "Post";

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AbumEntityService abumEntityService;

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private RelationshipEntityService relationshipEntityService;

    @Override
    public ResultBean createPost(String json, MultipartFile[] files) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PostEntity entity = new PostEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_POST_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        UserEntity userEntity = authenticationService.authentication();
        entity.setUserEntityPost(userEntity);
        if(abumEntityService.existsByTypeAbum(ConstantTypeAbum.POSTS)){
            AbumEntity abumEntity = abumEntityService.findOneByTypeAbum(ConstantTypeAbum.POSTS).get();
            entity.setAbumEntityPost(abumEntity);
        }else {
            AbumEntity abumEntity = abumEntityService.createAbumDefault(ConstantTypeAbum.POSTS);
            entity.setAbumEntityPost(abumEntity);
            //throw new ApiValidateException(ConstantMessage.ID_BKE00037, MessageUtils.getMessage(ConstantMessage.ID_BKE00037));
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        map.put(ConstantColumns.USER_ENTITY, userEntity);
        if (!DataUtil.isLengthImage(files)) {
            for (MultipartFile file: files) {
                String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
                fileEntityService.createFile(entityOld.getAbumEntityPost(), entityOld, fileName);
            }
        }

        return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean createAvatar(String json, MultipartFile file) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PostEntity entity = new PostEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_POST_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        UserEntity userEntity = authenticationService.authentication();
        entity.setUserEntityPost(userEntity);
        if(abumEntityService.existsByTypeAbum(ConstantTypeAbum.AVATAR)){
            AbumEntity abumEntity = abumEntityService.findOneByTypeAbum(ConstantTypeAbum.AVATAR).get();
            entity.setAbumEntityPost(abumEntity);
        }else {
            AbumEntity abumEntity = abumEntityService.createAbumDefault(ConstantTypeAbum.AVATAR);
            entity.setAbumEntityPost(abumEntity);
            //throw new ApiValidateException(ConstantMessage.ID_BKE00035, MessageUtils.getMessage(ConstantMessage.ID_BKE00035));
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        if (!DataUtil.isEmptyImage(file)) {
            String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
            fileEntityService.createFile(entityOld.getAbumEntityPost(), entityOld, fileName);
        }
        return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean createBanner(String json, MultipartFile file) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PostEntity entity = new PostEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_POST_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        UserEntity userEntity = authenticationService.authentication();
        entity.setUserEntityPost(userEntity);
        if(abumEntityService.existsByTypeAbum(ConstantTypeAbum.BANNER)){
            AbumEntity abumEntity = abumEntityService.findOneByTypeAbum(ConstantTypeAbum.BANNER).get();
            entity.setAbumEntityPost(abumEntity);
        }else {
            AbumEntity abumEntity = abumEntityService.createAbumDefault(ConstantTypeAbum.BANNER);
            entity.setAbumEntityPost(abumEntity);
           // throw new ApiValidateException(ConstantMessage.ID_BKE00036, MessageUtils.getMessage(ConstantMessage.ID_BKE00036));
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        if (!DataUtil.isEmptyImage(file)) {
            String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
            fileEntityService.createFile(entityOld.getAbumEntityPost(), entityOld, fileName);
        }
        return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public PostEntity findOneById(String id) throws ApiValidateException, Exception {
        PostEntity entity = postEntityRepository.findById(id).get();
        return entity;
    }

    @Override
    public ResultBean findAllAvatarOrBannerByUser() throws ApiValidateException, Exception {
        List<FileEntity> list = fileEntityService.findAllAvatarOrBannerByUser(authenticationService.authentication().getId(), ConstantTypeAbum.AVATAR);
        return new ResultBean(list, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    private void convertJsonToEntity(JsonObject json, PostEntity entity) throws ApiValidateException {
        if (DataUtil.hasMember(json, ConstantColumns.CONTENT)) {
            entity.setContent(DataUtil.getJsonString(json, ConstantColumns.CONTENT));
        }
        if (DataUtil.hasMember(json, ConstantColumns.ACCESS_MODIFIER_LEVEL)) {
            entity.setAccessModifierLevel(DataUtil.getJsonString(json, ConstantColumns.ACCESS_MODIFIER_LEVEL));
        }
        if (DataUtil.hasMember(json, ConstantColumns.TYPE_POST)) {
            entity.setTypePost(DataUtil.getJsonString(json, ConstantColumns.TYPE_POST));
        }
//        if (DataUtil.hasMember(json, ConstantColumns.ID_ABUM)) {
//            AbumEntity abumEntity = abumEntityService.findOneById(DataUtil.getJsonString(json, ConstantColumns.ID_ABUM));
//            entity.setAbumEntityPost(abumEntity);
//        }
    }
}
