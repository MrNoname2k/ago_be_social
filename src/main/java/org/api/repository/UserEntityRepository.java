package org.api.repository;

import org.api.entities.RelationshipEntity;
import org.api.entities.UserEntity;
import org.api.payload.response.admin.ReportTotalUser;
import org.api.payload.response.admin.ReportUserAccessByHour;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends BaseRepository<UserEntity, String> {

    public Optional<UserEntity> findOneById(String id);

    public Optional<UserEntity> findOneByMail(String mail);

    public Boolean existsByMail(String mail);

    public List<UserEntity> findAll();

    @Query("SELECT u FROM UserEntity u WHERE u.mail = ?1 AND u.id <> ?2")
    public Optional<UserEntity> existsByMailAndId(String mail, String id);

    @Query("SELECT DISTINCT u FROM UserEntity u " +
            "JOIN RelationshipEntity r1 ON u.id = r1.userEntityOne.id " +
            "JOIN RelationshipEntity r2 ON u.id = r2.userEntityTow.id " +
            "WHERE r1.userEntityOne.id = ?1 OR r2.userEntityTow.id = ?1")
    public List<UserEntity> recommendFriends(String idUser);
        @Query("SELECT new org.api.payload.response.admin.ReportTotalUser(MONTH(u.createDate),COUNT(u))\n"+
            "FROM UserEntity u \n"+
            "WHERE YEAR(u.createDate) = ?1 \n"+
            "GROUP BY MONTH(u.createDate)"
    )
    public List<ReportTotalUser> totalUsers (int year);

    @Query(
            "SELECT new org.api.payload.response.admin.ReportUserAccessByHour(HOUR(u.lastLoginDate),COUNT(u))\n"+
                    "FROM UserEntity u \n"+
                    "WHERE DAY(u.lastLoginDate) =?1 AND MONTH(u.lastLoginDate) =?2 AND YEAR(u.lastLoginDate) = ?3 \n"+
                    "GROUP BY HOUR(u.lastLoginDate)"
    )
    public List<ReportUserAccessByHour> totalEachHour(int day,int month,int year);

    @Query("select count(p) from UserEntity p")
    public long countAllPost();
    @Query("select count(p) from UserEntity  p where day(p.createDate) = ?1 and month(p.createDate) =?2 and year(p.createDate) =?3")
    public long countUserToday(int day, int month, int year);

    @Query("select count(p) from UserEntity  p where month(p.createDate) =?1 and year(p.createDate) =?2")
    public long countUserByMonth(int month, int year);
    @Query("update UserEntity u set u.delFlg = 1 where u.id =?1")
    @Modifying
    public void softDeleteUser(String id);

}
