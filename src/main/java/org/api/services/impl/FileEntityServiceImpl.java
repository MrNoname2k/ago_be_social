package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.entities.AbumEntity;
import org.api.entities.FileEntity;
import org.api.entities.PostEntity;
import org.api.repository.FileEntityRepository;
import org.api.services.FileEntityService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class FileEntityServiceImpl implements FileEntityService {

    public static final String ALIAS = "File";

    @Autowired
    private FileEntityRepository fileEntityRepository;


    @Override
    public FileEntity createFile(AbumEntity abum, PostEntity post, String fileName) {
        FileEntity entity = new FileEntity();
        entity.setAbumEntityFile(abum);
        entity.setFileName(fileName);
        entity.setPostEntity(post);
        return fileEntityRepository.save(entity);
    }

    @Override
    public List<FileEntity> findAllAvatarOrBannerByUser(String idUser, String typeAbum) {
        List<FileEntity> list = fileEntityRepository.findAllByPostEntityUserEntityPostIdAndAbumEntityFileTypeAbum(idUser, typeAbum);
        return list;
    }
}
