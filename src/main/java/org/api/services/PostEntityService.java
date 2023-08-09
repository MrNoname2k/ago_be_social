package org.api.services;

import org.api.payload.ResultBean;
import org.api.entities.PostEntity;
import org.api.payload.response.homePageResponses.PostHomePageResponse;
import org.api.utils.ApiValidateException;
import org.springframework.web.multipart.MultipartFile;

public interface PostEntityService {

    public ResultBean createPost(String json, MultipartFile[] file) throws ApiValidateException, Exception;

    public ResultBean createAvatar(String json, MultipartFile file) throws ApiValidateException, Exception;

    public ResultBean createBanner(String json, MultipartFile file) throws ApiValidateException, Exception;

    public PostEntity findOneById(String id) throws ApiValidateException, Exception;

    public ResultBean getbyId(String id) throws ApiValidateException, Exception;

    public PostHomePageResponse findAllByUserEntityPostIdInPage(int  size, String idUser) throws ApiValidateException, Exception;

    public ResultBean updateAvatar(String json) throws ApiValidateException, Exception;
    public ResultBean updateBanner(String json) throws ApiValidateException, Exception;

    public ResultBean getLegalPosts() throws ApiValidateException,Exception;

    public ResultBean getIllegalPosts() throws ApiValidateException,Exception;

    public ResultBean softDeletePostById(String json) throws ApiValidateException,Exception;

    public ResultBean getAllPostsSoftDelete() throws ApiValidateException,Exception;

    public ResultBean recoverPostSoftDelete(String json) throws ApiValidateException,Exception;


}
