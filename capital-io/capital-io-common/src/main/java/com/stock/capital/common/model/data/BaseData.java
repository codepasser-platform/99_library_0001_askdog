package com.stock.capital.common.model.data;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

/**
 * [BaseData Document for MongoDB].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public abstract class BaseData implements Serializable {
  private static final long serialVersionUID = 5050667946461088947L;

  @Id private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
