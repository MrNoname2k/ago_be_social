package org.api.repository;

import org.api.entities.PostEntity;

import org.api.entities.UserEntity;
import org.api.payload.response.homePageResponses.UserHomeRespon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

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
    @Query(value = "CALL GET_LEGAL_POST(:rule)",nativeQuery = true)
    public List<PostEntity> getLegalPosts(@Param("rule") String rule);
    @Query(value = "CALL GET_ILLEGAL_POST(:rule)",nativeQuery = true)
    public List<PostEntity> getIllegalPosts(@Param("rule") String rule);

    @Query("update PostEntity p set p.delFlg = 1 where p.id =?1")
    @Modifying
    public void softDeletePost(String id);

    @Query("update PostEntity p set p.delFlg = 0 where p.id =?1")
    @Modifying
    public void recoverPost(String id);
    @Query(value = "select * from t1_post_entity where del_flg=1",nativeQuery = true)
    public List<PostEntity> getAllPostDeleted();

}
