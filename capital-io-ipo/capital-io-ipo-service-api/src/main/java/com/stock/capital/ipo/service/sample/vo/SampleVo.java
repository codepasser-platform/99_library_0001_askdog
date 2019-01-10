package com.stock.capital.ipo.service.sample.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * [SampleVo].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class SampleVo implements Serializable {

  private static final long serialVersionUID = -7018516609709502753L;

  @JsonFormat(shape = STRING)
  private long id;

  private String name;

  private String postCode;

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
