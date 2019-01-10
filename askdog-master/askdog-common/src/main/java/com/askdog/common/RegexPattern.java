package com.askdog.common;

public interface RegexPattern {

    String REGEX_USER_NAME = "^[\\w.]{3,18}$";
    String REGEX_USER_PASSWORD= "^[\\w.]{6,50}$";
    String REGEX_PHONE = "^(1[3578][0-9]{9}|(\\d{3,4}-)\\d{7,8}(-\\d{1,4})?)$";
    String REGEX_MAIL = "^[^@\\s]+@(?:[^@\\s.]+)(?:\\.[^@\\s.]+)+$";
    String REGEX_TEMPLATE_NAME = "^[\\w-]+$";
}
