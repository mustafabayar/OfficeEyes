package com.mbcoder.officeeyes.model;

import java.time.Instant;

public class Reminder {

    Instant date;

    SlackRequest request;

    public Reminder() {
    }

    public Reminder(Instant date, SlackRequest request) {
        this.date = date;
        this.request = request;
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
}
