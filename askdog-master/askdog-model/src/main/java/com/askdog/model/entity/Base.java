package com.askdog.model.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static com.askdog.model.validation.Group.Delete;
import static com.askdog.model.validation.Group.Edit;

@MappedSuperclass
@DynamicUpdate
public abstract class Base implements Serializable {

    private static final long serialVersionUID = 5422411418045572855L;

    @NotNull(groups = {Edit.class, Delete.class})
    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true, nullable = false, insertable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Base)) return false;
        Base base = (Base) o;
        return Objects.equals(getUuid(), base.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}