package com.stock.capital.ipo.model.entity;

import com.stock.capital.common.model.entity.Base;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * [Dictionary entity].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Entity
@Table(name = "sys_dictionary")
public class Dictionary extends Base {

  private static final long serialVersionUID = -4222340305336692381L;

  @NotNull
  @Column(name = "dict_name", unique = true, length = 20)
  private String dictName;

  @NotNull
  @Column(name = "dictValue", length = 20)
  private String dictValue;

  public String getDictName() {
    return dictName;
  }

  public void setDictName(String dictName) {
    this.dictName = dictName;
  }

  public String getDictValue() {
    return dictValue;
  }

  public void setDictValue(String dictValue) {
    this.dictValue = dictValue;
  }
}
