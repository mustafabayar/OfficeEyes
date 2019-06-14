package com.mbcoder.officeeyes.model.slack;

public class SlackRequest {

    private String teamId;
    private String teamDomain;
    private String channelId;
    private String channelName;
    private String userId;
    private String userName;
    private String command;
    private String text;
    private String responseUrl;

    public SlackRequest(String teamId, String teamDomain, String channelId, String channelName, String userId, String userName, String command, String text, String responseUrl) {
        this.teamId = teamId;
        this.teamDomain = teamDomain;
        this.channelId = channelId;
        this.channelName = channelName;
        this.userId = userId;
        this.userName = userName;
        this.command = command;
        this.text = text;
        this.responseUrl = responseUrl;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamDomain() {
        return teamDomain;
    }

    public void setTeamDomain(String teamDomain) {
        this.teamDomain = teamDomain;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getResponseType() {
        if (command.equals("/pong") || command.equals("/ping") || command.equals("/kicker")) {
            return "in_channel";
        } else if (command.equals("/pongme")) {
            return "ephemeral";
        }
        return "ephemeral";
    }

    @Override
    public String toString() {
        return "SlackRequest{" +
                "teamId='" + teamId + '\'' +
                ", teamDomain='" + teamDomain + '\'' +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", command='" + command + '\'' +
                ", text='" + text + '\'' +
                ", responseUrl='" + responseUrl + '\'' +
                '}';
    }
}
