package org.api.services;

import org.api.payload.ResultBean;
import org.api.entities.PostEntity;
import org.api.utils.ApiValidateException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

public interface PostEntityService {

    public ResultBean createPost(String json, MultipartFile[] file) throws ApiValidateException, Exception;

    public ResultBean createAvatar(String json, MultipartFile file) throws ApiValidateException, Exception;

    public ResultBean createBanner(String json, MultipartFile file) throws ApiValidateException, Exception;

    public PostEntity findOneById(String id) throws ApiValidateException, Exception;

    //public ResultBean getAllByPropertiesWhereIdUser(String accessModifierLevel, String typePost, String idUser, String typeAlbum, Date startDate, Date endDate) throws ApiValidateException, Exception;

    public ResultBean findAllByUserEntityPostId(int  size, String idUser) throws ApiValidateException, Exception;

    public ResultBean findAllByUserEntityPostIdIn(int  size, String idUser) throws ApiValidateException, Exception;

}
