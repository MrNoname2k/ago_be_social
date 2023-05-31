package org.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class PostResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("postFiles")
    private List<String> postFiles;

    @JsonProperty("updateDate")
    private Date updateDate;

    @JsonProperty("idUser")
    private String idUser;

    @JsonProperty("avatarFile")
    private String avatarFile;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("totalLike")
    private Long totalLike;

    @JsonProperty("totalComment")
    private Long totalComment;

    public PostResponse(String id, String content, Date updateDate, String idUser, String fullName, Long totalLike, Long totalComment) {
        this.id = id;
        this.content = content;
        this.updateDate = updateDate;
        this.idUser = idUser;
        this.fullName = fullName;
        this.totalLike = totalLike;
        this.totalComment = totalComment;
    }
}
