package com.askdog.model.entity.inner;

import com.askdog.common.utils.MarkdownEscaper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

import static com.google.common.base.Joiner.on;

public class PicContent extends Content {

    private static final long serialVersionUID = 3253586811409604059L;

    @NotNull
    @Size(min = 1, max = 5)
    private Map<String, String> content;

    public PicContent() {
        setType(Type.PIC);
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    @Override
    public String textContent() {
        return MarkdownEscaper.remove(on(' ').skipNulls().join(content.values()));
    }
}
