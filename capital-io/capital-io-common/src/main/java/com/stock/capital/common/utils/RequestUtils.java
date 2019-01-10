package com.stock.capital.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

  public static String getArguments(HttpServletRequest request) {
    String arguments = null;
    try {
      arguments = Json.writeValueAsString(request.getParameterMap());
    } catch (Exception ex) {
      // watch ignore
    }
    return arguments;
  }
}
