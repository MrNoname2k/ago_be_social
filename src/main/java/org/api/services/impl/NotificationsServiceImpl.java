package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.utils.ApiValidateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class NotificationsServiceImpl {
}
