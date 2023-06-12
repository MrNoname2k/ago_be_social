package org.api.services;

import org.api.entities.NotificationEntity;
import org.api.payload.ResultBean;
import org.api.payload.response.NotifiPageResponse;
import org.api.payload.response.PageResponse;
import org.api.utils.ApiValidateException;

public interface NotificationEntityService {

    public NotificationEntity create(String idUser, String idPost, String type) throws Exception, ApiValidateException;

    public void sendNotification(NotificationEntity notificationEntity) throws ApiValidateException, Exception;

    public ResultBean findAllByPostEntityUserEntityPostId(int size, String idUser) throws ApiValidateException, Exception;
    public NotifiPageResponse findAllByPostEntityUserEntityPostIdPage(int size, String idUser) throws ApiValidateException, Exception;

}
