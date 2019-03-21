package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Confirm {

    @JsonProperty("title")
    private String title;

    @JsonProperty("text")
    private String text;

    @JsonProperty("ok_text")
    private String okText;

    @JsonProperty("dismiss_text")
    private String dismissText;

    public Confirm() {
    }

    public Confirm(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public String getDismissText() {
        return dismissText;
    }

    public void setDismissText(String dismissText) {
        this.dismissText = dismissText;
    }

    @Override
    public String toString() {
        return "Confirm{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", okText='" + okText + '\'' +
                ", dismissText='" + dismissText + '\'' +
                '}';
    }
}
