package org.api.payload.response.UserResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.api.payload.response.CommonResponse;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends CommonResponse {

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("address")
    private String address;

    @JsonProperty("birthDay")
    private String birthDay;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("linkIg")
    private String linkIg;

    @JsonProperty("linkYoutube")
    private String linkYoutube;

    @JsonProperty("linkTiktok")
    private String linkTiktok;

    @JsonProperty("lastLoginDate")
    private Date lastLoginDate;

    @JsonProperty("online")
    private Boolean online;

    @JsonProperty("posts")
    private List<PostResponse> posts;
}
