package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Category;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Date;

public final class CategoryBuilder {

    private String name;

    private String summary;

    private int level;

    private boolean display;

    private int displayOrder;

    private Category parent;

    public CategoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public CategoryBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    public CategoryBuilder setDisplay(boolean display) {
        this.display = display;
        return this;
    }

    public CategoryBuilder setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public CategoryBuilder setParent(@Nullable String parentId) {
        Category category = new Category();
        category.setUuid(parentId);
        this.parent = category;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setName(this.name);
        category.setSummary(this.summary);
        category.setLevel(this.level);
        category.setDisplay(this.display);
        category.setDisplayOrder(this.displayOrder);
        category.setParent(this.parent);
        category.setCreationTime(new Date());
        return category;
    }

    public static CategoryBuilder categoryBuilder() {
        return new CategoryBuilder();
    }
}
