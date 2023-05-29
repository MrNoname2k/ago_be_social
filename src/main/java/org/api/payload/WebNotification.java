package org.api.payload;

import java.io.Serializable;

public class WebNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String body;
    private String token;

    public WebNotification() {
    }

    public WebNotification(String title, String body, String token) {
        this.title = title;
        this.body = body;
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
