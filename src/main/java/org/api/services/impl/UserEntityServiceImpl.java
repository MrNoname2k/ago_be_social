package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.*;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.repository.UserEntityRepository;
import org.api.services.FirebaseService;
import org.api.services.UserEntityService;
import org.api.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * [OVERVIEW] UserEntityServiceImpl.
 *
 * @author: (DAI)
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       5/15/2023 7:37 PM      (DAI)        Create new
*/

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class UserEntityServiceImpl implements UserEntityService {

    public static final String ALIAS = "User";

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public ResultBean createUser(String json) throws ApiValidateException, Exception {
        UserEntity entity = new UserEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_USER_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntity(jsonObject, entity);
        if(userEntityRepository.existsByMail(entity.getMail())){
            throw new ApiValidateException(ConstantMessage.ID_ERR00003, MessageUtils.getMessage(ConstantMessage.ID_ERR00003));
        }
        UserEntity entityOld = userEntityRepository.save(entity);
        return new ResultBean(entityOld, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean updateUser(String json) throws ApiValidateException, Exception {
        return null;
    }

    @Override
    public ResultBean getById(String id) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<UserEntity> userOptional = userEntityRepository.findOneById(id);
        if (userOptional.isPresent()) {
            map.put(ConstantColumns.USER_ENTITY, userOptional.get());
            return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
        } else {
            throw new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, ALIAS)));
        }
    }

    @Override
    public ResultBean getByMail(String json) throws ApiValidateException, Exception {
        return null;
    }

    @Override
    public ResultBean getAll() throws ApiValidateException, Exception {
        return null;
    }

    @Override
    public UserEntity updateLastLogin(String mail) throws ApiValidateException, Exception {
        Optional<UserEntity> userOptional = userEntityRepository.findOneByMail(mail);
        if (userOptional.isPresent()) {
            userOptional.get().setLastLoginDate(new Date());
            UserEntity entityOld = userEntityRepository.save(userOptional.get());
            return entityOld;
        } else {
            throw new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, ALIAS)));
        }
    }

    @Override
    public ResultBean updateAvatar(MultipartFile file, String id) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<UserEntity> userOptional = userEntityRepository.findOneById(id);
        if (userOptional.isPresent()) {
            ValidateData.check(file);
            String fileName = firebaseService.uploadImage(file, id, ConstantFirebase.FIREBASE_STORAGE_USER + id);
            map.put(ConstantColumns.IMAGE, fileName);
            //userOptional.get().setAvatarUrl(fileName);
            UserEntity entityOld = userEntityRepository.save(userOptional.get());
            map.put(ConstantColumns.USER_ENTITY, entityOld);
            return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
        } else {
            throw new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, ALIAS)));
        }
    }

    @Override
    public ResultBean updateBanner(MultipartFile file, String id) throws ApiValidateException, Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<UserEntity> userOptional = userEntityRepository.findOneById(id);
        if (userOptional.isPresent()) {
            ValidateData.check(file);
            String fileName = firebaseService.uploadImage(file, id, ConstantFirebase.FIREBASE_STORAGE_USER + id);
            map.put(ConstantColumns.IMAGE, fileName);
            //userOptional.get().setBannerUrl(fileName);
            UserEntity entityOld = userEntityRepository.save(userOptional.get());
            map.put(ConstantColumns.USER_ENTITY, entityOld);
            return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
        } else {
            throw new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, ALIAS)));
        }
    }

    @Override
    public UserEntity findOneByMail(String mail) throws ApiValidateException, Exception {
        UserEntity entityOld = userEntityRepository.findOneByMail(mail).get();
        return entityOld;
    }

    @Override
    public UserEntity updateOnline(String mail, String online) throws ApiValidateException, Exception {
        Optional<UserEntity> userOptional = userEntityRepository.findOneByMail(mail);
        if (userOptional.isPresent()) {
            userOptional.get().setOnline(online);
            UserEntity entityOld = userEntityRepository.save(userOptional.get());
            return entityOld;
        } else {
            throw new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, ALIAS)));
        }
    }

    private void convertJsonToEntity(JsonObject json, UserEntity entity) throws ApiValidateException {
        if (DataUtil.hasMember(json, ConstantColumns.FULL_NAME)) {
            entity.setFullName(DataUtil.getJsonString(json, ConstantColumns.FULL_NAME));
        }
        if (DataUtil.hasMember(json, ConstantColumns.MAIL)) {
            entity.setMail(DataUtil.getJsonString(json, ConstantColumns.MAIL));
        }
        if (DataUtil.hasMember(json, ConstantColumns.PHONE)) {
            entity.setPhone(DataUtil.getJsonString(json, ConstantColumns.PHONE));
        }
        if (DataUtil.hasMember(json, ConstantColumns.ADDRESS)) {
            entity.setAddress(DataUtil.getJsonString(json, ConstantColumns.ADDRESS));
        }
        if (DataUtil.hasMember(json, ConstantColumns.BIRTHDAY)) {
            entity.setBirthDay(DataUtil.getJsonString(json, ConstantColumns.BIRTHDAY));
        }
        if (DataUtil.hasMember(json, ConstantColumns.GENDER)) {
            entity.setGender(DataUtil.getJsonString(json, ConstantColumns.GENDER));
        }
        if (DataUtil.hasMember(json, ConstantColumns.LINK_IG)) {
            entity.setLinkIg(DataUtil.getJsonString(json, ConstantColumns.LINK_IG));
        }
        if (DataUtil.hasMember(json, ConstantColumns.LINK_YOUTUBE)) {
            entity.setLinkYoutube(DataUtil.getJsonString(json, ConstantColumns.LINK_YOUTUBE));
        }
        if (DataUtil.hasMember(json, ConstantColumns.LINK_TIKTOK)) {
            entity.setLinkTiktok(DataUtil.getJsonString(json, ConstantColumns.LINK_TIKTOK));
        }
    }
}
