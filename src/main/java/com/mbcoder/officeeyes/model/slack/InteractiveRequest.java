package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InteractiveRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("callback_id")
    private String callbackId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("trigger_id")
    private String triggerId;

    @JsonProperty("response_url")
    private String responseUrl;

    @JsonProperty("team")
    private Team team;

    @JsonProperty("channel")
    private Channel channel;

    @JsonProperty("user")
    private User user;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("actions")
    private List<Action> actions;

    @JsonProperty("action_ts")
    private String actionTs;

    @JsonProperty("message_ts")
    private String messageTs;

    @JsonProperty("attachment_id")
    private String attachmentId;

    @JsonProperty("originalMessage")
    private SlackResponse originalMessage;

    public InteractiveRequest() {
        actions = new ArrayList<>();
    }

    public String getToken() { return token; }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getActionTs() {
        return actionTs;
    }

    public void setActionTs(String actionTs) {
        this.actionTs = actionTs;
    }

    public String getMessageTs() {
        return messageTs;
    }

    public void setMessageTs(String messageTs) {
        this.messageTs = messageTs;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public SlackResponse getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(SlackResponse originalMessage) {
        this.originalMessage = originalMessage;
    }

    @Override
    public String toString() {
        return "InteractiveRequest{" +
                ", callbackId='" + callbackId + '\'' +
                ", type='" + type + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", responseUrl='" + responseUrl + '\'' +
                ", team=" + team +
                ", channel=" + channel +
                ", user=" + user +
                ", message=" + message +
                ", actions=" + actions +
                ", actionTs='" + actionTs + '\'' +
                ", messageTs='" + messageTs + '\'' +
                ", attachmentId='" + attachmentId + '\'' +
                ", originalMessage=" + originalMessage +
                '}';
    }
}
