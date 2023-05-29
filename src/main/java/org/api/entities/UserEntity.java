package org.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t1_user_entity")
@SQLDelete(sql = "UPDATE t1_user_entity SET del_flg = 1 WHERE id=?")
@Where(clause = "del_flg=0")
public class UserEntity extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "org.api.entities.CustomIdGenerator")
    @Column(name = "id", columnDefinition = "varchar(50)")
    @JsonProperty("id")
    private String id;

    @Column(name = "full_name")
    @JsonProperty("fullName")
    private String fullName;

    @Column(name = "phone")
    @JsonProperty("phone")
    private String phone;

    @Column(name = "mail")
    @JsonProperty("mail")
    private String mail;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @Column(name = "birthDay")
    @JsonProperty("birthDay")
    private String birthDay;

    @Column(name = "gender")
    @JsonProperty("gender")
    private String gender;

    @Column(name = "role")
    @JsonProperty("role")
    private String role;

    @Column(name = "password")
    @JsonProperty("password")
    private String password;

    @Column(name = "link_ig")
    @JsonProperty("linkIg")
    private String linkIg;

    @Column(name = "link_youtube")
    @JsonProperty("linkYoutube")
    private String linkYoutube;

    @Column(name = "link_tiktok")
    @JsonProperty("linkTiktok")
    private String linkTiktok;

    @Column(name = "last_login_date")
    @JsonProperty("lastLoginDate")
    private Date lastLoginDate;

    @Column(name = "online")
    @JsonProperty("online")
    private String online;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityOne", cascade = CascadeType.ALL)
    private List<RelationshipEntity> relationshipOne;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityPost", cascade = CascadeType.ALL)
    private List<PostEntity> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityLike", cascade = CascadeType.ALL)
    private List<LikeEntity> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityShare", cascade = CascadeType.ALL)
    private List<ShareEntity> shares;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityComment", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityFrom", cascade = CascadeType.ALL)
    private List<MessageEntity> messageFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntityTo", cascade = CascadeType.ALL)
    private List<MessageEntity> messageTo;
}
