package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.entities.AlbumEntity;
import org.api.repository.AlbumEntityRepository;
import org.api.services.AlbumEntityService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class AlbumEntityServiceImpl implements AlbumEntityService {

    public static final String ALIAS = "Album";

    @Autowired
    private AlbumEntityRepository albumEntityRepository;

    @Override
    public AlbumEntity createAlbumDefault(String type){
        AlbumEntity entity = new AlbumEntity();
        entity.setName(type);
        entity.setTypeAlbum(type);
        return albumEntityRepository.save(entity);
    }

    @Override
    public Boolean existsByTypeAlbum(String tpeAlbum) {
        if(Boolean.TRUE.equals(albumEntityRepository.existsByTypeAlbum(tpeAlbum))){
            return true;
        }
        return false;
    }

    @Override
    public AlbumEntity findOneById(String id) {
        AlbumEntity entity = albumEntityRepository.findById(id).get();
        return entity;
    }

    @Override
    public Optional<AlbumEntity> findOneByTypeAlbum(String tpeAlbum) {
        Optional<AlbumEntity> entity = albumEntityRepository.findOneByTypeAlbum(tpeAlbum);
        return entity;
    }
}
