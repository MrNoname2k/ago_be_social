package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.*;
import org.api.entities.NotificationEntity;
import org.api.entities.PostEntity;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.payload.WebNotification;
import org.api.payload.request.PageableRequest;
import org.api.payload.response.homePageResponses.NotificationHomeResponse;
import org.api.payload.response.homePageResponses.NotifiHomePageResponse;
import org.api.repository.NotificationEntityRepository;
import org.api.repository.PostEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.NotificationEntityService;
import org.api.services.RelationshipEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.ItemNameUtils;
import org.api.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class NotificationEntityServiceImpl implements NotificationEntityService {

    @Autowired
    private NotificationEntityRepository notificationEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RelationshipEntityService relationshipEntityService;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public NotificationEntity create(String idUser, String idPost, String type) throws ApiValidateException, Exception{
        UserEntity userEntity = userEntityRepository.findOneById(idUser).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Notification"))));
        PostEntity postEntity = postEntityRepository.findOneById(idPost).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00002, ConstantColumns.POST_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00002, null, ItemNameUtils.getItemName(ConstantColumns.POST_ID, "Notification"))));
        NotificationEntity entity = new NotificationEntity();
        entity.setUserEntity(userEntity);
        entity.setPostEntity(postEntity);
        entity.setType(type);
        if (type.equals(ConstantNotificationType.POST_AVATAR))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_001, null, new Object[]{entity.getUserEntity().getFullName(), entity.getPostEntity().getContent()}));
        else if (type.equals(ConstantNotificationType.POST_BANNER))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_002, null, new Object[]{entity.getUserEntity().getFullName(), entity.getPostEntity().getContent()}));
        else if (type.equals(ConstantNotificationType.POST_CREATE))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_003, null, new Object[]{entity.getUserEntity().getFullName(), entity.getPostEntity().getContent()}));
        else if (type.equals(ConstantNotificationType.LIKE))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_004, null, new Object[]{entity.getUserEntity().getFullName(), entity.getPostEntity().getContent()}));
        else if (type.equals(ConstantNotificationType.COMMENT))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_005, null, new Object[]{entity.getUserEntity().getFullName(), entity.getPostEntity().getContent()}));
        NotificationEntity entityOld = notificationEntityRepository.save(entity);
        return entityOld;
    }

    @Override
    public void sendNotification(NotificationEntity notificationEntity) throws ApiValidateException, Exception{
        if (notificationEntity.getType().equals(ConstantNotificationType.LIKE) || notificationEntity.getType().equals(ConstantNotificationType.COMMENT)) {
            WebNotification webNotification = new WebNotification();
            webNotification.setUserEntity(notificationEntity.getPostEntity().getUserEntityPost());
            String url = "/user/" + notificationEntity.getPostEntity().getUserEntityPost().getMail() + "/notifications";
            webNotification.setContent(notificationEntity.getContent());
            messagingTemplate.convertAndSend(url, webNotification);
        } else {
            List<RelationshipEntity> listFriends = relationshipEntityService.findAllByUserEntityOneIdOrUserEntityTowAndStatus(notificationEntity.getUserEntity().getId(), ConstantRelationshipStatus.FRIEND);
            if (!listFriends.isEmpty()) {
                for (RelationshipEntity friend : listFriends) {
                    if (friend.getUserEntityOne().getId().equals(notificationEntity.getUserEntity().getId())) {
                        WebNotification webNotification = new WebNotification();
                        webNotification.setUserEntity(friend.getUserEntityTow());
                        String url = "/user/" + friend.getUserEntityTow().getMail() + "/notifications";
                        webNotification.setContent(notificationEntity.getContent());
                        messagingTemplate.convertAndSend(url, webNotification);
                    } else if (friend.getUserEntityTow().getId().equals(notificationEntity.getUserEntity().getId())) {
                        WebNotification webNotification = new WebNotification();
                        webNotification.setUserEntity(friend.getUserEntityOne());
                        String url = "/user/" + friend.getUserEntityOne().getMail() + "/notifications";
                        webNotification.setContent(notificationEntity.getContent());
                        messagingTemplate.convertAndSend(url, webNotification);
                    }
                }
            }
        }
    }

    @Override
    public ResultBean findAllByPostEntityUserEntityPostId(int size, String idUser) throws ApiValidateException, Exception {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setSize(size);
        pageableRequest.setSort(Sort.by("id").ascending());
        pageableRequest.setPage(0);
        Page<NotificationEntity> notificationEntityPage = notificationEntityRepository.findAllByPostEntityUserEntityPostId(idUser, pageableRequest.getPageable());
        List<NotificationEntity> notificationEntities = notificationEntityPage.getContent();
        List<NotificationHomeResponse> responses = notificationEntities.stream().map(notificationEntity -> modelMapper.map(notificationEntity, NotificationHomeResponse.class)).collect(Collectors.toList());
        NotifiHomePageResponse pageResponse = new NotifiHomePageResponse();
        if (notificationEntityPage.hasContent()) {
            pageResponse.setResults(responses);
            pageResponse.setCurrentPage(notificationEntityPage.getNumber());
            pageResponse.setNoRecordInPage(notificationEntityPage.getSize());
            pageResponse.setTotalPage(notificationEntityPage.getTotalPages());
            pageResponse.setTotalRecords(notificationEntityPage.getTotalElements());
        }
        return new ResultBean(pageResponse, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public NotifiHomePageResponse findAllByPostEntityUserEntityPostIdPage(int size, String idUser) throws ApiValidateException, Exception {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setSize(size);
        pageableRequest.setSort(Sort.by("id").ascending());
        pageableRequest.setPage(0);
        Page<NotificationEntity> notificationEntityPage = notificationEntityRepository.findAllByPostEntityUserEntityPostId(idUser, pageableRequest.getPageable());
        List<NotificationEntity> notificationEntities = notificationEntityPage.getContent();
        List<NotificationHomeResponse> responses = notificationEntities.stream().map(notificationEntity -> modelMapper.map(notificationEntity, NotificationHomeResponse.class)).collect(Collectors.toList());
        NotifiHomePageResponse pageResponse = new NotifiHomePageResponse();
        if (notificationEntityPage.hasContent()) {
            pageResponse.setResults(responses);
            pageResponse.setCurrentPage(notificationEntityPage.getNumber());
            pageResponse.setNoRecordInPage(notificationEntityPage.getSize());
            pageResponse.setTotalPage(notificationEntityPage.getTotalPages());
            pageResponse.setTotalRecords(notificationEntityPage.getTotalElements());
        }
        return pageResponse;
    }

}
