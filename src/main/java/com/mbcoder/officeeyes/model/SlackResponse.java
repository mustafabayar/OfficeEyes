package com.mbcoder.officeeyes.model;

import me.ramswaroop.jbot.core.slack.models.Attachment;

import java.util.ArrayList;
import java.util.List;

public class SlackResponse {

    private String text;

    private List<Attachment> attachments;

    public SlackResponse() {
        attachments = new ArrayList<>();
    }

    public SlackResponse(String text) {
        this.text = text;
        attachments = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
