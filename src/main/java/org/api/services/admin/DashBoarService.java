package org.api.services.admin;

import org.api.payload.ResultBean;
import org.api.utils.ApiValidateException;

public interface DashBoarService {
    public ResultBean totalReport() throws ApiValidateException, Exception;
}
