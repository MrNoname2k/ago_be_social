package org.api.services;

import org.api.payload.ResultBean;
import org.api.utils.ApiValidateException;

public interface MessageEntityService {
    public ResultBean createMessage(String json) throws ApiValidateException, Exception;

    public ResultBean getAllMessages(String loggedInUsername, String chatUserId) throws ApiValidateException, Exception;

    public ResultBean getAllFriendMessages(String loggedInUsername) throws ApiValidateException, Exception;
}
