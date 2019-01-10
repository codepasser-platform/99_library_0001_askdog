package com.askdog.wechat.api.wxclient.model;

import com.askdog.wechat.api.WxObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TemplateNotice implements WxObject {

    private TemplateNotice() {
    }

    @JsonProperty(value = "touser")
    private String toUser;

    @JsonProperty(value = "template_id")
    private String templateId;

    @JsonProperty(value = "topcolor")
    private String topColor;

    @JsonProperty(value = "url")
    private String url;

    @JsonProperty(value = "data")
    private Map<String, DataValue> data;


    private long targetId;

    public long getTargetId() {
        return targetId;
    }

    public TemplateNotice setTargetId(long targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToUser() {
        return toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getTopColor() {
        return topColor;
    }

    public Map<String, DataValue> getData() {
        return data;
    }

    public TemplateNotice setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public TemplateNotice setData(Map<String, DataValue> data) {
        this.data = data;
        return this;
    }

    public static TemplateNotice build(String openId, long targetId, Map<String, DataValue> data) {
        TemplateNotice notice = new TemplateNotice();
        notice.setToUser(openId).setData(data).setTargetId(targetId);
        return notice;
    }

    public static class DataValue {

        private String value;
        private String color;

        public DataValue(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
