package org.api.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantJsonFileValidate;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.entities.MessageEntity;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.repository.MessageEntityRepository;
import org.api.services.MessageEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.ValidateData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class MessageEntityServiceImpl implements MessageEntityService {

    @Autowired
    private MessageEntityRepository messageEntityRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private Gson gson;

    @Override
    public ResultBean createMessage(String json) throws ApiValidateException, Exception {
        MessageEntity entity = new MessageEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_MESSAGE_JSON_VALIDATE, jsonObject, false);
        entity = gson.fromJson(jsonObject, MessageEntity.class);
        MessageEntity messageOld = messageEntityRepository.save(entity);
        simpMessagingTemplate.convertAndSendToUser(messageOld.getUserEntityTo().getId(),"/private-message",messageOld);
        return new ResultBean(messageOld, ConstantStatus.STATUS_201, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean getAllMessages(String loggedInUsername, String chatUserId) throws ApiValidateException, Exception {
        return null;
    }

    @Override
    public ResultBean getAllFriendMessages(String loggedInUsername) throws ApiValidateException, Exception {
        return null;
    }
}
