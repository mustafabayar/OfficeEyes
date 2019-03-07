package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    @JsonProperty("title")
    private String title;

    @JsonProperty("value")
    private String value;

    @JsonProperty("short")
    private boolean shortField;

    public Field() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isShort() {
        return this.shortField;
    }

    public void setShort(boolean shortField) {
        this.shortField = shortField;
    }
}