package org.api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.api.entities.UserEntity;

import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WebNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;
    private String title;
    private String content;
}
