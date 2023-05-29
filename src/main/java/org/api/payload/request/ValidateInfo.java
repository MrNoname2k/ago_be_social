package org.api.payload.request;

public class ValidateInfo {

    private String id;

    private String type = null;
    //0: all, 1: add, 2: update
    private String required = null;

    private int minLength = -1;

    private int maxLength = 0;

    private String number = null;

    private boolean isKanji = false;

    private boolean isKana = false;

    private boolean isEmail = false;

    private boolean isPhoneNumber = false;

    private boolean isDate = false;

    private boolean isDateTime = false;

    private String min = null;

    private String minEqual = null;

    private String max = null;

    private String maxEqual = null;

    private String pattern = null;

    private String range = null;

    private String decimal = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isKanji() {
        return isKanji;
    }

    public boolean isKana() {
        return isKana;
    }

    public boolean isEmail() {
        return isEmail;
    }

    public String getMin() {
        return min;
    }

    public String getMinEqual() {
        return minEqual;
    }

    public String getMax() {
        return max;
    }

    public String getMaxEqual() {
        return maxEqual;
    }

    public String getPattern() {
        return pattern;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setKanji(boolean isKanji) {
        this.isKanji = isKanji;
    }

    public void setKana(boolean isKana) {
        this.isKana = isKana;
    }

    public void setEmail(boolean isEmail) {
        this.isEmail = isEmail;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMinEqual(String minEqual) {
        this.minEqual = minEqual;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setMaxEqual(String maxEqual) {
        this.maxEqual = maxEqual;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public boolean isPhoneNumber() {
        return isPhoneNumber;
    }

    public void setPhoneNumber(boolean isPhoneNumber) {
        this.isPhoneNumber = isPhoneNumber;
    }

    public boolean isDateTime() {
        return isDateTime;
    }

    public void setDateTime(boolean isDateTime) {
        this.isDateTime = isDateTime;
    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean isDate) {
        this.isDate = isDate;
    }

    public String getDecimal() {
        return decimal;
    }

    public void String(String decimal) {
        this.decimal = decimal;
    }
}
