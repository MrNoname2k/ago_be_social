package org.api.payload.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.api.entities.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class HomePageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;
    private List<RelationshipEntity> relationshipEntities;
    private PostPageResponse postEntityPage;
    private MessagePageResponse messageEntityPage;
    private NotifiPageResponse notificationEntityPage;
}
