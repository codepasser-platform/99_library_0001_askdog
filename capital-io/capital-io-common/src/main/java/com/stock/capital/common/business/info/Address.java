package com.stock.capital.common.business.info;

import java.io.Serializable;

/**
 * [Address].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class Address implements Serializable {
  private String province;
  private String city;

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
