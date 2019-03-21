package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionGroup {

    @JsonProperty("text")
    private String text;

    @JsonProperty("options")
    private List<Option> options;

    public OptionGroup() {
        options = new ArrayList<>();
    }

    public OptionGroup(String text, List<Option> options) {
        this.text = text;
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "OptionGroup{" +
                "text='" + text + '\'' +
                ", options=" + options +
                '}';
    }
}
