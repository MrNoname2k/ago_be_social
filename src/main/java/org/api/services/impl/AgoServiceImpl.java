package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantColumns;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantRelationshipStatus;
import org.api.constants.ConstantStatus;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.payload.response.*;
import org.api.payload.response.homePageResponses.HomePageResponse;
import org.api.payload.response.homePageResponses.NotifiHomePageResponse;
import org.api.payload.response.homePageResponses.PostHomePageResponse;
import org.api.repository.RelationshipEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.AgoService;
import org.api.services.NotificationEntityService;
import org.api.services.PostEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.ItemNameUtils;
import org.api.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResultBean homePage(String idUser) throws ApiValidateException, Exception {
        UserEntity userEntity = userEntityRepository.findOneById(idUser).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Home"))));

        List<RelationshipEntity> relationshipEntityList = relationshipEntityRepository.findAllByUserEntityOneIdOrUserEntityTowIdAndStatus(userEntity.getId(), ConstantRelationshipStatus.FRIEND);

        List<RelationshipResponse> responseList = relationshipEntityList.stream().map((relationshipEntity -> modelMapper.map(relationshipEntity, RelationshipResponse.class))).collect(Collectors.toList());

        PostHomePageResponse postEntityPage = postEntityService.findAllByUserEntityPostIdInPage(10, userEntity.getId());
        NotifiHomePageResponse notificationEntityPage = notificationEntityService.findAllByPostEntityUserEntityPostIdPage(10, userEntity.getId());

        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.setRelationshipEntities(responseList);
        homePageResponse.setPostEntityPage(postEntityPage);
        homePageResponse.setNotificationEntityPage(notificationEntityPage);
        return new ResultBean(homePageResponse, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }
}
