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
import org.api.payload.response.NotifiPageResponse;
import org.api.payload.response.PageResponse;
import org.api.payload.response.PostPageResponse;
import org.api.repository.NotificationEntityRepository;
import org.api.repository.PostEntityRepository;
import org.api.services.*;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private NotificationEntityRepository notificationEntityRepository;

    @Override
    public ResultBean homePage(String idUser) throws ApiValidateException, Exception {
        ResultBean resultBeanUser = userEntityService.getById(idUser);
        UserEntity userEntity = (UserEntity) resultBeanUser.getData();

        List<RelationshipEntity> relationshipEntityList = relationshipEntityService.findAllByUserEntityOneIdOrUserEntityTowAndStatus(idUser, ConstantRelationshipStatus.FRIEND);

//        ResultBean resultBeanPost = postEntityService.findAllByUserEntityPostIdIn(10, idUser);
//        List<PostEntity> postEntityPage = (List<PostEntity>) resultBeanPost.getData();
//
//        ResultBean resultBeanNotification = notificationEntityService.findAllByPostEntityUserEntityPostId(10, idUser);
//        List<NotificationEntity> notificationEntityPage = (List<NotificationEntity>) resultBeanNotification.getData();
        PostPageResponse postEntityPage = postEntityService.findAllByUserEntityPostIdInPage(10, idUser);
        NotifiPageResponse notificationEntityPage = notificationEntityService.findAllByPostEntityUserEntityPostIdPage(10, idUser);


        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.setUserEntity(userEntity);
        homePageResponse.setRelationshipEntities(relationshipEntityList);
        homePageResponse.setPostEntityPage(postEntityPage);
        homePageResponse.setNotificationEntityPage(notificationEntityPage);
        return new ResultBean(homePageResponse, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }
}
