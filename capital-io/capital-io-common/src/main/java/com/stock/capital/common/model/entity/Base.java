package com.stock.capital.common.model.entity;

import static com.stock.capital.common.model.entity.inner.State.OK;
import static javax.persistence.EnumType.STRING;

import com.stock.capital.common.model.entity.inner.State;
import com.stock.capital.common.utils.IdGenerator;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

/**
 * [Base Entity].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@MappedSuperclass
@DynamicUpdate
public abstract class Base implements Serializable {

  private static final long serialVersionUID = -3342549882205845275L;

  @Id
  @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
  private Long id = IdGenerator.next();

  @Column(name = "creation_time", nullable = false, updatable = false)
  private Date creationTime;

  @Column(name = "update_time", nullable = false)
  private Date updateTime;

  @NotNull
  @Enumerated(STRING)
  @Column(name = "state", nullable = false, length = 20)
  private State state = OK;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Base)) {
      return false;
    }
    Base base = (Base) o;
    return Objects.equals(getId(), base.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
