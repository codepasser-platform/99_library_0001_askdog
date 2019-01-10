package com.askdog.store.web.api;

import java.nio.charset.Charset;
import java.util.Map;

public class MediaType extends org.springframework.http.MediaType {

    public final static org.springframework.http.MediaType APPLICATION_JSON_UTF8;
    public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    private static final long serialVersionUID = 8852684764300859077L;

    static {
        APPLICATION_JSON_UTF8 = valueOf(APPLICATION_JSON_UTF8_VALUE);
    }

    public MediaType(String type) {
        super(type);
    }

    public MediaType(String type, String subtype) {
        super(type, subtype);
    }

    public MediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public MediaType(String type, String subtype, double qualityValue) {
        super(type, subtype, qualityValue);
    }

    public MediaType(org.springframework.http.MediaType other, Map<String, String> parameters) {
        super(other, parameters);
    }

    public MediaType(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }
}