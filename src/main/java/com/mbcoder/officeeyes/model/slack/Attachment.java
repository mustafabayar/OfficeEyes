package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {
    @JsonProperty("fallback")
    private String fallback;

    @JsonProperty("color")
    private String color;

    @JsonProperty("pretext")
    private String pretext;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("author_link")
    private String authorLink;

    @JsonProperty("author_icon")
    private String authorIcon;

    @JsonProperty("title")
    private String title;

    @JsonProperty("title_link")
    private String titleLink;

    @JsonProperty("text")
    private String text;

    @JsonProperty("attachment_type")
    private String attachmentType;

    @JsonProperty("callback_id")
    private String callbackId;

    @JsonProperty("fields")
    private List<Field> fields;

    @JsonProperty("actions")
    private List<Action> actions;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("thumb_url")
    private String thumbUrl;

    @JsonProperty("footer")
    private String footer;

    @JsonProperty("footer_icon")
    private String footerIcon;

    @JsonProperty("ts")
    private String ts;

    @JsonProperty("mrkdwn_in")
    private List<String> markdownIn;

    public Attachment() {
        fields = new ArrayList<>();
        actions = new ArrayList<>();
        markdownIn = new ArrayList<>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public String getFallback() {
        return this.fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPretext() {
        return this.pretext;
    }

    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLink() {
        return this.authorLink;
    }

    public void setAuthorLink(String authorLink) {
        this.authorLink = authorLink;
    }

    public String getAuthorIcon() {
        return this.authorIcon;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleLink() {
        return this.titleLink;
    }

    public void setTitleLink(String titleLink) {
        this.titleLink = titleLink;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAttachmentType() { return attachmentType; }

    public void setAttachmentType(String attachmentType) { this.attachmentType = attachmentType; }

    public String getCallbackId() { return callbackId; }

    public void setCallbackId(String callbackId) { this.callbackId = callbackId; }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Action> getActions() { return actions; }

    public void setActions(List<Action> actions) { this.actions = actions; }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getFooter() {
        return this.footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getFooterIcon() {
        return this.footerIcon;
    }

    public void setFooterIcon(String footerIcon) {
        this.footerIcon = footerIcon;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List<String> getMarkdownIn() {
        return markdownIn;
    }

    public void setMarkdownIn(List<String> markdownIn) {
        this.markdownIn = markdownIn;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fallback='" + fallback + '\'' +
                ", color='" + color + '\'' +
                ", pretext='" + pretext + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorLink='" + authorLink + '\'' +
                ", authorIcon='" + authorIcon + '\'' +
                ", title='" + title + '\'' +
                ", titleLink='" + titleLink + '\'' +
                ", text='" + text + '\'' +
                ", attachmentType='" + attachmentType + '\'' +
                ", callbackId='" + callbackId + '\'' +
                ", fields=" + fields +
                ", actions=" + actions +
                ", imageUrl='" + imageUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", footer='" + footer + '\'' +
                ", footerIcon='" + footerIcon + '\'' +
                ", ts='" + ts + '\'' +
                ", markdownIn=" + markdownIn +
                '}';
    }
}
