package com.askdog.store.model.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_category")
public class Category extends Base {

    private static final long serialVersionUID = 7447092563072249318L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "display", nullable = false)
    private boolean display;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    @JoinColumn(name = "parent", nullable = true, updatable = false)
    @ManyToOne(optional = true, fetch = LAZY)
    private Category parent;

    @OrderBy(value = "dsplayOrder ASC")
    @OneToMany(mappedBy = "parent", fetch = LAZY, cascade = REMOVE)
    private List<Category> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

}
