package org.api.repository;

import org.api.entities.ShareEntity;
import org.springframework.data.jpa.repository.Query;

public interface ShareEntityRepository extends BaseRepository<ShareEntity,String>{
    @Query(
            "select count(l) from ShareEntity l where month(l.createDate) =?1 and year(l.createDate)=?2"
    )
    public long countShareByMonth(int month, int year);

    @Query(
            "select count(l) from ShareEntity l where day(l.createDate)=?1 and month(l.createDate) =?2 and year(l.createDate)=?3"
    )
    public long countShareToday(int day,int month, int year);

    @Query(
            "select count(l) from ShareEntity  l"
    )
    public long countAllShare();
}
