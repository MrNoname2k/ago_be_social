package org.api.services;

import org.api.payload.ResultBean;
import org.api.entities.UserEntity;
import org.api.utils.ApiValidateException;
import org.springframework.web.multipart.MultipartFile;

public interface UserEntityService {

    public ResultBean createUser(String json) throws ApiValidateException, Exception;

    public ResultBean updateUser(String json) throws ApiValidateException, Exception;

    public ResultBean getById(String id) throws ApiValidateException, Exception;

    public ResultBean getByMail(String json) throws ApiValidateException, Exception;

    public ResultBean getAll() throws ApiValidateException, Exception;

    public UserEntity updateLastLogin(String mail) throws ApiValidateException, Exception;

    public ResultBean updateAvatar(MultipartFile file, String id) throws ApiValidateException, Exception;

    public ResultBean updateBanner(MultipartFile file, String id) throws ApiValidateException, Exception;

    public UserEntity findOneByMail(String mail) throws ApiValidateException, Exception;

    public UserEntity updateOnline(String mail, boolean online) throws ApiValidateException, Exception;
}
