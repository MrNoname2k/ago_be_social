package org.api.repository;

import org.api.entities.FileEntity;

import java.util.List;
import java.util.Optional;

public interface FileEntityRepository extends BaseRepository<FileEntity, String> {

    public List<FileEntity> findAllByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbum(String idUser, String typeAlbum);

    public Optional<FileEntity> findAllByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbumAndIsCurrenAvatar(String idUser, String typeAlbum, int isCurrenAvatar);

    public Optional<FileEntity> findAllByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbumAndIsCurrenBanner(String idUser, String typeAlbum, int isCurrenBanner);

    public List<FileEntity> findAllByPostEntityId(String idPost);

}
