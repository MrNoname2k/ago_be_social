package org.api.services;

import org.api.entities.AlbumEntity;

import java.util.Optional;

public interface AlbumEntityService {

    public AlbumEntity createAlbumDefault(String type);

    public Boolean existsByTypeAlbum(String tpeAlbum);

    public AlbumEntity findOneById(String id);

    public Optional<AlbumEntity> findOneByTypeAlbum(String tpeAlbum);


}
