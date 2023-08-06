package org.api.repository;

import org.api.entities.PostEntity;

import org.api.entities.UserEntity;
import org.api.payload.response.homePageResponses.UserHomeRespon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PostEntityRepository extends BaseRepository<PostEntity, String>  {

    public Optional<PostEntity> findOneById(String id);

    public Page<PostEntity> findAllByUserEntityPostId(String idUser, Pageable pageable);

    @Query("select p FROM PostEntity p WHERE p.userEntityPost.id in ?1 and (p.accessModifierLevel = 'public' or p.accessModifierLevel = 'people')")
    public Page<PostEntity> findAllByUserEntityPostIdIn(List<String> idUser, Pageable pageable);
    @Query("select p from PostEntity p where p.userEntityPost = ?1 and p.typePost = ?2")
    public List<PostEntity> getPostByUserAndType(UserEntity user, String type);

    @Query("select p from PostEntity p where p.userEntityPost.id = ?1 and p.typePost = ?2")
    public List<PostEntity> getPostByUserIdAndType(String userId, String type);

    public long countAllByCreateDateContaining(String date);
    @Query("select count(p) from PostEntity p")
    public long countAllPost();
    @Query("select count(p) from PostEntity  p where day(p.createDate) = ?1 and month(p.createDate) =?2 and year(p.createDate) =?3")
    public long countPostToday(int day, int month, int year);

    @Query("select count(p) from PostEntity  p where month(p.createDate) =?1 and year(p.createDate) =?2")
    public long countPostByMonth(int month, int year);

//    @Query("SELECT new org.api.payload.response.UserResponse.PostResponse(" +
//                "p.id, " +
//                "p.content, " +
//                " p.updateDate, " +
//                "u.id, " +
//                "u.fullName, " +
//                "COUNT(l), " +
//                "COUNT(c)) " +
//            "FROM PostEntity p " +
//            "LEFT JOIN p.comments c " +
//            "LEFT JOIN p.likes l " +
//            "LEFT JOIN p.userEntityPost u " +
//            "WHERE u.id = :idUser AND " +
//                "p.typePost = :typePost AND " +
//                "p.accessModifierLevel = :accessModifierLevel AND " +
//                "p.albumEntityPost.typeAlbum = :typeAlbum AND " +
//                "p.updateDate BETWEEN :startDate AND :endDate " +
//            "GROUP BY p.id " +
//            "ORDER BY p.updateDate ASC")
//    public List<PostResponse> getAllByPropertiesWhereIdUser(@Param("accessModifierLevel") String accessModifierLevel,
//                                                 @Param("typePost") String typePost,
//                                                 @Param("idUser") String idUser,
//                                                 @Param("typeAlbum") String typeAlbum,
//                                                 @Param("startDate") Date startDate,
//                                                 @Param("endDate") Date endDate);

}
