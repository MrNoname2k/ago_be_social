package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantRelationshipStatus;
import org.api.constants.ConstantStatus;
import org.api.entities.NotificationEntity;
import org.api.entities.PostEntity;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.payload.response.HomePageResponse;
import org.api.services.*;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class AgoServiceImpl implements AgoService {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private RelationshipEntityService relationshipEntityService;

    @Autowired
    private PostEntityService postEntityService;

    @Autowired
    private NotificationEntityService notificationEntityService;

    @Override
    public ResultBean homePage(String idUser) throws ApiValidateException, Exception {
        ResultBean resultBeanUser = userEntityService.getById(idUser);
        UserEntity userEntity = (UserEntity) resultBeanUser.getData();

        List<RelationshipEntity> relationshipEntityList = relationshipEntityService.findAllByUserEntityOneIdOrUserEntityTowAndStatus(idUser,idUser, ConstantRelationshipStatus.FRIEND);

        ResultBean resultBeanPost = postEntityService.findAllByUserEntityPostIdIn(10, idUser);
        Page<PostEntity> postEntityPage = (Page<PostEntity>) resultBeanPost.getData();

        ResultBean resultBeanNotification = notificationEntityService.findAllByPostEntityUserEntityPostId(10,idUser);
        Page<NotificationEntity> notificationEntityPage = (Page<NotificationEntity>) resultBeanNotification.getData();

        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.setUserEntity(userEntity);
        homePageResponse.setRelationshipEntities(relationshipEntityList);
        homePageResponse.setPostEntityPage(postEntityPage);
        homePageResponse.setNotificationEntityPage(notificationEntityPage);
        return new ResultBean(homePageResponse, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }
}
