package com.askdog.model.converter;

import com.askdog.model.entity.inner.Content;

public class ContentConverter extends JsonConverter<Content> {

    @Override
    protected Class<Content> type() {
        return Content.class;
    }
}