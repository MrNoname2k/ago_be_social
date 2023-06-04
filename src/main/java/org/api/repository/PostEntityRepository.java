package org.api.repository;

import org.api.entities.PostEntity;
import org.api.payload.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface PostEntityRepository extends BaseRepository<PostEntity, String>  {

    public Page<PostEntity> findAllByUserEntityPostId(String idUser, Pageable pageable);

    public Page<PostEntity> findAllByUserEntityPostIdIn(List<String> idUser, Pageable pageable);

//    @Query("SELECT new org.api.payload.response.PostResponse(" +
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
