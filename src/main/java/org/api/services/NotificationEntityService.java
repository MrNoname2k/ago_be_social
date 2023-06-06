package org.api.services;

import org.api.constants.ConstantNotificationType;
import org.api.entities.NotificationEntity;

public interface NotificationEntityService {

    public NotificationEntity create(String idUser, String idPost, String type);

    public void sendNotification(NotificationEntity notificationEntity);

}
