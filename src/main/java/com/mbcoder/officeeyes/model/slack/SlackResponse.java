package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackResponse {
    @JsonProperty("username")
    private String username;

    @JsonProperty("icon_emoji")
    private String iconEmoji;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("text")
    private String text;

    @JsonProperty("response_type")
    private String responseType;

    @JsonProperty("attachments")
    private List<Attachment> attachments;

    public SlackResponse() {
        attachments = new ArrayList<>();
    }

    public SlackResponse(String text) {
        this.text = text;
        attachments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "SlackResponse{" +
                "username='" + username + '\'' +
                ", iconEmoji='" + iconEmoji + '\'' +
                ", channel='" + channel + '\'' +
                ", text='" + text + '\'' +
                ", responseType='" + responseType + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
