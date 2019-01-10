package com.askdog.model.entity.builder;

import com.askdog.model.entity.inner.PicContent;
import com.askdog.model.entity.inner.TextContent;

import java.util.HashMap;
import java.util.Map;

public final class ContentBuilder {

    public static TextContentBuilder textContentBuilder() {
        return new TextContentBuilder();
    }

    public static PictureContentBuilder pictureContentBuilder() {
        return new PictureContentBuilder();
    }

    public static class TextContentBuilder {

        private String content;

        public TextContent content(String content) {
            this.content = content;
            return build();
        }

        private TextContent build() {
            return new TextContent(this.content);
        }
    }

    public static class PictureContentBuilder {

        private Map<String, String> content = new HashMap<>();

        public PictureContentBuilder addPicture(String key, String description) {
            content.put(key, description);
            return this;
        }

        public PicContent content() {
            return build();
        }

        private PicContent build() {
            PicContent picContent = new PicContent();
            picContent.setContent(this.content);
            return picContent;
        }

    }
}
