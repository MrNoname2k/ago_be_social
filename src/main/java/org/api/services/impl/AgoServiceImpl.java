package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantColumns;
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
import org.api.repository.RelationshipEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.*;
import org.api.utils.ApiValidateException;
import org.api.utils.ItemNameUtils;
import org.api.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class AgoServiceImpl implements AgoService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RelationshipEntityRepository relationshipEntityRepository;

    @Autowired
    private PostEntityService postEntityService;

    @Autowired
    private NotificationEntityService notificationEntityService;


    @Override
    public ResultBean homePage(String idUser) throws ApiValidateException, Exception {
        UserEntity userEntity = userEntityRepository.findOneById(idUser).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Home"))));

        List<RelationshipEntity> relationshipEntityList = relationshipEntityRepository.findAllByUserEntityOneIdOrUserEntityTowIdAndStatus(userEntity.getId(), ConstantRelationshipStatus.FRIEND);
        if(relationshipEntityList.isEmpty()) {
            new ApiValidateException(ConstantMessage.ID_ERR00005, "Relationships",
                    MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName("Relationships", "Home")));
        }

        PostPageResponse postEntityPage = postEntityService.findAllByUserEntityPostIdInPage(10, userEntity.getId());
        NotifiPageResponse notificationEntityPage = notificationEntityService.findAllByPostEntityUserEntityPostIdPage(10, userEntity.getId());

        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.setUserEntity(userEntity);
        homePageResponse.setRelationshipEntities(relationshipEntityList);
        homePageResponse.setPostEntityPage(postEntityPage);
        homePageResponse.setNotificationEntityPage(notificationEntityPage);
        return new ResultBean(homePageResponse, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }
}
