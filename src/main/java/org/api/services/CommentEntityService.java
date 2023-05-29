package org.api.services;

import org.api.payload.ResultBean;
import org.api.utils.ApiValidateException;

public interface CommentEntityService {

    public ResultBean createComment(String json) throws ApiValidateException, Exception;

}
