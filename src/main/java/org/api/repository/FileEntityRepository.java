package org.api.repository;

import org.api.entities.FileEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileEntityRepository extends BaseRepository<FileEntity, String> {

    @Query("SELECT f FROM FileEntity f WHERE f.postEntity.userEntityPost.id = ?1 and f.postEntity.albumEntityPost.typeAlbum = ?2")
    public List<FileEntity> findAllByUserIdAndAlbumType(String idUser, String typeAlbum);

    @Query("SELECT f FROM FileEntity f WHERE f.postEntity.userEntityPost.id = ?1 and f.postEntity.albumEntityPost.typeAlbum = ?2 and f.isCurrenAvatar = 0")
    public FileEntity findCurrentAvatar(String idUser, String typeAlbum);

    @Query("SELECT f FROM FileEntity f WHERE f.postEntity.userEntityPost.id = ?1 and f.postEntity.albumEntityPost.typeAlbum = ?2 and f.isCurrenBanner = 0")
    public FileEntity findCurrentBanner(String idUser, String typeAlbum);

    @Query("SELECT f FROM FileEntity f WHERE f.postEntity.userEntityPost.id = ?1 and f.postEntity.albumEntityPost.typeAlbum = ?2 and f.isCurrenAvatar = ?3")
    public Optional<FileEntity> findByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbumAndIsCurrenAvatar(
            String idUser, String typeAlbum, int isCurrenAvatar);

    @Query("SELECT f FROM FileEntity f WHERE f.postEntity.userEntityPost.id = ?1 and f.postEntity.albumEntityPost.typeAlbum = ?2 and f.isCurrenBanner = ?3")
    public Optional<FileEntity> findByPostEntityUserEntityPostIdAndAlbumEntityFileTypeAlbumAndIsCurrenBanner(
            String idUser, String typeAlbum, int isCurrenBanner);

    public List<FileEntity> findAllByPostEntityId(String idPost);

    @Query("select count(l) from FileEntity  l")
    public long countAllFile();

    @Query("select count(l) from FileEntity l where month(l.createDate) =?1 and year(l.createDate)=?2")
    public long countFileByMonth(int month, int year);

    @Query("select count(l) from FileEntity l where day(l.createDate)=?1 and month(l.createDate) =?2 and year(l.createDate)=?3")
    public long countFileToday(int day, int month, int year);
}
