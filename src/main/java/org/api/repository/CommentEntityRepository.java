package org.api.repository;

import org.api.entities.CommentEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentEntityRepository extends BaseRepository<CommentEntity, String> {
    public List<CommentEntity> findByIdComment(String id);
        @Query(
            "select count(l) from CommentEntity l where month(l.createDate) =?1 and year(l.createDate)=?2"
    )
    public long countCommentByMonth(int month, int year);

    @Query(
            "select count(l) from CommentEntity l where day(l.createDate)=?1 and month(l.createDate) =?2 and year(l.createDate)=?3"
    )
    public long countCommentToday(int day,int month, int year);

    @Query(
            "select count(l) from CommentEntity  l"
    )
    public long countAllComment();
}
