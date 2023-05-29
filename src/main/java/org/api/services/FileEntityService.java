package org.api.services;

import org.api.entities.AbumEntity;
import org.api.entities.FileEntity;
import org.api.entities.PostEntity;

import java.util.List;

public interface FileEntityService {

    public FileEntity createFile(AbumEntity abum, PostEntity post, String fileName);

    public List<FileEntity> findAllAvatarOrBannerByUser(String idUser, String typeAbum);

}
