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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "t1_user_entity")
@SQLDelete(sql = "UPDATE t1_user_entity SET del_flg = 1 WHERE id=?")
@Where(clause = "del_flg=0")
public class UserEntity extends CommonEntity implements Serializable, UserDetails {

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

    @Column(name = "username")
    @JsonProperty("username")
    private String username;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> authorities;

    @Column(name = "is_account_non_expired", nullable = false , columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAccountNonExpired;
    @Column(name = "is_account_non_locked", nullable = false , columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAccountNonLocked;
    @Column(name = "is_credentials_non_expired", nullable = false , columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isCredentialsNonExpired;
    @Column(name = "is_enabled", nullable = false , columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isEnabled;

    public UserEntity() {
        this.authorities = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkIg() {
        return linkIg;
    }

    public void setLinkIg(String linkIg) {
        this.linkIg = linkIg;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getLinkTiktok() {
        return linkTiktok;
    }

    public void setLinkTiktok(String linkTiktok) {
        this.linkTiktok = linkTiktok;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public List<RelationshipEntity> getRelationshipOne() {
        return relationshipOne;
    }

    public void setRelationshipOne(List<RelationshipEntity> relationshipOne) {
        this.relationshipOne = relationshipOne;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public List<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeEntity> likes) {
        this.likes = likes;
    }

    public List<ShareEntity> getShares() {
        return shares;
    }

    public void setShares(List<ShareEntity> shares) {
        this.shares = shares;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<MessageEntity> getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(List<MessageEntity> messageFrom) {
        this.messageFrom = messageFrom;
    }

    public List<MessageEntity> getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(List<MessageEntity> messageTo) {
        this.messageTo = messageTo;
    }

    @Override
    public Set<UserRole> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
