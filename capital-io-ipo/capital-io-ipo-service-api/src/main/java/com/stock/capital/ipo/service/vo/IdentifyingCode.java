package com.stock.capital.ipo.service.vo;

import com.stock.capital.common.business.category.IdentifyingCodeType;
import java.io.Serializable;

/**
 * [IdentifyingCode].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class IdentifyingCode implements Serializable {

  private static final long serialVersionUID = 4422677977905018653L;
  private IdentifyingCodeType identifyingCodeType;
  private String code;

  public IdentifyingCodeType getIdentifyingCodeType() {
    return identifyingCodeType;
  }

  public void setIdentifyingCodeType(IdentifyingCodeType identifyingCodeType) {
    this.identifyingCodeType = identifyingCodeType;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
