package org.api.repository;

import org.api.entities.FileEntity;

import java.util.List;

public interface FileEntityRepository extends BaseRepository<FileEntity, String> {

    List<FileEntity> findAllByPostEntityUserEntityPostIdAndAbumEntityFileTypeAbum(String idUser, String typeAbum);

}
