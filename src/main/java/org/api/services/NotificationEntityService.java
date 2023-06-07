package org.api.services;

import org.api.entities.NotificationEntity;
import org.api.payload.ResultBean;
import org.api.utils.ApiValidateException;

public interface NotificationEntityService {

    public NotificationEntity create(String idUser, String idPost, String type);

    public void sendNotification(NotificationEntity notificationEntity);

    public ResultBean findAllByPostEntityUserEntityPostId(int size, String idUser) throws ApiValidateException, Exception;

}
