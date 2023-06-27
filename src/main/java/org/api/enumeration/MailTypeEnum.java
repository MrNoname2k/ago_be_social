package org.api.enumeration;

public enum MailTypeEnum {
    FORGOT("Forgot password");

    MailTypeEnum(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    private String text;
}
