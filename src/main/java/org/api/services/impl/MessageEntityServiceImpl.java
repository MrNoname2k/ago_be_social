package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.repository.MessageEntityRepository;
import org.api.services.MessageEntityService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class MessageEntityServiceImpl implements MessageEntityService {

    @Autowired
    private MessageEntityRepository messageEntityRepository;

    @Override
    public ResultBean createMessage(String json) throws ApiValidateException, Exception {
        return null;
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
