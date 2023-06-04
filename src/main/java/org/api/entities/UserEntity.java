package org.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
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

    @Column(name = "online", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @JsonProperty("online")
    private Boolean online;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="t1_users_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<UserRoleEntity> authorities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="t1_users_relationships",
            joinColumns = @JoinColumn(name = "id_user_entity_one", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_user_entity_tow", referencedColumnName = "id"))
    private Set<RelationshipEntity> relationships;

    @Override
    public String toString() {
        return getMail();
    }
}
