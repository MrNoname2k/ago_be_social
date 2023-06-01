package org.api.repository;

import org.api.entities.AlbumEntity;

import java.util.Optional;

public interface AlbumEntityRepository extends BaseRepository<AlbumEntity, String> {

    public Boolean existsByTypeAlbum(String tpeAlbum);

    public Optional<AlbumEntity> findOneByTypeAlbum(String tpeAlbum);

}
