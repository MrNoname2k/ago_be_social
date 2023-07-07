package org.api.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantColumns;
import org.api.constants.ConstantJsonFileValidate;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.entities.MessageEntity;
import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.payload.request.FriendMessageResponse;
import org.api.payload.request.MessageRequest;
import org.api.payload.response.RelationshipResponse;
import org.api.payload.response.homePageResponses.UserHomeRespon;
import org.api.payload.response.messageResponse.MessageResponse;
import org.api.payload.response.messageResponse.MessagesBetweenTwoUserResponse;
import org.api.repository.MessageEntityRepository;
import org.api.repository.RelationshipEntityRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.AuthenticationService;
import org.api.services.MessageEntityService;
import org.api.services.UserEntityService;
import org.api.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class MessageEntityServiceImpl implements MessageEntityService {

    @Autowired
    private MessageEntityRepository messageEntityRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RelationshipEntityRepository relationshipEntityRepository;
    @Autowired
    private Gson gson;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageResponse createMessage(String json) throws ApiValidateException, Exception {
        MessageRequest messageMapping = new MessageRequest();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_MESSAGE_JSON_VALIDATE, jsonObject, false);
        messageMapping = gson.fromJson(jsonObject, MessageRequest.class);

        UserEntity toUser = userEntityRepository.findOneById(messageMapping.getToUserId()).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00005, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Message"))));

        UserEntity fromUser = userEntityRepository.findOneById(messageMapping.getFromUserId()).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00005, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Message"))));

        RelationshipEntity relationship = relationshipEntityRepository.findRelationshipByUserOneAndUserTwo(fromUser.getId(), toUser.getId());

        MessageEntity message = new MessageEntity();
        message.setContent(messageMapping.getContent());
        message.setRelationship(relationship);
        message.setUserEntityTo(toUser);
        message.setUserEntityFrom(fromUser);

        MessageEntity savedMessage = messageEntityRepository.save(message);

        if(savedMessage != null) {
            return modelMapper.map(savedMessage, MessageResponse.class);
        }

        throw new ApiValidateException(ConstantMessage.ID_ERR00004, MessageUtils.getMessage(ConstantMessage.ID_ERR00004));
    }

    @Override
    public MessagesBetweenTwoUserResponse getAllMessages(String loggedInUsername, String chatUserId) throws ApiValidateException, Exception {
        UserEntity loggedInUser = userEntityRepository.findOneByMail(loggedInUsername).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00005, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Message"))));

        UserEntity chatUser = userEntityRepository.findOneById(chatUserId).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00005, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Message"))));

        List<MessageEntity> allMessageBetweenTwoUsers = messageEntityRepository.findAllMessageBetweenTwoUser(loggedInUser.getId(), chatUser.getId());

        List<MessageResponse> messageResponses = allMessageBetweenTwoUsers.stream()
                .map((messageEntity -> modelMapper.map(messageEntity, MessageResponse.class))).collect(Collectors.toList());

        MessagesBetweenTwoUserResponse messagesBetweenTwoUserResponse = new MessagesBetweenTwoUserResponse();

        messagesBetweenTwoUserResponse.setToUserId(chatUser.getId());
        messagesBetweenTwoUserResponse.setToUserFirstName(chatUser.getFirstName());
        messagesBetweenTwoUserResponse.setToUSerLastName(chatUser.getLastName());
        messagesBetweenTwoUserResponse.setLogin(chatUser.getOnline());
        messagesBetweenTwoUserResponse.setMessages(messageResponses);
        return messagesBetweenTwoUserResponse;
    }

    @Override
    public List<MessageEntity> getAllFriendMessages(String loggedInUsername) throws ApiValidateException, Exception {
        UserEntity loggedInUser = userEntityRepository.findOneByMail(loggedInUsername).orElseThrow(() -> new ApiValidateException(ConstantMessage.ID_ERR00005, ConstantColumns.USER_ID,
                MessageUtils.getMessage(ConstantMessage.ID_ERR00005, null, ItemNameUtils.getItemName(ConstantColumns.USER_ID, "Message"))));

        List<MessageEntity> allMessage = messageEntityRepository.getAllFriendMessages(loggedInUser.getId());

        return allMessage;
    }
}
