package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.*;
import org.api.entities.*;
import org.api.payload.ResultBean;
import org.api.payload.request.PageableRequest;
import org.api.payload.response.PageResponse;
import org.api.repository.FileEntityRepository;
import org.api.repository.PostEntityRepository;
import org.api.services.*;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.ValidateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class PostEntityServiceImpl implements PostEntityService {

    public static final String ALIAS = "Post";

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlbumEntityService albumEntityService;

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private FileEntityRepository fileEntityRepository;

    @Autowired
    private RelationshipEntityService relationshipEntityService;

    @Autowired
    private NotificationEntityService notificationEntityService;

    @Override
    public ResultBean createPost(String json, MultipartFile[] files) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PostEntity entity = new PostEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_POST_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        UserEntity userEntity = authenticationService.authentication();
        entity.setUserEntityPost(userEntity);
        if (albumEntityService.existsByTypeAlbum(ConstantTypeAlbum.POSTS)) {
            AlbumEntity albumEntity = albumEntityService.findOneByTypeAlbumAndUserEntityId(ConstantTypeAlbum.POSTS, userEntity.getId()).get();
            entity.setAlbumEntityPost(albumEntity);
        } else {
            AlbumEntity albumEntity = albumEntityService.createAlbumDefault(ConstantTypeAlbum.POSTS, userEntity);
            entity.setAlbumEntityPost(albumEntity);
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        map.put(ConstantColumns.USER_ENTITY, userEntity);
        if (!DataUtil.isLengthImage(files)) {
            for (MultipartFile file : files) {
                String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
                fileEntityService.createFile(entityOld.getAlbumEntityPost(), entityOld, fileName);
            }
        }
        NotificationEntity notificationEntity = notificationEntityService.create(entity.getUserEntityPost().getId(), entity.getId(), ConstantNotificationType.POST_CREATE);
        notificationEntityService.sendNotification(notificationEntity);
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
        if (albumEntityService.existsByTypeAlbum(ConstantTypeAlbum.AVATAR)) {
            AlbumEntity albumEntity = albumEntityService.findOneByTypeAlbumAndUserEntityId(ConstantTypeAlbum.AVATAR, userEntity.getId()).get();
            entity.setAlbumEntityPost(albumEntity);
            List<FileEntity> list = fileEntityRepository.findAllByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbum(userEntity.getId(), ConstantTypeAlbum.AVATAR);
            if (!list.isEmpty()) {
                for (FileEntity fileEntity : list) {
                    fileEntity.setIsCurrenAvatar(1);
                    fileEntityRepository.save(fileEntity);
                }
            }
        } else {
            AlbumEntity albumEntity = albumEntityService.createAlbumDefault(ConstantTypeAlbum.AVATAR, userEntity);
            entity.setAlbumEntityPost(albumEntity);
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        if (!DataUtil.isEmptyImage(file)) {
            String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
            fileEntityService.createFile(entityOld.getAlbumEntityPost(), entityOld, fileName);
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
        if (albumEntityService.existsByTypeAlbum(ConstantTypeAlbum.BANNER)) {
            AlbumEntity albumEntity = albumEntityService.findOneByTypeAlbumAndUserEntityId(ConstantTypeAlbum.BANNER, userEntity.getId()).get();
            entity.setAlbumEntityPost(albumEntity);
            List<FileEntity> list = fileEntityRepository.findAllByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbum(userEntity.getId(), ConstantTypeAlbum.BANNER);
            if (!list.isEmpty()) {
                for (FileEntity fileEntity : list) {
                    fileEntity.setIsCurrenAvatar(1);
                    fileEntityRepository.save(fileEntity);
                }
            }
        } else {
            AlbumEntity albumEntity = albumEntityService.createAlbumDefault(ConstantTypeAlbum.BANNER, userEntity);
            entity.setAlbumEntityPost(albumEntity);
        }
        PostEntity entityOld = postEntityRepository.save(entity);
        map.put(ConstantColumns.POST_ENTITY, entityOld);
        if (!DataUtil.isEmptyImage(file)) {
            String fileName = firebaseService.uploadImage(file, entityOld.getId(), ConstantFirebase.FIREBASE_STORAGE_USER + userEntity.getId());
            fileEntityService.createFile(entityOld.getAlbumEntityPost(), entityOld, fileName);
        }
        return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public PostEntity findOneById(String id) throws ApiValidateException, Exception {
        PostEntity entity = postEntityRepository.findById(id).get();
        return entity;
    }

    @Override
    public ResultBean findAllByUserEntityPostId(int size, String idUser) throws ApiValidateException, Exception {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setSize(size);
        Page<PostEntity> pagePostEntity = postEntityRepository.findAllByUserEntityPostId(idUser, pageableRequest.getPageable());
        PageResponse<PostEntity> pageResponse = new PageResponse<>();
        pageResponse.setResultPage(pagePostEntity);
        return new ResultBean(pageResponse.getResults(), ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean findAllByUserEntityPostIdIn(int size, String idUser) throws ApiValidateException, Exception {
        List<RelationshipEntity> listFriends = relationshipEntityService.findAllByUserEntityOneIdOrUserEntityTowAndStatus(idUser, idUser, ConstantRelationshipStatus.FRIEND);
        if (!listFriends.isEmpty()) {
            List<String> listIdFriend = new ArrayList<>();
            for (RelationshipEntity friend : listFriends) {
                if (friend.getUserEntityOne().getId().equals(idUser))
                    listIdFriend.add(friend.getUserEntityTow().getId());
                else if (friend.getUserEntityTow().getId().equals(idUser))
                    listIdFriend.add(friend.getUserEntityOne().getId());
            }
            PageableRequest pageableRequest = new PageableRequest();
            pageableRequest.setSize(size);
            Page<PostEntity> pagePostEntity = postEntityRepository.findAllByUserEntityPostIdIn(listIdFriend, pageableRequest.getPageable());
            PageResponse<PostEntity> pageResponse = new PageResponse<>();
            if (pagePostEntity.hasContent()) {
                pageResponse.setResultPage(pagePostEntity);
            } else {
                pageResponse.setResultPage(null);
            }
            return new ResultBean(pageResponse.getResults(), ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
        }
        return null;
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
    }
}
