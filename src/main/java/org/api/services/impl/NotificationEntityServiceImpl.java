package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantNotificationType;
import org.api.constants.ConstantRelationshipStatus;
import org.api.entities.NotificationEntity;
import org.api.entities.PostEntity;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.repository.NotificationEntityRepository;
import org.api.repository.PostEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.NotificationEntityService;
import org.api.services.RelationshipEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
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

    @Override
    public NotificationEntity create(String idUser, String idPost, String type) {
        UserEntity userEntity = userEntityRepository.findOneById(idUser).get();
        PostEntity postEntity = postEntityRepository.findOneById(idPost).get();
        NotificationEntity entity = new NotificationEntity();
        entity.setUserEntity(userEntity);
        entity.setPostEntity(postEntity);
        entity.setType(type.toString());
        if(type.equals(ConstantNotificationType.POST_AVATAR))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_001, null, new Object[] { entity.getUserEntity().getFullName(), entity.getPostEntity().getContent() }));
        else if(type.equals(ConstantNotificationType.POST_BANNER))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_002, null, new Object[] { entity.getUserEntity().getFullName(), entity.getPostEntity().getContent() }));
        else if(type.equals(ConstantNotificationType.POST_CREATE))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_003, null, new Object[] { entity.getUserEntity().getFullName(), entity.getPostEntity().getContent() }));
        else if(type.equals(ConstantNotificationType.LIKE))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_004, null, new Object[] { entity.getUserEntity().getFullName(), entity.getPostEntity().getContent() }));
        else if(type.equals(ConstantNotificationType.COMMENT))
            entity.setContent(MessageUtils.getMessage(ConstantMessage.ID_NOTIFICATION_005, null, new Object[] { entity.getUserEntity().getFullName(), entity.getPostEntity().getContent() }));
        NotificationEntity entityOld = notificationEntityRepository.save(entity);
        return entityOld;
    }

    @Override
    public void sendNotification(NotificationEntity notificationEntity) {
        if(notificationEntity.getType().equals(ConstantNotificationType.LIKE) || notificationEntity.getType().equals(ConstantNotificationType.COMMENT)){
            String destination = "/user/" + notificationEntity.getPostEntity().getUserEntityPost().getMail() + "/notifications";
            String message = notificationEntity.getContent();
            messagingTemplate.convertAndSend(destination, message);
        }else {
            List<String> destinations = new ArrayList<>();
            String message = notificationEntity.getContent();
            List<RelationshipEntity> listFriends = relationshipEntityService.findAllByUserEntityOneIdOrUserEntityTowAndStatus(notificationEntity.getUserEntity().getId(),notificationEntity.getUserEntity().getId(), ConstantRelationshipStatus.FRIEND);
            if(!listFriends.isEmpty()){
                for (RelationshipEntity friend: listFriends) {
                    if(!friend.getUserEntityOne().getId().equals(notificationEntity.getUserEntity().getId()))
                        destinations.add(friend.getUserEntityTow().getMail());
                    else if(!friend.getUserEntityTow().getId().equals(notificationEntity.getUserEntity().getId()))
                        destinations.add(friend.getUserEntityOne().getMail());
                }
            }
            for (String str: destinations) {
                messagingTemplate.convertAndSend("/user/" + str + "/notifications", message);
            }
        }
    }

}
