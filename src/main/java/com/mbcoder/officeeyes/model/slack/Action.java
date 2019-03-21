package com.mbcoder.officeeyes.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {

    @JsonProperty("name")
    private String name;

    @JsonProperty("text")
    private String text;

    @JsonProperty("style")
    private String style;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    @JsonProperty("confirm")
    private Confirm confirm;

    @JsonProperty("options")
    private List<Option> options;

    @JsonProperty("option_groups")
    private List<OptionGroup> optionGroups;

    @JsonProperty("data_source")
    private String dataSource;

    @JsonProperty("selected_options")
    private List<Option> selectedOptions;

    @JsonProperty("min_query_length")
    private int minQueryLength;

    public Action() {
        options = new ArrayList<>();
        optionGroups = new ArrayList<>();
        selectedOptions = new ArrayList<>();
    }

    public Action(String name, String text, String style, String type, String value) {
        this.name = name;
        this.text = text;
        this.style = style;
        this.type = type;
        this.value = value;
        options = new ArrayList<>();
        optionGroups = new ArrayList<>();
        selectedOptions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Confirm getConfirm() {
        return confirm;
    }

    public void setConfirm(Confirm confirm) {
        this.confirm = confirm;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<OptionGroup> getOptionGroups() {
        return optionGroups;
    }

    public void setOptionGroups(List<OptionGroup> optionGroups) {
        this.optionGroups = optionGroups;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<Option> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<Option> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public int getMinQueryLength() {
        return minQueryLength;
    }

    public void setMinQueryLength(int minQueryLength) {
        this.minQueryLength = minQueryLength;
    }

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", style='" + style + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", confirm=" + confirm +
                ", options=" + options +
                ", optionGroups=" + optionGroups +
                ", dataSource='" + dataSource + '\'' +
                ", selectedOptions=" + selectedOptions +
                ", minQueryLength=" + minQueryLength +
                '}';
    }
}
