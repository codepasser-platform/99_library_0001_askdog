package com.stock.capital.common.service.response;

/**
 * [AssertResponse].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class AssertResponse implements BaseResponse {

  private boolean success;

  public AssertResponse() {}

  public AssertResponse(boolean success) {
    this.success = success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public boolean isSuccess() {
    return this.success;
  }
}
