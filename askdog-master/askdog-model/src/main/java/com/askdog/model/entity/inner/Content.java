package com.askdog.model.entity.inner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(
        use = NAME,
        include = EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextContent.class, name = "TEXT"),
        @JsonSubTypes.Type(value = PicContent.class, name = "PIC")
})
public abstract class Content<T> implements Serializable {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public abstract T getContent();

    public abstract String textContent();

}
