package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty("type")
    private String type;

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("ts")
    private String ts;

    @JsonProperty("thread_ts")
    private String threadTs;

    @JsonProperty("replace_original")
    private Boolean replaceOriginal;

    @JsonProperty("delete_original")
    private Boolean deleteOriginal;

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

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getSubType() { return subType; }

    public void setSubType(String subType) { this.subType = subType; }

    public String getTs() { return ts; }

    public void setTs(String ts) { this.ts = ts; }

    public String getThreadTs() { return threadTs; }

    public void setThreadTs(String threadTs) { this.threadTs = threadTs; }

    public Boolean getReplaceOriginal() { return replaceOriginal; }

    public void setReplaceOriginal(Boolean replaceOriginal) { this.replaceOriginal = replaceOriginal; }

    public Boolean getDeleteOriginal() { return deleteOriginal; }

    public void setDeleteOriginal(Boolean deleteOriginal) { this.deleteOriginal = deleteOriginal; }

    public void addAttachment(Attachment attachment) {
        if (attachment != null) {
            attachments.add(attachment);
        }
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
