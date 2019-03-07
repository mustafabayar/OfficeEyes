package com.mbcoder.officeeyes.model;

import java.time.Instant;

public class Reminder {

    Instant date;

    SlackRequest request;

    boolean sentSuccessfully;

    public Reminder() {
        this.sentSuccessfully = false;
    }

    public Reminder(Instant date, SlackRequest request) {
        this.date = date;
        this.request = request;
        this.sentSuccessfully = false;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public SlackRequest getRequest() {
        return request;
    }

    public void setRequest(SlackRequest request) {
        this.request = request;
    }

    public boolean isSentSuccessfully() {
        return sentSuccessfully;
    }

    public void setSentSuccessfully(boolean sentSuccessfully) {
        this.sentSuccessfully = sentSuccessfully;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Reminder) {
            Reminder toCompare = (Reminder) o;
            if (this.request.getCommand().equals(toCompare.getRequest().getCommand())) {
                if (this.request.getUserId().equals(toCompare.getRequest().getUserId())) {
                    return true;
                } else {
                    if (this.request.getCommand().equals("/pong") && this.request.getChannelId().equals(toCompare.getRequest().getChannelId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + request.getCommand().hashCode();
        result = 31 * result + request.getUserId().hashCode();
        result = 31 * result + request.getChannelId().hashCode();
        return result;
    }
}
